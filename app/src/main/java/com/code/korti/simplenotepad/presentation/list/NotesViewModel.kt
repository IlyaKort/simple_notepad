package com.code.korti.simplenotepad.presentation.list

import android.app.Application
import androidx.lifecycle.*
import com.code.korti.simplenotepad.R
import com.code.korti.simplenotepad.data.NotesRepository
import com.code.korti.simplenotepad.data.db.models.Color
import com.code.korti.simplenotepad.data.db.models.Note
import kotlinx.coroutines.launch
import timber.log.Timber

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application

    private val repository = NotesRepository()

    private val notesMutableLiveData = MutableLiveData<List<Note>>()

    val notesLiveData: LiveData<List<Note>>
        get() = notesMutableLiveData

    fun loadList() {
        viewModelScope.launch {
            try {
                notesMutableLiveData.postValue(repository.getAllNotes())
            } catch (t: Throwable) {
                Timber.e(t)
                notesMutableLiveData.postValue(emptyList())
            }
        }
    }

    fun removeNoteById(
        noteId: Long,
        showResult: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val note = repository.getNoteById(noteId)!!
                repository.removeNote(note)
                loadList()
                showResult(context.getString(R.string.note_is_delete))
            } catch (t: Throwable) {
                Timber.e(t)
                showResult(context.getString(R.string.delete_error))
            }
        }
    }

    fun updateNote(
        noteId: Long,
        color: Color
    ){
        viewModelScope.launch {
            try {
                val note = repository.getNoteById(noteId)!!
                repository.updateNote(
                    Note(
                        noteId,
                        note.title,
                        note.body,
                        note.time,
                        color
                    )
                )
                loadList()
            } catch (t: Throwable) {
                Timber.e(t)
            }
        }
    }
}