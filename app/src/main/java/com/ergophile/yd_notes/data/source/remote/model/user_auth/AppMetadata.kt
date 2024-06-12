package com.ergophile.yd_notes.data.source.remote.model.user_auth


import com.google.gson.annotations.SerializedName

data class AppMetadata(
    @SerializedName("provider")
    val provider: String,
    @SerializedName("providers")
    val providers: List<String>
)