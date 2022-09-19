package com.horacek.roomtodo.domain

import com.horacek.roomtodo.data.local.model.Note
import com.horacek.roomtodo.data.local.model.NoteDto
import com.horacek.roomtodo.data.local.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach

class GetAllNotesUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(): Flow<List<Note>> = repository.getAllNotes().map { notesDto ->
        notesDto.map { it.toNote() }
    }
}