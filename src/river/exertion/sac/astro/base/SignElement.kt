package river.exertion.sac.astro.base

enum class SignElement {
    FIRE_ELEMENT
    , EARTH_ELEMENT
    , AIR_ELEMENT
    , WATER_ELEMENT
    , NONE_ELEMENT
    ;

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }
    }
}