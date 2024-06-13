package com.ergophile.yd_notes.data.source.repository

import com.ergophile.yd_notes.data.source.remote.model.user_auth.UserAuth
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModel
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface YDNotesRepository {
    fun userSignUp(signUpBody: RequestBody): Flow<ResponseState<UserAuth>>

    fun userLogin(loginBody: RequestBody): Flow<ResponseState<UserAuth>>

    fun getNote(userUid: String): Flow<ResponseState<UserNotesModel>>

    fun addNewNote(newNoteBody: RequestBody): Flow<ResponseState<Boolean>>

    fun updateNote(idNote: String,userUid: String, updateBody: RequestBody): Flow<ResponseState<Boolean>>

    fun getDetailNote(idNote: String, userUid: String): Flow<ResponseState<UserNotesModel>>

    fun deleteNote(idNote: String): Flow<ResponseState<Unit?>>
}