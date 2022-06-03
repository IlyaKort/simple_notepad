package com.code.korti.simplenotepad.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.code.korti.simplenotepad.data.db.models.Note

@Database(
    entities = [
        Note::class,
    ], version = KoriDatabase.DB_VERSION
)
abstract class KoriDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        const val DB_VERSION = 2
        const val DB_NAME = "my-database"
    }
}
