package br.com.felixgilioli.templateprocessor.core.usecase

import br.com.felixgilioli.templateprocessor.core.port.inbound.NewProjectFromTemplatePort
import br.com.felixgilioli.templateprocessor.core.port.model.NewProjectFromTemplateInfo
import br.com.felixgilioli.templateprocessor.core.port.outbound.CloneProjectPort
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

class NewProjectFromTemplateUseCase(private val cloneProjectPort: CloneProjectPort) : NewProjectFromTemplatePort {

    override fun generateNewProject(info: NewProjectFromTemplateInfo) {
        val repositoryTempId = UUID.randomUUID()
        val templateFile = Paths.get("temp/$repositoryTempId").toFile()

        cloneProjectPort.clone(templateFile, info.repositoryUrl)

        File(templateFile.path + "/.git").deleteRecursively()

        val hasProperties = !info.properties.isNullOrEmpty()

        val files = templateFile.listFiles().toMutableList()

        var i = 0
        while (i < files.size) {
            val file = files[i]

            val fileName = file.name

            if (file.isDirectory) {
                files.addAll(file.listFiles())
            }

            if (hasProperties) {
                info.properties?.forEach { (key, value) ->
                    if (fileName.contains("__${key}__")) {
                        val newName = fileName.replace("__${key}__", value.toString())
                        Files.move(file.toPath(), file.toPath().resolveSibling(newName))
                    }
                }
            }

            i++
        }
    }
}