package com.horacek.roomtodo.presentation.screens.notes_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horacek.roomtodo.data.local.model.Note
import com.horacek.roomtodo.domain.GetAllNotesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesScreenViewModel(private val getAllNotesUseCase: GetAllNotesUseCase): ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes

    init {
        viewModelScope.launch {
            getAllNotesUseCase().collect { notes ->
                _notes.value = notes
            }
        }
    }

}