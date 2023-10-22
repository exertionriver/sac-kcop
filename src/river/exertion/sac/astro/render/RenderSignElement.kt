package river.exertion.sac.astro.render

import river.exertion.sac.Constants

enum class RenderSignElement {
    FIRE_ELEMENT { override fun getLabel() = Constants.SYM_FIRE_ELEMENT }
    , EARTH_ELEMENT { override fun getLabel() = Constants.SYM_EARTH_ELEMENT }
    , AIR_ELEMENT { override fun getLabel() = Constants.SYM_AIR_ELEMENT }
    , WATER_ELEMENT { override fun getLabel() = Constants.SYM_WATER_ELEMENT }
    , NONE_ELEMENT { override fun getLabel() = Constants.SYM_ASPECT_NONE }
    ;

    abstract fun getLabel(): String

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }
    }
}