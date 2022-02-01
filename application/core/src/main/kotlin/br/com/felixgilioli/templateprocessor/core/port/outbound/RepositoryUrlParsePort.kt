package br.com.felixgilioli.templateprocessor.core.port.outbound

interface RepositoryUrlParsePort {

    fun parse(repositoryUrl: String): String
}