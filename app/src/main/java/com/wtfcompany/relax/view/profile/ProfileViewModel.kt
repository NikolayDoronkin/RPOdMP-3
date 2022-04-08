package com.wtfcompany.relax.view.profile

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.wtfcompany.relax.App
import com.wtfcompany.relax.data.entity.Photo
import com.wtfcompany.relax.util.bitmapToString
import com.wtfcompany.relax.view.base.BaseViewModel
import com.wtfcompany.relax.view.profile.ProfileContract.Effect.UpdateList
import com.wtfcompany.relax.view.profile.ProfileContract.Event.*
import com.wtfcompany.relax.view.profile.ProfileContract.ProfileState
import kotlinx.coroutines.launch

class ProfileViewModel : BaseViewModel<ProfileContract.Event, ProfileContract.State, ProfileContract.Effect>() {

    val photoList = mutableListOf<Photo>()

    init {
        downloadImages()
    }

    override fun createInitialState(): ProfileContract.State {
        return ProfileContract.State(ProfileState.Idle)
    }

    override fun handleEvent(event: ProfileContract.Event) {
        when (event) {
            is OnLoadImageButtonClick -> {
                uploadImage(event.bitmap)
            }
            is OnOpenImageButtonClick -> {
                setState { copy(profileState = ProfileState.Image(event.photo)) }
            }
            is OnLogoutButtonClick -> {
                setState { copy(profileState = ProfileState.Logout) }
            }
            is OnMenuButtonClick -> {
                setState { copy(profileState = ProfileState.Menu) }
            }
        }
    }

    private fun uploadImage(bitmap: Bitmap?) {
        bitmap?.let { btm ->
            setState { copy(profileState = ProfileState.Loading) }
            viewModelScope.launch {
                val photo = Photo(App.instance.user!!.id, bitmapToString(btm))
                val photoId = App.instance.photoRepository.insert(photo)
                photo.id = photoId
                photoList.add(photo)
                setEffect { UpdateList(photoList) }
                setState { copy(profileState = ProfileState.Idle) }
            }
        }
    }

    private fun downloadImages() {
        setState { copy(profileState = ProfileState.Loading) }
        viewModelScope.launch {
            val userId = App.instance.user!!.id
            val photos = App.instance.photoRepository.getPhotos(userId)
            photoList.addAll(photos)
            setEffect { UpdateList(photoList) }
            setState { copy(profileState = ProfileState.Idle) }
        }
    }

    fun deleteImage(photo: Photo) {
        viewModelScope.launch {
            App.instance.photoRepository.delete(photo)
            photoList.remove(photo)
            setEffect { UpdateList(photoList) }
        }
    }

    override fun clearState() {
        setState { copy(profileState = ProfileState.Idle) }
    }
}