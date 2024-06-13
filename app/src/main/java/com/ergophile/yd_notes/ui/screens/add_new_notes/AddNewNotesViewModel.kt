package com.ergophile.yd_notes.ui.screens.add_new_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModelItem
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

class AddNewNotesViewModel(private val repository: YDNotesRepository) : ViewModel() {
    private val _addNewNotesState =
        MutableStateFlow<ResponseState<UserNotesModelItem>>(ResponseState.Idle)

    val addNewNotesState = _addNewNotesState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun addNewNotes(noteBody: JsonObject) {
        viewModelScope.launch {
            _addNewNotesState.emitAll(
                repository.addNewNote(
                    newNoteBody = noteBody.toString()
                        .toRequestBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }
}