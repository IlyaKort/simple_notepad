package com.code.korti.simplenotepad.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.code.korti.simplenotepad.R
import com.code.korti.simplenotepad.data.db.models.Color
import com.code.korti.simplenotepad.data.db.models.Note
import com.code.korti.simplenotepad.databinding.ItemNoteBinding

class NoteViewHolder (
    private val binding: ItemNoteBinding,
    listener: NoteAdapterListener,
    private val context: Context
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.run {
            this.listener = listener
        }
    }

    private var currentId: Long? = null

    @SuppressLint("NewApi")
    fun bind(note: Note) {
        binding.note = note
        currentId = note.id
        binding.bodyTextView.text = note.body
        binding.titleTextView.text = note.title
        binding.constraintLayout.setBackgroundColor(context.getColor(colorDetection(note.color)))
    }

    private fun colorDetection(color: Color): Int {
        return when(color){
            Color.WHITE -> R.color.white
            Color.GREEN -> R.color.green
            Color.BLUE -> R.color.blue
            Color.RED -> R.color.red
        }
    }
}