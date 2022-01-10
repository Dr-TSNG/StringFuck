package icu.nullptr.stringfuck.unittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "UnitTest"
        const val helloWorld = "Hello World"
    }

    private fun test() {
        Log.d(TAG, helloWorld)
        Log.d(TAG, Test.test0)
        Log.d(TAG, Test.test1)
        Log.d(TAG, Test().test2)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
    }
}
