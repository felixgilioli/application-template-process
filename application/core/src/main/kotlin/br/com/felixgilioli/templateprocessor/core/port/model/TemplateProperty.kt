package br.com.felixgilioli.templateprocessor.core.port.model

data class TemplateProperty(
    val name: String,
    val type: TemplatePropertyType,
    val required: Boolean?,
    val default: Any?
)
