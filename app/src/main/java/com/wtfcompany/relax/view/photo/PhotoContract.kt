package com.wtfcompany.relax.view.photo

import com.wtfcompany.relax.data.entity.Photo
import com.wtfcompany.relax.view.base.UiEffect
import com.wtfcompany.relax.view.base.UiEvent
import com.wtfcompany.relax.view.base.UiState

class PhotoContract {

    sealed class Event : UiEvent {
        data class OnDeleteImageButtonClick(val photo: Photo) : Event()
        object OnCloseImageButtonClick : Event()
    }

    data class State(val photoState: PhotoState) : UiState

    sealed class PhotoState {
        object Idle : PhotoState()
        object Delete : PhotoState()
        object Close : PhotoState()
    }

    sealed class Effect : UiEffect {}
}