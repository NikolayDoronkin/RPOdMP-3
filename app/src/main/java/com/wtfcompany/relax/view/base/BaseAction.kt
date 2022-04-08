package com.wtfcompany.relax.view.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

abstract class BaseAction(
    val composableScope: CoroutineScope
) {
    protected abstract fun onState()
    protected abstract fun onEffect()

    fun init() {
        onState()
        onEffect()
    }

    fun cancelScope() {
        composableScope.cancel()
    }
}