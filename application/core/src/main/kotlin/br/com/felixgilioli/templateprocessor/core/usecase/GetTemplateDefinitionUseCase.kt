package br.com.felixgilioli.templateprocessor.core.usecase

import br.com.felixgilioli.templateprocessor.core.exception.TemplateDefinitionNotFoundException
import br.com.felixgilioli.templateprocessor.core.port.inbound.GetTemplateDefinitionPort
import br.com.felixgilioli.templateprocessor.core.port.model.TemplateDefinition
import br.com.felixgilioli.templateprocessor.core.port.outbound.ConvertYamlToTemplateDefinitionPort
import br.com.felixgilioli.templateprocessor.core.port.outbound.GetTemplateDefinitionContentPort

class GetTemplateDefinitionUseCase(
    private val getTemplateDefinitionContentPort: GetTemplateDefinitionContentPort,
    private val convertYamlToTemplateDefinitionPort: ConvertYamlToTemplateDefinitionPort
) : GetTemplateDefinitionPort {

    override fun get(repositoryUrl: String): TemplateDefinition {
        val templateDefinitionYaml =
            getTemplateDefinitionContentPort.get(repositoryUrl) ?: throw TemplateDefinitionNotFoundException()

        return convertYamlToTemplateDefinitionPort.convert(templateDefinitionYaml)
    }
}