package icu.nullptr.stringfuck

import com.android.build.gradle.AppExtension
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.*
import java.util.*

@SuppressWarnings("unused")
class StringFuckPlugin : Plugin<Project> {

    companion object {
        const val PLUGIN_NAME = "stringFuck"
    }

    private lateinit var mStringFuckOptions: StringFuckOptions

    override fun apply(project: Project) {
        project.configurations.create(PLUGIN_NAME)
        mStringFuckOptions = project.extensions.create(PLUGIN_NAME, StringFuckOptions::class.java, project)

        val android = project.extensions.findByType(AppExtension::class.java)
        if (android != null) {
            project.afterEvaluate {
                android.applicationVariants.forEach { variant ->
                    if (mStringFuckOptions.obfuscateWhenDebug || !variant.buildType.isDebuggable) {
                        createObfuscateTask(project, variant)
                    }
                }
            }
        }
    }

    private fun createObfuscateTask(project: Project, variant: ApplicationVariant) {
        val variantCapped = variant.name.capitalize(Locale.ROOT)
        val variantLowered = variant.name.lowercase(Locale.ROOT)

        val mergeDexTasks = mutableListOf<Task>().apply {
            addTaskIfExists(project, "mergeDex${variantCapped}")
            addTaskIfExists(project, "mergeExtDex${variantCapped}")
            addTaskIfExists(project, "mergeLibDex${variantCapped}")
            addTaskIfExists(project, "mergeProjectDex${variantCapped}")
            addTaskIfExists(project, "minify${variantCapped}WithR8")
        }
        if (mergeDexTasks.isEmpty()) throw IllegalStateException("Merge dex task not found")

        val packageTasks = mutableListOf<Task>().apply {
            addTaskIfExists(project, "package${variantCapped}")
            addTaskIfExists(project, "package${variantCapped}Bundle")
        }
        if (packageTasks.isEmpty()) throw IllegalStateException("Package task not found")

        project.tasks.create("obfuscate${variantCapped}StringTask") { thisTask ->
            thisTask.dependsOn(mergeDexTasks)
            packageTasks.forEach { it.dependsOn(thisTask) }

            thisTask.doLast {
                var dexDir = project.buildDir.resolve("intermediates/dex/${variantLowered}")
                if (!dexDir.exists()) dexDir = project.buildDir.resolve("intermediates/dex/${variantCapped}")
                if (!dexDir.exists()) throw IllegalStateException("Dex dir not found")
                println("Dex dir is $dexDir")
                dexDir.walk().forEach {
                    if (it.name.endsWith(".dex")) {
                        println("StringFuck: Obfuscate ${it.toRelativeString(dexDir)}")
                        // TODO: Deal with dex
                    }
                }
            }
        }
    }

    private fun MutableList<Task>.addTaskIfExists(project: Project, name: String) {
        try {
            this.add(project.tasks.getByName(name))
        } catch (ignored: UnknownTaskException) {
        }
    }
}
