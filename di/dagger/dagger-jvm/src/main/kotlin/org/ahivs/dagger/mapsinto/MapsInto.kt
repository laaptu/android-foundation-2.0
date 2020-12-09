package org.ahivs.dagger.mapsinto

import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@MapKey
annotation class VersionKey(val key: Int)

data class Version(val version: Int)


@Module
class VersionProviderModule {
    @Provides
    @IntoMap
    @VersionKey(1)
    fun getFirstVersion(): Version = Version(1)

    @Provides
    @IntoMap
    @VersionKey(2)
    fun getSecondVersion(): Version = Version(2)
}

@Component(modules = [VersionProviderModule::class])
interface VersionComponent {
    fun getVersionMaps(): Map<Integer, Version>
}

class Activity {
    lateinit var firstVersion: Version
    lateinit var secondVersion: Version
}

fun main() {
    val activity = Activity()
    val versionComponent: VersionComponent = DaggerVersionComponent.builder().build()
    activity.firstVersion = versionComponent.getVersionMaps()[1]!!
    println(activity.firstVersion.version)
    activity.secondVersion = versionComponent.getVersionMaps()[2]!!
    println(activity.secondVersion.version)

}
