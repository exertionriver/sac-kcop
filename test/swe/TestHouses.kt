package swe

import river.exertion.sac.astro.base.CelestialHouse
import river.exertion.sac.swe.Houses
import river.exertion.sac.swe.Julday
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestHouses {

    @Test
    fun testGetCelestialHousesData() {
        val testUTC = TestEarthLocations.atxEarthLocation.utcDateTime
        val testLat = TestEarthLocations.atxEarthLocation.latitude
        val testLong = TestEarthLocations.atxEarthLocation.longitude

        println("test localdatetime: $testUTC")
        println("test lat/long: $testLat / $testLong")

        val uniTimeDec = Julday.getJulianTimeDecimal(testUTC)
        val uniTimeHouses = Houses.getCelestialHousesData(uniTimeDec, testLat, testLong)

        println ("Julday: $uniTimeDec")

        println("Universal Time Houses (raw)")
        uniTimeHouses.forEachIndexed { idx, house -> println ("${CelestialHouse.fromOrdinal(idx)}, $house") }
        assertEquals("160.0979", "%1.4f".format(uniTimeHouses[0]))
        assertEquals("280.8778", "%1.4f".format(uniTimeHouses[4]))
        assertEquals("36.6535", "%1.4f".format(uniTimeHouses[8]))

        val uniTimeHousesDeg = uniTimeHouses.map{ it.toInt() }
        val uniTimeHousesMin = uniTimeHouses.mapIndexed { idx, house -> ((house - uniTimeHousesDeg[idx]) * 60).toInt() }
        val uniTimeHousesSec = uniTimeHouses.mapIndexed { idx, house -> ((house - uniTimeHousesDeg[idx] - uniTimeHousesMin[idx].toDouble() / 60) * 3600) }

        println("Universal Time Houses (calc'd)")
        uniTimeHousesDeg.forEachIndexed { idx, houseDeg -> println ("${CelestialHouse.fromOrdinal(idx)}, $houseDeg ${uniTimeHousesMin[idx]}'${"%2.4f".format(uniTimeHousesSec[idx])}") }
        assertEquals(186, uniTimeHousesDeg[1])
        assertEquals(311, uniTimeHousesDeg[5])
        assertEquals(68, uniTimeHousesDeg[9])

        assertEquals(39, uniTimeHousesMin[2])
        assertEquals(5, uniTimeHousesMin[6])
        assertEquals(52, uniTimeHousesMin[10])

        assertEquals("19.3377", "%1.4f".format(uniTimeHousesSec[3]))
        assertEquals("36.0074", "%1.4f".format(uniTimeHousesSec[7]))
        assertEquals("35.6749", "%1.4f".format(uniTimeHousesSec[11]))
    }
}