package com.ergophile.yd_notes.data.source.remote.model.user_notes


import com.google.gson.annotations.SerializedName

data class UserNotesModelItem(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("notes_content")
    val notesContent: String,
    @SerializedName("subtitle_notes")
    val subtitleNotes: String,
    @SerializedName("title_notes")
    val titleNotes: String,
    @SerializedName("user_uuid_notes")
    val userUuidNotes: String
)