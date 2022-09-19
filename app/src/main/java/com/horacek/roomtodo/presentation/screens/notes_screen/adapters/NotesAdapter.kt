package com.horacek.roomtodo.presentation.screens.notes_screen.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.horacek.roomtodo.R
import com.horacek.roomtodo.data.local.model.Note

class NotesAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var onItemClickListener: ((Note) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return ViewHolder(v = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.note.setCardBackgroundColor(notes[position].color ?: Color.WHITE)
        holder.title.text = notes[position].title
        holder.date.text = notes[position].date
        holder.content.text = notes[position].content

        holder.note.setOnClickListener {
            onItemClickListener?.let { click ->
                click(notes[position])
            }
        }
    }

    override fun getItemCount(): Int = notes.size

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val note: CardView = v.findViewById(R.id.cv_note)
        val title: TextView = v.findViewById(R.id.tv_title)
        val date: TextView = v.findViewById(R.id.tv_date)
        val content: TextView = v.findViewById(R.id.tv_content)
    }

    fun setOnItemClickListener(onItemClickListener: ((Note) -> Unit)?) {
        this.onItemClickListener = onItemClickListener
    }
}