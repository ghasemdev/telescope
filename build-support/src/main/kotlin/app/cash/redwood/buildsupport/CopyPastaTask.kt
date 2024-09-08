package app.cash.redwood.buildsupport

import java.nio.charset.StandardCharsets.UTF_8
import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.ProjectLayout
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

abstract class CopyPastaTask @Inject constructor(layout: ProjectLayout) : DefaultTask() {
    @get:Input
    abstract val fileName: Property<String>

    @get:Input
    abstract val packageName: Property<String>

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    init {
        @Suppress("LeakingThis")
        outputDirectory.convention(
            layout.buildDirectory.zip(fileName) { buildDir, fileName ->
                buildDir.dir("generated").dir("source").dir(fileName)
            },
        )
    }

    @TaskAction fun run() {
        val file = "${fileName.get()}.kt"
        val content = CopyPastaTask::class.java.getResourceAsStream(file)!!
            .use { it.readAllBytes().toString(UTF_8) }
            .replace("package com.example\n", "package ${packageName.get()}\n")

        outputDirectory.get().file(file).asFile.writeText(content)
    }
}
