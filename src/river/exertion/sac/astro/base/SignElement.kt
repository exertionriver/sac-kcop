package river.exertion.sac.astro.base

import river.exertion.sac.Constants
import river.exertion.sac.view.SACLayoutHandler.airFontColor
import river.exertion.sac.view.SACLayoutHandler.baseFontColor
import river.exertion.sac.view.SACLayoutHandler.earthFontColor
import river.exertion.sac.view.SACLayoutHandler.fireFontColor
import river.exertion.sac.view.SACLayoutHandler.waterFontColor

enum class SignElement {
    FIRE_ELEMENT {
        override val label = Constants.SYM_FIRE_ELEMENT
        override val fontColor = fireFontColor
    }
    , EARTH_ELEMENT {
        override val label = Constants.SYM_EARTH_ELEMENT
        override val fontColor = earthFontColor
    }
    , AIR_ELEMENT {
        override val label = Constants.SYM_AIR_ELEMENT
        override val fontColor = airFontColor
    }
    , WATER_ELEMENT {
        override val label = Constants.SYM_WATER_ELEMENT
        override val fontColor = waterFontColor
    }
    , NONE_ELEMENT
    ;

    open val label : String = Constants.SYM_NONE
    open val fontColor = baseFontColor

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }
    }
}