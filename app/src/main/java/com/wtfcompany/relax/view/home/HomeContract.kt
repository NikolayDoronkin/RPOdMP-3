package com.wtfcompany.relax.view.home

import com.wtfcompany.relax.view.base.UiEffect
import com.wtfcompany.relax.view.base.UiEvent
import com.wtfcompany.relax.view.base.UiState
import com.wtfcompany.relax.view.home.data.HoroscopeData
import com.wtfcompany.relax.view.home.data.Mood

class HomeContract {

    sealed class Event : UiEvent {
        object OnMenuButtonClick : Event()
        data class OnMoodButtonClick(val mood: Mood) : Event()
    }

    data class State(val homeState: HomeState) : UiState

    sealed class HomeState {
        object Idle : HomeState()
        object Menu : HomeState()
    }

    sealed class Effect : UiEffect {
        data class UpdateSuggestionList(
            val horoscopeData: HoroscopeData?,
            val todayMood: Mood,
            val dailyMood: Mood
        ) : Effect()

        data class UpdateMoodList(val list: List<MoodInfo>) : Effect()
    }
}