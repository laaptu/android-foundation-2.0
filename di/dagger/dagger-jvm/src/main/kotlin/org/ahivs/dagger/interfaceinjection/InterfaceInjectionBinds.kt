package org.ahivs.dagger.interfaceinjection.binds

import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject

interface Vehicle {
    fun buildEngine()
    fun refuel()
}

class ElectricVehicle @Inject constructor() : Vehicle {
    override fun buildEngine() {
        println("This is electric engine")
    }

    override fun refuel() {
        println("I am charging")
    }
}

class PetrolVehicle @Inject constructor() : Vehicle {
    override fun buildEngine() {
        println("This is petrol engine")
    }

    override fun refuel() {
        println("I am refuelling petrol")
    }
}

@Module
abstract class VehicleModule {
    @Binds
    abstract fun getPetrolVehicle(petrolVehicle: PetrolVehicle): Vehicle

    //Use named providers if needed as following won't work
//    @Binds
//    abstract fun getElectricVehicle(electricVehicle: ElectricVehicle): Vehicle
}

@Component(modules = [VehicleModule::class])
interface VehicleComponent {
    fun putOn(activity: Activity)
}

class Activity {
    @Inject
    lateinit var vehicle: Vehicle

    fun printVehicle() {
        vehicle.buildEngine()
        vehicle.refuel()
    }

}

fun main() {
    val activity = Activity()
    val vehicleComponent = DaggerVehicleComponent.builder().build()
    vehicleComponent.putOn(activity)
    activity.printVehicle()
}


