package river.exertion.sac.astro.base

enum class AspectValueType {
    //aspects shared between two natal charts
    APPRECIATION_REF {
        override val label = "APR"
        override val chartValueType = ChartValueType.APPRECIATION
    }
    , APPRECIATION_SYN {
        override val label = "APR"
        override val chartValueType = ChartValueType.APPRECIATION
    }
    //aspects shared between the first natal chart and a synastry chart
    , REF_NATAL_AFFINITY_REF {
        override val label = "AF1"
        override val chartValueType = ChartValueType.AFFINITY
    }
    , REF_NATAL_AFFINITY_SYN {
        override val label = "AF1"
        override val chartValueType = ChartValueType.AFFINITY
    }
    //aspects shared between a synastry chart and the second natal chart
    , SYN_NATAL_AFFINITY_REF {
        override val label = "AF2"
        override val chartValueType = ChartValueType.AFFINITY
    }
    , SYN_NATAL_AFFINITY_SYN {
        override val label = "AF2"
        override val chartValueType = ChartValueType.AFFINITY
    }
    //aspects shared between the first natal chart and a composite chart
    , REF_NATAL_COMMONALITY_REF {
        override val label = "CO1"
        override val chartValueType = ChartValueType.COMMONALITY
    }
    , REF_NATAL_COMMONALITY_SYN {
        override val label = "CO1"
        override val chartValueType = ChartValueType.COMMONALITY
    }
    //aspects shared between a composite chart and the second natal chart
    , SYN_NATAL_COMMONALITY_REF {
        override val label = "CO2"
        override val chartValueType = ChartValueType.COMMONALITY
    }
    , SYN_NATAL_COMMONALITY_SYN {
        override val label = "CO2"
        override val chartValueType = ChartValueType.COMMONALITY
    }
    //aspects shared between a composite chart and a synastry chart
    , COMPATIBILITY_REF {
        override val label = "CMP"
        override val chartValueType = ChartValueType.COMPATIBILITY
    }
    , COMPATIBILITY_SYN {
        override val label = "CMP"
        override val chartValueType = ChartValueType.COMPATIBILITY
    }
    , VALUE_TYPE_NONE
    ;
    open val label : String = ""
    open val chartValueType : ChartValueType = ChartValueType.VALUE_TYPE_NONE
}