package com.mustafacan.auth.ui.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.mustafacan.core.domain.error.BusinessLogicError
import com.mustafacan.core.domain.model.auth.LoginRequest
import com.mustafacan.core.domain.usecase.LoginUseCase
import com.mustafacan.core.ui.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mustafacan.auth.R

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

            LoginUiEvent.LoginClicked -> {

                login(email = uiState.value.email, password = uiState.value.password)
                /*viewModelScope.launch {


                    // Simülasyon: giriş başarılı
                    sendEffect(LoginUiEffect.NavigateToHome)

                }*/

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
            result
                .onSuccess {
                    setState { copy(isLoading = false) }
                    println("login basarili ${it.id} - ${it.email} - ${it.username}")
                }
                .onFailure { error ->
                    setState { copy(isLoading = false) }

                    var message = ""
                    if (error is BusinessLogicError) {
                        val messageResId = when (error) {
                            BusinessLogicError.InvalidEmail -> R.string.error_invalid_email
                            BusinessLogicError.InvalidPassword -> R.string.error_invalid_password
                            else -> { R.string.error_default }
                        }
                        message = context.getString(messageResId)

                    } else {
                        message = error.message?: context.getString(R.string.error_default)
                    }

                    println("login basarisiz ${message}")
                }

        }
    }
}