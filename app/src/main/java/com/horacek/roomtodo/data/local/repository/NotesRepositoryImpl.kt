package com.horacek.roomtodo.data.local.repository

import com.horacek.roomtodo.data.local.dao.NotesDao
import com.horacek.roomtodo.data.local.model.NoteDto
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val dao: NotesDao
): NotesRepository {
    override suspend fun getAllNotes(): Flow<List<NoteDto>> {
        return dao.getAllNotes()
    }

    override suspend fun insertNote(note: NoteDto) {
        dao.addNote(note = note)
    }

    override suspend fun loadOneItem(itemId: Int): NoteDto {
        return dao.loadOneItem(itemId = itemId)
    }
}