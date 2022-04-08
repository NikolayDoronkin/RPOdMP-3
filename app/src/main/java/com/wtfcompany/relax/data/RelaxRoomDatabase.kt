package com.wtfcompany.relax.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wtfcompany.relax.data.dao.PhotoDao
import com.wtfcompany.relax.data.dao.UserDao
import com.wtfcompany.relax.data.dao.UserMoodDao
import com.wtfcompany.relax.data.entity.Photo
import com.wtfcompany.relax.data.entity.User
import com.wtfcompany.relax.data.entity.UserMood

@Database(entities = [User::class, UserMood::class, Photo::class], version = 1, exportSchema = false)
abstract class RelaxRoomDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun userMoodDao(): UserMoodDao
    abstract fun photoDao(): PhotoDao

    companion object {

        @Volatile
        private var INSTANCE: RelaxRoomDatabase? = null

        fun getDatabase(context: Context): RelaxRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room
                    .databaseBuilder(
                        context.applicationContext,
                        RelaxRoomDatabase::class.java,
                        "relax_database"
                    )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
