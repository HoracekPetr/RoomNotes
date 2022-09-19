package com.horacek.roomtodo.data.local.model

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteDto(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val title: String? = null,
    val content: String? = null,
    val date: String? = null,
    val color: Int = Color.parseColor("#ffffff")
){
    fun toNote(): Note{
        return Note(
            id = this.id,
            title = this.title,
            content = this.content,
            date = this.date,
            color = this.color
        )
    }
}

data class Note(
    val id: Int? = 0,
    val title: String? = "",
    val content: String? = "",
    val date: String? = "",
    val color: Int? = Color.parseColor("#ffffff")
)

