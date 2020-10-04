package org.ahivs.dagger.runtimevalues.binds

import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Inject

interface Vehicle {
    fun buildEngine()
    fun refuel()
}

class ElectricVehicle @Inject constructor(val color: String) : Vehicle {
    override fun buildEngine() {
        println("This is electric engine with color = $color")
    }

    override fun refuel() {
        println("I am charging")
    }
}

class PetrolVehicle @Inject constructor(val color: String) : Vehicle {
    override fun buildEngine() {
        println("This is petrol engine with color = $color")
    }

    override fun refuel() {
        println("I am refuelling petrol")
    }
}

@Module
abstract class VehicleModule {
    @Binds
    abstract fun getPetrolVehicle(petrolVehicle: PetrolVehicle): Vehicle
}

@Component(modules = [VehicleModule::class])
interface VehicleComponent {
    fun putOn(activity: Activity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun supplyColor(color: String): Builder
        fun build(): VehicleComponent
    }
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
    val vehicleComponent = DaggerVehicleComponent.builder()
        .supplyColor("Blue")
        .build()
    vehicleComponent.putOn(activity)
    activity.printVehicle()
}


