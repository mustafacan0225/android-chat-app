package com.mustafacan.auth.ui.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.model.auth.LoginRequest
import com.mustafacan.core.domain.usecase.LoginUseCase
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mustafacan.core.common.app_event.AppEvent
import com.mustafacan.core.common.app_event.AppEventManager
import com.mustafacan.core.common.model.PopupType
import com.mustafacan.core.ui.util.ErrorHandler

@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext private val context: Context,
                                         private val loginUseCase: LoginUseCase,
                                         ) : BaseViewModel<LoginUiState, LoginUiEvent, LoginUiEffect>(
    initialState = LoginUiState()
) {

    override fun handleEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChanged -> {
                setState {
                    copy(
                        email = event.email,
                        isLoginButtonEnabled = isInputValid(event.email, password)
                    )
                }
            }

            is LoginUiEvent.PasswordChanged -> {
                setState {
                    copy(
                        password = event.password,
                        isLoginButtonEnabled = isInputValid(email, event.password)
                    )
                }
            }

            is LoginUiEvent.TogglePasswordVisibility -> {
                setState {
                    copy(
                        isPasswordVisible = !uiState.value.isPasswordVisible
                    )
                }

            }

            LoginUiEvent.LoginClicked -> {
                login(email = uiState.value.email, password = uiState.value.password)
            }

            LoginUiEvent.RegisterClicked -> {
                sendEffect(LoginUiEffect.NavigateToRegister)
            }
        }
    }

    private fun isInputValid(email: String, password: String): Boolean {
        return email.isNotBlank() && password.trim().isNotBlank()
    }

    private fun login(email: String, password: String) {

        viewModelScope.launch {
            setState { copy(isLoading = true) }
            delay(2000)
            val result = loginUseCase.invoke(request = LoginRequest(email, password))
            setState { copy(isLoading = false) }
            result
                .onSuccess {
                    sendEffect(LoginUiEffect.NavigateToHome)
                    println("login basarili ${it.id} - ${it.email} - ${it.username}")
                }
                .onFailure { throwable ->
                    //get user friendly message
                    val errorMessage = ErrorHandler.resolveErrorMessage(context, throwable)

                    //show popup
                    AppEventManager.emit(AppEvent.ShowPopup(message = errorMessage, popupType = PopupType.Info))
                }

        }
    }
}