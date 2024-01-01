package river.exertion.sac.astro.base

import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.sac.Constants

enum class Sign {
    ARIES { 
        override val signElement = SignElement.FIRE_ELEMENT
        override val signMode = SignMode.CARDINAL_MODE
        override val label = Constants.SYM_ARIES
    }
    , TAURUS { 
        override val signElement = SignElement.EARTH_ELEMENT
        override val signMode = SignMode.FIXED_MODE
        override val label = Constants.SYM_TAURUS
    }
    , GEMINI { 
        override val signElement = SignElement.AIR_ELEMENT
        override val signMode = SignMode.MUTABLE_MODE
        override val label = Constants.SYM_GEMINI
    }
    , CANCER { 
        override val signElement = SignElement.WATER_ELEMENT
        override val signMode = SignMode.CARDINAL_MODE
        override val label = Constants.SYM_CANCER
    }
    , LEO { 
        override val signElement = SignElement.FIRE_ELEMENT
        override val signMode = SignMode.FIXED_MODE
        override val label = Constants.SYM_LEO
    }
    , VIRGO { 
        override val signElement = SignElement.EARTH_ELEMENT
        override val signMode = SignMode.MUTABLE_MODE
        override val label = Constants.SYM_VIRGO
    }
    , LIBRA { 
        override val signElement = SignElement.AIR_ELEMENT
        override val signMode = SignMode.CARDINAL_MODE
        override val label = Constants.SYM_LIBRA
    }
    , SCORPIO { 
        override val signElement = SignElement.WATER_ELEMENT
        override val signMode = SignMode.FIXED_MODE
        override val label = Constants.SYM_SCORPIO
    }
    , SAGITTARIUS { 
        override val signElement = SignElement.FIRE_ELEMENT
        override val signMode = SignMode.MUTABLE_MODE
        override val label = Constants.SYM_SAGITTARIUS
    }
    , CAPRICORN { 
        override val signElement = SignElement.EARTH_ELEMENT
        override val signMode = SignMode.CARDINAL_MODE
        override val label = Constants.SYM_CAPRICORN
    }
    , AQUARIUS { 
        override val signElement = SignElement.AIR_ELEMENT
        override val signMode = SignMode.FIXED_MODE
        override val label = Constants.SYM_AQUARIUS
    }
    , PISCES { 
        override val signElement = SignElement.WATER_ELEMENT
        override val signMode = SignMode.MUTABLE_MODE
        override val label = Constants.SYM_PISCES
    }
    , SIGN_NONE
    ;
    
    open val signElement : SignElement = SignElement.NONE_ELEMENT
    open val signMode : SignMode = SignMode.NONE_MODE
    open val label : String = Constants.SYM_NONE

    fun signColor() : ColorPalette = signElement.fontColor

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }

        val signLongitudeHeaderLabel = Constants.SYM_CELESTIAL_LONG + "(" + Constants.SYM_SIGN + ")"

        fun signLabelFromCelestialLongitude(celestialLongitude : Double, celestialLongitudeSpeed : Double = 0.0) : String {

            return if (celestialLongitudeSpeed < 0)
                signFromCelestialLongitude(celestialLongitude).label + " " + Constants.SYM_RETRO
            else
                signFromCelestialLongitude(celestialLongitude).label //space offset for Constants.SYM_RETRO
        }

        fun signColor(celestialLongitude : Double) : ColorPalette = signFromCelestialLongitude(celestialLongitude).signColor()

        fun signFromCelestialLongitude(celestialLongitude: Double) : Sign = fromOrdinal((celestialLongitude / 30).toInt())!!
    }
}