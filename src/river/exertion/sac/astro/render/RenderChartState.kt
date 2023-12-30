package river.exertion.sac.astro.render

import river.exertion.sac.Constants
import river.exertion.sac.astro.value.Value
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.view.SACInputProcessor
import kotlin.math.abs

object RenderChartState {

    fun String.impChartLabel() : String = Constants.SYM_IMPROVEMENT + this

    fun String.conAmChartLabel() : String = when (SACInputProcessor.analysisStateMachine.currentState) {
        AnalysisState.ROMANTIC_ANALYSIS -> Constants.SYM_ROMANTIC + this
        else -> this
    }

    fun String.impConAmChartLabel() : String = this.conAmChartLabel().impChartLabel()

    fun getChartSumLabel(overrideState : ChartState? = null) : String = "${Constants.SYM_SIGMA}${overrideState?.getLabel() ?: SACInputProcessor.chartStateMachine.currentState.getLabel()}:"

    fun getPosChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<RenderValueType, String> {
        val pos = sharedChartValue.positive - natalChartValue.positive

        val valueType = when {
            (pos > 0) -> RenderValueType.POSITIVE
            (pos < 0) -> RenderValueType.NEGATIVE
            else -> RenderValueType.NEUTRAL
        }

        return Pair(valueType, abs(pos).toString().padStart(4, ' '))
    }

    fun getNegChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<RenderValueType, String> {
        val neg = sharedChartValue.negative - natalChartValue.negative

        val valueType = when {
            (neg > 0) -> RenderValueType.POSITIVE
            (neg < 0) -> RenderValueType.NEGATIVE
            else -> RenderValueType.NEUTRAL
        }

        return Pair(valueType, abs(neg).toString().padStart(4, ' '))
    }

    fun getConsChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<RenderValueType, String> {
        val consPosChange = (sharedChartValue.positive > natalChartValue.positive)
        val consNegChange = (sharedChartValue.positive < natalChartValue.positive)

        val cons = if (consPosChange)
            (100 * sharedChartValue.positive.toDouble() / natalChartValue.positive.toDouble()).toInt() - 100
        else if (consNegChange)
            (100 * natalChartValue.positive.toDouble() / sharedChartValue.positive.toDouble()).toInt() - 100
        else 0

        val valueType = when {
            consPosChange -> RenderValueType.POSITIVE
            consNegChange -> RenderValueType.NEGATIVE
            else -> RenderValueType.NEUTRAL
        }

        return Pair(valueType, abs(cons).toString().padStart(3, ' '))
    }

    fun getDissChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<RenderValueType, String> {
        val dissPosChange = (sharedChartValue.negative > natalChartValue.negative)
        val dissNegChange = (sharedChartValue.negative < natalChartValue.negative)

        val diss = if (dissPosChange)
            (100 * abs(natalChartValue.negative.toDouble()) / abs(sharedChartValue.negative.toDouble())).toInt() - 100
        else if (dissNegChange)
            (100 * abs(sharedChartValue.negative.toDouble()) / abs(natalChartValue.negative.toDouble())).toInt() - 100
        else 0

        val valueType = when {
            dissPosChange -> RenderValueType.POSITIVE
            dissNegChange -> RenderValueType.NEGATIVE
            else -> RenderValueType.NEUTRAL
        }

        return Pair(valueType, abs(diss).toString().padStart(2, ' '))
    }

    fun getStimChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : Pair<RenderValueType, String> {
        val stim = sharedChartValue.getStimulation() - natalChartValue.getStimulation()

        val valueType = when {
            (stim > 0) -> RenderValueType.POSITIVE
            (stim < 0) -> RenderValueType.NEGATIVE
            else -> RenderValueType.NEUTRAL
        }

        return Pair(valueType, abs(stim).toString().padStart(4, ' '))
    }
}