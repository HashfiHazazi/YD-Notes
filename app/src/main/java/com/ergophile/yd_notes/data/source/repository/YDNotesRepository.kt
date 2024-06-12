package com.ergophile.yd_notes.data.source.repository

import com.ergophile.yd_notes.data.source.remote.model.user_account.UserAccountModel
import com.ergophile.yd_notes.data.source.remote.model.user_account.UserAccountModelItem
import com.ergophile.yd_notes.data.source.remote.model.user_auth.User
import com.ergophile.yd_notes.data.source.remote.model.user_auth.UserAuth
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModel
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModelItem
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.JsonObject
import okhttp3.RequestBody

interface YDNotesRepository {
    fun userSignUp(signUpBody: RequestBody): Flow<ResponseState<UserAuth>>

    fun userLogin(loginBody: RequestBody): Flow<ResponseState<UserAuth>>

    fun getNote(userUid: String): Flow<ResponseState<UserNotesModel>>

    fun addNewNote(newNoteBody: Map<String, Any>): Flow<ResponseState<UserNotesModelItem>>

    fun updateNote(idNote: String, updateBody: RequestBody): Flow<ResponseState<UserNotesModelItem>>

    fun getDetailNote(idNote: String, userUid: String): Flow<ResponseState<UserNotesModel>>

    fun deleteNote(idNote: String): Flow<ResponseState<Boolean>>
}