package com.horacek.roomtodo.presentation.screens.notes_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.horacek.roomtodo.R
import com.horacek.roomtodo.data.local.model.Note
import com.horacek.roomtodo.databinding.FragmentNotesBinding
import com.horacek.roomtodo.presentation.screens.notes_screen.adapters.NotesAdapter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NotesFragment : Fragment() {

    private val viewModel by viewModel<NotesScreenViewModel>()

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        binding.fabAdd.setOnClickListener {
            navigateToInsertNoteScreen()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.notes.collect { notes ->
                    setupNotesRecyclerView(notes)
                }
            }
        }
    }

    private fun navigateToInsertNoteScreen(){
        val action = NotesFragmentDirections.actionNotesFragmentToInsertNotesFragment()
        findNavController().navigate(action)
    }

    private fun setupNotesRecyclerView(notes: List<Note>){

        notesAdapter = NotesAdapter(notes)

        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = notesAdapter
        }

        notesAdapter.setOnItemClickListener { note ->
            note.id?.let { id ->
                findNavController().navigate(
                    NotesFragmentDirections.actionNotesFragmentToInsertNotesFragment(noteId = id)
                )
            }
        }
    }
}