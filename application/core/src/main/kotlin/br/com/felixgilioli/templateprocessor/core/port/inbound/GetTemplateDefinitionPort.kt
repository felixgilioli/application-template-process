package br.com.felixgilioli.templateprocessor.core.port.inbound

import br.com.felixgilioli.templateprocessor.core.port.model.TemplateDefinition

interface GetTemplateDefinitionPort {

    fun get(repositoryUrl: String): TemplateDefinition
}