package com.codetest.main

import android.app.Application
import org.koin.core.context.startKoin

class CodeTestApplicationTest : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {  }
    }
}