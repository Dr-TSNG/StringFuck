package icu.nullptr.stringfuck.unittest

import android.app.Application
import android.util.Log
import icu.nullptr.stringfuck.StringFuck

class TestApplication : Application() {

    companion object {
        const val TAG = "UnitTest"
        const val helloWorld = "Hello World"
    }

    private fun test() {
        Log.d(TAG, helloWorld)
        Log.d(TAG, Test.test0)
        Log.d(TAG, Test.test1)
        Log.d(TAG, Test().test2)
        Log.d(TAG, Test.test3)
    }

    override fun onCreate() {
        super.onCreate()
        StringFuck.init()
        test()
    }
}
