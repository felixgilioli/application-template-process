package br.com.felixgilioli.templateprocessor.core.port.outbound

interface GetTemplateDefinitionContentPort {

    fun get(repositoryUrl: String): String?
}