package com.code.korti.simplenotepad.presentation.detail

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.kori.simplenotepad.utils.SingleLiveEvent
import com.code.korti.simplenotepad.data.NotesRepository
import com.code.korti.simplenotepad.data.db.models.Note
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.sql.Timestamp

class NoteDetailViewModel : ViewModel() {

    private val repository = NotesRepository()

    private var currentJob: Job? = null

    private val saveSuccessLiveEvent = SingleLiveEvent<Unit>()

    val saveSuccessLiveData: LiveData<Unit>
        get() = saveSuccessLiveEvent

    @SuppressLint("NewApi")
    fun updateNote(
        titleFlow: Flow<String>,
        bodyFlow: Flow<String>,
        noteId: Long,
        onNote: (title: String, body: String, time: Long) -> Unit
    ) {
        viewModelScope.launch {
            val note = repository.getNoteById(noteId)!!
            onNote(note.title, note.body, note.time)

            currentJob = combine(
                titleFlow.onStart { emit(note.title) },
                bodyFlow.onStart { emit(note.body) }
            ) { title, body -> title to body }
                .debounce(500)
                .distinctUntilChanged()
                .mapLatest {
                    if (it.first != note.title || it.second != note.body) {
                        val time = Timestamp(System.currentTimeMillis()).time
                        repository.updateNote(
                            Note(
                                noteId,
                                it.first,
                                it.second,
                                time,
                                note.color
                            )
                        )
                    }
                }
                .catch {
                    Timber.e(it)
                }
                .launchIn(viewModelScope)
        }
    }

    fun jobCancel() {
        currentJob?.cancel()
    }
}