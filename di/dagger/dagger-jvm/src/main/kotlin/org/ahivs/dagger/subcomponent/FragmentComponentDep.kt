package org.ahivs.dagger.subcomponent.fragmentcomponentdep

import dagger.*
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
class Context();

@Module
class AppModule {
    @Provides
    @AppScope
    fun getAppService(): AppService = AppService()

    @Provides
    @AppScope
    fun getContext(): Context = Context()
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
    fun getFragmentService(): FragmentService =
        FragmentService()
}

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getAppService(): AppService
    //fun getContext():Context
}

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    //this declaration is essential as it is notifying the dependent components that it is exposing only activity service
    fun getActivityService(): ActivityService

    //this is needed as it is being passed to Fragment component and if not declared ,fragmentcomponent can only get ActivityService
    fun getAppService(): AppService
    //fun getContext():Context
}

@FragmentScope
@Component(dependencies = [ActivityComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: Fragment)
}

class Fragment(val name: String, activityComponent: ActivityComponent) {
    init {
        //since fragment component now requires activity component, we need to pass it here
        DaggerFragmentComponent.builder().activityComponent(activityComponent).build().inject(this)
    }

    @Inject
    lateinit var appService: AppService

    @Inject
    lateinit var activityService: ActivityService

    @Inject
    lateinit var fragmentService: FragmentService

    //@Inject
    //lateinit var context: Context

    fun printServices() {
        println("name = $name")
        println("AppService = $appService")
        println("ActivityService = $activityService")
        println("FragmentService = $fragmentService")
        println("---------------------")
    }
}

fun main() {
    val appComponent = DaggerAppComponent.create()
    val activityComponent = DaggerActivityComponent.builder().appComponent(appComponent).build()
    val fragment1 = Fragment("Fragment1", activityComponent)
    val fragment2 = Fragment("Fragment2", activityComponent)
    fragment1.printServices()
    fragment2.printServices()
}







