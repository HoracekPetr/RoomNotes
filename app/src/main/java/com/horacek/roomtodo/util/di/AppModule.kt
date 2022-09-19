package com.horacek.roomtodo.util.di

import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import com.horacek.roomtodo.data.local.NotesDatabase
import com.horacek.roomtodo.data.local.dao.NotesDao
import com.horacek.roomtodo.data.local.repository.NotesRepository
import com.horacek.roomtodo.data.local.repository.NotesRepositoryImpl
import com.horacek.roomtodo.domain.GetAllNotesUseCase
import com.horacek.roomtodo.domain.InsertNoteUseCase
import com.horacek.roomtodo.domain.LoadOneItemUseCase
import com.horacek.roomtodo.presentation.screens.insert_note_screen.InsertNotesViewModel
import com.horacek.roomtodo.presentation.screens.notes_screen.NotesScreenViewModel
import dagger.Module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.single

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NotesDatabase::class.java,
            "notes-db"
        ).build()
    }

    single {
        val database = get<NotesDatabase>()
        database.notesDao()
    }

    single<NotesRepository>{
        NotesRepositoryImpl(get())
    }

    single{
        InsertNoteUseCase(get())
    }

    single{
        GetAllNotesUseCase(get())
    }

    single {
        LoadOneItemUseCase(get())
    }

    viewModel{
        NotesScreenViewModel(get())
    }


    viewModel{
        InsertNotesViewModel(get(), get(), get())
    }

}