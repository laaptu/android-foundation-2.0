package org.ahivs.dagger2.github

import android.app.Application
import org.ahivs.dagger2.appdeps.ApplicationDeps
import org.ahivs.dagger2.appdeps.HasApplicationDeps
import org.ahivs.dagger2.github.di.ApplicationComponent
import org.ahivs.dagger2.github.di.DaggerApplicationComponent

class GithubApplication : Application(), HasApplicationDeps {
    private val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun getApplicationDeps(): ApplicationDeps {
        return applicationComponent
    }
}