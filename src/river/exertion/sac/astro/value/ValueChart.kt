package river.exertion.sac.astro.value

import river.exertion.sac.astro.AspectAngle
import river.exertion.sac.astro.AspectCelestial
import river.exertion.sac.astro.Value
import river.exertion.sac.astro.render.RenderAspect
import river.exertion.sac.astro.ValueType
import river.exertion.sac.astro.state.StateAspect
import river.exertion.sac.astro.state.StateBaseAspect
import river.exertion.sac.astro.state.StateBaseAspect.Companion.stateBaseAspects
import river.exertion.sac.astro.state.StateChart
import river.exertion.sac.console.state.*
import river.exertion.sac.console.state.ChartStateType.Companion.encodeChartStateType
import river.exertion.sac.view.SACInputProcessor

class ValueChart (val chartRows: Array<ValueChartRow>, val analysisState: AnalysisState = AnalysisState.NO_ANALYSIS) {

    constructor(chartAspects : Array<ValueAspect>, analysisState: AnalysisState = AnalysisState.NO_ANALYSIS) : this (
        getAspectsChart(chartAspects), analysisState)

    constructor(stateChartAspects : Array<StateAspect>, chartState : ChartState, analysisState: AnalysisState = AnalysisState.NO_ANALYSIS) : this (
        getAspectsStateAspects(stateChartAspects, chartState, analysisState), analysisState)

    constructor(stateChart : StateChart, analysisState: AnalysisState) : this (stateChart.getStateAspects().toTypedArray(), stateChart.chartState, analysisState)

    //analysisState = CHARACTER_ANALYSIS
    constructor(chartState : ChartState, synChart : StateChart, compChart : StateChart, refNatalChart : StateChart, synNatalChart : StateChart) :
            this (getCharacterAspectsStateAspects(chartState, synChart, compChart, refNatalChart, synNatalChart), AnalysisState.CHARACTER_ANALYSIS)

    fun getBaseValue() = Value(getSortedFilteredValueAspects().map { it.baseValue.positive }.reduce { acc, basePositive -> acc + basePositive },
        getSortedFilteredValueAspects().map { it.baseValue.negative }.reduce { acc, baseNegative -> acc + baseNegative } )

    fun getModValue() = Value(getSortedFilteredValueAspects().map { it.getModValue().positive }.reduce { acc, modPositive -> acc + modPositive },
        getSortedFilteredValueAspects().map { it.getModValue().negative }.reduce { acc, modNegative -> acc + modNegative } )

    fun getValueAspects() : List<ValueAspect> {
        val returnList = mutableListOf<ValueAspect>()

        chartRows.forEach { returnList.addAll( it.rowAspects ) }

        return returnList.filter { it.stateAspect.aspectAngle != AspectAngle.ASPECT_ANGLE_NONE }.sortedBy { it.stateAspect.aspectCelestialSecond }.sortedBy { it.stateAspect.aspectCelestialFirst }
    }

    fun getSortedFilteredValueAspects() : List<ValueAspect> {

        val valueAspects = getValueAspects()

        val sortedValueAspects = when {
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.ASPECT) -> valueAspects.sortedBy { it.stateAspect.aspectAngle.aspectType }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.SECOND_CELESTIAL) -> valueAspects.sortedBy { it.stateAspect.aspectCelestialSecond }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.VALUE_MAGNITUDE) -> valueAspects.sortedByDescending { RenderAspect(it).getAspectValue().second }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.VALUE_POS_TO_NEG) -> {
                valueAspects.filter { RenderAspect(it).getAspectValue().first == ValueType.POSITIVE || RenderAspect(it).getAspectValue().first == ValueType.REVERSAL_TO_POS }.sortedByDescending { RenderAspect(it).getAspectValue().second }.sortedBy { RenderAspect(it).getAspectValue().first }.plus(
                    valueAspects.filter { RenderAspect(it).getAspectValue().first == ValueType.NEUTRAL || RenderAspect(it).getAspectValue().first == ValueType.REVERSAL_TO_NEUT }.sortedBy { it.stateAspect.aspectAngle.aspectType }.sortedBy { RenderAspect(it).getAspectValue().first }.plus(
                        valueAspects.filter { RenderAspect(it).getAspectValue().first == ValueType.NEGATIVE || RenderAspect(it).getAspectValue().first == ValueType.REVERSAL_TO_NEG }.sortedBy { RenderAspect(it).getAspectValue().second }.sortedBy { RenderAspect(it).getAspectValue().first }
                    ))
            }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.VALUE_NEG_TO_POS) -> {
                valueAspects.filter { RenderAspect(it).getAspectValue().first == ValueType.NEGATIVE || RenderAspect(it).getAspectValue().first == ValueType.REVERSAL_TO_NEG }.sortedByDescending { RenderAspect(it).getAspectValue().second }.sortedBy { RenderAspect(it).getAspectValue().first }.plus(
                    valueAspects.filter { RenderAspect(it).getAspectValue().first == ValueType.NEUTRAL || RenderAspect(it).getAspectValue().first == ValueType.REVERSAL_TO_NEUT }.sortedBy { it.stateAspect.aspectAngle.aspectType }.sortedBy { RenderAspect(it).getAspectValue().first }.plus(
                        valueAspects.filter { RenderAspect(it).getAspectValue().first == ValueType.POSITIVE || RenderAspect(it).getAspectValue().first == ValueType.REVERSAL_TO_POS }.sortedBy { RenderAspect(it).getAspectValue().second }.sortedBy { RenderAspect(it).getAspectValue().first }
                    ))
            }
            else -> valueAspects.sortedBy { it.stateAspect.aspectCelestialFirst }
        }

        val filteredValueAspects = when {
            SACInputProcessor.aspectsFilterStateMachine.isInState(AspectsFilterState.OVER_TWENTY) -> sortedValueAspects.filter { RenderAspect(it).getAspectValue().second >= 20 }
            SACInputProcessor.aspectsFilterStateMachine.isInState(AspectsFilterState.OVER_FIFTY) -> sortedValueAspects.filter { RenderAspect(it).getAspectValue().second >= 50 }
            else -> sortedValueAspects
        }

        return filteredValueAspects
    }

    companion object {

        fun getEmptyChart() = ValueChart(arrayOf(ValueAspect.getEmptyAspect()))

        fun getAspectsStateAspects(
            stateChartAspects: Array<StateAspect>,
            chartState: ChartState,
            analysisState: AnalysisState
        ): Array<ValueAspect> {

            val returnAspects: MutableList<ValueAspect> = ArrayList()

            stateChartAspects.forEach { returnAspects.add(ValueAspect(it, chartState, analysisState)) }

            return returnAspects.toTypedArray()
        }

        fun getCharacterAspectsStateAspects(
            chartState: ChartState,
            synChart: StateChart,
            compChart: StateChart,
            refNatalChart: StateChart,
            synNatalChart: StateChart
        ): Array<ValueAspect> {

            if (chartState == ChartState.NATAL_CHART) return getAspectsStateAspects(
                refNatalChart.getStateAspects().toTypedArray(), chartState, AnalysisState.CHARACTER_ANALYSIS
            )

            val returnAspects: MutableList<ValueAspect> = ArrayList()

            val synAspects = synChart.getStateAspects()
            val compAspects = compChart.getStateAspects()
            val refNatalBaseAspects = refNatalChart.getStateAspects().stateBaseAspects()
            val synNatalBaseAspects = synNatalChart.getStateAspects().stateBaseAspects()

            if (chartState == ChartState.COMPOSITE_CHART) {
                val synBaseAspects = synAspects.stateBaseAspects()
                val synSharedAspects = mutableListOf<StateBaseAspect>()

                compAspects.forEach {
                    val baseAspect = it.getStateBaseAspect()

                    val commonAspectCharts = mutableListOf<ChartStateType>()

/*                    when {
                        refNatalBaseAspects.contains(baseAspect) && synBaseAspects.contains(baseAspect) -> {
                            commonAspectCharts.add(ChartStateType.REF_NATAL_CHART)
//                            commonAspectCharts.add(ChartStateType.REF_NATAL_OPP_CHART)
                            synSharedAspects.add(baseAspect)
                        }
                        synNatalBaseAspects.contains(baseAspect) && synBaseAspects.contains(baseAspect) -> {
                            commonAspectCharts.add(ChartStateType.SYN_NATAL_CHART)
//                            commonAspectCharts.add(ChartStateType.SYN_NATAL_OPP_CHART)
                            synSharedAspects.add(baseAspect)
                        }
*/
                    if (refNatalBaseAspects.contains(baseAspect)) commonAspectCharts.add(ChartStateType.REF_NATAL_CHART)
                    if (synNatalBaseAspects.contains(baseAspect)) commonAspectCharts.add(ChartStateType.SYN_NATAL_CHART)
                    if (synBaseAspects.contains(baseAspect)) commonAspectCharts.add(ChartStateType.SYNASTRY_CHART)

                    returnAspects.add(
                        ValueAspect(
                            it,
                            chartState,
                            AnalysisState.CHARACTER_ANALYSIS,
                            commonAspectCharts.encodeChartStateType()
                        )
                    )
                }

/* TODO: implement fourth chart rendering for character analysis

                synAspects.forEach {
                    val baseAspect = it.getStateBaseAspect()

                    if ( !synSharedAspects.contains(baseAspect) ) {

                        val commonAspectCharts = mutableListOf<ChartStateType>()

                        if ( refNatalBaseAspects.contains(baseAspect) ) commonAspectCharts.add(ChartStateType.REF_NATAL_OPP_CHART)
                        if ( synNatalBaseAspects.contains(baseAspect) ) commonAspectCharts.add(ChartStateType.SYN_NATAL_OPP_CHART)

                        returnAspects.add(ValueAspect(it, chartState, AnalysisState.CHARACTER_ANALYSIS, commonAspectCharts.encodeChartStateType()))
                    }
                }
*/
            } else { // (chartState == ChartState.SYNASTRY_CHART)
                val compBaseAspects = compAspects.stateBaseAspects()
                val compSharedAspects = mutableListOf<StateBaseAspect>()

                synAspects.forEach {
                    val baseAspect = it.getStateBaseAspect()

                    val commonAspectCharts = mutableListOf<ChartStateType>()

/*                    when {
                        refNatalBaseAspects.contains(baseAspect) && compBaseAspects.contains(baseAspect) -> {
                            commonAspectCharts.add(ChartStateType.REF_NATAL_CHART)
//                            commonAspectCharts.add(ChartStateType.REF_NATAL_OPP_CHART)
                            compSharedAspects.add(baseAspect)
                        }
                        synNatalBaseAspects.contains(baseAspect) && compBaseAspects.contains(baseAspect) -> {
                            commonAspectCharts.add(ChartStateType.SYN_NATAL_CHART)
//                            commonAspectCharts.add(ChartStateType.SYN_NATAL_OPP_CHART)
                            compSharedAspects.add(baseAspect)
                        }
*/
                    if (refNatalBaseAspects.contains(baseAspect)) commonAspectCharts.add(ChartStateType.REF_NATAL_CHART)
                    if (synNatalBaseAspects.contains(baseAspect)) commonAspectCharts.add(ChartStateType.SYN_NATAL_CHART)
                    if (compBaseAspects.contains(baseAspect)) commonAspectCharts.add(ChartStateType.COMPOSITE_CHART)

                    returnAspects.add(
                        ValueAspect(
                            it,
                            chartState,
                            AnalysisState.CHARACTER_ANALYSIS,
                            commonAspectCharts.encodeChartStateType()
                        )
                    )
                }

/* TODO: implement fourth chart rendering for character analysis

                compAspects.forEach {
                    val baseAspect = it.getStateBaseAspect()

                    if ( !compSharedAspects.contains(baseAspect) ) {

                        val commonAspectCharts = mutableListOf<ChartStateType>()

                        if ( refNatalBaseAspects.contains(baseAspect) ) commonAspectCharts.add(ChartStateType.REF_NATAL_OPP_CHART)
                        if ( synNatalBaseAspects.contains(baseAspect) ) commonAspectCharts.add(ChartStateType.SYN_NATAL_OPP_CHART)

                        returnAspects.add(ValueAspect(it, chartState, AnalysisState.CHARACTER_ANALYSIS, commonAspectCharts.encodeChartStateType()))
                    }
                }
*/
            }

            return returnAspects.toTypedArray()
        }

        fun getAspectsChart(chartAspects: Array<ValueAspect>): Array<ValueChartRow> {

            val returnAspectsChart: MutableList<ValueChartRow> = ArrayList()

            for (verticalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                val chartRow: MutableList<ValueAspect> = mutableListOf()

                for (horizontalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                    val chartAspect = chartAspects.firstOrNull {
                        (it.stateAspect.aspectCelestialFirst == verticalAspectCelestial) && (it.stateAspect.aspectCelestialSecond == horizontalAspectCelestial)
                    } ?: ValueAspect.getEmptyAspect(verticalAspectCelestial, horizontalAspectCelestial)

                    chartRow.add(horizontalAspectCelestial.ordinal, chartAspect)
                }
                returnAspectsChart.add(verticalAspectCelestial.ordinal, ValueChartRow(chartRow.toTypedArray()))
            }

            return returnAspectsChart.toTypedArray()
        }
    }
}