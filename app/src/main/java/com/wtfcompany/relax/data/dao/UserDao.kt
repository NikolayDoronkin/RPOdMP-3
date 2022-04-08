package com.wtfcompany.relax.data.dao

import androidx.room.*
import com.wtfcompany.relax.data.entity.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user WHERE name = :name OR email = :email")
    suspend fun existUser(name: String, email: String): List<User>

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    suspend fun getUser(email: String, password: String): List<User>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Long): List<User>

}
