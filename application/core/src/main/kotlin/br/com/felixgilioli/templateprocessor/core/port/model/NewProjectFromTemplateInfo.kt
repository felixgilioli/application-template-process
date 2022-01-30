package br.com.felixgilioli.templateprocessor.core.port.model

data class NewProjectFromTemplateInfo(
    val projectName: String,
    val repositoryUrl: String,
    val properties: Map<String, Any>?
)
