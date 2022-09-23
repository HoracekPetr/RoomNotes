package com.horacek.roomtodo.presentation.screens.insert_note_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.horacek.roomtodo.data.local.model.Note
import com.horacek.roomtodo.domain.InsertNoteUseCase
import com.horacek.roomtodo.domain.LoadOneItemUseCase
import com.horacek.roomtodo.presentation.screens.insert_note_screen.model.InsertNotesMode
import com.horacek.roomtodo.util.extensions.getCurrentDateTime
import com.horacek.roomtodo.util.extensions.toString
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class InsertNotesViewModel(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val loadOneItemUseCase: LoadOneItemUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteState = MutableStateFlow(Note())
    val noteState: StateFlow<Note> = _noteState.asStateFlow()

    private val _insertNoteModeState = MutableStateFlow(InsertNotesMode.INSERT)
    val insertNoteModeState: StateFlow<InsertNotesMode> = _insertNoteModeState

    init {
        val id = InsertNotesFragmentArgs.fromSavedStateHandle(savedStateHandle).noteId
        println("Init VM")
        println("ID $id")
        if(id > 0){
            loadOneItem(id)
        }
    }

    private fun loadOneItem(itemId: Int) {
        viewModelScope.launch {
            val note = loadOneItemUseCase(itemId)
            _noteState.update { note }
        }
    }

    fun setTitleText(title: String) {
        _noteState.update { note ->
            note.copy(
                title = title
            )
        }
    }

    fun setContentText(content: String) {
        _noteState.update { note ->
            note.copy(
                content = content
            )
        }
    }

    fun setBackgroundColor(color: Int) {
        _noteState.update { note ->
            note.copy(
                color = color
            )
        }
    }

    fun addNote() {
        _noteState.update { note ->
            note.copy(
                date = getCurrentDateTime().toString(format = "dd/MM/yyyy")
            )
        }

        viewModelScope.launch {
            insertNoteUseCase(_noteState.value)
        }
    }
}