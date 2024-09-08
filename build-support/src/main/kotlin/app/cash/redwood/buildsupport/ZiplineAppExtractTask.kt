package app.cash.redwood.buildsupport

import javax.inject.Inject
import org.gradle.api.DefaultTask
import org.gradle.api.file.ArchiveOperations
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileSystemOperations
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

internal abstract class ZiplineAppExtractTask
@Inject constructor(
    private val fsOperations: FileSystemOperations,
    private val archiveOperations: ArchiveOperations,
) : DefaultTask() {
    @get:InputFiles
    abstract val inputFiles: ConfigurableFileCollection

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @TaskAction
    fun copy() {
        val outputDirectory = outputDirectory.get().asFile
        fsOperations.delete {
            it.delete(outputDirectory)
        }
        fsOperations.copy {
            it.from(archiveOperations.zipTree(inputFiles.singleFile))
            it.into(outputDirectory)
        }
    }
}
