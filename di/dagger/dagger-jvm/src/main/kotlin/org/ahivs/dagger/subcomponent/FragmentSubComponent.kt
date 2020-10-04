package org.ahivs.dagger.subcomponent.fragment

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import javax.inject.Inject
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

class AppService()
class ActivityService()
class FragmentService()

@Module
class AppModule {
    @Provides
    @AppScope
    fun getAppService(): AppService = AppService()
}

@Module
class ActivityModule {
    @Provides
    @ActivityScope
    fun getActivityService(): ActivityService = ActivityService()
}

@Module
class FragmentModule {
    @Provides
    @FragmentScope
    fun getFragmentService(): FragmentService = FragmentService()
}

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getActivitySubComponent(): ActivitySubComponent
}

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivitySubComponent {
    fun getFragmentSubComponent(): FragmentSubComponent
}

@FragmentScope
@Subcomponent(modules = [FragmentModule::class])
interface FragmentSubComponent {
    fun inject(fragment: Fragment)
}

class Fragment(val name: String, activitySubComponent: ActivitySubComponent) {

    init {
        activitySubComponent.getFragmentSubComponent().inject(this)
    }

    @Inject
    lateinit var appService: AppService

    @Inject
    lateinit var activityService: ActivityService

    @Inject
    lateinit var fragmentService: FragmentService

    fun printServices() {
        println("Fragment = $name")
        println("AppService = $appService")
        println("ActivityService = $activityService")
        println("FragmentService = $fragmentService")
        println("------------------")
    }
}

fun main() {
    val appComponent = DaggerAppComponent.create()
    val activitySubcomponent = appComponent.getActivitySubComponent()
    //val fragmentSubcomponent = activitySubcomponent.getFragmentSubComponent()

    val fragment1 = Fragment("Fragment1", activitySubcomponent)
    //fragmentSubcomponent.inject(fragment1)
    fragment1.printServices()

    val fragment2 = Fragment("Fragment2", activitySubcomponent)
    fragment2.printServices()
}
