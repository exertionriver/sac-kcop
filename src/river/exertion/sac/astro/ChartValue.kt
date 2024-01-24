package river.exertion.sac.astro

import river.exertion.sac.astro.AspectValue.sortFilterValueAspects
import river.exertion.sac.astro.base.ChartValueType
import river.exertion.sac.astro.base.ValueType
import river.exertion.sac.console.state.ChartState
import kotlin.math.abs

object ChartValue {

    fun Chart.getChartBaseValue() = Value(chartAspects.sortFilterValueAspects().map { it.baseValue.positive }.reduce { acc, basePositive -> acc + basePositive },
        chartAspects.sortFilterValueAspects().map { it.baseValue.negative }.reduce { acc, baseNegative -> acc + baseNegative } )

    fun Chart.getChartModValue() = Value(chartAspects.sortFilterValueAspects().map { it.modValue.positive }.reduce { acc, modPositive -> acc + modPositive },
        chartAspects.sortFilterValueAspects().map { it.modValue.negative }.reduce { acc, modNegative -> acc + modNegative } )

    fun Chart.getChartNetValue() = Value(chartAspects.sortFilterValueAspects().map { it.netValue.positive }.reduce { acc, netPositive -> acc + netPositive },
        chartAspects.sortFilterValueAspects().map { it.netValue.negative }.reduce { acc, netNegative -> acc + netNegative } )

    fun getPosChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<ValueType, String> {
        val pos = sharedChartValue.positive - natalChartValue.positive

        val valueType = when {
            (pos > 0) -> ValueType.POSITIVE
            (pos < 0) -> ValueType.NEGATIVE
            else -> ValueType.NEUTRAL
        }

        return Pair(valueType, abs(pos).toString().padStart(4, ' '))
    }

    fun getNegChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<ValueType, String> {
        val neg = sharedChartValue.negative - natalChartValue.negative

        val valueType = when {
            (neg > 0) -> ValueType.POSITIVE
            (neg < 0) -> ValueType.NEGATIVE
            else -> ValueType.NEUTRAL
        }

        return Pair(valueType, abs(neg).toString().padStart(4, ' '))
    }

    fun getConsChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<ValueType, String> {
        val consPosChange = (sharedChartValue.positive > natalChartValue.positive)
        val consNegChange = (sharedChartValue.positive < natalChartValue.positive)

        val cons = if (consPosChange)
            (100 * sharedChartValue.positive.toDouble() / natalChartValue.positive.toDouble()).toInt() - 100
        else if (consNegChange)
            (100 * natalChartValue.positive.toDouble() / sharedChartValue.positive.toDouble()).toInt() - 100
        else 0

        val valueType = when {
            consPosChange -> ValueType.POSITIVE
            consNegChange -> ValueType.NEGATIVE
            else -> ValueType.NEUTRAL
        }

        return Pair(valueType, abs(cons).toString().padStart(3, ' '))
    }

    fun getDissChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<ValueType, String> {
        val dissPosChange = (sharedChartValue.negative > natalChartValue.negative)
        val dissNegChange = (sharedChartValue.negative < natalChartValue.negative)

        val diss = if (dissPosChange)
            (100 * abs(natalChartValue.negative.toDouble()) / abs(sharedChartValue.negative.toDouble())).toInt() - 100
        else if (dissNegChange)
            (100 * abs(sharedChartValue.negative.toDouble()) / abs(natalChartValue.negative.toDouble())).toInt() - 100
        else 0

        val valueType = when {
            dissPosChange -> ValueType.POSITIVE
            dissNegChange -> ValueType.NEGATIVE
            else -> ValueType.NEUTRAL
        }

        return Pair(valueType, abs(diss).toString().padStart(2, ' '))
    }

    fun getStimChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<ValueType, String> {
        val stim = sharedChartValue.stimulation - natalChartValue.stimulation

        val valueType = when {
            (stim > 0) -> ValueType.POSITIVE
            (stim < 0) -> ValueType.NEGATIVE
            else -> ValueType.NEUTRAL
        }

        return Pair(valueType, abs(stim).toString().padStart(4, ' '))
    }

    fun Chart.analysisAppreciationValue() = if (chartState == ChartState.COMBINED_CHART) {
        Value(chartAspects.sortFilterValueAspects().filter {
            it.aspectValueType.chartValueType == ChartValueType.APPRECIATION
        }.map { it.baseValue.positive }.reduceOrNull { acc, basePositive -> acc + basePositive } ?: 0
            ,chartAspects.sortFilterValueAspects().filter {
                it.aspectValueType.chartValueType == ChartValueType.APPRECIATION
            }.map { it.baseValue.negative }.reduceOrNull { acc, baseNegative -> acc + baseNegative } ?: 0
        )
    } else {
        Value(0, 0)
    }

    fun Chart.analysisAffinityValue() = if (chartState == ChartState.COMBINED_CHART) {
        Value(chartAspects.sortFilterValueAspects().filter {
            it.aspectValueType.chartValueType == ChartValueType.AFFINITY
        }.map { it.baseValue.positive }.reduceOrNull { acc, basePositive -> acc + basePositive } ?: 0
            ,chartAspects.sortFilterValueAspects().filter {
                it.aspectValueType.chartValueType == ChartValueType.AFFINITY
            }.map { it.baseValue.negative }.reduceOrNull { acc, baseNegative -> acc + baseNegative } ?: 0
        )
    } else {
        Value(0, 0)
    }

    fun Chart.analysisCommonalityValue() = if (chartState == ChartState.COMBINED_CHART) {
        Value(chartAspects.sortFilterValueAspects().filter {
            it.aspectValueType.chartValueType == ChartValueType.COMMONALITY
        }.map { it.baseValue.positive }.reduceOrNull { acc, basePositive -> acc + basePositive } ?: 0
            ,chartAspects.sortFilterValueAspects().filter {
                it.aspectValueType.chartValueType == ChartValueType.COMMONALITY
            }.map { it.baseValue.negative }.reduceOrNull { acc, baseNegative -> acc + baseNegative } ?: 0
        )
    } else {
        Value(0, 0)
    }

    fun Chart.analysisCompatibilityValue() = if (chartState == ChartState.COMBINED_CHART) {
        Value(chartAspects.sortFilterValueAspects().filter {
            it.aspectValueType.chartValueType == ChartValueType.COMPATIBILITY
        }.map { it.baseValue.positive }.reduceOrNull { acc, basePositive -> acc + basePositive } ?: 0
            ,chartAspects.sortFilterValueAspects().filter {
                it.aspectValueType.chartValueType == ChartValueType.COMPATIBILITY
            }.map { it.baseValue.negative }.reduceOrNull { acc, baseNegative -> acc + baseNegative } ?: 0
        )
    } else {
        Value(0, 0)
    }
}