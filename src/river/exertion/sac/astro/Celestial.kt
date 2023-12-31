package river.exertion.sac.astro

import river.exertion.sac.Constants
import river.exertion.sac.astro.base.CelestialData

enum class Celestial {
    SUN {
        override val label = Constants.SYM_SUN
    }
    , MOON {
        override val label = Constants.SYM_MOON
    }
    , MERCURY {
        override val label = Constants.SYM_MERCURY
    }
    , VENUS {
        override val label = Constants.SYM_VENUS
    }
    , MARS {
        override val label = Constants.SYM_MARS
    }
    , JUPITER {
        override val label = Constants.SYM_JUPITER
    }
    , SATURN {
        override val label = Constants.SYM_SATURN
    }
    , URANUS {
        override val label = Constants.SYM_URANUS
    }
    , NEPTUNE {
        override val label = Constants.SYM_NEPTUNE
    }
    , PLUTO {
        override val label = Constants.SYM_PLUTO
    }
    , NORTH_NODE {
        override val label = Constants.SYM_NORTH_NODE
    }
    , BLACK_MOON_LILITH {
        override val label = Constants.SYM_BLACK_MOON_LILITH
    }
    , CHIRON {
        override val label = Constants.SYM_CHIRON
    }
    , PHOLUS {
        override val label = Constants.SYM_PHOLUS
    }
    , CERES {
        override val label = Constants.SYM_CERES
    }
    , PALLAS {
        override val label = Constants.SYM_PALLAS
    }
    , JUNO {
        override val label = Constants.SYM_JUNO
    }
    , VESTA {
        override val label = Constants.SYM_VESTA
    }
    ;

    abstract val label : String

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }

        val transitMax = PLUTO

        val celestialHeaderLabel = Constants.SYM_CELESTIAL
        val celestialSignHeaderLabel = Constants.SYM_SIGN + "(" + Constants.SYM_CELESTIAL + ")"
        val celestialLongitudeHeaderLabel = Constants.SYM_CELESTIAL_LONG + "(" + Constants.SYM_CELESTIAL + ")"
        val celestialLatitudeHeaderLabel = Constants.SYM_CELESTIAL_LAT + "(" + Constants.SYM_CELESTIAL + ")"
        val celestialDistanceHeaderLabel = Constants.SYM_CELESTIAL_DIST + "(" + Constants.SYM_CELESTIAL + ")"
        val celestialLongitudeSpeedHeaderLabel = "d(" + Constants.SYM_CELESTIAL_LONG + ")"
        val celestialLatitudeSpeedHeaderLabel = "d(" + Constants.SYM_CELESTIAL_LAT + ")"
        val celestialDistanceSpeedHeaderLabel = "d(" + Constants.SYM_CELESTIAL_DIST + ")"
        val celestialHouseHeaderLabel = Constants.SYM_HOUSE + "(" + Constants.SYM_CELESTIAL + ")"
        val celestialTransitHouseHeaderLabel = Constants.SYM_HOUSE + " " + Constants.SYM_CONTAINS + " " + Constants.SYM_CELESTIAL + " "
        val celestialTransitCelestialHeaderLabel = Constants.SYM_CELESTIAL + " " + Constants.SYM_EXISTS_IN + " " + Constants.SYM_HOUSE + " "

        fun getTransitCelestialsLabel(refTransitHouseNum : Int, synCelestialData: Array<CelestialData>) : String {

            var transitCelestialsLabel = ""
            var celestialHouseNumInt: Int

            for (celestialTransitPlanetIdx in SUN.ordinal..transitMax.ordinal) {

                celestialHouseNumInt = synCelestialData[celestialTransitPlanetIdx].celestialHouse.toInt()
                if (celestialHouseNumInt == refTransitHouseNum) {
                    transitCelestialsLabel = transitCelestialsLabel.plus(fromOrdinal(celestialTransitPlanetIdx)!!.label)
                }
            }

            return transitCelestialsLabel
        }
    }
}