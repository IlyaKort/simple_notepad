package com.code.korti.simplenotepad.data.db

import androidx.room.*
import com.code.korti.simplenotepad.data.db.models.Note
import com.code.korti.simplenotepad.data.db.models.contract.NoteContract

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(notes: List<Note>)

    @Query("SELECT * FROM ${NoteContract.TABLE_NAME} ORDER BY ${NoteContract.Columns.TIME} DESC")
    suspend fun getAllNotes(): List<Note>


    @Query(
        "SELECT * FROM ${NoteContract.TABLE_NAME} WHERE ${
            NoteContract.Columns.ID} = :noteId"
    )
    suspend fun getNoteById(noteId: Long): Note?

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun removeNote(note: Note)

}