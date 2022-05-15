package icu.nullptr.stringfuck.code

import icu.nullptr.stringfuck.StringFuckOptions
import javassist.ClassPool
import javassist.CtField
import javassist.CtMethod
import javassist.Modifier
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

internal abstract class ClassGeneratorTask : DefaultTask() {

    @get:OutputDirectory
    abstract val output: DirectoryProperty

    @TaskAction
    fun taskAction() {
        val pool = ClassPool.getDefault()
        pool.makeClass(StringFuckOptions.INSTANCE.decryptMethodClassPath).run {
            addMethod(CtMethod.make("public static String decrypt(byte[] dummy) { return null; }", this))
        }
        pool.makeClass("icu.nullptr.stringfuck.Config").run {
            modifiers = Modifier.PUBLIC and Modifier.FINAL
            addField(CtField.make("public static Class decryptorClass = ${StringFuckOptions.INSTANCE.decryptMethodClassPath}.class;", this))
            addField(CtField.make("public static byte[] key = new byte[] {${StringFuckOptions.INSTANCE.key.joinToString(",")}};", this))
            val dummyStatement = "${StringFuckOptions.INSTANCE.decryptMethodClassPath}.decrypt(new byte[0]);"
            makeClassInitializer().setBody("{$dummyStatement;$dummyStatement;}")
            writeFile(output.get().asFile.absolutePath)
        }
    }
}
