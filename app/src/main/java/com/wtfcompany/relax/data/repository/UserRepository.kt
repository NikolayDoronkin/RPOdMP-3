package com.wtfcompany.relax.data.repository

import androidx.annotation.WorkerThread
import com.wtfcompany.relax.data.dao.UserDao
import com.wtfcompany.relax.data.entity.User

class UserRepository(private val userDao: UserDao) {

    @WorkerThread
    suspend fun insert(user: User) = userDao.insert(user)

    @WorkerThread
    suspend fun update(user: User) {
        userDao.update(user)
    }

    @WorkerThread
    suspend fun delete(user: User) {
        userDao.delete(user)
    }

    @WorkerThread
    suspend fun existUser(name: String, email: String) = userDao.existUser(name, email)

    @WorkerThread
    suspend fun getUser(user: User) = userDao.getUser(user.email, user.password)

    @WorkerThread
    suspend fun getUser(email: String, password: String) = userDao.getUser(email, password)

    @WorkerThread
    suspend fun getUser(id: Long) = userDao.getUser(id)
}
