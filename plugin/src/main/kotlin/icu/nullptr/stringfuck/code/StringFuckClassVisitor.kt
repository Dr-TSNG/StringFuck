package icu.nullptr.stringfuck.code

import icu.nullptr.stringfuck.StringFuckOptions
import icu.nullptr.stringfuck.util.Logger
import org.objectweb.asm.*
import org.objectweb.asm.Opcodes.*

class StringFuckClassVisitor(api: Int, cv: ClassVisitor) : ClassVisitor(api, cv) {

    private lateinit var className: String

    private var visitedStaticBlock = false
    private val stringMap = mutableMapOf<String, ByteArray>()

    override fun visit(version: Int, access: Int, name: String, signature: String?, superName: String?, interfaces: Array<out String>?) {
        super.visit(version, access, name, signature, superName, interfaces)
        className = name
    }

    override fun visitField(access: Int, name: String, descriptor: String, signature: String?, value: Any?): FieldVisitor? {
        if (descriptor == "Ljava/lang/String;" && value is String) {
            Logger.debug("Encrypt $name")
            val encrypted = StringFuckOptions.INSTANCE.encryptMethod!!(value)
            stringMap[name] = encrypted
            return super.visitField(access, name, descriptor, signature, null)
        }
        return super.visitField(access, name, descriptor, signature, value)
    }

    override fun visitMethod(access: Int, name: String, descriptor: String, signature: String?, exceptions: Array<out String>?): MethodVisitor? {
        val mv = super.visitMethod(access, name, descriptor, signature, exceptions)
        if (name == "<clinit>" && !visitedStaticBlock) {
            visitedStaticBlock = true
            return StaticBlockMethodVisitor(mv)
        }
        return mv
    }

    override fun visitEnd() {
        if (!visitedStaticBlock) {
            val mv = super.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null)
            val smv = StaticBlockMethodVisitor(mv)
            smv.visitCode()
            smv.visitInsn(RETURN)
            smv.visitMaxs(0, 0)
            smv.visitEnd()
        }
        super.visitEnd()
    }

    private inner class StaticBlockMethodVisitor(mv: MethodVisitor) : MethodVisitor(api, mv) {

        override fun visitCode() {
            super.visitCode()
            stringMap.forEach { (str, bytes) ->
                visitIntInsn(BIPUSH, bytes.size)
                visitIntInsn(NEWARRAY, T_BYTE)
                for (i in bytes.indices) {
                    visitInsn(DUP)
                    visitIntInsn(BIPUSH, i)
                    visitIntInsn(BIPUSH, bytes[i].toInt())
                    visitInsn(BASTORE)
                }
                visitMethodInsn(INVOKESTATIC, "icu/nullptr/stringfuck/Stub", "decrypt", "([B)Ljava/lang/String;", false)
                visitFieldInsn(PUTSTATIC, className, str, "Ljava/lang/String;")
            }
        }

        override fun visitMaxs(maxStack: Int, maxLocals: Int) {
            val stringFuckMaxStack = 4
            val stringFuckMaxLocals = 0
            super.visitMaxs(maxStack.coerceAtLeast(stringFuckMaxStack), maxLocals.coerceAtLeast(stringFuckMaxLocals))
        }
    }
}
