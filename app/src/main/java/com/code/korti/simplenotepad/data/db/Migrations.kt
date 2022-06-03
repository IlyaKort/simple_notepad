package com.code.korti.simplenotepad.data.db

import android.util.Log
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2){
    override fun migrate(database: SupportSQLiteDatabase) {
        Log.d("MIGRATION_1_2", "migration 1-2 start")
        database.execSQL("ALTER TABLE note ADD COLUMN color TEXT NOT NULL DEFAULT 'WHITE'")
        Log.d("MIGRATION_1_2", "migration 1-2 success")
    }
}