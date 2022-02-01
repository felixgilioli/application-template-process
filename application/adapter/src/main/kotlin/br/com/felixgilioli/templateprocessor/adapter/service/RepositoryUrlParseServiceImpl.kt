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
        const val GITLAB_DOMAIN = "gitlab.com"

        const val TEMPLATE_DEFINITION_FILE_NAME = "template-definition.yaml"
    }

    override fun parse(repositoryUrl: String): String = with(repositoryUrl) {
        val repositoryInformation = getRepositoryInformation(repositoryUrl)
        val defaultBranch = getDefaultBranch(repositoryUrl)
        return when {
            contains(GITHUB_DOMAIN) -> {
                "https://raw.githubusercontent.com/${repositoryInformation.user}/${repositoryInformation.getRepositoryNameWithoutExtension()}/${defaultBranch}/${TEMPLATE_DEFINITION_FILE_NAME}"
            }
            contains(BITBUCKET_DOMAIN) -> {
                "https://bitbucket.org/${repositoryInformation.user}/${repositoryInformation.repositoryName}/raw/${defaultBranch}/${TEMPLATE_DEFINITION_FILE_NAME}"
            }
            contains(GITLAB_DOMAIN) -> {
                "https://gitlab.com/${repositoryInformation.user}/${repositoryInformation.getRepositoryNameWithoutExtension()}/-/raw/${defaultBranch}/${TEMPLATE_DEFINITION_FILE_NAME}"
            }
            else -> throw InvalidGitRepositoryException()
        }
    }

    private fun getRepositoryInformation(repositoryUrl: String): RepositoryInformation {
        val parameters = UriComponentsBuilder.fromHttpUrl(repositoryUrl).build().pathSegments
        val repositoryInformation = RepositoryInformation(user = parameters[0], repositoryName = parameters[1])
        return repositoryInformation
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

