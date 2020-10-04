package org.ahivs.dagger.scopes.custom

import dagger.Component
import javax.inject.Inject
import javax.inject.Scope

@CustomScope
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


//there is no difference between @Singleton and @CustomScope as both do the same work
// i.e. create a singleton object defined in the scope of the component, maybe this has
// some usage on component and subcomponent, but the way it is used here, there is no difference
//between singleton and customscope as both are doing the same
@CustomScope
@Component
interface CarComponent {
    fun getCar(): Car
}

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class CustomScope

fun main() {
    //since the singleton resides on the carcomponent itself
    //meaning if there was no singleton scope, everytime we call carComponent.getCar() we will
    // be presented with different car objects i.e. each time a new object is instantiated
    val carComponent: CarComponent = DaggerCarComponent.create()

    //here since it is singleton we will get the same object
    val car = carComponent.getCar()
    println(car)
    println(carComponent.getCar())
}
