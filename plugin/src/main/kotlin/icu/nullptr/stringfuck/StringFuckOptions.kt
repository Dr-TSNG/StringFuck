package icu.nullptr.stringfuck

import org.gradle.api.Project

open class StringFuckOptions(@Suppress("UNUSED_PARAMETER") project: Project) {

    companion object {
        internal lateinit var INSTANCE: StringFuckOptions
    }

    /**
     * Decode key.
     */
    lateinit var key: ByteArray

    fun setKey(str: String) {
        key = str.encodeToByteArray()
    }

    var isPrintDebugInfo = false

    /**
     * If this option is set true, StringFuck will work on debug buildType.
     */
    var isWorkOnDebug = false

    /**
     * Set whether obfuscation list should work as whitelist mode.
     * This controls both annotations and obfuscation list.
     *
     * When set false, only strings in classes of annotated with "StringFuck" or in obfuscationList will be obfuscated.
     * When set true, all strings will be obfuscated except those in classes of annotated with "NoStringFuck" or in obfuscationList.
     */
    var isWhiteList = false

    /**
     * List to control strings in which classes should be obfuscated.
     *
     * If using annotations, you can leave it empty.
     */
    var obfuscationList = setOf<String>()

    /**
     * Method to encrypt strings. This should be defined in build.gradle(.kts).
     *
     * Leave it null to use the default xor encryptor.
     */
    var encryptMethod: ((String) -> ByteArray)? = null


    /**
     * Classpath of the method to decrypt strings. This should be define in app src.
     *
     * Leave it null to use the default xor decryptor.
     */
    var decryptMethodClassPath: String? = null
}
