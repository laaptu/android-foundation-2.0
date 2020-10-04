package org.ahivs.dagger.interfaceinjection.namedprovider

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

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
class VehicleModule {

    //here the function name must also be different otherwise it won't work even we have added
    // provides with named argument i.e. it says cannot have more than one binding method with the same
    // name in a single module
    //this doesn't need to be with interface, if you have a single module providing instances of same object
    // then named provider will be of rescue
    @Provides
    @Named("Petrol")
    fun getPetrolVehicle(petrolVehicle: PetrolVehicle): Vehicle = petrolVehicle

    @Provides
    @Named("Electric")
    fun getElectricVehicle(electricVehicle: ElectricVehicle): Vehicle = electricVehicle
}

@Component(modules = [VehicleModule::class])
interface VehicleComponent {
    fun putOn(activity: Activity)
}

class Activity {
    @Inject
    @Named("Electric")
    lateinit var vehicle: Vehicle

    fun printVehicle() {
        vehicle.buildEngine()
        vehicle.refuel()
    }
}

fun main() {
    val activity = Activity()
    val vehicleComponent = DaggerVehicleComponent.create()
    vehicleComponent.putOn(activity)
    activity.printVehicle()
}


