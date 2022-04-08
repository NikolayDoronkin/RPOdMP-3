package com.wtfcompany.relax.view.firstenter

import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.wtfcompany.relax.navigation.Screen
import com.wtfcompany.relax.view.base.BaseAction
import com.wtfcompany.relax.view.firstenter.FirstEnterContract.FirstEnterState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

class FirstEnterAction(
    composableScope: CoroutineScope,
    val viewModel: FirstEnterViewModel,
    val isLoadingState: MutableState<Boolean>,
    val navController: NavHostController
) : BaseAction(composableScope) {

    override fun onState() {
        composableScope.launch {
            viewModel.uiState.collect {
                composableScope.ensureActive()
                when (it.firstEnterState) {
                    is FirstEnterState.Success -> {
                        viewModel.clearState()
                        navController.popBackStack()
                        navController.navigate(Screen.Main.route)
                        cancelScope()
                    }
                    is FirstEnterState.Idle -> {
                        isLoadingState.value = false
                    }
                    is FirstEnterState.Loading -> {
                        isLoadingState.value = true
                    }
                }
            }
        }
    }

    override fun onEffect() {

    }
}