package river.exertion.sac.astro.render

import river.exertion.sac.Constants
import river.exertion.sac.astro.base.CelestialData
import river.exertion.sac.astro.base.Sign
import river.exertion.sac.astro.base.SignElement

enum class RenderSign {
    ARIES { override fun getLabel() = Constants.SYM_ARIES }
    , TAURUS { override fun getLabel() = Constants.SYM_TAURUS }
    , GEMINI { override fun getLabel() = Constants.SYM_GEMINI }
    , CANCER { override fun getLabel() = Constants.SYM_CANCER }
    , LEO { override fun getLabel() = Constants.SYM_LEO }
    , VIRGO { override fun getLabel() = Constants.SYM_VIRGO }
    , LIBRA { override fun getLabel() = Constants.SYM_LIBRA }
    , SCORPIO { override fun getLabel() = Constants.SYM_SCORPIO }
    , SAGITTARIUS { override fun getLabel() = Constants.SYM_SAGITTARIUS }
    , CAPRICORN { override fun getLabel() = Constants.SYM_CAPRICORN }
    , AQUARIUS { override fun getLabel() = Constants.SYM_AQUARIUS }
    , PISCES { override fun getLabel() = Constants.SYM_PISCES }
    , SIGN_NONE { override fun getLabel() = Constants.SYM_ASPECT_NONE }
    ;

    abstract fun getLabel(): String

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }

        fun getSynLabel(sign : Sign) : String = Constants.KMAG + fromName(sign.toString())!!.getLabel() + Constants.KNRM

        fun getElementLabel(sign : Sign) : String = when {
            (sign.getElement() == SignElement.FIRE_ELEMENT) -> Constants.KRED + fromName(sign.toString())!!.getLabel() + Constants.KNRM
            (sign.getElement() == SignElement.EARTH_ELEMENT) -> Constants.KYEL + fromName(sign.toString())!!.getLabel()+ Constants.KNRM
            (sign.getElement() == SignElement.AIR_ELEMENT) -> Constants.KGRN + fromName(sign.toString())!!.getLabel() + Constants.KNRM
            (sign.getElement() == SignElement.WATER_ELEMENT) -> Constants.KBLU + fromName(sign.toString())!!.getLabel() + Constants.KNRM
            else -> fromName(sign.toString())!!.getLabel()
        }

        fun getSignLongitudeLabel() = Constants.SYM_CELESTIAL_LONG + "(" + Constants.SYM_SIGN + " )"

        fun getSignLabelFromCelestialData(celestialData : CelestialData) : String {

            return getSignLabelFromCelestialLongitude(celestialData.longitude, celestialData.longitudeSpeed)
        }

        fun getSignLabelFromCelestialLongitude(celestialLongitude : Double, celestialLongitudeSpeed : Double = 0.0) : String {

            return if (celestialLongitudeSpeed < 0)
                getElementLabel(Sign.getSignFromCelestialLongitude(celestialLongitude)) + " " + Constants.SYM_RETRO
            else
                getElementLabel(Sign.getSignFromCelestialLongitude(celestialLongitude)) + "  " //space offset for Constants.SYM_RETRO
        }
    }
}