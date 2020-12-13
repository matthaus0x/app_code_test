package com.codetest.main

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

class CodeTestApplicationRunner : AndroidJUnitRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, CodeTestApplicationTest::class.java.name, context)
    }
}