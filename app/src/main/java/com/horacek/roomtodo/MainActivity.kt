package com.horacek.roomtodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.horacek.roomtodo.presentation.screens.insert_note_screen.InsertNotesViewModel
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}