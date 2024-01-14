package river.exertion.sac.astro.base

import river.exertion.sac.Constants

enum class ChartValueType {
    //aspects shared between two natal charts
    APPRECIATION {
        override val label = Constants.SYM_NATCOMP_CHART
    }
    //aspects shared between a natal chart and a synastry chart
    , AFFINITY
    //aspects shared between a natal chart and a composite chart
    , COMMONALITY
    //aspects shared between a composite chart and a synastry chart
    , COMPATIBILITY
    , VALUE_TYPE_NONE
    ;
    open val label = " "
}