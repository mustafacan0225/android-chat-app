package com.mustafacan.feature.users.ui.allusers

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.mustafacan.core.model.users.UserSearchRequest
import com.mustafacan.core.model.users.User
import com.mustafacan.core.domain.usecase.api.GetAllUsersPagingDataUseCase
import com.mustafacan.core.domain.usecase.api.GetSearchedUsersPagingDataUseCase
import com.mustafacan.core.domain.usecase.datastore.GetLocalUserUseCase
import com.mustafacan.core.ui.component.scaffold.RootScaffoldController
import com.mustafacan.core.ui.component.scaffold.ScaffoldEvent
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AllUsersViewModel @Inject constructor(@ApplicationContext private val context: Context,
                                            private val getAllUsersPagingDataUseCase: GetAllUsersPagingDataUseCase,
                                            private val getSearchedUsersPagingDataUseCase: GetSearchedUsersPagingDataUseCase,
                                            private val getLocalUserUseCase: GetLocalUserUseCase
                                            ) :
    BaseViewModel<AllUsersUiState, AllUsersUiEvent, AllUsersUiEffect>(initialState = AllUsersUiState()) {

    val allUsersPagingDataFlow: Flow<PagingData<User>> =
        getAllUsersPagingDataUseCase().cachedIn(viewModelScope)

    private val _searchedUsersPagingDataFlow = MutableStateFlow<Flow<PagingData<User>>?>(null)
    val searchedUsersPagingDataFlow: StateFlow<Flow<PagingData<User>>?> = _searchedUsersPagingDataFlow

    init {
        getUserInfo()
        viewModelScope.launch {
            RootScaffoldController.emit(ScaffoldEvent.SetBottomBarVisibility(false))
        }
    }

    override fun handleEvent(event: AllUsersUiEvent) {
        when (event) {
            AllUsersUiEvent.DismissDialog -> {
                setState { copy(dialogModel = null) }
            }

            is AllUsersUiEvent.ShowDialog -> {
                setState { copy(dialogModel = event.dialogModel) }
            }

            is AllUsersUiEvent.Search -> {
                search()
            }

            is AllUsersUiEvent.SearchedTextChanged -> {
                setState { copy(searchedText = event.searchedText) }

                if (searchedUsersPagingDataFlow.value != null)
                    _searchedUsersPagingDataFlow.value = null

            }

            is AllUsersUiEvent.RetryAllUsers -> {
                event.users.retry()
            }

            is AllUsersUiEvent.UsersLoadStateChanged -> {
                allUsersLoadStateChanged(event.users)
            }

            is AllUsersUiEvent.SearchedUsersLoadStateChanged -> {
                searchedUsersLoadStateChanged(event.users)
            }

            is AllUsersUiEvent.RetrySearchedUsers -> {
                event.users.retry()
            }

            is AllUsersUiEvent.NavigateToDirectMessage -> {
                sendEffect(AllUsersUiEffect.NavigateToDirectMessage(event.user))
            }
        }

    }

    fun getUserInfo() {
        viewModelScope.launch {
            getLocalUserUseCase()?.let {
                setState { copy(userId = it.id) }
            }
        }
    }

    fun search() {
        _searchedUsersPagingDataFlow.value = getSearchedUsersPagingDataUseCase(
            UserSearchRequest(
                uiState.value.searchedText
            )
        ).cachedIn(viewModelScope)
    }

    fun allUsersLoadStateChanged(users: LazyPagingItems<User>) {
        val refresh = users.loadState.refresh
        val append = users.loadState.append

        setState {
            copy(
                isLoadingUsers = refresh is LoadState.Loading && users.itemCount == 0,
                isAppendingUsers = append is LoadState.Loading,
                usersLoadingError = (refresh as? LoadState.Error)?.error?.localizedMessage,
                usersAppendError = (append as? LoadState.Error)?.error?.localizedMessage,
                isUsersListEmpty = refresh is LoadState.NotLoading && users.itemCount == 0
            )
        }
    }

    fun searchedUsersLoadStateChanged(users: LazyPagingItems<User>) {
        val refresh = users.loadState.refresh
        val append = users.loadState.append

        setState {
            copy(
                isLoadingSearchedUsers = refresh is LoadState.Loading && users.itemCount == 0,
                isAppendingSearchedUsers = append is LoadState.Loading,
                searchedUsersLoadingError = (refresh as? LoadState.Error)?.error?.localizedMessage,
                searchedUsersAppendError = (append as? LoadState.Error)?.error?.localizedMessage,
                isSearchedUsersListEmpty = refresh is LoadState.NotLoading && users.itemCount == 0
            )
        }
    }

    override fun onCleared() {
        runBlocking {
            RootScaffoldController.emit(ScaffoldEvent.SetBottomBarVisibility(true))
        }
        super.onCleared()
    }
}