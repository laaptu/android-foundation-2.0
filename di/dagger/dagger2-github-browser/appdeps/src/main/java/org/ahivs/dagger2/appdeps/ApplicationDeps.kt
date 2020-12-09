package org.ahivs.dagger2.appdeps

import org.ahivs.dagger2.repository.AppRepository

interface ApplicationDeps {
    fun appRepository(): AppRepository
}