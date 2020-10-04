package org.ahivs.dagger.simpleinjection.module

import dagger.Component
import dagger.Module
import dagger.Provides

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
    fun provideCar(engine: Engine, wheels: Wheels): Car = Car(engine, wheels)
}

@Component(modules = [CarModule::class])
interface CarComponent {
    fun getCar(): Car
}

fun main() {
    val carComponent: CarComponent = DaggerCarComponent.create();
    val car = carComponent.getCar()
    car.engine.printEngine()
    car.wheels.printWheels()
}

