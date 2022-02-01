package br.com.felixgilioli.templateprocessor.core.usecase

import br.com.felixgilioli.templateprocessor.core.exception.TemplateDefinitionNotFoundException
import br.com.felixgilioli.templateprocessor.core.port.inbound.GetTemplateDefinitionPort
import br.com.felixgilioli.templateprocessor.core.port.model.TemplateDefinition
import br.com.felixgilioli.templateprocessor.core.port.outbound.ConvertYamlToTemplateDefinitionPort
import br.com.felixgilioli.templateprocessor.core.port.outbound.GetTemplateDefinitionContentPort
import br.com.felixgilioli.templateprocessor.core.port.outbound.RepositoryUrlParsePort

class GetTemplateDefinitionUseCase(
    private val getTemplateDefinitionContentPort: GetTemplateDefinitionContentPort,
    private val convertYamlToTemplateDefinitionPort: ConvertYamlToTemplateDefinitionPort,
    private val repositoryUrlParsePort: RepositoryUrlParsePort
) : GetTemplateDefinitionPort {

    override fun get(repositoryUrl: String): TemplateDefinition {
        val templateDefinitionRawContentUrl = repositoryUrlParsePort.parse(repositoryUrl)

        val templateDefinitionYaml =
            getTemplateDefinitionContentPort.get(templateDefinitionRawContentUrl)
                ?: throw TemplateDefinitionNotFoundException()

        return convertYamlToTemplateDefinitionPort.convert(templateDefinitionYaml)
    }
}