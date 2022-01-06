package icu.nullptr.stringfuck

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import icu.nullptr.stringfuck.util.Logger
import org.objectweb.asm.ClassVisitor

@Suppress("UnstableApiUsage")
abstract class StringFuckClassVisitor : AsmClassVisitorFactory<InstrumentationParameters> {

    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        Logger.debug("StringFuck: Instrument ${classContext.currentClassData.className}")
        // TODO: Implementation
        return nextClassVisitor
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
        return when (StringFuckOptions.INSTANCE.isWhiteList) {
            true -> !classData.classAnnotations.contains("NoStringFuck") &&
                    !StringFuckOptions.INSTANCE.obfuscationList.contains(classData.className)
            false -> classData.classAnnotations.contains("StringFuck") ||
                    StringFuckOptions.INSTANCE.obfuscationList.contains(classData.className)
        }
    }
}
