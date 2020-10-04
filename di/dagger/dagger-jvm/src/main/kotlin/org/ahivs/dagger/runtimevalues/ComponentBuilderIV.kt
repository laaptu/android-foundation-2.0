package org.ahivs.dagger.runtimevalues.componentbuilderIV

import dagger.*
import javax.inject.Inject
import javax.inject.Named

class Car @Inject constructor(val engine: Engine, val wheels: Wheels)

class Engine @Inject constructor(@Named("engine") val engineName: String) {
    fun printEngine() {
        println("This is engine = $engineName ")
    }
}

class Wheels @Inject constructor(@Named("wheel") val wheelName: String) {
    fun printWheels() {
        println("This is wheel = $wheelName")
    }
}

@Component
interface CarComponent {
    fun inject(activity: Activity)

    //so builder interface is creating  an interface where we provide dependency values to our modules
    // meaning or carmodule requires enginename to getEngine, so for that it must be provided at runtime
    //and it will then provide these values to the providers
    @Component.Builder
    interface Builder {
        //this should only have one parameter
        // once we use this, this will be a provider and it can be used by any modules
        @BindsInstance
        fun supplyEngine(@Named("engine") engineName: String): Builder

        //here named is essential as we are providing two strings value, and if any module is wanting the string
        //values it cannot decide which string value the module is wanting, so named is essential
        @BindsInstance
        fun supplyWheels(@Named("wheel") wheelName: String): Builder

        //and must have this method to specfiy the build the component else it won't work
        fun build(): CarComponent
    }
}

class Activity {
    @Inject
    lateinit var car: Car

    fun printCar() {
        car.engine.printEngine()
        car.wheels.printWheels()
    }
}

fun main() {
    val activity = Activity()
    //so the values are passed from component to module and to respective providers
    //it cannot be used with @Binds i.e with abstract class as abstract class cannot be instantiated
    //and maybe the runtime values cannot be passed
    val carComponent = DaggerCarComponent.builder()
        .supplyEngine("Camaro")
        .supplyWheels("Chrome Plated")
        .build()
    //if we do activity.printCar() here ,it will throw null pointer as at this time
    // dagger hasn't injected the car to the activity
    //activity.printCar()
    carComponent.inject(activity)
    activity.printCar()

}
