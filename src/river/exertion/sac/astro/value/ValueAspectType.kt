package river.exertion.sac.astro.value

import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.sac.astro.base.Sign
import river.exertion.sac.astro.base.SignElement
import river.exertion.sac.view.SACLayout

enum class ValueAspectType {
    CONJUNCTION { override fun isPositive() = true }
    , SEXTILE { override fun isPositive() = true }
    , SQUARE { override fun isNegative() = true }
    , TRINE { override fun isPositive() = true }
    , OPPOSITION { override fun isNegative() = true }
    , QUINCUNX { override fun isNeutral() = true }
    , SEMISEXTILE { override fun isNeutral() = true }
    , QUINTILE { override fun isPositive() = true }
    , BIQUINTILE { override fun isPositive() = true }
    , SEMISQUARE { override fun isNegative() = true }
    , SESQUISQUARE { override fun isNegative() = true }
    , SEPTILE { override fun isPositive() = true }
    , NOVILE { override fun isNeutral() = true }
    , ASPECT_NONE
    ;

    open fun isPositive() : Boolean = false
    open fun isNegative() : Boolean = false
    open fun isNeutral() : Boolean = false

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }
    }
}