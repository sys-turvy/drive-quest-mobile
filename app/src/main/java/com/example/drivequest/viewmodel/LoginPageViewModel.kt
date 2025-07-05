package com.example.drivequest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivequest.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginScreenState(
    val emailInput: String = "",
    val passwordInput: String = "",
    val isAuthenticating: Boolean = false,
    val emailValidationError: String? = null,
    val passwordValidationError: String? = null,
    val networkError: String? = null,
    val isLoginEnabled: Boolean = false
)

@HiltViewModel
class LoginPageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _pageState = MutableStateFlow(LoginScreenState())
    val pageState: StateFlow<LoginScreenState> = _pageState.asStateFlow()

    private val _loginSuccessEvent = MutableSharedFlow<Unit>()
    val loginSuccessEvent: SharedFlow<Unit> = _loginSuccessEvent.asSharedFlow()

    private val _loginErrorEvent = MutableSharedFlow<String>()
    val loginErrorEvent = _loginErrorEvent.asSharedFlow()


    fun updateEmailInput(newEmail: String) {
        _pageState.value = _pageState.value.copy(
            emailInput = newEmail
        )
        validateEmail()
        updateLoginEnabled()
    }

    fun updatePasswordInput(newPassword: String) {
        _pageState.value = _pageState.value.copy(
            passwordInput = newPassword
        )
        validatePassword()
        updateLoginEnabled()
    }

    fun onLoginClicked() {
        if (!validateAll()) {
            updateLoginEnabled()
            return
        }

        _pageState.value = _pageState.value.copy(
            isAuthenticating = true,
            networkError = null
        )

        viewModelScope.launch {
            val email = _pageState.value.emailInput
            val password = _pageState.value.passwordInput

            val result = authRepository.login(email, password)

            if (result.isSuccess) {
                _pageState.value = _pageState.value.copy(
                    isAuthenticating = false,
                    networkError = null
                )

                _loginSuccessEvent.emit(Unit)
            } else {
                _pageState.value = _pageState.value.copy(
                    isAuthenticating = false,
                    networkError = "メールアドレスまたはパスワードが間違っています"
                )
                _loginErrorEvent.emit("ログインに失敗しました。メールアドレスまたはパスワードが間違っています")
            }
            resetInputAndValidation()
            updateLoginEnabled()
        }
    }

    private fun validateEmail(): Boolean {
        val email = _pageState.value.emailInput
        val emailPattern = Regex("^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}$", RegexOption.IGNORE_CASE)
        return when {
            email.isBlank() -> {
                _pageState.value = _pageState.value.copy(emailValidationError = "メールアドレスを入力してください")
                false
            }
            !emailPattern.matches(email) -> {
                _pageState.value = _pageState.value.copy(emailValidationError = "正しいメールアドレスの形式で入力してください")
                false
            }
            else -> {
                _pageState.value = _pageState.value.copy(emailValidationError = null)
                true
            }
        }
    }

    private fun validatePassword(): Boolean {
        val password = _pageState.value.passwordInput
        return when {
            password.isBlank() -> {
                _pageState.value = _pageState.value.copy(passwordValidationError = "パスワードを入力してください")
                false
            }
            password.length < 6 -> {
                _pageState.value = _pageState.value.copy(passwordValidationError = "パスワードは6文字以上で入力してください")
                false
            }
            else -> {
                _pageState.value = _pageState.value.copy(passwordValidationError = null)
                true
            }
        }
    }

    private fun validateAll(): Boolean {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        return isEmailValid && isPasswordValid
    }

    private fun updateLoginEnabled() {
        val state = _pageState.value
        val isEmailValid = state.emailValidationError == null && state.emailInput.isNotBlank()
        val isPasswordValid = state.passwordValidationError == null && state.passwordInput.isNotBlank()
        val shouldEnable = isEmailValid && isPasswordValid

        _pageState.value = state.copy(isLoginEnabled = shouldEnable)
    }

    private fun resetInputAndValidation() {
        _pageState.value = _pageState.value.copy(
            emailInput = "",
            passwordInput = "",
            emailValidationError = null,
            passwordValidationError = null,
            isLoginEnabled = false
        )
    }
}