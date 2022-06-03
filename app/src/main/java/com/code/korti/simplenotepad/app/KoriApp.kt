package com.code.korti.simplenotepad.app

import android.app.Application
import com.code.korti.simplenotepad.data.db.Database
import timber.log.Timber

class KoriApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Database.init(this)
    }
}