package br.com.felixgilioli.templateprocessor.core.port.outbound

interface FileProcessPort {

    fun process(content: String, properties: Map<String, Any>): String
}