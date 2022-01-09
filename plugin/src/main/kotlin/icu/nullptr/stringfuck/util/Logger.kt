package icu.nullptr.stringfuck.util

import icu.nullptr.stringfuck.StringFuckOptions

internal object Logger {

    fun debug(message: String) {
        if (StringFuckOptions.INSTANCE.isPrintDebugInfo)
            println("[StringFuck] $message")
    }

    fun info(message: String) {
        println("[StringFuck] $message")
    }
}
