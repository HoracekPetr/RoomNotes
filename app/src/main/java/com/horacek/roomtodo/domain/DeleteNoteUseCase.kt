package com.horacek.roomtodo.domain

import com.horacek.roomtodo.data.local.model.Note
import com.horacek.roomtodo.data.local.repository.NotesRepository

class DeleteNoteUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteOneItem(noteDto = note.toNoteDto())
    }
}