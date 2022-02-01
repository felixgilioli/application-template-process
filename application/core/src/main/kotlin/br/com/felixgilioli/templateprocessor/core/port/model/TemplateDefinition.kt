package br.com.felixgilioli.templateprocessor.core.port.model

data class TemplateDefinition(
    val version: Int,
    val properties: List<TemplateProperty>?
)