package com.ergophile.yd_notes.ui.screens.detail_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergophile.yd_notes.KotprefLocalStorage
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModel
import com.ergophile.yd_notes.data.source.repository.YDNotesRepository
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

class DetailViewModel(private val repository: YDNotesRepository) : ViewModel() {
    private val _detailState =
        MutableStateFlow<ResponseState<UserNotesModel>>(ResponseState.Idle)

    val detailState = _detailState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun getDetail(idNotes: Int) {
        viewModelScope.launch {
            _detailState.emitAll(
                repository.getDetailNote(
                    idNote = "eq.$idNotes",
                    userUid = "eq.${KotprefLocalStorage.userUid}"
                )
            )
        }
    }

    private val _updateDetailState =
        MutableStateFlow<ResponseState<Boolean>>(ResponseState.Idle)

    val updateDetailState = _updateDetailState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun updateNoteDetail(idNotes: Int, updateBody: JsonObject) {
        viewModelScope.launch {
            _updateDetailState.emitAll(
                repository.updateNote(
                    idNote = "eq.$idNotes",
                    userUid = "eq.${KotprefLocalStorage.userUid}",
                    updateBody = updateBody.toString()
                        .toRequestBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }

    private val _deleteNoteState = MutableStateFlow<ResponseState<Unit?>>(ResponseState.Idle)

    val deleteNoteState = _deleteNoteState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )
    fun deleteNote(idNote: Int){
        viewModelScope.launch {
            _deleteNoteState.emitAll(
                repository.deleteNote(idNote = "eq.$idNote")
            )
        }
    }
}