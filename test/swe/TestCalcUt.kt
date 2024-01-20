package swe

import TestEarthLocations
import river.exertion.sac.astro.base.Celestial
import river.exertion.sac.swe.CalcUt
import river.exertion.sac.swe.Houses
import river.exertion.sac.swe.Julday
import org.junit.jupiter.api.Test
import river.exertion.sac.astro.base.CelestialHouse
import kotlin.test.assertEquals

class TestCalcUt {

    /*slight celestial variations from online swetest, e.g. with JulDate 2458735.881250000 swetest 2024-01 shows:
        Sun : 166.4102357
        Moon : 293.8492244
        Mercury : 171.2312683
    with JulDate 2458735.881250000 getCelestialsData() 2.01.00-02 shows:
        SUN, 166.41024982851437
        MOON, 293.8493989606262
        MERCURY, 171.2312947832916
    Houses tie out ok with online swetest.
    */
    @Test
    fun testGetCelestialsData() {
        val utc = TestEarthLocations.sfeEarthLocation.utcDateTime
        val lat = TestEarthLocations.sfeEarthLocation.latitude
        val long = TestEarthLocations.sfeEarthLocation.longitude

        println("utc: $utc")
        println("lat/long: $lat / $long")

        val julday = Julday.getJulianTimeDecimal(utc)
        val houses = Houses.getCelestialHousesData(julday, lat, long)
        val celestials = CalcUt.getCelestialsData(julday, houses)

        println ("julday: $julday")

        println("celestials (dec)")
        celestials.forEachIndexed { idx, celestial -> println ("${Celestial.fromOrdinal(idx)}, ${celestial.longitude}") }
        assertEquals("166.4102", "%1.4f".format(celestials[0].longitude))
        assertEquals("164.1089", "%1.4f".format(celestials[4].longitude))
        assertEquals("347.3360", "%1.4f".format(celestials[8].longitude))

        val celestialsDeg = celestials.map{ it.longitude.toInt() }
        val celestialsMin = celestials.mapIndexed { idx, celestial -> ((celestial.longitude - celestialsDeg[idx]) * 60).toInt() }
        val celestialsSec = celestials.mapIndexed { idx, celestial -> ((celestial.longitude - celestialsDeg[idx] - celestialsMin[idx].toDouble() / 60) * 3600) }

        println("celestials (deg)")
        celestialsDeg.forEachIndexed { idx, celestialDeg -> println ("${Celestial.fromOrdinal(idx)}, $celestialDeg ${celestialsMin[idx]}'${celestialsSec[idx]}") }
        assertEquals(293, celestialsDeg[1])
        assertEquals(255, celestialsDeg[5])
        assertEquals(290, celestialsDeg[9])

        assertEquals(13, celestialsMin[2])
        assertEquals(58, celestialsMin[6])
        assertEquals(58, celestialsMin[10])

        assertEquals("25.1522", "%1.4f".format(celestialsSec[3]))
        assertEquals("42.5056", "%1.4f".format(celestialsSec[7]))
        assertEquals("16.8775", "%1.4f".format(celestialsSec[11]))
    }

    @Test
    fun testGetCelestialData() {
        val julday = 2458735.881250000 // julday for sfeEarthLocation
        val lat = TestEarthLocations.sfeEarthLocation.latitude
        val long = TestEarthLocations.sfeEarthLocation.longitude

        val transitJulday = Julday.getJulianTimeDecimal(TestEarthLocations.tnmEarthLocation.utcDateTime)
        val transitLat = TestEarthLocations.tnmEarthLocation.latitude
        val transitLong = TestEarthLocations.tnmEarthLocation.longitude

        println("julday: $julday")
        println("lat/long: $lat / $long")

        val houses = Houses.getCelestialHousesData(julday, lat, long)
        val transitHouses = Houses.getCelestialHousesData(transitJulday, transitLat, transitLong)

        val sunCelestial = CalcUt.getCelestialData(julday, Celestial.SUN.ordinal, houses, transitHouses)
        val moonCelestial = CalcUt.getCelestialData(julday, Celestial.MOON.ordinal, houses, transitHouses)
        val sunMoonCelestial = CalcUt.getCelestialData(julday, Celestial.SUN_MOON_MIDPOINT.ordinal, houses, transitHouses)

        println("celestials (dec)")
        println ("${Celestial.SUN.name}: ${sunCelestial.longitude}, ${sunCelestial.longitudeSpeed}, ${sunCelestial.celestialHouse}, ${sunCelestial.transitHouse}")
        println ("${Celestial.MOON.name}: ${moonCelestial.longitude}, ${moonCelestial.longitudeSpeed}, ${moonCelestial.celestialHouse}, ${moonCelestial.transitHouse}")
        println ("${Celestial.SUN_MOON_MIDPOINT.name}: ${sunMoonCelestial.longitude}, ${sunMoonCelestial.longitudeSpeed}, ${sunMoonCelestial.celestialHouse}, ${sunMoonCelestial.transitHouse}")

        assertEquals("166.4102", "%1.4f".format(sunCelestial.longitude))
        assertEquals("293.8494", "%1.4f".format(moonCelestial.longitude))
        assertEquals("230.1298", "%1.4f".format(sunMoonCelestial.longitude))

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

    @Test
    fun testGetCompositeCelestialsData() {
        val firstUTC = TestEarthLocations.sfeEarthLocation.utcDateTime
        val firstLat = TestEarthLocations.sfeEarthLocation.latitude
        val firstLong = TestEarthLocations.sfeEarthLocation.longitude

        println("first utc: $firstUTC")
        println("first lat/long: $firstLat / $firstLong")

        val firstJulday = Julday.getJulianTimeDecimal(firstUTC)
        val firstHouses = Houses.getCelestialHousesData(firstJulday, firstLat, firstLong)
        val firstCelestials = CalcUt.getCelestialsData(firstJulday, firstHouses)

        println("first julday: $firstJulday")
        println("first celestials (dec)")
        firstCelestials.forEachIndexed { idx, celestial -> println ("${Celestial.fromOrdinal(idx)}, ${celestial.longitude}") }

        val secondUTC = TestEarthLocations.tnmEarthLocation.utcDateTime
        val secondLat = TestEarthLocations.tnmEarthLocation.latitude
        val secondLong = TestEarthLocations.tnmEarthLocation.longitude

        println("second utc: $secondUTC")
        println("second lat/long: $secondLat / $secondLong")

        val secondJulday = Julday.getJulianTimeDecimal(secondUTC)
        val secondHouses = Houses.getCelestialHousesData(secondJulday, secondLat, secondLong)
        val secondCelestials = CalcUt.getCelestialsData(secondJulday, secondHouses)

        println("second julday: $secondJulday")
        println("second celestials (dec)")
        secondCelestials.forEachIndexed { idx, celestial -> println ("${Celestial.fromOrdinal(idx)}, ${celestial.longitude}") }

        val compositeHouses = Houses.getCompositeCelestialHousesData(firstHouses, secondHouses)
        val compositeCelestials = CalcUt.getCompositeCelestialsData(compositeHouses, firstCelestials, secondCelestials)

        println("composite celestials (dec)")
        compositeCelestials.forEachIndexed { idx, celestial -> println ("${Celestial.fromOrdinal(idx)}, ${celestial.longitude}") }

        assertEquals("181.5390", "%1.4f".format(compositeCelestials[0].longitude))
        assertEquals("180.1266", "%1.4f".format(compositeCelestials[4].longitude))
        assertEquals("349.2252", "%1.4f".format(compositeCelestials[8].longitude))

        val compositeCelestialsDeg = compositeCelestials.map{ it.longitude.toInt() }
        val compositeCelestialsMin = compositeCelestials.mapIndexed { idx, celestial -> ((celestial.longitude - compositeCelestialsDeg[idx]) * 60).toInt() }
        val compositeCelestialsSec = compositeCelestials.mapIndexed { idx, celestial -> ((celestial.longitude - compositeCelestialsDeg[idx] - compositeCelestialsMin[idx].toDouble() / 60) * 3600) }

        println("composite celestials (deg)")
        compositeCelestialsDeg.forEachIndexed { idx, celestialDeg -> println ("${Celestial.fromOrdinal(idx)}, ${compositeCelestialsDeg[idx]} ${compositeCelestialsMin[idx]}'${compositeCelestialsSec[idx]}") }
        assertEquals(267, compositeCelestialsDeg[1])
        assertEquals(289, compositeCelestialsDeg[5])
        assertEquals(292, compositeCelestialsDeg[9])

        assertEquals(51, compositeCelestialsMin[2])
        assertEquals(25, compositeCelestialsMin[6])
        assertEquals(15, compositeCelestialsMin[10])

        assertEquals("36.5701", "%1.4f".format(compositeCelestialsSec[3]))
        assertEquals("17.1815", "%1.4f".format(compositeCelestialsSec[7]))
        assertEquals("13.9635", "%1.4f".format(compositeCelestialsSec[11]))
    }

    @Test
    fun testGetCompositeCelestialData() {
        val firstUTC = TestEarthLocations.sfeEarthLocation.utcDateTime
        val firstLat = TestEarthLocations.sfeEarthLocation.latitude
        val firstLong = TestEarthLocations.sfeEarthLocation.longitude

        println("first utc: $firstUTC")
        println("first lat/long: $firstLat / $firstLong")

        val firstJulday = Julday.getJulianTimeDecimal(firstUTC)
        val firstHouses = Houses.getCelestialHousesData(firstJulday, firstLat, firstLong)
        val firstCelestials = CalcUt.getCelestialsData(firstJulday, firstHouses)

        println("first julday: $firstJulday")
        println("first celestials (dec)")
        firstCelestials.forEachIndexed { idx, celestial -> println ("${Celestial.fromOrdinal(idx)}, ${celestial.longitude}") }

        val secondUTC = TestEarthLocations.tnmEarthLocation.utcDateTime
        val secondLat = TestEarthLocations.tnmEarthLocation.latitude
        val secondLong = TestEarthLocations.tnmEarthLocation.longitude

        println("second utc: $secondUTC")
        println("second lat/long: $secondLat / $secondLong")

        val secondJulday = Julday.getJulianTimeDecimal(secondUTC)
        val secondHouses = Houses.getCelestialHousesData(secondJulday, secondLat, secondLong)
        val secondCelestials = CalcUt.getCelestialsData(secondJulday, secondHouses)

        println("second julday: $secondJulday")
        println("second celestials (dec)")
        secondCelestials.forEachIndexed { idx, celestial -> println ("${Celestial.fromOrdinal(idx)}, ${celestial.longitude}") }

        val compositeHouses = Houses.getCompositeCelestialHousesData(firstHouses, secondHouses)

        println("composite houses (dec)")
        compositeHouses.forEachIndexed { idx, house -> println ("${CelestialHouse.fromOrdinal(idx)}, $house") }

        val marsCelestial = CalcUt.getCompositeCelestialData(Celestial.SUN.ordinal, compositeHouses, firstCelestials, secondCelestials)
        val jupiterCelestial = CalcUt.getCompositeCelestialData(Celestial.MOON.ordinal, compositeHouses, firstCelestials, secondCelestials)
        val neptuneCelestial = CalcUt.getCompositeCelestialData(Celestial.SUN_MOON_MIDPOINT.ordinal, compositeHouses, firstCelestials, secondCelestials)

        println("composite celestials (dec)")
        println ("${Celestial.MARS.name}: ${marsCelestial.longitude}, ${marsCelestial.longitudeSpeed}, ${marsCelestial.celestialHouse}, ${marsCelestial.transitHouse}")
        println ("${Celestial.JUPITER.name}: ${jupiterCelestial.longitude}, ${jupiterCelestial.longitudeSpeed}, ${jupiterCelestial.celestialHouse}, ${jupiterCelestial.transitHouse}")
        println ("${Celestial.NEPTUNE.name}: ${neptuneCelestial.longitude}, ${neptuneCelestial.longitudeSpeed}, ${neptuneCelestial.celestialHouse}, ${neptuneCelestial.transitHouse}")

        assertEquals("181.5390", "%1.4f".format(marsCelestial.longitude))
        assertEquals("267.7675", "%1.4f".format(jupiterCelestial.longitude))
        assertEquals("224.6532", "%1.4f".format(neptuneCelestial.longitude))

        assertEquals("0.9796", "%1.4f".format(marsCelestial.longitudeSpeed))
        assertEquals("13.3509", "%1.4f".format(jupiterCelestial.longitudeSpeed))
        assertEquals("0.9796", "%1.4f".format(neptuneCelestial.longitudeSpeed))

        assertEquals("12.6622", "%1.4f".format(marsCelestial.celestialHouse))
        assertEquals("3.5283", "%1.4f".format(jupiterCelestial.celestialHouse))
        assertEquals("2.2044", "%1.4f".format(neptuneCelestial.celestialHouse))
    }
}