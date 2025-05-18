package com.mustafacan.android_chat_app.ui.splash

import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.usecase.datastore.GetLocalUserUseCase
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getLocalUserUseCase: GetLocalUserUseCase) :
    BaseViewModel<SplashUiState, SplashUiEvent, SplashUiEffect>(initialState = SplashUiState()) {

    init {
        checkUser()
    }

    fun checkUser() {
        viewModelScope.launch {

            //for splash animation
            delay(3000)

            val user = withContext(Dispatchers.IO) {
                getLocalUserUseCase()
            }

            if (user == null) {
                sendEffect(SplashUiEffect.NavigateToLogin)
            } else {
                println("Local User Data: ${user.id} - ${user.email} - ${user.username}")
                sendEffect(SplashUiEffect.NavigateToHome)
            }
        }

    }

    override fun handleEvent(event: SplashUiEvent) {

    }
}