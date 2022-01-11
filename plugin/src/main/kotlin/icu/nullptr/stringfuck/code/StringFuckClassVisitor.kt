package icu.nullptr.stringfuck.code

import icu.nullptr.stringfuck.StringFuckOptions
import icu.nullptr.stringfuck.util.Logger
import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.*

class StringFuckClassVisitor(api: Int, cv: ClassVisitor) : ClassVisitor(api, cv) {

    private lateinit var className: String

    private var visitedStaticBlock = false
    private val stringMap = mutableMapOf<String, String>()
    private val logBuffer = mutableListOf<String>()

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    override fun visitField(access: Int, name: String, descriptor: String, signature: String?, value: Any?): FieldVisitor? {
        if (descriptor == "Ljava/lang/String;" && value is String) {
            if (StringFuckOptions.INSTANCE.isPrintDebugInfo) logBuffer += "- Encrypt $name"
            stringMap[name] = StringFuckOptions.INSTANCE.encryptMethod!!(value).decodeToString()
            return super.visitField(access, name, descriptor, signature, null)
        }
        return super.visitField(access, name, descriptor, signature, value)
    }

    override fun visitMethod(access: Int, name: String, descriptor: String, signature: String?, exceptions: Array<out String>?): MethodVisitor {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        return if (name == "<clinit>") {
            visitedStaticBlock = true
            StringFuckMethodVisitor(mv, true)
        } else {
            StringFuckMethodVisitor(mv, false)
        }
    }

    override fun visitEnd() {
        if (!visitedStaticBlock) {
            val mv = super.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null)
            val smv = StringFuckMethodVisitor(mv, true)
            smv.visitCode()
            smv.visitInsn(RETURN)
            smv.visitMaxs(0, 0)
            smv.visitEnd()
        }
        super.visitEnd()
        if (logBuffer.isNotEmpty()) {
            Logger.debug("Instrument $className")
            logBuffer.forEach { Logger.debug(it) }
        }
    }

    private inner class StringFuckMethodVisitor(mv: MethodVisitor, private val clinit: Boolean) : MethodVisitor(api, mv) {

        private var modified = false

        private fun writeEncrypted(encrypted: String) {
            modified = true
            super.visitFieldInsn(GETSTATIC, "icu/nullptr/stringfuck/Stub", "instance", "Licu/nullptr/stringfuck/Stub;")
            super.visitLdcInsn(encrypted)
            super.visitMethodInsn(INVOKEVIRTUAL, "icu/nullptr/stringfuck/Stub", "decrypt", "(Ljava/lang/String;)Ljava/lang/String;", false)
        }

        override fun visitLdcInsn(value: Any) {
            if (value is String) {
                if (StringFuckOptions.INSTANCE.isPrintDebugInfo) logBuffer += "- Encrypt LDC \"$value\""
                val encrypted = StringFuckOptions.INSTANCE.encryptMethod!!(value).decodeToString()
                writeEncrypted(encrypted)
            } else super.visitLdcInsn(value)
        }

        override fun visitCode() {
            if (clinit) stringMap.forEach {
                writeEncrypted(it.value)
                super.visitFieldInsn(PUTSTATIC, className, it.key, "Ljava/lang/String;")
            }
            super.visitCode()
        }

        override fun visitMaxs(maxStack: Int, maxLocals: Int) {
            if (!modified) super.visitMaxs(maxStack, maxLocals)
            else super.visitMaxs((maxStack + 1).coerceAtLeast(2), maxLocals)
        }
    }
}
