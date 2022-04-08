package com.wtfcompany.relax.view.firstenter

import androidx.lifecycle.viewModelScope
import com.wtfcompany.relax.App
import com.wtfcompany.relax.view.base.BaseViewModel
import com.wtfcompany.relax.view.firstenter.FirstEnterContract.Event.OnFinishButtonClick
import com.wtfcompany.relax.view.firstenter.FirstEnterContract.FirstEnterState.*
import kotlinx.coroutines.launch

class FirstEnterViewModel :
    BaseViewModel<FirstEnterContract.Event, FirstEnterContract.State, FirstEnterContract.Effect>() {

    override fun createInitialState(): FirstEnterContract.State {
        return FirstEnterContract.State(Idle)
    }

    override fun handleEvent(event: FirstEnterContract.Event) {
        when (event) {
            is OnFinishButtonClick ->
                saveData(event.phone, event.weight, event.pressure, event.birthday)
        }
    }

    private fun saveData(phone: String, weight: String, height: String, birthday: String) {
        setState { copy(firstEnterState = Loading) }
        App.instance.user?.let { user ->
            user.phone = phone
            user.weight = weight
            user.height = height
            user.birthday = birthday
            viewModelScope.launch {
                App.instance.userRepository.update(user)
                App.instance.user = user
                setState { copy(firstEnterState = Success) }
            }
        }
    }

    override fun clearState() {
        setState { copy(firstEnterState = Idle) }
    }
}