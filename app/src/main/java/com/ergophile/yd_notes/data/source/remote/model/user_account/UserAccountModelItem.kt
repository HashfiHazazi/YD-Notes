package com.ergophile.yd_notes.data.source.remote.model.user_account


import com.google.gson.annotations.SerializedName

data class UserAccountModelItem(
    @SerializedName("email_user")
    val emailUser: String,
    @SerializedName("user_uuid")
    val userUuid: String,
    @SerializedName("username")
    val username: String
)