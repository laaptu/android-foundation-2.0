package org.ahivs.dagger2.home.list

data class RepoItem(
    val name: String,
    val description: String,
    val starCount: Int,
    val forkCount: Int
)