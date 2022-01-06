package icu.nullptr.stringfuck.util

import icu.nullptr.stringfuck.StringFuckOptions

internal object Logger {

    fun debug(message: String) {
        if (StringFuckOptions.INSTANCE.isPrintDebugInfo)
            println(message)
    }

    fun info(message: String) {
        println(message)
    }
}
