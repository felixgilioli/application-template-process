package br.com.felixgilioli.templateprocessor.core.usecase

import br.com.felixgilioli.templateprocessor.core.exception.TemplateNotFoundException
import br.com.felixgilioli.templateprocessor.core.port.inbound.NewProjectFromTemplatePort
import br.com.felixgilioli.templateprocessor.core.port.model.NewProjectFromTemplateInfo
import br.com.felixgilioli.templateprocessor.core.port.outbound.CloneProjectPort
import br.com.felixgilioli.templateprocessor.core.port.outbound.FileProcessPort
import java.io.ByteArrayOutputStream
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

        val hasProperties = !info.properties.isNullOrEmpty()

        val files = templateFile.listFiles()?.toMutableList() ?: throw TemplateNotFoundException()

        var i = 0
        while (i < files.size) {
            var file = files[i]

            val fileName = file.name

            if (file.isDirectory) {
                val children = file.listFiles()
                if (children != null) {
                    files.addAll(children)
                }
            }

            if (hasProperties) {
                info.properties?.forEach { (key, value) ->
                    if (fileName.contains("__${key}__")) {
                        val newName = fileName.replace("__${key}__", value.toString())
                        file = Files.move(file.toPath(), file.toPath().resolveSibling(newName)).toFile()
                    }
                }

                if (!file.isDirectory) {
                    val processedContent = fileProcessPort.process(Files.readString(file.toPath()), info.properties!!)

                    val printWriter = PrintWriter(file)
                    printWriter.println(processedContent)
                    printWriter.close()
                }
            }

            i++
        }

        return pack(templateFile.absolutePath)
            .also { templateFile.deleteRecursively() }
    }

    private fun pack(sourceDirPath: String): ByteArray {
        val outputStream = ByteArrayOutputStream()
        ZipOutputStream(outputStream).use { zs ->
            val pp = Paths.get(sourceDirPath)
            Files.walk(pp)
                .filter { !Files.isDirectory(it)}
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