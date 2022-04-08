package com.wtfcompany.relax.view.registration

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.wtfcompany.relax.App
import com.wtfcompany.relax.data.entity.User
import com.wtfcompany.relax.view.base.BaseViewModel
import com.wtfcompany.relax.view.registration.RegistrationContract.*
import kotlinx.coroutines.launch

class RegistrationViewModel : BaseViewModel<Event, State, Effect>() {

    override fun createInitialState(): State {
        return State(RegistrationState.Idle)
    }

    override fun handleEvent(event: Event) {
        when (event) {
            is Event.OnAuthButtonClick -> register(event.name, event.email, event.password)
            is Event.OnLoginButtonClick -> {
                setState { copy(registrationState = RegistrationState.Login) }
            }
        }
    }

    private fun isFieldsCorrect(name: String, email: String, password: String): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email)
            .matches() && password.isNotEmpty()
    }

    private fun register(name: String, email: String, password: String) {
        if (!isFieldsCorrect(name, email, password)) {
            setEffect { Effect.ShowWrongParamsToast }
            return
        }
        setState { copy(registrationState = RegistrationState.Loading) }

        viewModelScope.launch {
            val userRepository = App.instance.userRepository
            val users = userRepository.existUser(name, email)
            if (users.isEmpty()) {
                val user = User(name, email, password)
                user.id = userRepository.insert(user)
                App.instance.user = user
                setState { copy(registrationState = RegistrationState.Success) }
            } else {
                setEffect { Effect.ShowIncorrectDataToast }
                setState { copy(registrationState = RegistrationState.Idle) }
            }
        }
    }

    override fun clearState() {
        setState { copy(registrationState = RegistrationState.Idle) }
    }
}