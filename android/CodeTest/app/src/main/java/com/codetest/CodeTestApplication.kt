package com.codetest

import android.app.Application
import android.content.Context
import com.codetest.di.applicationModule
import com.codetest.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CodeTestApplication : Application() {
    companion object {
        private var context: Context? = null
        fun appContext(): Context? {
            return CodeTestApplication.context
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
            androidLogger()
            androidContext(this@CodeTestApplication)
            modules(applicationModule, networkModule)
        }
    }
}
