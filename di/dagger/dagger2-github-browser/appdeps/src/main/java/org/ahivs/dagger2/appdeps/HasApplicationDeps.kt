package org.ahivs.dagger2.appdeps

import android.content.Context

interface HasApplicationDeps {
    fun getApplicationDeps(): ApplicationDeps
}

fun Context.applicationDeps(): ApplicationDeps {
    return (applicationContext as? HasApplicationDeps)?.getApplicationDeps()
        ?: throw IllegalArgumentException("Application must implement HasApplicationDeps")
}