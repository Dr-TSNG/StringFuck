package icu.nullptr.stringfuck

import com.android.build.api.instrumentation.FramesComputationMode
import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused", "UnstableApiUsage")
class StringFuckPlugin : Plugin<Project> {

    companion object {
        const val PLUGIN_NAME = "stringFuck"
    }

    override fun apply(project: Project) {
        project.configurations.create(PLUGIN_NAME)
        StringFuckOptions.INSTANCE = project.extensions.create(PLUGIN_NAME, StringFuckOptions::class.java, project)

        val androidComponents = project.extensions.findByType(AndroidComponentsExtension::class.java)
        androidComponents?.onVariants { variant ->
            if (StringFuckOptions.INSTANCE.isWorkOnDebug || variant.buildType?.contains("debug") == false) {
                variant.transformClassesWith(StringFuckClassVisitor::class.java, InstrumentationScope.ALL) {}
                variant.setAsmFramesComputationMode(FramesComputationMode.COPY_FRAMES)
            }
        }
    }
}
