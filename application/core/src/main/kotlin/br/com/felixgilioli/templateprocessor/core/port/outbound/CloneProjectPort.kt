package br.com.felixgilioli.templateprocessor.core.port.outbound

import java.io.File

interface CloneProjectPort {

    fun clone(directory: File, repositoryUrl: String)
}