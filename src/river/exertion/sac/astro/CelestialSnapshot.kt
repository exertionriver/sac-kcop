package river.exertion.sac.astro

import river.exertion.sac.swe.CalcUt
import river.exertion.sac.swe.Houses
import river.exertion.sac.swe.UtcToJd
import river.exertion.sac.Constants.normalizeDeg
import river.exertion.sac.astro.base.CelestialData
import java.util.*
import kotlin.collections.LinkedHashMap

//syn info is for transits
data class CelestialSnapshot(var refEarthLocation: EarthLocation
    , var synEarthLocation: EarthLocation = refEarthLocation
    , var refCelestialHouseData : DoubleArray = Houses.getCelestialHousesData(UtcToJd.getJulianTimeDecimal(refEarthLocation.utcDateTime, UtcToJd.UNIVERSAL_TIME), refEarthLocation.latitude, refEarthLocation.longitude) //as per documentation, "/* calculate houses with tjd_ut */"
    , var synCelestialHouseData : DoubleArray = Houses.getCelestialHousesData(UtcToJd.getJulianTimeDecimal(synEarthLocation.utcDateTime, UtcToJd.UNIVERSAL_TIME), synEarthLocation.latitude, refEarthLocation.longitude) //as per documentation, "/* calculate houses with tjd_ut */"
    , var refCelestialData: Array<CelestialData> = CalcUt.getCelestialsData(UtcToJd.getJulianTimeDecimal(refEarthLocation.utcDateTime, UtcToJd.UNIVERSAL_TIME), refCelestialHouseData, synCelestialHouseData) ) { //as per documentation, "/* calculate planet with tjd_et */"

    fun partOfFortuneData() : Double = partOfFortuneData(refCelestialData[Celestial.SUN.ordinal].longitude
            , refCelestialData[Celestial.MOON.ordinal].longitude
            , refCelestialHouseData[CelestialHouse.HOUSE_1_ASC.ordinal]
            , refCelestialData[Celestial.SUN.ordinal].celestialHouse)

    fun partOfSpiritData() : Double = partOfSpiritData(refCelestialData[Celestial.SUN.ordinal].longitude
        , refCelestialData[Celestial.MOON.ordinal].longitude
        , refCelestialHouseData[CelestialHouse.HOUSE_1_ASC.ordinal]
        , refCelestialData[Celestial.SUN.ordinal].celestialHouse)

    fun recalc() {
        refEarthLocation.recalc()
        refCelestialHouseData = Houses.getCelestialHousesData(UtcToJd.getJulianTimeDecimal(refEarthLocation.utcDateTime, UtcToJd.UNIVERSAL_TIME), refEarthLocation.latitude, refEarthLocation.longitude)
        synCelestialHouseData = Houses.getCelestialHousesData(UtcToJd.getJulianTimeDecimal(synEarthLocation.utcDateTime, UtcToJd.UNIVERSAL_TIME), synEarthLocation.latitude, refEarthLocation.longitude)
        refCelestialData = CalcUt.getCelestialsData(UtcToJd.getJulianTimeDecimal(refEarthLocation.utcDateTime, UtcToJd.UNIVERSAL_TIME), refCelestialHouseData, synCelestialHouseData)
    }

    fun getAspectCelestialLongitudeMap() : Map<AspectCelestial, Double> {

        val unsortedMap : MutableMap<AspectCelestial, Double> = EnumMap(AspectCelestial::class.java)

        for(celestial in Celestial.entries) {
            if (getAspectCelestial(celestial) != AspectCelestial.ASPECT_CELESTIAL_NONE)
                unsortedMap[getAspectCelestial(celestial)] = refCelestialData[celestial.ordinal].longitude
        }

        for(celestialHouse in CelestialHouse.entries) {
            if (getAspectCelestial(celestialHouse) != AspectCelestial.ASPECT_CELESTIAL_NONE)
                unsortedMap[getAspectCelestial(celestialHouse)] = refCelestialHouseData[celestialHouse.ordinal]
        }

        unsortedMap[AspectCelestial.ASPECT_PART_OF_FORTUNE] = partOfFortuneData()

        val sortedMap : MutableMap<AspectCelestial, Double> = LinkedHashMap()
        unsortedMap.entries.sortedBy { it.key }.forEach { sortedMap[it.key] = it.value }

        return sortedMap
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as CelestialSnapshot

        if (!refCelestialHouseData.contentEquals(other.refCelestialHouseData)) return false
        if (!refCelestialData.contentEquals(other.refCelestialData)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = refCelestialHouseData.contentHashCode()
        result = 31 * result + refCelestialData.hashCode()
        return result
    }

    companion object {

        private fun isNightChart(sunHouse : Double) : Boolean {
            return (sunHouse < 7)
        }

        fun partOfFortuneData(sunLongitude : Double, moonLongitude : Double, ascLongitude : Double, sunHouse : Double) : Double {

            val sunMoonDiff : Double = if (isNightChart(sunHouse)) (sunLongitude - moonLongitude) else (moonLongitude - sunLongitude)

            return (ascLongitude + sunMoonDiff).normalizeDeg()
        }

        fun partOfSpiritData(sunLongitude : Double, moonLongitude : Double, ascLongitude : Double, sunHouse : Double) : Double {

            val sunMoonDiff : Double = if (isNightChart(sunHouse)) (moonLongitude - sunLongitude) else (sunLongitude - moonLongitude)

            return (ascLongitude + sunMoonDiff).normalizeDeg()
        }

        fun getAspectCelestial(celestial : Celestial) : AspectCelestial {
            return when (celestial) {
                Celestial.SUN -> AspectCelestial.ASPECT_SUN
                Celestial.MOON -> AspectCelestial.ASPECT_MOON
                Celestial.MERCURY -> AspectCelestial.ASPECT_MERCURY
                Celestial.VENUS -> AspectCelestial.ASPECT_VENUS
                Celestial.MARS -> AspectCelestial.ASPECT_MARS
                Celestial.JUPITER -> AspectCelestial.ASPECT_JUPITER
                Celestial.SATURN -> AspectCelestial.ASPECT_SATURN
                Celestial.URANUS -> AspectCelestial.ASPECT_URANUS
                Celestial.NEPTUNE -> AspectCelestial.ASPECT_NEPTUNE
                Celestial.PLUTO -> AspectCelestial.ASPECT_PLUTO
                Celestial.NORTH_NODE -> AspectCelestial.ASPECT_NORTH_NODE
                Celestial.BLACK_MOON_LILITH -> AspectCelestial.ASPECT_BLACK_MOON_LILITH
                Celestial.CHIRON -> AspectCelestial.ASPECT_CHIRON
                else -> AspectCelestial.ASPECT_CELESTIAL_NONE
            }
        }

        fun getAspectCelestial(celestialHouse : CelestialHouse) : AspectCelestial {
            return when (celestialHouse) {
                CelestialHouse.HOUSE_1_ASC -> AspectCelestial.ASPECT_ASCENDANT
                CelestialHouse.HOUSE_10_MC -> AspectCelestial.ASPECT_MIDHEAVEN
                CelestialHouse.VERTEX -> AspectCelestial.ASPECT_VERTEX
                else -> AspectCelestial.ASPECT_CELESTIAL_NONE
            }
        }

        fun getCompositeSnapshot(firstSnapshot : CelestialSnapshot, secondSnapshot : CelestialSnapshot
                                 , firstEarthLocation: EarthLocation = EarthLocation(), secondEarthLocation: EarthLocation = EarthLocation()
        ) : CelestialSnapshot {

            val compositeHousesData = Houses.getCompositeCelestialHousesData(firstSnapshot.refCelestialHouseData, secondSnapshot.refCelestialHouseData)

            return CelestialSnapshot(firstEarthLocation, secondEarthLocation
                    , compositeHousesData, compositeHousesData
                    , CalcUt.getCompositeCelestialsData(compositeHousesData, firstSnapshot.refCelestialData, secondSnapshot.refCelestialData)
            )

        }
    }
}