package com.horacek.roomtodo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.horacek.roomtodo.data.local.dao.NotesDao
import com.horacek.roomtodo.data.local.model.NoteDto

@Database(entities = [NoteDto::class], version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}