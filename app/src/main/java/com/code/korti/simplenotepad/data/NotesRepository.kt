package com.code.korti.simplenotepad.data

import com.code.korti.simplenotepad.data.db.Database
import com.code.korti.simplenotepad.data.db.models.Note

class NotesRepository {

    private val noteDao = Database.instance.noteDao()

    suspend fun saveNote(note: List<Note>){
        noteDao.insertNotes(note)
    }

    suspend fun removeNote(note: Note){
        noteDao.removeNote(note)
    }

    suspend fun getAllNotes(): List<Note>{
        return noteDao.getAllNotes()
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun getNoteById(noteId: Long): Note?{
        return noteDao.getNoteById(noteId)
    }
}