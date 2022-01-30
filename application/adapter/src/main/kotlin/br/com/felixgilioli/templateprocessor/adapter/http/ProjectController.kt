package br.com.felixgilioli.templateprocessor.adapter.http

import br.com.felixgilioli.templateprocessor.adapter.dto.NewProjectFromTemplateDto
import br.com.felixgilioli.templateprocessor.core.port.inbound.GetTemplateDefinitionPort
import br.com.felixgilioli.templateprocessor.core.port.inbound.NewProjectFromTemplatePort
import br.com.felixgilioli.templateprocessor.core.port.model.TemplateDefinition
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/project")
class ProjectController(
    private val newProjectFromTemplatePort: NewProjectFromTemplatePort,
    private val getTemplateDefinitionPort: GetTemplateDefinitionPort
) {

    @PostMapping("/new", produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun newProjectFromTemplate(@RequestBody dto: NewProjectFromTemplateDto): ResponseEntity<ByteArray> {
        val zipBytes = newProjectFromTemplatePort.generateNewProject(dto.toNewProjectFromTemplateInfo())

        return ResponseEntity
            .ok()
            .header("Content-Disposition", "attachment;filename=${dto.projectName}.zip")
            .header("Content-Type", "application/octet-stream")
            .body(zipBytes)
    }

    @GetMapping("/properties")
    fun getTemplateDefinition(@RequestParam repositoryUrl: String): TemplateDefinition {
        return getTemplateDefinitionPort.get(repositoryUrl)
    }
}