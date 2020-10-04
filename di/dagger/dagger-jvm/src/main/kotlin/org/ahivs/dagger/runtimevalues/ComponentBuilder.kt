package org.ahivs.dagger.runtimevalues.componentbuilder

import dagger.*
import javax.inject.Inject
import javax.inject.Named

class Car(val engine: Engine, val wheels: Wheels)

class Engine(val engineName: String) {
    fun printEngine() {
        println("This is engine = $engineName ")
    }
}

class Wheels() {
    fun printWheels() {
        println("This is wheel = Alloy")
    }
}

@Module
class CarModule() {

    @Provides
    fun getEngine(engineName: String): Engine = Engine(engineName)

    @Provides
    fun getWheels(): Wheels = Wheels()

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
        fun supplyEngine(engineName: String): Builder

        //and must have this method to specfiy the build
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
        .build()
    //if we do activity.printCar() here ,it will throw null pointer as at this time
    // dagger hasn't injected the car to the activity
    //activity.printCar()
    carComponent.inject(activity)
    activity.printCar()

}
