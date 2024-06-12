package com.ergophile.yd_notes.data.source.repository

import com.ergophile.yd_notes.data.source.remote.ApiInterface
import com.ergophile.yd_notes.data.source.remote.model.user_account.UserAccountModelItem
import com.ergophile.yd_notes.data.source.remote.model.user_auth.User
import com.ergophile.yd_notes.data.source.remote.model.user_auth.UserAuth
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModel
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModelItem
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.JsonObject
import okhttp3.RequestBody

class YDNotesRepositoryImpl(private val apiInterface: ApiInterface) : YDNotesRepository {
    override fun userSignUp(signUpBody: RequestBody): Flow<ResponseState<UserAuth>> = flow {
        emit(ResponseState.Loading)
        try {
            val signUpResult = apiInterface.userSignUp(body = signUpBody)
            emit(ResponseState.Success(signUpResult))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun userLogin(loginBody: RequestBody): Flow<ResponseState<UserAuth>> = flow {
        emit(ResponseState.Loading)
        try {
            val loginResult = apiInterface.userLogin(loginBody)
            emit(ResponseState.Success(loginResult))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getNote(userUid: String): Flow<ResponseState<UserNotesModel>> = flow {
        emit(ResponseState.Loading)
        try {
            val listNotes = apiInterface.getAllNotes(userUid = userUid)
            emit(ResponseState.Success(listNotes))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun addNewNote(newNoteBody: Map<String, Any>): Flow<ResponseState<UserNotesModelItem>> =
        flow {
            emit(ResponseState.Loading)
            try {
                val noteBody = apiInterface.addNewNote(newNoteBody)
                emit(ResponseState.Success(noteBody))
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override fun updateNote(
        idNote: String,
        updateBody: RequestBody
    ): Flow<ResponseState<UserNotesModelItem>> = flow {
        emit(ResponseState.Loading)
        try {
            val noteUpdate = apiInterface.updateNote(id = idNote, body = updateBody)
            emit(ResponseState.Success(noteUpdate))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun getDetailNote(idNote: String, userUid: String): Flow<ResponseState<UserNotesModel>> = flow {
        emit(ResponseState.Loading)
        try {
            val detailNote = apiInterface.getDetailNotes(id = idNote, userUid = userUid)
            emit(ResponseState.Success(detailNote))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    override fun deleteNote(idNote: String): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val noteDelete = apiInterface.deleteNote(id = idNote)
            emit(ResponseState.Success(noteDelete))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

}