package com.ergophile.yd_notes.data.source.remote.model.user_auth


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("app_metadata")
    val appMetadata: AppMetadata,
    @SerializedName("aud")
    val aud: String,
    @SerializedName("confirmed_at")
    val confirmedAt: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_confirmed_at")
    val emailConfirmedAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("identities")
    val identities: List<Identity>,
    @SerializedName("is_anonymous")
    val isAnonymous: Boolean,
    @SerializedName("last_sign_in_at")
    val lastSignInAt: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_metadata")
    val userMetadata: UserMetadata
)