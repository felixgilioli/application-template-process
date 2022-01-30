package br.com.felixgilioli.templateprocessor.adapter.service

import br.com.felixgilioli.templateprocessor.core.port.outbound.CloneProjectPort
import org.eclipse.jgit.api.Git
import org.springframework.stereotype.Service
import java.io.File

@Service
class CloneProjectServiceImpl : CloneProjectPort {

    override fun clone(directory: File, repositoryUrl: String) {
        Git.cloneRepository()
            .setURI(repositoryUrl)
            .setDirectory(directory)
            .call()
    }
}