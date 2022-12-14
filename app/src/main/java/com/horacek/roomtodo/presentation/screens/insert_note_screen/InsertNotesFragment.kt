package com.horacek.roomtodo.presentation.screens.insert_note_screen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.opengl.Visibility
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.horacek.roomtodo.R
import com.horacek.roomtodo.databinding.ActivityMainBinding.inflate
import com.horacek.roomtodo.databinding.FragmentInsertNotesBinding
import com.horacek.roomtodo.presentation.screens.insert_note_screen.model.InsertNotesMode
import com.horacek.roomtodo.util.extensions.sharedArgumentsStateViewModel
import com.horacek.roomtodo.util.extensions.toEditable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedStateViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InsertNotesFragment : Fragment() {

    private val viewModel by sharedArgumentsStateViewModel<InsertNotesViewModel>(
        owner = { this }
    )
    private var _binding: FragmentInsertNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsertNotesBinding.inflate(inflater, container, false)

/*        binding.etTitle.addTextChangedListener {
            val position = binding.etTitle.selectionStart
            viewModel.setTitleText(title = it.toString())
            binding.etTitle.setSelection(position)
        }

        binding.etContent.addTextChangedListener {
            val position = binding.etContent.selectionStart
            viewModel.setContentText(content = it.toString())
            binding.etContent.setSelection(position)
        }*/

        binding.ibBgChange.setOnClickListener {
            viewModel.setTitleAndContentText(
                title = binding.etTitle.text.toString(),
                content = binding.etContent.text.toString()
            )
            NoteSheetFragment().show(childFragmentManager, "NoteSheet")
        }

        binding.ibDone.setOnClickListener {
            when (viewModel.insertNoteModeState.value) {
                InsertNotesMode.INSERT -> viewModel.addNote()
                InsertNotesMode.UPDATE -> viewModel.updateOneItem()
            }

            val action = InsertNotesFragmentDirections.actionInsertNotesFragmentToNotesFragment()
            findNavController().navigate(directions = action)
        }

        when (viewModel.insertNoteModeState.value) {
            InsertNotesMode.INSERT -> {
                binding.ibDelete.visibility = View.GONE
            }
            InsertNotesMode.UPDATE -> {
                binding.ibDelete.visibility = View.VISIBLE
            }
        }

        binding.ibDelete.setOnClickListener {
            viewModel.deleteOneItem()
            val action = InsertNotesFragmentDirections.actionInsertNotesFragmentToNotesFragment()
            findNavController().navigate(directions = action)
        }

        binding.ibAddImage.setOnClickListener {
            requestPermission()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteState.collect { note ->
                    println("Collecting note $note")
                    binding.clLayout.setBackgroundColor(note.color ?: Color.WHITE)
                    binding.etTitle.text = note.title?.toEditable()
                    binding.etContent.text = note.content?.toEditable()
                    note.uriString?.let { uriString ->
                        binding.ivNoteImage.visibility = View.VISIBLE
                        binding.ivNoteImage.setImageURI(Uri.parse(uriString))
                    }
                }
            }
        }
    }

    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewModel.setNoteImageUri(uriString = uri.toString())
            binding.ivNoteImage.setImageURI(uri)
            binding.ivNoteImage.visibility = View.VISIBLE
        }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getImageContent.launch("image/*")
            } else {
                println("Not Granted")
            }
        }

    private fun requestPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                getImageContent.launch("image/*")
            }
            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }
        }
    }
}