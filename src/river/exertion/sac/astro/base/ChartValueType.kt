package river.exertion.sac.astro.base

import river.exertion.sac.console.state.ChartStateType

enum class ChartValueType {
    APPRECIATION {
        override fun getChartStateTypes() = listOf(ChartStateType.REF_NATAL_CHART, ChartStateType.SYN_NATAL_CHART)
        override val label = "APR"
    }
    , FIRST_NATAL_AFFINITY {
        override fun getChartStateTypes() = listOf(ChartStateType.REF_NATAL_CHART, ChartStateType.SYNASTRY_CHART)
        override val label = "AF1"
    }
    , SECOND_NATAL_AFFINITY {
        override fun getChartStateTypes() = listOf(ChartStateType.REF_NATAL_CHART, ChartStateType.SYNASTRY_CHART)
        override val label = "AF2"
    }
    , FIRST_NATAL_COMMONALITY {
        override fun getChartStateTypes() = listOf(ChartStateType.REF_NATAL_CHART, ChartStateType.COMPOSITE_CHART)
        override val label = "CO1"
    }
    , SECOND_NATAL_COMMONALITY {
        override fun getChartStateTypes() = listOf(ChartStateType.REF_NATAL_CHART, ChartStateType.COMPOSITE_CHART)
        override val label = "CO2"
    }
    , COMPATIBILITY {
        override fun getChartStateTypes() = listOf(ChartStateType.COMPOSITE_CHART, ChartStateType.SYNASTRY_CHART)
        override val label = "CMP"
    }
    , VALUE_TYPE_NONE
    ;
    open fun getChartStateTypes() : List<ChartStateType> = listOf()
    open val label : String = ""
}