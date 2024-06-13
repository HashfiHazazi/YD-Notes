package com.ergophile.yd_notes.data.source.remote
import com.ergophile.yd_notes.data.source.remote.model.user_auth.UserAuth
import com.ergophile.yd_notes.data.source.remote.model.user_notes.UserNotesModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiInterface {
    @POST("/auth/v1/signup")
    suspend fun userSignUp(
        @Body body: RequestBody,
    ): UserAuth

    @POST("/auth/v1/token?grant_type=password")
    suspend fun userLogin(
        @Body body: RequestBody
    ): UserAuth

    @GET("/rest/v1/User Notes")
    suspend fun getAllNotes(
        @Query("user_uuid_notes")userUid: String ,
        @Query("select") select: String = "*"
    ): UserNotesModel

    @GET("/rest/v1/User Notes")
    suspend fun getDetailNotes(
        @Query("user_uuid_notes") userUid: String,
        @Query("id") id: String,
        @Query("select") select: String = "*"
    ): UserNotesModel

    @POST("/rest/v1/User Notes")
    suspend fun addNewNote(
        @Body body: RequestBody
    )

    @PATCH("/rest/v1/User Notes")
    suspend fun updateNote(
        @Query("id") id: String,
        @Query("user_uuid_notes") userUid: String,
        @Body body: RequestBody
    )

    @DELETE("/rest/v1/User Notes")
    suspend fun deleteNote(
        @Query("id") id: String
    ): Unit?
}