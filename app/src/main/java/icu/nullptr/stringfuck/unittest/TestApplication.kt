package icu.nullptr.stringfuck.unittest

import android.app.Application
import android.util.Log

class TestApplication : Application() {

    companion object {
        const val helloWorld = "Hello World"
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("UnitTest", helloWorld)
    }
}
