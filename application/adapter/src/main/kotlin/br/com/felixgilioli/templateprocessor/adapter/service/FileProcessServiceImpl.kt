package br.com.felixgilioli.templateprocessor.adapter.service

import br.com.felixgilioli.templateprocessor.core.port.outbound.FileProcessPort
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import org.springframework.stereotype.Service

@Service
class FileProcessServiceImpl : FileProcessPort {

    override fun process(content: String, properties: Map<String, Any>): String =
        JtwigTemplate.inlineTemplate(content)
            .render(JtwigModel.newModel(properties))

}