package river.exertion.sac.astro.base

import river.exertion.sac.Constants


enum class CelestialHouse {
    HOUSE_1_ASC {
        override val label = Constants.SYM_HOUSE_1_ASC
    }
    , HOUSE_2 {
        override val label = Constants.SYM_HOUSE_2
    }
    , HOUSE_3 {
        override val label = Constants.SYM_HOUSE_3
    }
    , HOUSE_4 {
        override val label = Constants.SYM_HOUSE_4
    }
    , HOUSE_5 {
        override val label = Constants.SYM_HOUSE_5
    }
    , HOUSE_6 {
        override val label = Constants.SYM_HOUSE_6
    }
    , HOUSE_7 {
        override val label = Constants.SYM_HOUSE_7
    }
    , HOUSE_8 {
        override val label = Constants.SYM_HOUSE_8
    }
    , HOUSE_9 {
        override val label = Constants.SYM_HOUSE_9
    }
    , HOUSE_10_MC {
        override val label = Constants.SYM_HOUSE_10_MC
    }
    , HOUSE_11 {
        override val label = Constants.SYM_HOUSE_11
    }
    , HOUSE_12 {
        override val label = Constants.SYM_HOUSE_12
    }
    , VERTEX {
        override val label = Constants.SYM_VERTEX
        override val isBaseHouse = false
    }
    , PART_OF_FORTUNE {
        override val label = Constants.SYM_PART_OF_FORTUNE
        override val isBaseHouse = false
    }
    , PART_OF_SPIRIT {
        override val label = Constants.SYM_PART_OF_SPIRIT
        override val isBaseHouse = false
    }
    ;

    open val isBaseHouse = true
    abstract val label : String

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }

        val houseSystem: Char = 'P' // Placidus

        val celestialHouseLongLatHeaderLabel = Constants.SYM_HOUSE + "(" + Constants.SYM_EARTH_LONG + "," + Constants.SYM_EARTH_LAT + ")"
        val celestialHouseSignHeaderLabel = Constants.SYM_SIGN + "(" + Constants.SYM_HOUSE + ")"
        val celestialHouseLongitudeHeaderLabel = Constants.SYM_CELESTIAL_LONG + "(" + Constants.SYM_HOUSE + ")"

        fun celestialHouseLabel(celestialHouseDecimal : Double) : String {
            val celestialHouseNumInt = celestialHouseDecimal.toInt()

            val houseOut = fromOrdinal(celestialHouseNumInt - 1)!!.label

            val housePart = celestialHouseDecimal.mod(celestialHouseNumInt.toDouble())
            val housePartOut: String = Eighths.fractionToEighth(housePart).label

            return "$houseOut $housePartOut"
        }

        fun getHouseData(celestialPosition: Double, celestialHousesData : DoubleArray) : Double {

            var topCusp : Double
            var bottomCusp : Double

            var houseIdx = 0
            var houseFraction = 0.0

            //recalculate celestialPosition house data for cusps
            var nextHouse : CelestialHouse

                for (house in entries) {
                    if (!house.isBaseHouse) break

                    houseIdx = house.ordinal

                    nextHouse = fromOrdinal(houseIdx + 1)!!

                    topCusp = if (house == HOUSE_12) {
                        celestialHousesData[HOUSE_1_ASC.ordinal]
                    } else {
                        celestialHousesData[nextHouse.ordinal]
                    }

                    bottomCusp = celestialHousesData[houseIdx]

                    if (topCusp > bottomCusp) { //normal case
                        if ((bottomCusp <= celestialPosition) && (topCusp > celestialPosition)) {
                            houseFraction = (celestialPosition - bottomCusp) / (topCusp - bottomCusp)
                            break
                        }
                    } else { //house circles through 0deg
                        if (celestialPosition >= bottomCusp) { // position is < 360 deg
                            houseFraction = (celestialPosition - bottomCusp) / (360 + topCusp - bottomCusp)
                            break
                        } else if (celestialPosition < topCusp) { // position is >= 0 deg
                            houseFraction = (360 + celestialPosition - bottomCusp) / (360 + topCusp - bottomCusp)
                            break
                        }
                    }
                }
            return houseIdx + 1 + houseFraction
        }
    }
}