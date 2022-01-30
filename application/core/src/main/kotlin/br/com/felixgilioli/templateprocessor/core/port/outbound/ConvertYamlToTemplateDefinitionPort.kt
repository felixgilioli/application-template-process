package br.com.felixgilioli.templateprocessor.core.port.outbound

import br.com.felixgilioli.templateprocessor.core.port.model.TemplateDefinition

interface ConvertYamlToTemplateDefinitionPort {

    fun convert(yamlContent: String): TemplateDefinition
}