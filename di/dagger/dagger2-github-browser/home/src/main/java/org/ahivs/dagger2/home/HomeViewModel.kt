package org.ahivs.dagger2.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.ahivs.dagger2.home.list.RepoItem
import org.ahivs.dagger2.repository.AppRepository

class HomeViewModel(private val appRepository: AppRepository) : ViewModel() {
    private val _viewState = MutableLiveData<HomeViewState>(HomeViewStateLoading)
    val viewState: LiveData<HomeViewState> = _viewState

    init {
        val topRepos = appRepository.getTopRepo()
        _viewState.value = HomeViewStateLoaded(
            repos = topRepos.map {
                RepoItem(
                    name = it.name,
                    description = it.description,
                    starCount = it.stargazersCount,
                    forkCount = it.forksCount
                )
            }
        )
    }
}