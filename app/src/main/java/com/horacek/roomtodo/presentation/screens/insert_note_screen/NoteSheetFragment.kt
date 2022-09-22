package com.horacek.roomtodo.presentation.screens.insert_note_screen

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.horacek.roomtodo.R
import com.horacek.roomtodo.databinding.FragmentNoteSheetBinding
import com.horacek.roomtodo.presentation.screens.insert_note_screen.adapters.NoteBackgroundAdapter
import com.horacek.roomtodo.presentation.screens.insert_note_screen.util.NoteSheetUtil
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteSheetFragment : BottomSheetDialogFragment() {

    private val viewModel by sharedStateViewModel<InsertNotesViewModel>()

    private var _binding: FragmentNoteSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteSheetBinding.inflate(inflater, container, false)

        val noteBackgroundAdapter = NoteBackgroundAdapter(NoteSheetUtil().backgrounds){
            viewModel.setBackgroundColor(it)
        }

        binding.rvNoteBackgrounds.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvNoteBackgrounds.adapter = noteBackgroundAdapter

        return binding.root
    }
}
