package org.ahivs.dagger2.githubapi

import dagger.Binds
import dagger.Module

@Module
interface GithubApiModule {

    @Binds
    fun getGithubApi(mockGithubApi: MockGithubApi): GithubApi
}