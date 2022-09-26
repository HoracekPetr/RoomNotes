package com.horacek.roomtodo.domain

import com.horacek.roomtodo.data.local.model.Note
import com.horacek.roomtodo.data.local.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchNotesUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(searchQuery: String): Flow<List<Note>> {
        return repository.searchNotes(searchQuery = searchQuery).map { notesDtos ->
            notesDtos.map { it.toNote() }
        }
    }
}