package com.ergophile.yd_notes

import android.app.Application
import com.ergophile.yd_notes.di.AppModule
import com.ergophile.yd_notes.di.AppModuleImpl

class YDNotesApplication: Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl()
    }
}