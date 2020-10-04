package org.ahivs.dagger.runtimevalues.componentbuilderIII

import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
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
class VehicleModule {
    @Provides
    fun getVehicle(type: Int): Vehicle {
        return if (type == 1)
            PetrolVehicle()
        else
            ElectricVehicle()
    }
}

@Component(modules = [VehicleModule::class])
interface VehicleComponent {
    fun putOn(activity: Activity)

    @Component.Builder
    interface ComponentBuilder {
        @BindsInstance
        fun provideVehicleType(type: Int): ComponentBuilder
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
        .provideVehicleType(2)
        .build()
    vehicleComponent.putOn(activity)
    activity.printVehicle()
}


