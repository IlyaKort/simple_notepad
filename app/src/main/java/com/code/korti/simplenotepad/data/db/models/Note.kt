package com.code.korti.simplenotepad.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.code.korti.simplenotepad.data.db.models.contract.NoteContract

@Entity(tableName = NoteContract.TABLE_NAME)
data class Note(
    @PrimaryKey
    @ColumnInfo(name = NoteContract.Columns.ID)
    val id : Long,
    @ColumnInfo(name = NoteContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = NoteContract.Columns.BODY)
    val body: String,
    @ColumnInfo(name = NoteContract.Columns.TIME)
    val time: Long,
    @ColumnInfo(name = NoteContract.Columns.COLOR)
    val color: Color
)