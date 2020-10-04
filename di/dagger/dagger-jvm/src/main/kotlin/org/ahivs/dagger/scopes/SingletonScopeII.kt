package org.ahivs.dagger.scopes.singletonscopeII

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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


//singleton works in conjunction with providers/modules meaning if you just declare
// singleton in the component and begin calling the getCar() we will get different objects
// if we put singleton on provider/here in constructor itself, and in the component, then
// only we will properly define the scope of the object and will be singleton till the lifecycle of the component
@Singleton
@Component
interface CarComponent {
    fun getCar(): Car
}

fun main() {
    //since the singleton resides on the carcomponent itself
    //meaning if there was no singleton scope, everytime we call carComponent.getCar() we will
    // be presented with different car objects i.e. each time a new object is instantiated
    var carComponent: CarComponent = DaggerCarComponent.create();
    //here since it is singleton we will get the same object
    val car = carComponent.getCar()
    println(car)
    println(carComponent.getCar())
    //remember the singleton scope is within the carcomponent itself, if we again instantiate
    // a new component and call the car, we will get a new instance
    carComponent = DaggerCarComponent.create()
    //this will be different from above two
    println(carComponent.getCar())
}

