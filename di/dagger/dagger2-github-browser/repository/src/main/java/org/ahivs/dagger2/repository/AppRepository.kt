package org.ahivs.dagger2.repository

import org.ahivs.dagger2.githubapi.GithubApi
import org.ahivs.dagger2.githubapi.model.RepoApiModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val githubApi: GithubApi
) {

    fun getTopRepo(): List<RepoApiModel> = githubApi.getTopRepositories()
}