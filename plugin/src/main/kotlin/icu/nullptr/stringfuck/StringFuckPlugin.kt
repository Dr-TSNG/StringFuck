package icu.nullptr.stringfuck

import com.android.build.api.artifact.MultipleArtifact
import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.Variant
import icu.nullptr.stringfuck.code.ClassGeneratorTask
import icu.nullptr.stringfuck.util.Encryptors
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.*

@Suppress("unused", "UnstableApiUsage")
class StringFuckPlugin : Plugin<Project> {

    companion object {
        const val PLUGIN_NAME = "stringFuck"
    }

    override fun apply(project: Project) {
        project.configurations.create(PLUGIN_NAME)
        StringFuckOptions.INSTANCE = project.extensions.create(PLUGIN_NAME, StringFuckOptions::class.java, project)
        val options = StringFuckOptions.INSTANCE

        val androidComponents = project.extensions.findByType(AndroidComponentsExtension::class.java)
        androidComponents?.onVariants { variant ->
            if (options.isWorkOnDebug || variant.buildType?.contains("debug") == false) {
                variant.instrumentation.transformClassesWith(StringFuckClassVisitorFactory::class.java, InstrumentationScope.ALL) {}
                variant.instrumentation.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
                generateSources(project, variant)
            }
        }

        project.afterEvaluate {
            if (options.encryptMethod == null) options.encryptMethod = Encryptors.xor
            if (options.decryptMethodClassPath == null) options.decryptMethodClassPath = "icu.nullptr.stringfuck.Xor"
        }
    }

    private fun generateSources(project: Project, variant: Variant) {
        val variantCapped = variant.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
        val generateTaskProvider = project.tasks.register("generate${variantCapped}StringFuckSources", ClassGeneratorTask::class.java)
        variant.artifacts.use(generateTaskProvider)
            .wiredWith(ClassGeneratorTask::output)
            .toAppendTo(MultipleArtifact.ALL_CLASSES_DIRS)
    }
}
