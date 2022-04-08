package com.wtfcompany.relax.data.repository

import androidx.annotation.WorkerThread
import com.wtfcompany.relax.data.dao.UserMoodDao
import com.wtfcompany.relax.data.entity.UserMood

class UserMoodRepository(private val userMoodDao: UserMoodDao) {

    @WorkerThread
    suspend fun insert(userMood: UserMood) {
        userMoodDao.insert(userMood)
    }

    @WorkerThread
    suspend fun getUserMoods(id: Long) = userMoodDao.getUserMoods(id)
}
