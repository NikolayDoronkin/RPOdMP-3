package com.wtfcompany.relax.view.registration

import com.wtfcompany.relax.view.base.UiEffect
import com.wtfcompany.relax.view.base.UiEvent
import com.wtfcompany.relax.view.base.UiState

class RegistrationContract {

    sealed class Event : UiEvent {
        data class OnAuthButtonClick(val name: String, val email: String, val password: String) :
            Event()

        object OnLoginButtonClick : Event()
    }

    data class State(val registrationState: RegistrationState) : UiState

    sealed class RegistrationState {
        object Idle : RegistrationState()
        object Loading : RegistrationState()
        object Success : RegistrationState()
        object Login : RegistrationState()
    }

    sealed class Effect : UiEffect {
        object ShowIncorrectDataToast : Effect()
        object ShowWrongParamsToast : Effect()
    }
}