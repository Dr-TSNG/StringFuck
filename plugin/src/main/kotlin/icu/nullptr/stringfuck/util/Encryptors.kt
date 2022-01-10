package icu.nullptr.stringfuck.util

import icu.nullptr.stringfuck.StringFuckOptions
import kotlin.experimental.xor

object Encryptors {

    val xor: (String) -> ByteArray = { plainText ->
        val cypherBytes = plainText.encodeToByteArray()
        var j = 0
        for (i in cypherBytes.indices) {
            cypherBytes[i] = cypherBytes[i] xor StringFuckOptions.INSTANCE.key[j]
            j = (j + 1) % StringFuckOptions.INSTANCE.key.size
        }
        cypherBytes
    }
}
