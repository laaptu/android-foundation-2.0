package org.ahivs.dagger.simpleinjection.field

import dagger.Component
import javax.inject.Inject

class Car @Inject constructor(val engine: Engine, val wheels: Wheels) {
    fun printWheelEngine() {
        engine.printEngine()
        wheels.printWheels()
    }
}

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

class Remote @Inject constructor() {
    fun setListener(car: Car) {
        car.printWheelEngine()
    }
}

@Component
interface CarComponent {
    fun inject(activity: Activity)
}

class Activity {
    @Inject
    lateinit var car: Car

    //This method is invoked right after the  inject(activity) call is injection is done through it
    // or right after the constructor, if this activity was instantiated by Dagger through constructor
    @Inject
    fun printCar(remote: Remote) {
        remote.setListener(car)
    }
}

fun main() {
    val activity = Activity()
    val carComponent = DaggerCarComponent.create()
    //if we do activity.printCar() here ,it will throw null pointer as at this time
    // dagger hasn't injected the car to the activity
    //activity.printCar()
    carComponent.inject(activity)
}
