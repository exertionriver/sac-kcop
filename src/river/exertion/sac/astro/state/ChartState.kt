package river.exertion.sac.astro.state

import kotlinx.serialization.Serializable
import river.exertion.sac.Constants.LABEL_SPACE
import river.exertion.sac.Constants.SYM_NATCOMP_CHART
import river.exertion.sac.Constants.SYM_SYNASTRY_CHART

@Serializable
enum class ChartState {
    NONE_CHART //for values analysis only
    , NATAL_CHART
    , COMPOSITE_CHART { override fun getOperatorLabel() = "=" }
    , SYNASTRY_CHART { override fun getLabel() = SYM_SYNASTRY_CHART.plus(LABEL_SPACE) ; override fun getOperatorLabel() = "+"; override fun isNatComp() = false }
    , COMBINED_CHART //for values analysis only
    ;

    open fun getLabel() : String = SYM_NATCOMP_CHART.plus(LABEL_SPACE)
    open fun getOperatorLabel() : String = ""
    open fun isNatComp() : Boolean = true
}