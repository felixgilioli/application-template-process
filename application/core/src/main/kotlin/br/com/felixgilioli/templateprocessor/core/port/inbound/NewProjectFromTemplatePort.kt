package br.com.felixgilioli.templateprocessor.core.port.inbound

import br.com.felixgilioli.templateprocessor.core.port.model.NewProjectFromTemplateInfo

interface NewProjectFromTemplatePort {

    fun generateNewProject(info: NewProjectFromTemplateInfo): ByteArray
}