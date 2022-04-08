package com.wtfcompany.relax.view.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import com.wtfcompany.relax.App
import com.wtfcompany.relax.R
import com.wtfcompany.relax.navigation.Screen
import com.wtfcompany.relax.view.base.BaseAction
import com.wtfcompany.relax.view.login.LoginContract.Effect.ShowIncorrectDataToast
import com.wtfcompany.relax.view.login.LoginContract.Effect.ShowWrongParamsToast
import com.wtfcompany.relax.view.login.LoginContract.LoginState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch


class LoginAction(
    composableScope: CoroutineScope,
    val context: Context,
    val viewModel: LoginViewModel,
    val isLoadingState: MutableState<Boolean>,
    val navController: NavHostController
) : BaseAction(composableScope) {

    override fun onState() {
        composableScope.launch {
            viewModel.uiState.collect { state ->
                composableScope.ensureActive()
                when (state.loginState) {
                    is LoginState.Menu -> {
                        viewModel.clearState()
                        App.saveUserId(context)
                        navController.popBackStack()
                        navController.navigate(Screen.Main.route)
                        cancelScope()
                    }
                    is LoginState.FirstEnter -> {
                        viewModel.clearState()
                        App.saveUserId(context)
                        navController.popBackStack()
                        navController.navigate(Screen.FirstEnter.route)
                        cancelScope()
                    }
                    is LoginState.Register -> {
                        viewModel.clearState()
                        navController.popBackStack()
                        navController.navigate(Screen.Registration.route)
                        cancelScope()
                    }
                    is LoginState.Idle -> {
                        isLoadingState.value = false
                    }
                    is LoginState.Loading -> {
                        isLoadingState.value = true
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
                    is ShowIncorrectDataToast -> {
                        Toast.makeText(
                            context, context.getString(R.string.error_login), Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ShowWrongParamsToast -> {
                        Toast.makeText(
                            context, context.getString(R.string.error_fields_data), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}