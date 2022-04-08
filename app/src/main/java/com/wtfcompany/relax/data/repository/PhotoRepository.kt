package com.wtfcompany.relax.data.repository

import androidx.annotation.WorkerThread
import com.wtfcompany.relax.data.dao.PhotoDao
import com.wtfcompany.relax.data.entity.Photo

class PhotoRepository(private val photoDao: PhotoDao) {

    @WorkerThread
    suspend fun insert(photo: Photo) = photoDao.insert(photo)

    @WorkerThread
    suspend fun delete(photo: Photo) {
        photoDao.delete(photo)
    }

    suspend fun getPhotos(userId: Long) = photoDao.getPhotos(userId)

}
