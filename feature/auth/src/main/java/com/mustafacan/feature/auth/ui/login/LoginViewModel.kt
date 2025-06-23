package com.mustafacan.feature.auth.ui.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.model.error.BackendError
import com.mustafacan.core.model.auth.LoginRequest
import com.mustafacan.core.domain.usecase.api.LoginUseCase
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mustafacan.core.ui.component.dialog.DialogModel
import com.mustafacan.core.ui.component.dialog.DialogType
import com.mustafacan.core.ui.util.ErrorHandler
import com.mustafacan.feature.auth.R

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
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

            LoginUiEvent.TogglePasswordVisibility -> {
                setState {
                    copy(
                        isPasswordVisible = !uiState.value.isPasswordVisible
                    )
                }

            }

            LoginUiEvent.LoginClicked -> {
                login()
            }

            LoginUiEvent.RegisterClicked -> {
                sendEffect(LoginUiEffect.NavigateToRegister)
            }

            LoginUiEvent.DismissDialog -> {
                setState { copy(dialogModel = null) }
            }

            is LoginUiEvent.ShowDialog -> {
                setState { copy(dialogModel = event.dialogModel) }
            }
        }
    }

    private fun isInputValid(email: String, password: String): Boolean {
        return email.isNotBlank() && password.trim().isNotBlank()
    }

    private fun login() {

        viewModelScope.launch {
            setState { copy(isLoading = true) }
            delay(2000)
            val result = loginUseCase(
                request = LoginRequest(
                    uiState.value.email,
                    uiState.value.password
                )
            )
            setState { copy(isLoading = false) }
            result
                .onSuccess {
                    sendEffect(LoginUiEffect.NavigateToHome)
                    println("success ${it.id} - ${it.email} - ${it.username}")
                }
                .onFailure { throwable ->
                    val errorMessage = getErrorMessage(throwable)
                    sendEvent(
                        LoginUiEvent.ShowDialog(
                            DialogModel(
                                message = errorMessage,
                                dialogType = DialogType.Info,
                                onDismiss = {
                                    sendEvent(LoginUiEvent.DismissDialog)
                                }, onConfirm = {
                                    sendEvent(LoginUiEvent.DismissDialog)
                                }
                            )
                        )
                    )
                }

        }
    }

    fun getErrorMessage(throwable: Throwable) : String {
        try {
            val backendError = throwable as BackendError.Error
            if (backendError.code == 400) {
                return context.getString(R.string.incorrect_auth)
            } else {
                return ErrorHandler.resolveErrorMessage(context, throwable)
            }
        } catch (e: Exception) {
            return ErrorHandler.resolveErrorMessage(context, throwable)
        }
    }

}