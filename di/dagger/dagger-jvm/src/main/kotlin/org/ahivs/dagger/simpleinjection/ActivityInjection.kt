package org.ahivs.dagger.simpleinjection.activity

import dagger.Component
import javax.inject.Inject

class Car @Inject constructor(val engine: Engine, val wheels: Wheels)

class Engine @Inject constructor() {
    fun printEngine() {
        println("This is Porsche Engine On Modules")
    }
}

class Wheels @Inject constructor() {
    fun printWheels() {
        println("This is Racing Wheels On Modules")
    }
}

@Component
interface CarComponent {
    fun inject(activity: Activity)
}

class Activity {
    @Inject
    lateinit var car: Car

    fun printCar(){
        car.engine.printEngine()
        car.wheels.printWheels()
    }
}

fun main() {
    val activity = Activity()
    val carComponent = DaggerCarComponent.create()
    //if we do activity.printCar() here ,it will throw null pointer as at this time
    // dagger hasn't injected the car to the activity
    //activity.printCar()
    carComponent.inject(activity)
    activity.printCar()

}
