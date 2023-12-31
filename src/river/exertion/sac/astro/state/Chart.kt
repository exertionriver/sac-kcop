package river.exertion.sac.astro.state

import river.exertion.sac.astro.*
import river.exertion.sac.astro.ValueRomanticAspects
import river.exertion.sac.astro.render.RenderAspect
import river.exertion.sac.astro.state.StateBaseAspect.Companion.stateBaseAspects
import river.exertion.sac.astro.value.ValueAspect
import river.exertion.sac.console.state.*
import river.exertion.sac.console.state.ChartStateType.Companion.encodeChartStateType
import river.exertion.sac.swe.DegMidp
import river.exertion.sac.view.SACInputProcessor

class Chart (val chartRows: Array<ChartRow>, val chartState: ChartState = ChartState.NATAL_CHART, val analysisState: AnalysisState = AnalysisState.NO_ANALYSIS) {

    constructor(chartAspects : Array<StateAspect>, chartState: ChartState = ChartState.NATAL_CHART, analysisState: AnalysisState = AnalysisState.NO_ANALYSIS) : this (
        getAspectsChart(chartAspects), chartState, analysisState)

    constructor(firstCelestialSnapshot: CelestialSnapshot, secondCelestialSnapshot: CelestialSnapshot
                , chartState: ChartState, aspectsState: AspectsState, timeAspectsState: TimeAspectsState, aspectOverlayState: AspectOverlayState
                , analysisState: AnalysisState = AnalysisState.NO_ANALYSIS
    ) : this (
        getAspects(firstCelestialSnapshot, secondCelestialSnapshot, chartState, aspectsState, timeAspectsState, aspectOverlayState).plus(
            getExtendedAspects(firstCelestialSnapshot, secondCelestialSnapshot, chartState, aspectsState, timeAspectsState, aspectOverlayState)
        )
    , chartState, analysisState
    )

    //natal or composite--no need for second snapshot
    constructor(firstCelestialSnapshot: CelestialSnapshot, chartState: ChartState, aspectsState: AspectsState, timeAspectsState: TimeAspectsState, aspectOverlayState: AspectOverlayState
    ) : this (firstCelestialSnapshot, firstCelestialSnapshot, chartState, aspectsState, timeAspectsState, aspectOverlayState)

    fun getBaseValue() = Value(getSortedFilteredValueAspects().map { it.baseValue.positive }.reduce { acc, basePositive -> acc + basePositive },
        getSortedFilteredValueAspects().map { it.baseValue.negative }.reduce { acc, baseNegative -> acc + baseNegative } )

    fun getModValue() = Value(getSortedFilteredValueAspects().map { it.getModValue().positive }.reduce { acc, modPositive -> acc + modPositive },
        getSortedFilteredValueAspects().map { it.getModValue().negative }.reduce { acc, modNegative -> acc + modNegative } )

    fun getStateAspects() : List<StateAspect> {
        val returnList = mutableListOf<StateAspect>()

        chartRows.forEach { returnList.addAll( it.rowAspects ) }

        return returnList.filter { it.aspectAngle != AspectAngle.ASPECT_ANGLE_NONE }.sortedBy { it.aspectCelestialSecond.ordinal }.sortedBy { it.aspectCelestialFirst.ordinal }

    }

    fun getValueAspects() : List<ValueAspect> {
        val returnList = mutableListOf<ValueAspect>()

        chartRows.forEach { returnList.addAll( it.rowAspects.map { valueAspect -> ValueAspect(valueAspect) } ) }

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

        fun getEmptyChart() = Chart( arrayOf(StateAspect.getEmptyAspect()) )

        fun getAspects(firstCelestialSnapshot : CelestialSnapshot, secondCelestialSnapshot: CelestialSnapshot = firstCelestialSnapshot
                       , chartState: ChartState, aspectsState: AspectsState, timeAspectsState: TimeAspectsState, aspectOverlayState: AspectOverlayState
        ) : Array<StateAspect> {

            val firstCelestialAspectMap = if (chartState != ChartState.COMPOSITE_CHART) firstCelestialSnapshot.getAspectCelestialLongitudeMap()
            else CelestialSnapshot.getCompositeSnapshot(firstCelestialSnapshot, secondCelestialSnapshot).getAspectCelestialLongitudeMap()
            val secondCelestialAspectMap = if (chartState != ChartState.COMPOSITE_CHART) secondCelestialSnapshot.getAspectCelestialLongitudeMap()
            else CelestialSnapshot.getCompositeSnapshot(firstCelestialSnapshot, secondCelestialSnapshot).getAspectCelestialLongitudeMap()

            val returnAspects : MutableList<StateAspect> = ArrayList()
            var returnAspectsIdx = 0

            for (firstCelestialAspectEntry in firstCelestialAspectMap) {
                for (secondCelestialAspectEntry in secondCelestialAspectMap) {

                    if ( (chartState != ChartState.SYNASTRY_CHART)
                        && (firstCelestialAspectEntry.key.ordinal >= secondCelestialAspectEntry.key.ordinal) ) continue

                    if ( ( firstCelestialAspectEntry.key == AspectCelestial.ASPECT_ASCENDANT)
                        && (secondCelestialAspectEntry.key == AspectCelestial.ASPECT_MIDHEAVEN)) continue

                    if ( ( firstCelestialAspectEntry.key == AspectCelestial.ASPECT_MIDHEAVEN)
                        && (secondCelestialAspectEntry.key == AspectCelestial.ASPECT_ASCENDANT)) continue

                    val aspect = StateAspect(
                        Sign.signFromCelestialLongitude(firstCelestialAspectEntry.value)
                        , firstCelestialAspectEntry.key
                        , firstCelestialAspectEntry.value
                        , Sign.signFromCelestialLongitude(secondCelestialAspectEntry.value)
                        , secondCelestialAspectEntry.key
                        , secondCelestialAspectEntry.value
                        , aspectsState, timeAspectsState, aspectOverlayState
                    )

                    if (aspect.aspectAngle != AspectAngle.ASPECT_ANGLE_NONE)
                        returnAspects.add(returnAspectsIdx++, aspect)

                }
            }

            return returnAspects.toTypedArray()
        }

        fun getExtendedAspects(firstCelestialSnapshot : CelestialSnapshot, secondCelestialSnapshot : CelestialSnapshot
                               , chartState: ChartState, aspectsState: AspectsState, timeAspectsState: TimeAspectsState, aspectOverlayState: AspectOverlayState
        ) : Array<StateAspect> {

            val firstCelestialAspectMap = if (chartState != ChartState.COMPOSITE_CHART) firstCelestialSnapshot.getAspectCelestialLongitudeMap()
            else CelestialSnapshot.getCompositeSnapshot(firstCelestialSnapshot, secondCelestialSnapshot).getAspectCelestialLongitudeMap()
            val secondCelestialAspectMap = if (chartState != ChartState.COMPOSITE_CHART) secondCelestialSnapshot.getAspectCelestialLongitudeMap()
            else CelestialSnapshot.getCompositeSnapshot(firstCelestialSnapshot, secondCelestialSnapshot).getAspectCelestialLongitudeMap()

            val returnAspects : MutableList<StateAspect> = ArrayList()
            var returnAspectsIdx = 0

            var firstExtendedCelestialLongitude : Double
            var secondExtendedCelestialLongitude : Double
            var aspect : StateAspect

            //extended aspects are only in the second position at current
            for(extendedAspect in ValueRomanticAspects.extendedAspects) {
                firstExtendedCelestialLongitude = firstCelestialAspectMap[extendedAspect.aspectCelestialFirst]!!

                secondExtendedCelestialLongitude = when (extendedAspect.aspectCelestialSecond) {
                    AspectCelestial.ASPECT_SUN_MOON_MIDPOINT -> DegMidp.getMidpoint(
                        secondCelestialAspectMap[AspectCelestial.ASPECT_SUN]!!,
                        secondCelestialAspectMap[AspectCelestial.ASPECT_MOON]!!
                    )
                    AspectCelestial.ASPECT_FIRST_HOUSE -> DegMidp.getMidpoint(
                        secondCelestialSnapshot.refCelestialHouseData[CelestialHouse.HOUSE_1_ASC.ordinal],
                        secondCelestialSnapshot.refCelestialHouseData[CelestialHouse.HOUSE_2.ordinal]
                    )
                    AspectCelestial.ASPECT_SEVENTH_HOUSE -> DegMidp.getMidpoint(
                        secondCelestialSnapshot.refCelestialHouseData[CelestialHouse.HOUSE_7.ordinal],
                        secondCelestialSnapshot.refCelestialHouseData[CelestialHouse.HOUSE_8.ordinal]
                    )
                    else -> secondCelestialAspectMap[extendedAspect.aspectCelestialSecond]!!
                }

                aspect = StateAspect(
                        Sign.signFromCelestialLongitude(firstExtendedCelestialLongitude)
                        , extendedAspect.aspectCelestialFirst
                        , firstExtendedCelestialLongitude
                        , Sign.signFromCelestialLongitude(secondExtendedCelestialLongitude)
                        , extendedAspect.aspectCelestialSecond
                        , secondExtendedCelestialLongitude
                        , aspectsState, timeAspectsState, aspectOverlayState
                    )

                if (aspect.aspectAngle == extendedAspect.aspectAngle) {

                    returnAspects.add(returnAspectsIdx++, aspect)
                }

                if (chartState == ChartState.SYNASTRY_CHART) {
                    //check the reverse
                    firstExtendedCelestialLongitude = when (extendedAspect.aspectCelestialSecond) {
                        AspectCelestial.ASPECT_SUN_MOON_MIDPOINT -> DegMidp.getMidpoint(
                            firstCelestialAspectMap[AspectCelestial.ASPECT_SUN]!!,
                            firstCelestialAspectMap[AspectCelestial.ASPECT_MOON]!!
                        )
                        AspectCelestial.ASPECT_FIRST_HOUSE -> DegMidp.getMidpoint(
                            firstCelestialSnapshot.refCelestialHouseData[CelestialHouse.HOUSE_1_ASC.ordinal],
                            firstCelestialSnapshot.refCelestialHouseData[CelestialHouse.HOUSE_2.ordinal]
                        )
                        AspectCelestial.ASPECT_SEVENTH_HOUSE -> DegMidp.getMidpoint(
                            firstCelestialSnapshot.refCelestialHouseData[CelestialHouse.HOUSE_7.ordinal],
                            firstCelestialSnapshot.refCelestialHouseData[CelestialHouse.HOUSE_8.ordinal]
                        )
                        else -> firstCelestialAspectMap[extendedAspect.aspectCelestialSecond]!!
                    }

                    secondExtendedCelestialLongitude = secondCelestialAspectMap[extendedAspect.aspectCelestialFirst]!!

                    aspect = StateAspect(
                            Sign.signFromCelestialLongitude(firstExtendedCelestialLongitude)
                            , extendedAspect.aspectCelestialSecond
                            , firstExtendedCelestialLongitude
                            , Sign.signFromCelestialLongitude(secondExtendedCelestialLongitude)
                            , extendedAspect.aspectCelestialFirst
                            , secondExtendedCelestialLongitude
                            , aspectsState, timeAspectsState, aspectOverlayState
                        )

                    if (aspect.aspectAngle == extendedAspect.aspectAngle) {

                        returnAspects.add(returnAspectsIdx++, aspect)
                    }
                }

            }
            return returnAspects.toTypedArray()
        }

        fun getAspectsChart(chartAspects : Array<StateAspect>) : Array<ChartRow> {

            val returnAspectsChart : MutableList<ChartRow> = ArrayList()

            for (verticalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                val chartRow : MutableList<StateAspect> = mutableListOf()

                for (horizontalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                    val chartAspect = chartAspects.firstOrNull {
                        (it.aspectCelestialFirst == verticalAspectCelestial) && (it.aspectCelestialSecond == horizontalAspectCelestial)
                    } ?: StateAspect.getEmptyAspect(verticalAspectCelestial, horizontalAspectCelestial)

                    chartRow.add(horizontalAspectCelestial.ordinal, chartAspect)
                }
                returnAspectsChart.add(verticalAspectCelestial.ordinal, ChartRow(chartRow.toTypedArray()))
            }

            //add extended aspects as row
            val extendedAspectsRow = ChartRow(chartAspects.filter { it.aspectCelestialFirst.isExtendedAspect || it.aspectCelestialSecond.isExtendedAspect }.toMutableList().toTypedArray())

            returnAspectsChart.add(AspectCelestial.entries.filter { it.isChartAspectCelestial() }.size, extendedAspectsRow)

            return returnAspectsChart.toTypedArray()
        }



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
            synChart: Chart,
            compChart: Chart,
            refNatalChart: Chart,
            synNatalChart: Chart
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

        fun getAspectsChart(chartAspects: Array<ValueAspect>): Array<ChartRow> {

            val returnAspectsChart: MutableList<ChartRow> = ArrayList()

            for (verticalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                val chartRow: MutableList<StateAspect> = mutableListOf()

                for (horizontalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                    val chartAspect = chartAspects.firstOrNull {
                        (it.stateAspect.aspectCelestialFirst == verticalAspectCelestial) && (it.stateAspect.aspectCelestialSecond == horizontalAspectCelestial)
                    } ?: ValueAspect.getEmptyAspect(verticalAspectCelestial, horizontalAspectCelestial)

                    chartRow.add(horizontalAspectCelestial.ordinal, chartAspect.stateAspect)
                }
                returnAspectsChart.add(verticalAspectCelestial.ordinal, ChartRow(chartRow.toTypedArray()))
            }

            return returnAspectsChart.toTypedArray()
        }
    }
}