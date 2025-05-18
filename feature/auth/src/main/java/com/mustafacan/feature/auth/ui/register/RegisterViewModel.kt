package com.mustafacan.feature.auth.ui.register

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.common.app_event.AppEvent
import com.mustafacan.core.common.app_event.AppEventManager
import com.mustafacan.core.common.model.PopupType
import com.mustafacan.core.domain.model.auth.RegisterRequest
import com.mustafacan.core.domain.usecase.api.RegisterUseCase
import com.mustafacan.core.ui.util.ErrorHandler
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(@ApplicationContext private val context: Context, private val registerUseCase: RegisterUseCase) :
    BaseViewModel<RegisterUiState, RegisterUiEvent, RegisterUiEffect>(initialState = RegisterUiState()) {

    override fun handleEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.UserNameChanged -> {
                setState {
                    copy(
                        username = event.username,
                        isRegisterButtonEnabled = isInputValid(event.username, email, password)
                    )
                }
            }

            is RegisterUiEvent.EmailChanged -> {
                setState {
                    copy(
                        email = event.email,
                        isRegisterButtonEnabled = isInputValid(username, event.email, password)
                    )
                }
            }

            is RegisterUiEvent.PasswordChanged -> {
                setState {
                    copy(
                        password = event.password,
                        isRegisterButtonEnabled = isInputValid(username, email, event.password)
                    )
                }
            }

            is RegisterUiEvent.TogglePasswordVisibility -> {
                setState {
                    copy(
                        isPasswordVisible = !uiState.value.isPasswordVisible
                    )
                }

            }

            RegisterUiEvent.LoginClicked -> {
                sendEffect(RegisterUiEffect.NavigateToLogin)

            }

            RegisterUiEvent.RegisterClicked -> {
                register()
            }
        }
    }

    private fun isInputValid(username: String, email: String, password: String): Boolean {
        return username.isNotBlank() && email.isNotBlank() && password.isNotBlank()
    }

    private fun register() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            delay(2000)
            val result = registerUseCase.invoke(RegisterRequest(uiState.value.username, uiState.value.email, uiState.value.password))
            setState { copy(isLoading = false) }

            result
                .onSuccess {
                    sendEffect(RegisterUiEffect.NavigateToHome)
                    println("register basarili ${it.id} - ${it.email} - ${it.username}")
                }
                .onFailure { throwable ->
                    val errorMessage = ErrorHandler.resolveErrorMessage(context, throwable)
                    AppEventManager.emit(AppEvent.ShowPopup(message = errorMessage, popupType = PopupType.Info))
                }
        }
    }
}