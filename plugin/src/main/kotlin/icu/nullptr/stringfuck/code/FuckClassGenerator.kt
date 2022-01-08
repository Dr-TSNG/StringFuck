package icu.nullptr.stringfuck.code

import com.squareup.javawriter.JavaWriter
import icu.nullptr.stringfuck.StringFuckOptions
import java.io.File
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import javax.lang.model.element.Modifier

internal object FuckClassGenerator {

    fun generate(sourceDir: File) {
        val stubFile = sourceDir.resolve("Stub.java")
        JavaWriter(FileWriter(stubFile)).use {
            it.indent = "    "
            it.emitPackage("icu.nullptr.stringfuck")
            it.emitImports(StandardCharsets::class.java)
            it.emitEmptyLine()

            it.beginType("Stub", "class", setOf(Modifier.PUBLIC, Modifier.FINAL))
            it.emitEmptyLine()

            it.emitField(
                "byte[]", "key", setOf(Modifier.PUBLIC, Modifier.STATIC),
                "{${StringFuckOptions.INSTANCE.key!!.joinToString(",")}}"
            )
            it.emitField(
                "Stub", "instance", setOf(Modifier.PUBLIC, Modifier.STATIC),
                "new Stub()"
            )
            it.emitEmptyLine()

            it.beginMethod("String", "decrypt", setOf(Modifier.PUBLIC), "byte[]", "cypherBytes")
            it.emitStatement("byte[] stub = cypherBytes.clone()")
            it.beginControlFlow("for (int i = 1; i < stub.length; i++)")
            it.emitStatement("stub[i] ^= stub[i - 1]")
            it.endControlFlow()
            it.emitStatement("return new String(stub, StandardCharsets.UTF_8)")
            it.endMethod()

            it.endType()
        }
    }
}
