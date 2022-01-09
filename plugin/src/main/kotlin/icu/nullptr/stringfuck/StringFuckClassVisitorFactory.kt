package icu.nullptr.stringfuck

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import icu.nullptr.stringfuck.code.StringFuckClassVisitor
import icu.nullptr.stringfuck.util.Logger
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.Opcodes

@Suppress("UnstableApiUsage")
abstract class StringFuckClassVisitorFactory : AsmClassVisitorFactory<InstrumentationParameters> {

    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        Logger.debug("Instrument ${classContext.currentClassData.className}")
        return StringFuckClassVisitor(Opcodes.ASM7, nextClassVisitor)
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return when (StringFuckOptions.INSTANCE.isWhiteList) {
            true -> !classData.classAnnotations.contains("NoStringFuck") &&
                    !StringFuckOptions.INSTANCE.obfuscationList.parallelStream().anyMatch { classData.className.startsWith(it) }
            false -> classData.classAnnotations.contains("StringFuck") ||
                    StringFuckOptions.INSTANCE.obfuscationList.parallelStream().anyMatch { classData.className.startsWith(it) }
        }
    }
}
