package river.exertion.sac.astro.render

import river.exertion.sac.Constants
import river.exertion.sac.Constants.LABEL_SPACE

enum class RenderCelestial {
    SUN { override fun getLabel() = Constants.SYM_SUN }
    , MOON { override fun getLabel() = Constants.SYM_MOON }
    , MERCURY { override fun getLabel() = Constants.SYM_MERCURY }
    , VENUS { override fun getLabel() = Constants.SYM_VENUS }
    , MARS { override fun getLabel() = Constants.SYM_MARS }
    , JUPITER { override fun getLabel() = Constants.SYM_JUPITER }
    , SATURN { override fun getLabel() = Constants.SYM_SATURN }
    , URANUS { override fun getLabel() = Constants.SYM_URANUS}
    , NEPTUNE { override fun getLabel() = Constants.SYM_NEPTUNE }
    , PLUTO { override fun getLabel() = Constants.SYM_PLUTO }
    , NORTH_NODE { override fun getLabel() = Constants.SYM_NORTH_NODE }
    , BLACK_MOON_LILITH { override fun getLabel() = Constants.SYM_BLACK_MOON_LILITH }
    , CHIRON { override fun getLabel() = Constants.SYM_CHIRON }
    , PHOLUS { override fun getLabel() = Constants.SYM_PHOLUS }
    , CERES { override fun getLabel() = Constants.SYM_CERES}
    , PALLAS { override fun getLabel() = Constants.SYM_PALLAS }
    , JUNO { override fun getLabel() = Constants.SYM_JUNO }
    , VESTA { override fun getLabel() = Constants.SYM_VESTA }
    ;

    abstract fun getLabel() : String

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }

        fun getCelestialsLabel() = Constants.SYM_CELESTIAL
        fun getCelestialsSignLabel() = Constants.SYM_SIGN + "(" + Constants.SYM_CELESTIAL + ")"
        fun getCelestialsLongitudeLabel() = Constants.SYM_CELESTIAL_LONG + "(" + Constants.SYM_CELESTIAL + ")"
        fun getCelestialsLatitudeLabel() = Constants.SYM_CELESTIAL_LAT + "(" + Constants.SYM_CELESTIAL + ")"
        fun getCelestialsDistanceLabel() = Constants.SYM_CELESTIAL_DIST + "(" + Constants.SYM_CELESTIAL + ")"
        fun getCelestialsLongitudeSpeedLabel() = "d(" + Constants.SYM_CELESTIAL_LONG + ")"
        fun getCelestialsLatitudeSpeedLabel() = "d(" + Constants.SYM_CELESTIAL_LAT + ")"
        fun getCelestialsDistanceSpeedLabel() = "d(" + Constants.SYM_CELESTIAL_DIST + ")"
        fun getCelestialsHouseLabel() = Constants.SYM_HOUSE + "(" + Constants.SYM_CELESTIAL + ")"
        fun getCelestialsTransitHouseLabel() = Constants.KMAG + Constants.SYM_HOUSE + " " + Constants.SYM_CONTAINS + " " + Constants.KCYN + Constants.SYM_CELESTIAL + " " + Constants.KNRM
        fun getCelestialsTransitCelestialsLabel() = Constants.KCYN + Constants.SYM_CELESTIAL + " " + Constants.KMAG + Constants.SYM_EXISTS_IN + " " + Constants.SYM_HOUSE + " " + Constants.KNRM

    }
}