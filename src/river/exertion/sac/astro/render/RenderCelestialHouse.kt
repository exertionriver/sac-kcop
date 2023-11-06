package river.exertion.sac.astro.render

import river.exertion.sac.Constants
import river.exertion.sac.astro.base.Eighths

enum class RenderCelestialHouse {
    HOUSE_1_ASC { override fun getLabel() = Constants.SYM_HOUSE_1_ASC }
    , HOUSE_2 { override fun getLabel() = Constants.SYM_HOUSE_2 }
    , HOUSE_3 { override fun getLabel() = Constants.SYM_HOUSE_3 }
    , HOUSE_4 { override fun getLabel() = Constants.SYM_HOUSE_4 }
    , HOUSE_5 { override fun getLabel() = Constants.SYM_HOUSE_5 }
    , HOUSE_6 { override fun getLabel() = Constants.SYM_HOUSE_6 }
    , HOUSE_7 { override fun getLabel() = Constants.SYM_HOUSE_7 }
    , HOUSE_8 { override fun getLabel() = Constants.SYM_HOUSE_8 }
    , HOUSE_9 { override fun getLabel() = Constants.SYM_HOUSE_9 }
    , HOUSE_10_MC { override fun getLabel() = Constants.SYM_HOUSE_10_MC }
    , HOUSE_11 { override fun getLabel() = Constants.SYM_HOUSE_11 }
    , HOUSE_12 { override fun getLabel() = Constants.SYM_HOUSE_12 }
    , VERTEX { override fun getLabel() = Constants.SYM_VERTEX }
    ;

    abstract fun getLabel(): String

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }
        
        fun getPartOfFortuneLabel() : String {
            return Constants.SYM_PART_OF_FORTUNE
        }

        fun getPartOfSpiritLabel() : String {
            return Constants.SYM_PART_OF_SPIRIT
        }

        fun getHousesLabel() = Constants.SYM_HOUSE + "(" + Constants.SYM_EARTH_LONG + "," + Constants.SYM_EARTH_LAT + ")"
        fun getHousesSignLabel() = Constants.SYM_SIGN + "(" + Constants.SYM_HOUSE + ")"
        fun getHousesLongitudeLabel() = Constants.SYM_CELESTIAL_LONG + "(" + Constants.SYM_HOUSE + ")"

        fun celestialHouseLabel(celestialHouseDecimal : Double) : String {
            val celestialHouseNumInt = celestialHouseDecimal.toInt()

            val houseOut = fromOrdinal(celestialHouseNumInt - 1)!!.getLabel()

            val housePart = celestialHouseDecimal.mod(celestialHouseNumInt.toDouble())
            val housePartOut: String = RenderEighths.fromName(Eighths.getEighth(housePart).toString())!!.getLabel()

            return "$houseOut $housePartOut"
        }
    }
}