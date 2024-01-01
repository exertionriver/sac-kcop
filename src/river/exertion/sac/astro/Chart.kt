package river.exertion.sac.astro

import river.exertion.sac.astro.base.*
import river.exertion.sac.console.state.*
import river.exertion.sac.swe.DegMidp
import river.exertion.sac.view.SACInputProcessor

class Chart (val chartAspects : List<Aspect>,  val chartState: ChartState = ChartState.NATAL_CHART, val analysisState: AnalysisState = AnalysisState.NO_ANALYSIS) {

//    constructor(chartState: ChartState = ChartState.NATAL_CHART, analysisState: AnalysisState = AnalysisState.NO_ANALYSIS) : this (
//        getAspectsChart(chartAspects), chartState, analysisState)

    constructor(firstCelestialSnapshot: CelestialSnapshot, secondCelestialSnapshot: CelestialSnapshot
                , chartState: ChartState, aspectsState: AspectsState, timeAspectsState: TimeAspectsState, aspectOverlayState: AspectOverlayState
                , analysisState: AnalysisState = AnalysisState.NO_ANALYSIS
    ) : this (
        getAspects(
            firstCelestialSnapshot,
            secondCelestialSnapshot,
            chartState,
            aspectsState,
            timeAspectsState,
            aspectOverlayState,
            analysisState
        ).plus(
            getExtendedAspects(firstCelestialSnapshot, secondCelestialSnapshot, chartState, aspectsState, timeAspectsState, aspectOverlayState, analysisState)
        )
    , chartState, analysisState
    )

    //natal or composite--no need for second snapshot
    constructor(firstCelestialSnapshot: CelestialSnapshot, chartState: ChartState, aspectsState: AspectsState, timeAspectsState: TimeAspectsState, aspectOverlayState: AspectOverlayState
    ) : this (firstCelestialSnapshot, firstCelestialSnapshot, chartState, aspectsState, timeAspectsState, aspectOverlayState)

    val baseValue = Value(getSortedFilteredValueAspects().map { it.baseValue.positive }.reduce { acc, basePositive -> acc + basePositive },
        getSortedFilteredValueAspects().map { it.baseValue.negative }.reduce { acc, baseNegative -> acc + baseNegative } )

    val modValue = Value(getSortedFilteredValueAspects().map { it.modValue.positive }.reduce { acc, modPositive -> acc + modPositive },
        getSortedFilteredValueAspects().map { it.modValue.negative }.reduce { acc, modNegative -> acc + modNegative } )

    val netValue = Value(getSortedFilteredValueAspects().map { it.netValue.positive }.reduce { acc, basePositive -> acc + basePositive },
        getSortedFilteredValueAspects().map { it.netValue.negative }.reduce { acc, baseNegative -> acc + baseNegative } )

    fun getAspects() : List<Aspect> =
        chartAspects.filter { it.aspectAngle != AspectAngle.ASPECT_ANGLE_NONE }.sortedBy { it.aspectCelestialSecond }.sortedBy { it.aspectCelestialFirst }

    fun getSortedFilteredValueAspects() : List<Aspect> {

        val valueAspects = getAspects()

        val sortedValueAspects = when {
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.ASPECT) -> valueAspects.sortedBy { it.aspectAngle.aspectType }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.SECOND_CELESTIAL) -> valueAspects.sortedBy { it.aspectCelestialSecond }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.VALUE_MAGNITUDE) -> valueAspects.sortedByDescending { it.getAspectValue().second }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.VALUE_POS_TO_NEG) -> {
                valueAspects.filter { it.getAspectValue().first == ValueType.POSITIVE || it.getAspectValue().first == ValueType.REVERSAL_TO_POS }.sortedByDescending { it.getAspectValue().second }.sortedBy { it.getAspectValue().first }.plus(
                    valueAspects.filter { it.getAspectValue().first == ValueType.NEUTRAL || it.getAspectValue().first == ValueType.REVERSAL_TO_NEUT }.sortedBy { it.aspectAngle.aspectType }.sortedBy { it.getAspectValue().first }.plus(
                        valueAspects.filter { it.getAspectValue().first == ValueType.NEGATIVE || it.getAspectValue().first == ValueType.REVERSAL_TO_NEG }.sortedBy { it.getAspectValue().second }.sortedBy { it.getAspectValue().first }
                    ))
            }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.VALUE_NEG_TO_POS) -> {
                valueAspects.filter { it.getAspectValue().first == ValueType.NEGATIVE || it.getAspectValue().first == ValueType.REVERSAL_TO_NEG }.sortedByDescending { it.getAspectValue().second }.sortedBy { it.getAspectValue().first }.plus(
                    valueAspects.filter { it.getAspectValue().first == ValueType.NEUTRAL || it.getAspectValue().first == ValueType.REVERSAL_TO_NEUT }.sortedBy { it.aspectAngle.aspectType }.sortedBy { it.getAspectValue().first }.plus(
                        valueAspects.filter { it.getAspectValue().first == ValueType.POSITIVE || it.getAspectValue().first == ValueType.REVERSAL_TO_POS }.sortedBy { it.getAspectValue().second }.sortedBy { it.getAspectValue().first }
                    ))
            }
            else -> valueAspects.sortedBy { it.aspectCelestialFirst }
        }

        val filteredValueAspects = when {
            SACInputProcessor.aspectsFilterStateMachine.isInState(AspectsFilterState.OVER_TWENTY) -> sortedValueAspects.filter { it.getAspectValue().second >= 20 }
            SACInputProcessor.aspectsFilterStateMachine.isInState(AspectsFilterState.OVER_FIFTY) -> sortedValueAspects.filter { it.getAspectValue().second >= 50 }
            else -> sortedValueAspects
        }

        return filteredValueAspects
    }

    companion object {

        fun getEmptyChart() = Chart( listOf(Aspect.getEmptyAspect()) )

        fun getAspects(firstCelestialSnapshot : CelestialSnapshot, secondCelestialSnapshot: CelestialSnapshot = firstCelestialSnapshot,
                       chartState: ChartState, aspectsState: AspectsState, timeAspectsState: TimeAspectsState, aspectOverlayState: AspectOverlayState, analysisState: AnalysisState
        ) : List<Aspect> {

            val firstCelestialAspectMap = if (chartState != ChartState.COMPOSITE_CHART) firstCelestialSnapshot.getAspectCelestialLongitudeMap()
            else CelestialSnapshot.getCompositeSnapshot(firstCelestialSnapshot, secondCelestialSnapshot).getAspectCelestialLongitudeMap()
            val secondCelestialAspectMap = if (chartState != ChartState.COMPOSITE_CHART) secondCelestialSnapshot.getAspectCelestialLongitudeMap()
            else CelestialSnapshot.getCompositeSnapshot(firstCelestialSnapshot, secondCelestialSnapshot).getAspectCelestialLongitudeMap()

            val returnAspects : MutableList<Aspect> = ArrayList()
            var returnAspectsIdx = 0

            for (firstCelestialAspectEntry in firstCelestialAspectMap) {
                for (secondCelestialAspectEntry in secondCelestialAspectMap) {

                    if ( (chartState != ChartState.SYNASTRY_CHART)
                        && (firstCelestialAspectEntry.key.ordinal >= secondCelestialAspectEntry.key.ordinal) ) continue

                    if ( ( firstCelestialAspectEntry.key == AspectCelestial.ASPECT_ASCENDANT)
                        && (secondCelestialAspectEntry.key == AspectCelestial.ASPECT_MIDHEAVEN)) continue

                    if ( ( firstCelestialAspectEntry.key == AspectCelestial.ASPECT_MIDHEAVEN)
                        && (secondCelestialAspectEntry.key == AspectCelestial.ASPECT_ASCENDANT)) continue

                    val aspect = Aspect(
                        Sign.signFromCelestialLongitude(firstCelestialAspectEntry.value)
                        , firstCelestialAspectEntry.key
                        , firstCelestialAspectEntry.value
                        , Sign.signFromCelestialLongitude(secondCelestialAspectEntry.value)
                        , secondCelestialAspectEntry.key
                        , secondCelestialAspectEntry.value
                        , aspectsState, timeAspectsState, aspectOverlayState, chartState, analysisState
                    )

                    if (aspect.aspectAngle != AspectAngle.ASPECT_ANGLE_NONE)
                        returnAspects.add(returnAspectsIdx++, aspect)

                }
            }

            return returnAspects
        }

        fun getExtendedAspects(firstCelestialSnapshot : CelestialSnapshot, secondCelestialSnapshot : CelestialSnapshot
                               , chartState: ChartState, aspectsState: AspectsState, timeAspectsState: TimeAspectsState, aspectOverlayState: AspectOverlayState, analysisState : AnalysisState
        ) : List<Aspect> {

            val firstCelestialAspectMap = if (chartState != ChartState.COMPOSITE_CHART) firstCelestialSnapshot.getAspectCelestialLongitudeMap()
            else CelestialSnapshot.getCompositeSnapshot(firstCelestialSnapshot, secondCelestialSnapshot).getAspectCelestialLongitudeMap()
            val secondCelestialAspectMap = if (chartState != ChartState.COMPOSITE_CHART) secondCelestialSnapshot.getAspectCelestialLongitudeMap()
            else CelestialSnapshot.getCompositeSnapshot(firstCelestialSnapshot, secondCelestialSnapshot).getAspectCelestialLongitudeMap()

            val returnAspects : MutableList<Aspect> = ArrayList()
            var returnAspectsIdx = 0

            var firstExtendedCelestialLongitude : Double
            var secondExtendedCelestialLongitude : Double
            var aspect : Aspect

            for(extendedAspect in RomanticAnalysis.affinityCelestialAspectModifiers) {
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

                aspect = Aspect(
                        Sign.signFromCelestialLongitude(firstExtendedCelestialLongitude)
                        , extendedAspect.aspectCelestialFirst
                        , firstExtendedCelestialLongitude
                        , Sign.signFromCelestialLongitude(secondExtendedCelestialLongitude)
                        , extendedAspect.aspectCelestialSecond
                        , secondExtendedCelestialLongitude
                        , aspectsState, timeAspectsState, aspectOverlayState, chartState, analysisState
                    )

                if (aspect.aspectAngle.aspectType == extendedAspect.aspectType) {

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

                    aspect = Aspect(
                            Sign.signFromCelestialLongitude(firstExtendedCelestialLongitude)
                            , extendedAspect.aspectCelestialSecond
                            , firstExtendedCelestialLongitude
                            , Sign.signFromCelestialLongitude(secondExtendedCelestialLongitude)
                            , extendedAspect.aspectCelestialFirst
                            , secondExtendedCelestialLongitude
                            , aspectsState, timeAspectsState, aspectOverlayState, chartState, analysisState
                        )

                    if (aspect.aspectAngle.aspectType == extendedAspect.aspectType) {

                        returnAspects.add(returnAspectsIdx++, aspect)
                    }
                }

            }
            return returnAspects
        }

        fun getAspectsChart(chartAspects : List<Aspect>) : List<ChartRow> {

            val returnAspectsChart : MutableList<ChartRow> = ArrayList()

            for (verticalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                val chartRow : MutableList<Aspect> = mutableListOf()

                for (horizontalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                    val chartAspect = chartAspects.firstOrNull {
                        (it.aspectCelestialFirst == verticalAspectCelestial) && (it.aspectCelestialSecond == horizontalAspectCelestial)
                    } ?: Aspect.getEmptyAspect(verticalAspectCelestial, horizontalAspectCelestial)

                    chartRow.add(horizontalAspectCelestial.ordinal, chartAspect)
                }
                returnAspectsChart.add(verticalAspectCelestial.ordinal, ChartRow(chartRow))
            }

            //add extended aspects as row
            val extendedAspectsRow = ChartRow(chartAspects.filter { it.aspectCelestialFirst.isExtendedAspect || it.aspectCelestialSecond.isExtendedAspect })

            returnAspectsChart.add(AspectCelestial.entries.filter { it.isChartAspectCelestial() }.size, extendedAspectsRow)

            return returnAspectsChart
        }


/*
        fun getAspectsStateAspects(
            stateChartAspects: List<Aspect>,
            chartState: ChartState,
            analysisState: AnalysisState
        ): Array<Aspect> {

            val returnAspects: MutableList<Aspect> = ArrayList()

            stateChartAspects.forEach { returnAspects.add(Aspect(it, chartState, analysisState)) }

            return returnAspects.toTypedArray()
        }

        fun getCharacterAspectsStateAspects(
            chartState: ChartState,
            synChart: Chart,
            compChart: Chart,
            refNatalChart: Chart,
            synNatalChart: Chart
        ): Array<Aspect> {

            if (chartState == ChartState.NATAL_CHART) return getAspectsStateAspects(
                refNatalChart.getStateAspects().toTypedArray(), chartState, AnalysisState.CHARACTER_ANALYSIS
            )

            val returnAspects: MutableList<Aspect> = ArrayList()

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
                        Aspect(
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
                        Aspect(
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
*/
        fun getAspectsChart(chartAspects: Array<Aspect>): Array<ChartRow> {

            val returnAspectsChart: MutableList<ChartRow> = ArrayList()

            for (verticalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                val chartRow: MutableList<Aspect> = mutableListOf()

                for (horizontalAspectCelestial in AspectCelestial.entries.filter { it.isChartAspectCelestial() }) {

                    val chartAspect = chartAspects.firstOrNull {
                        (it.aspectCelestialFirst == verticalAspectCelestial) && (it.aspectCelestialSecond == horizontalAspectCelestial)
                    } ?: Aspect.getEmptyAspect(verticalAspectCelestial, horizontalAspectCelestial)

                    chartRow.add(horizontalAspectCelestial.ordinal, chartAspect)
                }
                returnAspectsChart.add(verticalAspectCelestial.ordinal, ChartRow(chartRow))
            }

            return returnAspectsChart.toTypedArray()
        }
    }
}