package org.ahivs.dagger.runtimevalues

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

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
class CarModule(val engineName: String, val wheelName: String) {

    @Provides
    fun getEngine(): Engine = Engine(engineName)

    @Provides
    fun getWheels(): Wheels = Wheels(wheelName)

    @Provides
    fun getCar(engine: Engine, wheel: Wheels): Car = Car(engine, wheel)
}

@Component(modules = [CarModule::class])
interface CarComponent {
    fun inject(activity: Activity)
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
        .carModule(CarModule("Porsche", "Alloy"))
        .build()
    //if we do activity.printCar() here ,it will throw null pointer as at this time
    // dagger hasn't injected the car to the activity
    //activity.printCar()
    carComponent.inject(activity)
    activity.printCar()

}
