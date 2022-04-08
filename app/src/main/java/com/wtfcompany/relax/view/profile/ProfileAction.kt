package com.wtfcompany.relax.view.profile

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.wtfcompany.relax.App
import com.wtfcompany.relax.data.StoreUserId
import com.wtfcompany.relax.data.entity.Photo
import com.wtfcompany.relax.navigation.Screen
import com.wtfcompany.relax.view.base.BaseAction
import com.wtfcompany.relax.view.profile.ProfileContract.Effect.UpdateList
import com.wtfcompany.relax.view.profile.ProfileContract.ProfileState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch


class ProfileAction(
    composableScope: CoroutineScope,
    val context: Context,
    val viewModel: ProfileViewModel,
    val isLoadingState: MutableState<Boolean>,
    val dataWithUpdater: Pair<MutableState<MutableList<Photo>>, MutableState<Boolean>>,
    val navController: NavHostController
) : BaseAction(composableScope) {

    override fun onState() {
        composableScope.launch {
            viewModel.uiState.collect { state ->
                composableScope.ensureActive()
                when (state.profileState) {
                    is ProfileState.Logout -> {
                        viewModel.clearState()
                        navController.popBackStack()
                        navController.navigate(Screen.Splash.route)
                        App.instance.user = null
                        StoreUserId(context).saveUserId(-1L)
                        cancelScope()
                    }
                    is ProfileState.Menu -> {
                        viewModel.clearState()
                        navController.navigate(Screen.Menu.route)
                        cancelScope()
                    }
                    is ProfileState.Image -> {
                        viewModel.clearState()
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "info", state.profileState.photo
                        )
                        navController.navigate(Screen.Photo.route)
                        cancelScope()
                    }
                    is ProfileState.Idle -> isLoadingState.value = false
                    is ProfileState.Loading -> isLoadingState.value = true
                }
            }
        }
    }

    override fun onEffect() {
        composableScope.launch {
            viewModel.effect.collect { effect ->
                composableScope.ensureActive()
                when (effect) {
                    is UpdateList -> {
                        val (photos, updater) = dataWithUpdater
                        updater.value = false
                        photos.value = effect.photos
                        updater.value = true
                    }
                }
            }
        }
    }


}