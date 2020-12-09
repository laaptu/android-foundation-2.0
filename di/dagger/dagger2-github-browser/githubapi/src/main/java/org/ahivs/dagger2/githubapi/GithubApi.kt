package org.ahivs.dagger2.githubapi

import org.ahivs.dagger2.githubapi.model.RepoApiModel
import org.ahivs.dagger2.githubapi.model.UserApiModel
import javax.inject.Inject
import javax.inject.Singleton

interface GithubApi {

    fun getTopRepositories(): List<RepoApiModel>
}

@Singleton
class MockGithubApi @Inject constructor() : GithubApi {
    override fun getTopRepositories(): List<RepoApiModel> {
        return listOf(
            RepoApiModel(
                id = 1L,
                name = "Mock Repo",
                description = "Mock Repo Description",
                owner = UserApiModel(id = 1L, login = "dagger"),
                stargazersCount = 1,
                forksCount = 1,
                contributorsUrl = "",
                createdDate = "1/1/2020",
                updatedDate = "2/2/2020"
            )
        )
    }

}