package org.ahivs.dagger2.repository

import com.google.common.truth.ExpectFailure
import com.google.common.truth.Truth
import org.ahivs.dagger2.githubapi.GithubApi
import org.ahivs.dagger2.githubapi.model.RepoApiModel
import org.ahivs.dagger2.githubapi.model.UserApiModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppRepositoryTest {

    lateinit var appRepository: AppRepository

    @Before
    fun setup() {
        appRepository = AppRepository(FakeGithubApi())
    }

    @Test
    fun successfulQuery() {
        val topRepos = appRepository.getTopRepo()

        Truth.assertThat(topRepos.size).isEqualTo(1)
        Truth.assertThat(topRepos[0]).isEqualTo(fakeRepoApiModel)
    }
}

private class FakeGithubApi : GithubApi {
    override fun getTopRepositories(): List<RepoApiModel> = listOf(fakeRepoApiModel)
}

private val fakeRepoApiModel = RepoApiModel(
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