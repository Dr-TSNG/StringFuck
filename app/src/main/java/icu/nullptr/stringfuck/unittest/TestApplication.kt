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

    private fun test() {
        Log.d(TAG, helloWorld)
        Log.d(TAG, Test.test1)
        Log.d(TAG, Test().test2)
        Log.d(TAG, Test.test3)
        Log.d(TAG, Test.test4)
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "-- Before Init --")
        test()
        StringFuck.init()
        Log.d(TAG, "-- After Init --")
        test()
    }
}
