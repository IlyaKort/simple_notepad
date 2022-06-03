package com.code.korti.simplenotepad.presentation.list.dialog

import com.code.korti.simplenotepad.data.db.models.Color

interface ColorListener {
    fun changeColorOfNote(color: Color, noteId: Long)
}