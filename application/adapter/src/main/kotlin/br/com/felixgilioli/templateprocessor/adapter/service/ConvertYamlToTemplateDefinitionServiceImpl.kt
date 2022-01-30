package br.com.felixgilioli.templateprocessor.adapter.service

import br.com.felixgilioli.templateprocessor.core.port.model.TemplateDefinition
import br.com.felixgilioli.templateprocessor.core.port.outbound.ConvertYamlToTemplateDefinitionPort
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.stereotype.Service

@Service
class ConvertYamlToTemplateDefinitionServiceImpl : ConvertYamlToTemplateDefinitionPort {

    override fun convert(yamlContent: String): TemplateDefinition {
        val mapper = ObjectMapper(YAMLFactory())
        mapper.registerModule(KotlinModule.Builder().build())
        return mapper.readValue(yamlContent, TemplateDefinition::class.java)
    }
}