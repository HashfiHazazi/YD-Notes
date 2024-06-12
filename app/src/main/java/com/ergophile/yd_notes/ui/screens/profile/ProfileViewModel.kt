package com.ergophile.yd_notes.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergophile.yd_notes.data.source.remote.model.user_account.UserAccountModelItem
import com.ergophile.yd_notes.data.source.repository.YDNotesRepository
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: YDNotesRepository) : ViewModel() {
    private val _profileState =
        MutableStateFlow<ResponseState<UserAccountModelItem>>(ResponseState.Idle)

    val profileState = _profileState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )
//
//    fun getProfile(){
//        viewModelScope.launch {
//            _profileState.emitAll(repository.userAccountProfile())
//        }
//    }
}