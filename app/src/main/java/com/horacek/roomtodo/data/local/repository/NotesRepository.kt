package com.horacek.roomtodo.data.local.repository

import com.horacek.roomtodo.data.local.model.NoteDto
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun getAllNotes(): Flow<List<NoteDto>>
    suspend fun insertNote(note: NoteDto)
    suspend fun loadOneItem(itemId: Int): NoteDto
}