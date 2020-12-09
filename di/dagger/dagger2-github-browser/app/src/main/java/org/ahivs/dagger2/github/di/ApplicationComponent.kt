package org.ahivs.dagger2.github.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import org.ahivs.dagger2.appdeps.ApplicationDeps
import org.ahivs.dagger2.githubapi.GithubApiModule
import org.ahivs.dagger2.repository.AppRepository
import javax.inject.Singleton

@Singleton
@Component(modules = [GithubApiModule::class])
interface ApplicationComponent : ApplicationDeps {
    //this same interface is not defined in ApplicationDeps file
    //fun getAppRepository(): AppRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}