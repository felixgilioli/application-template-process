package br.com.felixgilioli.templateprocessor.adapter.dto

import br.com.felixgilioli.templateprocessor.core.port.model.NewProjectFromTemplateInfo

data class NewProjectFromTemplateDto(
    val projectName: String,
    val repositoryUrl: String,
    val properties: Map<String, Any>?
) {
    fun toNewProjectFromTemplateInfo() = NewProjectFromTemplateInfo(
        projectName = projectName,
        repositoryUrl = repositoryUrl,
        properties = properties
    )
}
