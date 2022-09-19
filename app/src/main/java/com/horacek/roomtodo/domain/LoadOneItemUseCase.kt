package com.horacek.roomtodo.domain

import com.horacek.roomtodo.data.local.model.Note
import com.horacek.roomtodo.data.local.repository.NotesRepository

class LoadOneItemUseCase(
    private val repository: NotesRepository
) {
    suspend operator fun invoke(itemId: Int): Note = repository.loadOneItem(itemId = itemId).toNote()
}