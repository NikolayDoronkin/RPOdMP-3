package com.wtfcompany.relax.view.photo

import androidx.navigation.NavHostController
import com.wtfcompany.relax.view.base.BaseAction
import com.wtfcompany.relax.view.photo.PhotoContract.PhotoState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch


class PhotoAction(
    composableScope: CoroutineScope,
    val viewModel: PhotoViewModel,
    val navController: NavHostController
) : BaseAction(composableScope) {

    override fun onState() {
        composableScope.launch {
            viewModel.uiState.collect {
                composableScope.ensureActive()
                when (it.photoState) {
                    is PhotoState.Idle -> {
                    }
                    is PhotoState.Delete -> {
                        viewModel.clearState()
                        navController.popBackStack()
                        cancelScope()
                    }
                    is PhotoState.Close -> {
                        viewModel.clearState()
                        navController.popBackStack()
                        cancelScope()
                    }
                }
            }
        }
    }

    override fun onEffect() {

    }
}