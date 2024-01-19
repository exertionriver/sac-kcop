package swe

import TestEarthLocations
import river.exertion.sac.astro.base.Celestial
import river.exertion.sac.swe.CalcUt
import river.exertion.sac.swe.Houses
import river.exertion.sac.swe.Julday
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestCalcUt {

    /*slight celestial variations from online swetest, e.g. with JulDate 2458735.881250000 swetest shows:
        Sun : 166.4102357
        Moon : 293.8492244
        Mercury : 171.2312683
    with JulDate 2458735.881250000 getCelestialsData() shows:
        SUN, 166.41025540926918
        MOON, 293.8494684770537
        MERCURY, 171.2313052937299
    Houses tie out ok with online swetest.
    */
    @Test
    fun testGetCelestialsData() {
        val testUTC = TestEarthLocations.sfeEarthLocation.utcDateTime
        val testLat = TestEarthLocations.sfeEarthLocation.latitude
        val testLong = TestEarthLocations.sfeEarthLocation.longitude

        println("test localdatetime: $testUTC")
        println("test lat/long: $testLat / $testLong")

        val uniTimeDec = Julday.getJulianTimeDecimal(testUTC)
        val uniTimeHouses = Houses.getCelestialHousesData(uniTimeDec, testLat, testLong)
        val uniCelestials = CalcUt.getCelestialsData(uniTimeDec, uniTimeHouses)

        println ("Julday: $uniTimeDec")

        println("Universal Time Celestials (raw-long)")
        uniCelestials.forEachIndexed { idx, celestial -> println ("${Celestial.fromOrdinal(idx)}, ${celestial.longitude}") }
        assertEquals("166.4103", "%1.4f".format(uniCelestials[0].longitude))
        assertEquals("164.1089", "%1.4f".format(uniCelestials[4].longitude))
        assertEquals("347.3360", "%1.4f".format(uniCelestials[8].longitude))

        val uniCelestialsDeg = uniCelestials.map{ it.longitude.toInt() }
        val uniCelestialsMin = uniCelestials.mapIndexed { idx, celestial -> ((celestial.longitude - uniCelestialsDeg[idx]) * 60).toInt() }
        val uniCelestialsSec = uniCelestials.mapIndexed { idx, celestial -> ((celestial.longitude - uniCelestialsDeg[idx] - uniCelestialsMin[idx].toDouble() / 60) * 3600) }

        println("Universal Time Celestials (calc'd-long)")
        uniCelestialsDeg.forEachIndexed { idx, celestialDeg -> println ("${Celestial.fromOrdinal(idx)}, $celestialDeg ${uniCelestialsMin[idx]}'${uniCelestialsSec[idx]}") }
        assertEquals(293, uniCelestialsDeg[1])
        assertEquals(255, uniCelestialsDeg[5])
        assertEquals(290, uniCelestialsDeg[9])

        assertEquals(13, uniCelestialsMin[2])
        assertEquals(58, uniCelestialsMin[6])
        assertEquals(58, uniCelestialsMin[10])

        assertEquals("25.1779", "%1.4f".format(uniCelestialsSec[3]))
        assertEquals("42.5052", "%1.4f".format(uniCelestialsSec[7]))
        assertEquals("16.8798", "%1.4f".format(uniCelestialsSec[11]))
    }

    @Test
    fun testGetCelestialData() {
        val testJulday = 2458735.881250000 // julday for sfeEarthLocation
        val testLat = TestEarthLocations.sfeEarthLocation.latitude
        val testLong = TestEarthLocations.sfeEarthLocation.longitude

        val testTransJulday = Julday.getJulianTimeDecimal(TestEarthLocations.tnmEarthLocation.utcDateTime)
        val testTransLat = TestEarthLocations.tnmEarthLocation.latitude
        val testTransLong = TestEarthLocations.tnmEarthLocation.longitude

        println("test julday: $testJulday")
        println("test lat/long: $testLat / $testLong")

        val uniTimeHouses = Houses.getCelestialHousesData(testJulday, testLat, testLong)
        val transTimeHouses = Houses.getCelestialHousesData(testTransJulday, testTransLat, testTransLong)

        val sunCelestial = CalcUt.getCelestialData(testJulday, Celestial.SUN.ordinal, uniTimeHouses, transTimeHouses)
        val moonCelestial = CalcUt.getCelestialData(testJulday, Celestial.MOON.ordinal, uniTimeHouses, transTimeHouses)
        val sunMoonCelestial = CalcUt.getCelestialData(testJulday, Celestial.SUN_MOON_MIDPOINT.ordinal, uniTimeHouses, transTimeHouses)

        println("Universal Time Celestials (raw-long)")
        println ("${Celestial.SUN.name}: ${sunCelestial.longitude}, ${sunCelestial.longitudeSpeed}, ${sunCelestial.celestialHouse}, ${sunCelestial.transitHouse}")
        println ("${Celestial.MOON.name}: ${moonCelestial.longitude}, ${moonCelestial.longitudeSpeed}, ${moonCelestial.celestialHouse}, ${moonCelestial.transitHouse}")
        println ("${Celestial.SUN_MOON_MIDPOINT.name}: ${sunMoonCelestial.longitude}, ${sunMoonCelestial.longitudeSpeed}, ${sunMoonCelestial.celestialHouse}, ${sunMoonCelestial.transitHouse}")

        assertEquals("166.4103", "%1.4f".format(sunCelestial.longitude))
        assertEquals("293.8495", "%1.4f".format(moonCelestial.longitude))
        assertEquals("230.1299", "%1.4f".format(sunMoonCelestial.longitude))

        assertEquals("0.9710", "%1.4f".format(sunCelestial.longitudeSpeed))
        assertEquals("12.0947", "%1.4f".format(moonCelestial.longitudeSpeed))
        assertEquals("0.9710", "%1.4f".format(sunMoonCelestial.longitudeSpeed))

        assertEquals("2.8776", "%1.4f".format(sunCelestial.celestialHouse))
        assertEquals("6.7325", "%1.4f".format(moonCelestial.celestialHouse))
        assertEquals("4.8181", "%1.4f".format(sunMoonCelestial.celestialHouse))

        /* sfe houses
            house  1         122° 3'16.9566  295° 9'57.6586
            house  2         143°46' 3.2996  316°39'50.1071
            house  3         169°34' 6.0559  355° 4'18.8980
            house  4         201° 1'16.8181  385°26' 5.4846
            house  5         236°36'14.5973  372°56' 2.2075
            house  6         271°22'44.7645  329°23'30.0457
            house  7         302° 3'16.9566  295° 9'57.6586
            house  8         323°46' 3.2996  316°39'50.1071
            house  9         349°34' 6.0559  355° 4'18.8980
            house 10          21° 1'16.8181  385°26' 5.4846
            house 11          56°36'14.5973  372°56' 2.2075
            house 12          91°22'44.7645  329°23'30.0457
        */

        assertEquals("9.4451", "%1.4f".format(sunCelestial.transitHouse))
        assertEquals("2.0365", "%1.4f".format(moonCelestial.transitHouse))
        assertEquals("11.6748", "%1.4f".format(sunMoonCelestial.transitHouse))

        /* tnm houses
            house  1         258°23'22.4089  310°53'18.6099
            house  2         292°27'20.5710  373°53'50.9978
            house  3         330°40'24.7064  418°16'12.0821
            house  4           6° 1'51.6979  392°45'38.4324
            house  5          34°31'51.7234  342°26' 8.2757
            house  6          57°38'52.3052  310°36' 9.9968
            house  7          78°23'22.4089  310°53'18.6099
            house  8         112°27'20.5710  373°53'50.9978
            house  9         150°40'24.7064  418°16'12.0821
            house 10         186° 1'51.6979  392°45'38.4324
            house 11         214°31'51.7234  342°26' 8.2757
            house 12         237°38'52.3052  310°36' 9.9968
        */

    }
}