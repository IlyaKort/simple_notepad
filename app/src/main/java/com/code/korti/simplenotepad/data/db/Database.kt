package com.code.korti.simplenotepad.data.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: KoriDatabase
        private set

    fun init (context: Context){
        instance = Room.databaseBuilder(
            context,
            KoriDatabase::class.java,
            KoriDatabase.DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }

}