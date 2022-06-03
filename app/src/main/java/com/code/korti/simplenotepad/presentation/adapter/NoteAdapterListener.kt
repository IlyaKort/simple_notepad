package com.code.korti.simplenotepad.presentation.adapter

import android.view.View
import com.code.korti.simplenotepad.data.db.models.Note

interface NoteAdapterListener {
    fun onNoteClicked(cardView: View, note: Note)
    fun onNoteMenuClicked(v: View, noteId: Long)
}