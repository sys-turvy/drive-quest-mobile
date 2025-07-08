package com.example.drivequest.presentation.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.drivequest.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    data class InputState(
        val input: String = "",
        val validationError: String? = null,
    )
    private val _emailInputState = MutableStateFlow(InputState())
    val emailInputState: StateFlow<InputState> = _emailInputState.asStateFlow()

    private val _passwordInputState = MutableStateFlow(InputState())
    val passwordInputState: StateFlow<InputState> = _passwordInputState.asStateFlow()

    var isLoginEnabled = mutableStateOf<Boolean>(false)
        private set

    sealed class ButtonState {
        object Idle : ButtonState()
        object Loading : ButtonState()
    }
    private val _buttonState = MutableStateFlow<ButtonState>(ButtonState.Idle)
    val buttonState: StateFlow<ButtonState> = _buttonState.asStateFlow()

    private val _loginSuccessEvent = MutableSharedFlow<Unit>()
    val loginSuccessEvent: SharedFlow<Unit> = _loginSuccessEvent.asSharedFlow()

    private val _loginErrorEvent = MutableSharedFlow<String>()
    val loginErrorEvent = _loginErrorEvent.asSharedFlow()

    fun updateEmailInput(newEmail: String) {
        _emailInputState.value = _emailInputState.value.copy(
            input = newEmail
        )
        validateEmail()
        updateLoginEnabled()
    }

    fun updatePasswordInput(newPassword: String) {
        _passwordInputState.value = _passwordInputState.value.copy(
            input = newPassword
        )
        validatePassword()
        updateLoginEnabled()
    }

    fun onLoginClicked() {
        if (!validateAll()) {
            updateLoginEnabled()
            return
        }

        _buttonState.value = ButtonState.Loading

        viewModelScope.launch {
            val email = _emailInputState.value.input
            val password = _passwordInputState.value.input

            val result = loginUseCase(email, password)

            result.onSuccess {
                _buttonState.value = ButtonState.Idle
                _loginSuccessEvent.emit(Unit)
            }.onFailure { e ->
                _buttonState.value = ButtonState.Idle
                Log.d("Error", e.message.toString())
                _loginErrorEvent.emit("ログインに失敗しました。メールアドレスまたはパスワードが間違っています")
            }
            resetInputAndValidation()
            updateLoginEnabled()
        }
    }

    private fun validateEmail(): Boolean {
        val email = _emailInputState.value.input
        val emailPattern = Regex("^[\\w\\.-]+@[\\w\\.-]+\\.[a-z]{2,}$", RegexOption.IGNORE_CASE)
        return when {
            email.isBlank() -> {
                _emailInputState.value = _emailInputState.value.copy(validationError = "メールアドレスを入力してください")
                false
            }
            !emailPattern.matches(email) -> {
                _emailInputState.value = _emailInputState.value.copy(validationError = "正しいメールアドレスの形式で入力してください")
                false
            }
            else -> {
                _emailInputState.value = _emailInputState.value.copy(validationError = null)
                true
            }
        }
    }

    private fun validatePassword(): Boolean {
        val password = _passwordInputState.value.input
        return when {
            password.isBlank() -> {
                _passwordInputState.value = _passwordInputState.value.copy(validationError = "パスワードを入力してください")
                false
            }
            password.length < 6 -> {
                _passwordInputState.value = _passwordInputState.value.copy(validationError = "パスワードは6文字以上で入力してください")
                false
            }
            else -> {
                _passwordInputState.value = _passwordInputState.value.copy(validationError = null)
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
        val isEmailValid = _emailInputState.value.validationError == null && _emailInputState.value.input.isNotBlank()
        val isPasswordValid = _passwordInputState.value.validationError == null && _passwordInputState.value.input.isNotBlank()
        val shouldEnable = isEmailValid && isPasswordValid

        isLoginEnabled.value = shouldEnable
    }

    private fun resetInputAndValidation() {
        _emailInputState.value = _emailInputState.value.copy(
            input = "",
            validationError = null,
        )
        _passwordInputState.value = _passwordInputState.value.copy(
            input = "",
            validationError = null,
        )
        isLoginEnabled.value = false
    }
}