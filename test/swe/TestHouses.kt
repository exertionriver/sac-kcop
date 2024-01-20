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

        println("test utc: $testUTC")
        println("test lat/long: $testLat / $testLong")

        val uniTimeDec = Julday.getJulianTimeDecimal(testUTC)
        val uniTimeHouses = Houses.getCelestialHousesData(uniTimeDec, testLat, testLong)

        println ("Julday: $uniTimeDec")

        println("Houses (dec):")
        uniTimeHouses.forEachIndexed { idx, house -> println ("${CelestialHouse.fromOrdinal(idx)}, $house") }
        assertEquals("160.0979", "%1.4f".format(uniTimeHouses[0]))
        assertEquals("280.8778", "%1.4f".format(uniTimeHouses[4]))
        assertEquals("36.6535", "%1.4f".format(uniTimeHouses[8]))

        val uniTimeHousesDeg = uniTimeHouses.map{ it.toInt() }
        val uniTimeHousesMin = uniTimeHouses.mapIndexed { idx, house -> ((house - uniTimeHousesDeg[idx]) * 60).toInt() }
        val uniTimeHousesSec = uniTimeHouses.mapIndexed { idx, house -> ((house - uniTimeHousesDeg[idx] - uniTimeHousesMin[idx].toDouble() / 60) * 3600) }

        println("Houses (deg)")
        uniTimeHousesDeg.forEachIndexed { idx, houseDeg -> println ("${CelestialHouse.fromOrdinal(idx)}, $houseDeg ${uniTimeHousesMin[idx]}'${"%2.4f".format(uniTimeHousesSec[idx])}") }
        assertEquals(186, uniTimeHousesDeg[1])
        assertEquals(311, uniTimeHousesDeg[5])
        assertEquals(68, uniTimeHousesDeg[9])

        assertEquals(39, uniTimeHousesMin[2])
        assertEquals(5, uniTimeHousesMin[6])
        assertEquals(52, uniTimeHousesMin[10])

        assertEquals("19.3386", "%1.4f".format(uniTimeHousesSec[3]))
        assertEquals("36.0083", "%1.4f".format(uniTimeHousesSec[7]))
        assertEquals("35.6757", "%1.4f".format(uniTimeHousesSec[11]))
    }

    @Test
    fun testGetCompositeCelestialHousesData() {
        val firstUTC = TestEarthLocations.sfeEarthLocation.utcDateTime
        val firstLat = TestEarthLocations.sfeEarthLocation.latitude
        val firstLong = TestEarthLocations.sfeEarthLocation.longitude

        println("first utc: $firstUTC")
        println("first lat/long: $firstLat / $firstLong")

        val firstJulday = Julday.getJulianTimeDecimal(firstUTC)
        val firstHouses = Houses.getCelestialHousesData(firstJulday, firstLat, firstLong)

        println("first julday: $firstJulday")
        println("first houses (dec)")
        firstHouses.forEachIndexed { idx, house -> println ("${CelestialHouse.fromOrdinal(idx)}, $house") }

        val secondUTC = TestEarthLocations.tnmEarthLocation.utcDateTime
        val secondLat = TestEarthLocations.tnmEarthLocation.latitude
        val secondLong = TestEarthLocations.tnmEarthLocation.longitude

        println("second utc: $secondUTC")
        println("second lat/long: $secondLat / $secondLong")

        val secondJulday = Julday.getJulianTimeDecimal(secondUTC)
        val secondHouses = Houses.getCelestialHousesData(secondJulday, secondLat, secondLong)

        println("second julday: $secondJulday")
        println("second houses (dec)")
        secondHouses.forEachIndexed { idx, house -> println ("${CelestialHouse.fromOrdinal(idx)}, $house") }

        val compositeHouses = Houses.getCompositeCelestialHousesData(firstHouses, secondHouses)

        println("composite houses (dec)")
        compositeHouses.forEachIndexed { idx, house -> println ("${CelestialHouse.fromOrdinal(idx)}, $house") }

        assertEquals("190.2221", "%1.4f".format(compositeHouses[0]))
        assertEquals("10.2221", "%1.4f".format(compositeHouses[6]))
        assertEquals("188.7377", "%1.4f".format(compositeHouses[12]))
    }
}