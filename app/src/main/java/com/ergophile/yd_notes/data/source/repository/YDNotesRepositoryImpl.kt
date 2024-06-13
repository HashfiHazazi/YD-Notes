package com.ergophile.yd_notes.data.source.repository

import com.ergophile.yd_notes.data.source.remote.ApiInterface
import com.ergophile.yd_notes.data.source.remote.model.user_auth.UserAuth
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModel
import com.rmaprojects.apirequeststate.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun addNewNote(newNoteBody: RequestBody): Flow<ResponseState<Boolean>> =
        flow {
            emit(ResponseState.Loading)
            try {
                val noteBody = apiInterface.addNewNote(newNoteBody)
                emit(ResponseState.Success(true))
            } catch (e: Exception) {
                emit(ResponseState.Error(e.message.toString()))
            }
        }

    override fun updateNote(
        idNote: String,
        userUid: String,
        updateBody: RequestBody
    ): Flow<ResponseState<Boolean>> = flow {
        emit(ResponseState.Loading)
        try {
            val noteUpdate = apiInterface.updateNote(id = idNote, userUid = userUid,body = updateBody)
            emit(ResponseState.Success(true))
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

    override fun deleteNote(idNote: String): Flow<ResponseState<Unit?>> = flow {
        emit(ResponseState.Loading)
        try {
            val noteDelete = apiInterface.deleteNote(id = idNote)
            emit(ResponseState.Success(noteDelete))
        } catch (e: Exception) {
            emit(ResponseState.Error(e.message.toString()))
        }
    }

}