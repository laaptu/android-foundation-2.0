package org.ahivs.dagger.simpleinjection

import dagger.Component
import javax.inject.Inject

class Car @Inject constructor(val engine: Engine, val wheels: Wheels) {

}

class Engine @Inject constructor() {
    fun printEngine() {
        println("This is Porsche Engine")
    }
}

class Wheels @Inject constructor() {
    fun printWheels() {
        println("This is Racing Wheels")
    }
}

@Component
interface CarComponent {
    fun getCar(): Car
}

fun main() {
    val carComponent: CarComponent = DaggerCarComponent.create()
    val car = carComponent.getCar()
    car.engine.printEngine()
    car.wheels.printWheels()
}
