package river.exertion.sac.astro.render

import river.exertion.sac.Constants
import river.exertion.sac.Constants.LABEL_SPACE

enum class RenderAspectType {
    CONJUNCTION { override fun getLabel() = Constants.SYM_CONJUNCTION }
    , SEXTILE { override fun getLabel() = Constants.SYM_SEXTILE }
    , SQUARE { override fun getLabel() = Constants.SYM_SQUARE }
    , TRINE { override fun getLabel() = Constants.SYM_TRINE }
    , OPPOSITION { override fun getLabel() = Constants.SYM_OPPOSITION }
    , QUINCUNX { override fun getLabel() = Constants.SYM_QUINCUNX }
    , SEMISEXTILE { override fun getLabel() = Constants.SYM_SEMISEXTILE }
    , QUINTILE { override fun getLabel() = Constants.SYM_QUINTILE }
    , BIQUINTILE { override fun getLabel() = Constants.SYM_BIQUINTILE }
    , SEMISQUARE { override fun getLabel() = Constants.SYM_SEMISQUARE }
    , SESQUISQUARE { override fun getLabel() = Constants.SYM_SESQUISQUARE }
    , SEPTILE { override fun getLabel() = Constants.SYM_SEPTILE }
    , NOVILE { override fun getLabel() = Constants.SYM_NOVILE }
    , ASPECT_NONE { override fun getLabel() = Constants.SYM_ASPECT_NONE }
    ;

    abstract fun getLabel(): String

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }
    }
}