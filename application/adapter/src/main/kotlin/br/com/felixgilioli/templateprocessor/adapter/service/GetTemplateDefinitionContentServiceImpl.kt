package br.com.felixgilioli.templateprocessor.adapter.service

import br.com.felixgilioli.templateprocessor.core.port.outbound.GetTemplateDefinitionContentPort
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class GetTemplateDefinitionContentServiceImpl : GetTemplateDefinitionContentPort {

    override fun get(repositoryUrl: String): String? =
        RestTemplate()
            .getForEntity(repositoryUrl, String::class.java)
            .body
}