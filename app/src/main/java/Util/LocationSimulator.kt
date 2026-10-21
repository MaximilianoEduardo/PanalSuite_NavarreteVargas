package com.example.panalsuite.util

data class SimulatedLocation(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

object LocationSimulator {
    val sampleLocations = listOf(
        SimulatedLocation("Sucursal Alameda - Santiago", -33.4442, -70.6543),
        SimulatedLocation("Tienda Retail Centro", -33.4372, -70.6506),
        SimulatedLocation("Farmacia Sector Norte", -33.3921, -70.6210),
        SimulatedLocation("Bodega Central DSY", -33.5123, -70.7100)
    )

    fun getRandomLocation(): SimulatedLocation {
        return sampleLocations.random()
    }
}