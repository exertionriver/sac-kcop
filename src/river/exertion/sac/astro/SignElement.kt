package river.exertion.sac.astro

import river.exertion.sac.Constants
import river.exertion.sac.view.SACLayoutHandler.airFontColor
import river.exertion.sac.view.SACLayoutHandler.baseFontColor
import river.exertion.sac.view.SACLayoutHandler.earthFontColor
import river.exertion.sac.view.SACLayoutHandler.fireFontColor
import river.exertion.sac.view.SACLayoutHandler.waterFontColor

enum class SignElement {
    FIRE_ELEMENT {
        override val fontColor = fireFontColor
        override val label = Constants.SYM_FIRE_ELEMENT
    }
    , EARTH_ELEMENT {
        override val fontColor = earthFontColor
        override val label = Constants.SYM_EARTH_ELEMENT
    }
    , AIR_ELEMENT {
        override val fontColor = airFontColor
        override val label = Constants.SYM_AIR_ELEMENT
    }
    , WATER_ELEMENT {
        override val fontColor = waterFontColor
        override val label = Constants.SYM_WATER_ELEMENT
    }
    , NONE_ELEMENT
    ;

    open val fontColor = baseFontColor
    open val label : String = Constants.SYM_NONE

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }
    }
}