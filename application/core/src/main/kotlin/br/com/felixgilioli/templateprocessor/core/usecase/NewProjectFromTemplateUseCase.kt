package br.com.felixgilioli.templateprocessor.core.usecase

import br.com.felixgilioli.templateprocessor.core.exception.TemplateNotFoundException
import br.com.felixgilioli.templateprocessor.core.port.inbound.NewProjectFromTemplatePort
import br.com.felixgilioli.templateprocessor.core.port.model.NewProjectFromTemplateInfo
import br.com.felixgilioli.templateprocessor.core.port.outbound.CloneProjectPort
import br.com.felixgilioli.templateprocessor.core.port.outbound.FileProcessPort
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


class NewProjectFromTemplateUseCase(
    private val cloneProjectPort: CloneProjectPort,
    private val fileProcessPort: FileProcessPort
) : NewProjectFromTemplatePort {

    override fun generateNewProject(info: NewProjectFromTemplateInfo): ByteArray {
        val repositoryTempId = UUID.randomUUID()
        val templateFile = Paths.get("temp/$repositoryTempId").toFile()

        cloneProjectPort.clone(templateFile, info.repositoryUrl)

        Paths.get(templateFile.path, ".git").toFile().deleteRecursively()
        Paths.get(templateFile.path, "template-definition.yaml").toFile().delete()

        val hasProperties = !info.properties.isNullOrEmpty()

        val files = templateFile.listFiles()?.toMutableList() ?: throw TemplateNotFoundException()

        var i = 0
        while (i < files.size) {
            var file = files[i]

            if (hasProperties) {
                file = renameFilesAndDirectories(file, info.properties!!)
                replaceFileContent(file, info.properties)
            }

            if (file.isDirectory) {
                val children = file.listFiles()
                if (children != null) {
                    files.addAll(children)
                }
            }

            i++
        }

        return zipDirectory(templateFile.absolutePath)
            .also { templateFile.deleteRecursively() }
    }

    private fun replaceFileContent(file: File, properties: Map<String, Any>) {
        if (!file.isDirectory) {
            val processedContent = fileProcessPort.process(Files.readString(file.toPath()), properties)

            val printWriter = PrintWriter(file)
            printWriter.println(processedContent)
            printWriter.close()
        }
    }

    private fun renameFilesAndDirectories(file: File, properties: Map<String, Any>): File {
        var fileName = file.name
        
        var renamedFile = file
        properties.forEach { (key, value) ->
            if (fileName.contains("__${key}__")) {
                fileName = fileName.replace("__${key}__", value.toString())
                renamedFile = Files.move(renamedFile.toPath(), renamedFile.toPath().resolveSibling(fileName)).toFile()
            }
        }
        return renamedFile
    }

    private fun zipDirectory(directoryPath: String): ByteArray {
        val outputStream = ByteArrayOutputStream()
        ZipOutputStream(outputStream).use { zs ->
            val pp = Paths.get(directoryPath)
            Files.walk(pp)
                .filter { !Files.isDirectory(it) }
                .forEach { path ->
                    val zipEntry = ZipEntry(pp.relativize(path).toString())
                    try {
                        zs.putNextEntry(zipEntry)
                        Files.copy(path, zs)
                        zs.closeEntry()
                    } catch (e: IOException) {
                        System.err.println(e)
                    }
                }
        }

        return outputStream.toByteArray()
    }
}