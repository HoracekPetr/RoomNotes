package com.horacek.roomtodo.data.local.dao

import androidx.room.*
import com.horacek.roomtodo.data.local.model.Note
import com.horacek.roomtodo.data.local.model.NoteDto
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getAllNotes(): Flow<List<NoteDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note: NoteDto)

    @Query("SELECT * FROM notes WHERE id = (:itemId)")
    suspend fun loadOneItem(itemId: Int): NoteDto

    @Update
    suspend fun updateOneItem(noteDto: NoteDto)

    @Delete
    suspend fun deleteOneItem(noteDto: NoteDto)

}