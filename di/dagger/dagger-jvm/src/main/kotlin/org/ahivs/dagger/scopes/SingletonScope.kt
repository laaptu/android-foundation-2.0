package org.ahivs.dagger.scopes.singletonscope

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

class Car(val engine: Engine, val wheels: Wheels)

class Engine() {
    fun printEngine() {
        println("This is Porsche Engine On Modules")
    }
}

class Wheels() {
    fun printWheels() {
        println("This is Racing Wheels On Modules")
    }
}

@Module
class CarModule {
    @Provides
    fun provideEngine(): Engine = Engine()

    @Provides
    fun provideWheels(): Wheels = Wheels()

    @Provides
    @Singleton
    fun provideCar(engine: Engine, wheels: Wheels): Car = Car(engine, wheels)
}

@Singleton
@Component(modules = [CarModule::class])
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

