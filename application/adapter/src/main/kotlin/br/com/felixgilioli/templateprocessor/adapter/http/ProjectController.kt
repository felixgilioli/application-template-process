package br.com.felixgilioli.templateprocessor.adapter.http

import br.com.felixgilioli.templateprocessor.adapter.dto.NewProjectFromTemplateDto
import br.com.felixgilioli.templateprocessor.core.port.inbound.NewProjectFromTemplatePort
import org.eclipse.jgit.api.Git
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*

@RestController
@RequestMapping("/project")
class ProjectController(private val newProjectFromTemplatePort: NewProjectFromTemplatePort) {

    @PostMapping("/new")
    fun newProjectFromTemplate(@RequestBody dto: NewProjectFromTemplateDto) {
        newProjectFromTemplatePort.generateNewProject(dto.toNewProjectFromTemplateInfo())
    }
}