package com.wtfcompany.relax.view.menu

import androidx.navigation.NavHostController
import com.wtfcompany.relax.navigation.Screen
import com.wtfcompany.relax.view.base.BaseAction
import com.wtfcompany.relax.view.menu.MenuContract.MenuState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class MenuAction(
    composableScope: CoroutineScope,
    val viewModel: MenuViewModel,
    val navController: NavHostController
) : BaseAction(composableScope) {

    override fun onState() {
        composableScope.launch {
            viewModel.uiState.collect { state ->
                composableScope.ensureActive()
                when (state.menuState) {
                    is MenuState.EditProfile -> {
                        viewModel.clearState()
                        navController.popBackStack()
                        navController.navigate(Screen.EditProfile.route)
                        cancelScope()
                    }
                    is MenuState.CalculateBmi -> {
                        viewModel.clearState()
                        navController.popBackStack()
                        navController.navigate(Screen.CalculateBmi.route)
                        cancelScope()
                    }
                    is MenuState.AboutDeveloper -> {
                        viewModel.clearState()
                        navController.popBackStack()
                        navController.navigate(Screen.AboutDeveloper.route)
                        cancelScope()
                    }
                    is MenuState.Guide -> {
                        viewModel.clearState()
                        navController.popBackStack()
                        navController.navigate(Screen.Guide.route)
                        cancelScope()
                    }
                    is MenuState.Idle -> {}
                }
            }
        }
    }

    override fun onEffect() {

    }
}