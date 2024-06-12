package com.ergophile.yd_notes.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModel
import com.ergophile.yd_notes.data.source.repository.YDNotesRepository
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: YDNotesRepository) : ViewModel() {
    private val _homeState = MutableStateFlow<ResponseState<UserNotesModel>>(ResponseState.Idle)

    val homeState = _homeState.asStateFlow()
        .stateIn(viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun getListNotes(uid: String){
        viewModelScope.launch {
            _homeState.emitAll(repository.getNote(userUid = "eq.$uid"))
        }
    }
}