package com.ergophile.yd_notes.ui.screens.user_signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ergophile.yd_notes.data.source.remote.model.user_auth.User
import com.ergophile.yd_notes.data.source.remote.model.user_auth.UserAuth
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

class UserSignupViewModel(private val repository: YDNotesRepository) : ViewModel() {
    private val _userSignUpState = MutableStateFlow<ResponseState<UserAuth>>(ResponseState.Idle)

    val userSignUpState = _userSignUpState.asStateFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            ResponseState.Idle
        )

    fun postUserSignUp(user: JsonObject) {
        viewModelScope.launch {
            _userSignUpState.emitAll(
                repository.userSignUp(
                    signUpBody = user
                        .toString()
                        .toRequestBody("application/json".toMediaTypeOrNull())
                )
            )
        }
    }
}