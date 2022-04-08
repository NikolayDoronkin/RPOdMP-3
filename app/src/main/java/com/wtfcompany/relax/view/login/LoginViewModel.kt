package com.wtfcompany.relax.view.login

import android.util.Patterns.EMAIL_ADDRESS
import androidx.lifecycle.viewModelScope
import com.wtfcompany.relax.App
import com.wtfcompany.relax.view.base.BaseViewModel
import com.wtfcompany.relax.view.login.LoginContract.Event
import com.wtfcompany.relax.view.login.LoginContract.LoginState
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel<Event, LoginContract.State, LoginContract.Effect>() {

    override fun createInitialState(): LoginContract.State {
        return LoginContract.State(LoginState.Idle)
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnAuthButtonClick -> {
                login(event.email, event.password)
            }
            is Event.OnRegisterButtonClick -> {
                setState { copy(loginState = LoginState.Register) }
            }
        }
    }

    private fun isEmailCorrect(email: String): Boolean {
        return email.isNotEmpty() && EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordCorrect(password: String): Boolean {
        return password.isNotEmpty()
    }

    private fun login(email: String, password: String) {
        if (!(isEmailCorrect(email) && isPasswordCorrect(password))) {
            setEffect { LoginContract.Effect.ShowWrongParamsToast }
            return
        }
        setState { copy(loginState = LoginState.Loading) }
        viewModelScope.launch {
            val users = App.instance.userRepository.getUser(email, password)
            if (users.isNotEmpty()) {
                val user = users[0]
                App.instance.user = user
                if (user.phone.isNotEmpty()) {
                    setState { copy(loginState = LoginState.Menu) }
                } else {
                    setState { copy(loginState = LoginState.FirstEnter) }
                }
            } else {
                setEffect { LoginContract.Effect.ShowIncorrectDataToast }
                setState { copy(loginState = LoginState.Idle) }
            }
        }
    }

    override fun clearState() {
        setState { copy(loginState = LoginState.Idle) }
    }
}