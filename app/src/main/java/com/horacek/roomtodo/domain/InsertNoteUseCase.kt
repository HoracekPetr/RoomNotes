package com.horacek.roomtodo.domain

import android.graphics.Color
import com.horacek.roomtodo.data.local.model.Note
import com.horacek.roomtodo.data.local.model.NoteDto
import com.horacek.roomtodo.data.local.repository.NotesRepository

class InsertNoteUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note){
        repository.insertNote(
            NoteDto(
                title = note.title,
                content = note.content,
                date = note.date,
                color = note.color ?: Color.parseColor("#ffffff")
            )
        )
    }
}