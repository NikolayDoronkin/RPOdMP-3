package com.wtfcompany.relax.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class StoreUserId(private val context: Context) {

    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("user_id")
        val USER_ID_KEY = longPreferencesKey("user_id")
    }

    val getUserId: Flow<Long?> = context.dataStoree.data
        .map { it[USER_ID_KEY] ?: -1L }

    suspend fun saveUserId(id: Long) {
        context.dataStoree.edit { it[USER_ID_KEY] = id }
    }

}