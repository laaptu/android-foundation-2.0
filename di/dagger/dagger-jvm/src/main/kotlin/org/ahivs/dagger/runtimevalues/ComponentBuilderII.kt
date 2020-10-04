package org.ahivs.dagger.runtimevalues.componentbuilderII

import dagger.*
import javax.inject.Inject
import javax.inject.Named

class Car(val engine: Engine, val wheels: Wheels)

class Engine(val engineName: String) {
    fun printEngine() {
        println("This is engine = $engineName ")
    }
}

class Wheels(val wheelName: String) {
    fun printWheels() {
        println("This is wheel = $wheelName")
    }
}

@Module
class CarModule() {

    @Provides
    fun getEngine(@Named("engine") engineName: String): Engine = Engine(engineName)

    @Provides
    fun getWheels(@Named("wheel") wheelName: String): Wheels = Wheels(wheelName)

    @Provides
    fun getCar(engine: Engine, wheel: Wheels): Car = Car(engine, wheel)
}

@Component(modules = [CarModule::class])
interface CarComponent {
    fun inject(activity: Activity)

    //so builder interface is creating  an interface where we provide dependency values to our modules
    // meaning or carmodule requires enginename to getEngine, so for that it must be provided at runtime
    //and it will then provide these values to the providers
    @Component.Builder
    interface Builder {
        //this should only have one parameter
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
        .supplyEngine("Porsche")
        .supplyWheels("Chrome Plated")
        .build()
    //if we do activity.printCar() here ,it will throw null pointer as at this time
    // dagger hasn't injected the car to the activity
    //activity.printCar()
    carComponent.inject(activity)
    activity.printCar()

}
