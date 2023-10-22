package river.exertion.sac.astro.state

import river.exertion.sac.Constants.SYM_CHARACTER
import river.exertion.sac.Constants.SYM_ELEMENT
import river.exertion.sac.Constants.SYM_MODE
import river.exertion.sac.Constants.SYM_PLANET
import river.exertion.sac.Constants.SYM_ROMANTIC

enum class AnalysisState {
    NO_ANALYSIS
    , CHARACTER_ANALYSIS { override fun getLabel() = SYM_CHARACTER }
    , ROMANTIC_ANALYSIS { override fun getLabel() = SYM_ROMANTIC }
    , PLANET_ANALYSIS { override fun getLabel() = SYM_PLANET }
    , ELEMENT_ANALYSIS { override fun getLabel() = SYM_ELEMENT }
    , MODE_ANALYSIS { override fun getLabel() = SYM_MODE }
    ;

    open fun getLabel() : String = " "
}