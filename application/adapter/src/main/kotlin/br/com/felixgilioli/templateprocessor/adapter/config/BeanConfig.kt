package br.com.felixgilioli.templateprocessor.adapter.config

import br.com.felixgilioli.templateprocessor.core.port.inbound.NewProjectFromTemplatePort
import br.com.felixgilioli.templateprocessor.core.port.outbound.CloneProjectPort
import br.com.felixgilioli.templateprocessor.core.usecase.NewProjectFromTemplateUseCase
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Repository

@Repository
class BeanConfig {

    @Bean
    fun newProjectFromTemplatePort(cloneProjectPort: CloneProjectPort): NewProjectFromTemplatePort =
        NewProjectFromTemplateUseCase(cloneProjectPort)
}