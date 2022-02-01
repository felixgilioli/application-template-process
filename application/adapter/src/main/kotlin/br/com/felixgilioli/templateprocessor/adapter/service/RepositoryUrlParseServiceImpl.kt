package br.com.felixgilioli.templateprocessor.adapter.service

import br.com.felixgilioli.templateprocessor.adapter.exception.InvalidGitRepositoryException
import br.com.felixgilioli.templateprocessor.core.port.outbound.RepositoryUrlParsePort
import org.eclipse.jgit.api.Git
import org.springframework.stereotype.Service
import org.springframework.web.util.UriComponentsBuilder

@Service
class RepositoryUrlParseServiceImpl : RepositoryUrlParsePort {

    companion object {
        const val GITHUB_DOMAIN = "github.com"
        const val BITBUCKET_DOMAIN = "bitbucket.org"
    }

    override fun parse(repositoryUrl: String): String = with(repositoryUrl) {
        return when {
            contains(GITHUB_DOMAIN) -> {
                val parameters = UriComponentsBuilder.fromHttpUrl(repositoryUrl).build().pathSegments
                val repositoryInformation = RepositoryInformation(user = parameters[0], repositoryName = parameters[1])
                "https://raw.githubusercontent.com/${repositoryInformation.user}/${repositoryInformation.getRepositoryNameWithoutExtension()}/${
                    getDefaultBranch(
                        repositoryUrl
                    )
                }/template-definition.yaml"
            }
            contains(BITBUCKET_DOMAIN) -> {
                val parameters = UriComponentsBuilder.fromHttpUrl(repositoryUrl).build().pathSegments
                val repositoryInformation = RepositoryInformation(user = parameters[0], repositoryName = parameters[1])
                "https://bitbucket.org/${repositoryInformation.user}/${repositoryInformation.repositoryName}/raw/${
                    getDefaultBranch(
                        repositoryUrl
                    )
                }/template-definition.yaml"
            }
            else -> throw InvalidGitRepositoryException()
        }
    }

    private fun getDefaultBranch(repositoryUrl: String): String {
        val headInfo = (Git.lsRemoteRepository().setRemote(repositoryUrl).callAsMap())["HEAD"]?.target?.name?.split("/")
        return if (headInfo != null) headInfo[2] else "main"
    }

    data class RepositoryInformation(
        val user: String,
        val repositoryName: String
    ) {
        fun getRepositoryNameWithoutExtension(): String = repositoryName.substring(0, repositoryName.length - 4)
    }
}

