package com.wtfcompany.relax.view.home

import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.wtfcompany.relax.navigation.Screen
import com.wtfcompany.relax.view.base.BaseAction
import com.wtfcompany.relax.view.home.HomeContract.Effect.UpdateMoodList
import com.wtfcompany.relax.view.home.HomeContract.Effect.UpdateSuggestionList
import com.wtfcompany.relax.view.home.HomeContract.HomeState.Idle
import com.wtfcompany.relax.view.home.HomeContract.HomeState.Menu
import com.wtfcompany.relax.view.home.data.HoroscopeData
import com.wtfcompany.relax.view.home.data.Mood
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class HomeAction(
    composableScope: CoroutineScope,
    val viewModel: HomeViewModel,
    val updater: MutableState<Boolean>,
    val updateMood: MutableState<Boolean>,
    val suggestionData: MutableState<Triple<HoroscopeData?, Mood, Mood>>,
    val moodList: MutableState<List<MoodInfo>>,
    val navController: NavHostController
) : BaseAction(composableScope) {

    override fun onState() {
        composableScope.launch {
            viewModel.uiState.collect { state ->
                composableScope.ensureActive()
                when (state.homeState) {
                    is Idle -> {}
                    is Menu -> {
                        viewModel.clearState()
                        navController.navigate(Screen.Menu.route)
                        cancelScope()
                    }
                }
            }
        }
    }

    override fun onEffect() {
        composableScope.launch {
            viewModel.effect.collect { effect ->
                composableScope.ensureActive()
                when (effect) {
                    is UpdateSuggestionList -> {
                        updater.value = false
                        suggestionData.value =
                            Triple(effect.horoscopeData, effect.todayMood, effect.dailyMood)
                        updater.value = true
                    }
                    is UpdateMoodList -> {
                        updateMood.value = false
                        moodList.value = effect.list
                        updateMood.value = true
                    }
                }
            }
        }
    }
}