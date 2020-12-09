package org.ahivs.dagger2.home

import org.ahivs.dagger2.home.list.RepoItem

sealed class HomeViewState
object HomeViewStateLoading : HomeViewState()
data class HomeViewStateLoaded(val repos: List<RepoItem>) : HomeViewState()
data class HomeViewStateError(val message: String) : HomeViewState()