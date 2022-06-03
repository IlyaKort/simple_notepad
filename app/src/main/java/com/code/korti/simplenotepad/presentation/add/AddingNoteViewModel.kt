package com.code.korti.simplenotepad.presentation.add

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code.korti.simplenotepad.data.NotesRepository
import com.code.korti.simplenotepad.data.db.models.Color
import com.code.korti.simplenotepad.data.db.models.Note
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.sql.Timestamp

class AddingNoteViewModel : ViewModel() {

    private val repository = NotesRepository()

    private var currentJob: Job? = null

    @SuppressLint("NewApi")
    fun bind(titleFlow: Flow<String>, bodyFlow: Flow<String>, noteId: Long, color: Color) {
        viewModelScope.launch {

            currentJob = combine(
                titleFlow.onStart { emit("") },
                bodyFlow.onStart { emit("") }
            ) { title, body -> title to body }
                .debounce(500)
                .distinctUntilChanged()
                .mapLatest {
                    if (it.first != "" || it.second != "") {
                        val time = Timestamp(System.currentTimeMillis()).time
                        repository.saveNote(
                            listOf(
                                Note(
                                    noteId,
                                    it.first,
                                    it.second,
                                    time,
                                    color
                                )
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
