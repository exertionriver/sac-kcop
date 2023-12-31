package swe

import river.exertion.sac.astro.CelestialHouse
import kotlinx.datetime.LocalDateTime
import river.exertion.sac.swe.Houses
import river.exertion.sac.swe.Julday
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestHouses {

    @Test
    fun testGetJulianUTCTimeDecimal() {
        val testLDT = LocalDateTime(1978,11,16,18,39,0,0)
        val testLat = 30.2667
        val testLong = -97.75

        println("test localdatetime: $testLDT")
        println("test lat/long: $testLat / $testLong")

        val uniTimeDec = Julday.getJulianUTCTimeDecimal(testLDT, Julday.UNIVERSAL_TIME)
        val uniTimeHouses = Houses.getCelestialHousesData(uniTimeDec, testLat, testLong)

        println("Universal Time Houses (raw)")
        uniTimeHouses.forEachIndexed { idx, house -> println ("${CelestialHouse.fromOrdinal(idx)}, $house") }
        assertEquals("315.2042", "%1.4f".format(uniTimeHouses[0]))
        assertEquals("83.2357", "%1.4f".format(uniTimeHouses[4]))
        assertEquals("212.0693", "%1.4f".format(uniTimeHouses[8]))

        val uniTimeHousesDeg = uniTimeHouses.map{ it.toInt() }
        val uniTimeHousesMin = uniTimeHouses.mapIndexed { idx, house -> ((house - uniTimeHousesDeg[idx]) * 60).toInt() }
        val uniTimeHousesSec = uniTimeHouses.mapIndexed { idx, house -> ((house - uniTimeHousesDeg[idx] - uniTimeHousesMin[idx].toDouble() / 60) * 3600) }

        println("Universal Time Houses (calc'd)")
        uniTimeHousesDeg.forEachIndexed { idx, houseDeg -> println ("${CelestialHouse.fromOrdinal(idx)}, $houseDeg ${uniTimeHousesMin[idx]}'${"%2.4f".format(uniTimeHousesSec[idx])}") }
        assertEquals(356, uniTimeHousesDeg[1])
        assertEquals(106, uniTimeHousesDeg[5])
        assertEquals(239, uniTimeHousesDeg[9])

        assertEquals(4, uniTimeHousesMin[2])
        assertEquals(12, uniTimeHousesMin[6])
        assertEquals(14, uniTimeHousesMin[10])

        assertEquals("20.5778", "%1.4f".format(uniTimeHousesSec[3]))
        assertEquals("38.6589", "%1.4f".format(uniTimeHousesSec[7]))
        assertEquals("31.8693", "%1.4f".format(uniTimeHousesSec[11]))
    }
}