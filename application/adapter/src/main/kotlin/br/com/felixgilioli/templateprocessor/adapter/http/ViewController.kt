package br.com.felixgilioli.templateprocessor.adapter.http

import br.com.felixgilioli.templateprocessor.core.port.inbound.GetTemplateDefinitionPort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/")
class ViewController(private val getTemplateDefinitionPort: GetTemplateDefinitionPort) {

    @GetMapping
    fun index(model: Model): String {
        return "index"
    }

    @GetMapping("/setup")
    fun setup(model: Model, @RequestParam repositoryUrl: String): String {
        val templateDefinition = getTemplateDefinitionPort.get(repositoryUrl)

        model.addAttribute("properties", templateDefinition.properties)

        return "setup"
    }
}