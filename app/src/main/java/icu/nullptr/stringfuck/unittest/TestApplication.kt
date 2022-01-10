package icu.nullptr.stringfuck.unittest

import android.app.Application
import icu.nullptr.stringfuck.StringFuck

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        StringFuck.init()
    }
}
