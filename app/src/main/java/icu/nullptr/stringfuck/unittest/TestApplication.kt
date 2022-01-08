package icu.nullptr.stringfuck.unittest

import android.app.Application
import android.util.Log
import icu.nullptr.stringfuck.StringFuck
import icu.nullptr.stringfuck.Stub

class TestApplication : Application() {

    companion object {
        const val TAG = "UnitTest"
        const val helloWorld = "Hello World"
    }

    override fun onCreate() {
        super.onCreate()
        StringFuck.init()
        Log.d(TAG, Stub.instance.decrypt(helloWorld.encodeToByteArray()))
    }
}
