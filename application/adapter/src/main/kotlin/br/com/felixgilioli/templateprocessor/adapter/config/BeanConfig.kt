package br.com.felixgilioli.templateprocessor.adapter.config

import br.com.felixgilioli.templateprocessor.core.port.inbound.GetTemplateDefinitionPort
import br.com.felixgilioli.templateprocessor.core.port.inbound.NewProjectFromTemplatePort
import br.com.felixgilioli.templateprocessor.core.port.outbound.CloneProjectPort
import br.com.felixgilioli.templateprocessor.core.port.outbound.ConvertYamlToTemplateDefinitionPort
import br.com.felixgilioli.templateprocessor.core.port.outbound.FileProcessPort
import br.com.felixgilioli.templateprocessor.core.port.outbound.GetTemplateDefinitionContentPort
import br.com.felixgilioli.templateprocessor.core.usecase.GetTemplateDefinitionUseCase
import br.com.felixgilioli.templateprocessor.core.usecase.NewProjectFromTemplateUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

    @Bean
    fun newProjectFromTemplatePort(
        cloneProjectPort: CloneProjectPort,
        fileProcessPort: FileProcessPort
    ): NewProjectFromTemplatePort =
        NewProjectFromTemplateUseCase(cloneProjectPort, fileProcessPort)

    @Bean
    fun getTemplateDefinitionPort(
        getTemplateDefinitionContentPort: GetTemplateDefinitionContentPort,
        convertYamlToTemplateDefinitionPort: ConvertYamlToTemplateDefinitionPort
    ): GetTemplateDefinitionPort =
        GetTemplateDefinitionUseCase(getTemplateDefinitionContentPort, convertYamlToTemplateDefinitionPort)
}