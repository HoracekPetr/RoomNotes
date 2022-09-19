package com.horacek.roomtodo.presentation.screens.insert_note_screen.adapters

import android.graphics.Color
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.horacek.roomtodo.R
import com.horacek.roomtodo.data.local.model.Note

class NoteBackgroundAdapter(
    private val noteBackgrounds: List<String>,
    private val setNoteBackground: (Int) -> Unit
) : RecyclerView.Adapter<NoteBackgroundAdapter.ViewHolder>() {

    private var selected = 0
    var selectedBackgroundColor = Color.parseColor(noteBackgrounds[selected])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_background_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteBackgroundCard.setCardBackgroundColor(Color.parseColor(noteBackgrounds[position]))
        if (selected == position) {
            holder.noteBackgroundBorder.visibility = View.VISIBLE
        } else {
            holder.noteBackgroundBorder.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = noteBackgrounds.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val noteBackgroundBorder: CardView = v.findViewById(R.id.cv_note_border)
        val noteBackgroundCard: CardView = v.findViewById(R.id.cv_note_bg)

        init {
            noteBackgroundCard.setOnClickListener {
                setSingleSelection(adapterPosition)
                selectedBackgroundColor = Color.parseColor(noteBackgrounds[adapterPosition])
                setNoteBackground(selectedBackgroundColor)
            }
        }
    }

    private fun setSingleSelection(adapterPosition: Int) {
        if (adapterPosition == RecyclerView.NO_POSITION) return

        notifyItemChanged(selected)
        selected = adapterPosition
        notifyItemChanged(selected)
    }
}