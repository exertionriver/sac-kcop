package river.exertion.sac.astro.render

import river.exertion.sac.astro.render.RenderValue.Companion.valueDivider
import river.exertion.sac.astro.value.Value
import river.exertion.sac.console.render.RenderState
import river.exertion.sac.Constants
import river.exertion.sac.console.state.ChartState
import kotlin.math.abs

@ExperimentalUnsignedTypes
object RenderChartState {

    fun String.chartLabel() : String = this + ":"
    fun String.impChartLabel() : String = Constants.SYM_IMPROVEMENT + this + ":"
    fun String.romChartLabel() : String = Constants.SYM_ROMANTIC + this + ":"
    fun String.impRomChartLabel() : String = Constants.SYM_IMPROVEMENT + Constants.SYM_ROMANTIC + this + ":"

    fun getChartSumLabel(chartState : ChartState, colorString : String) : String =
        RenderState.getNestedLabelString(
            Constants.SYM_SIGMA,
            colorString,
            chartState.getLabel()
        )

    fun getChartImpLabel(sharedChartValue : Value, natalChartValue : Value) : String {
        val pos = sharedChartValue.positive - natalChartValue.positive
        val neg = -1 * (sharedChartValue.negative - natalChartValue.negative)
        val posLabel = abs(pos).toString().padStart(4, ' ')
        val negLabel = abs(neg).toString().padStart(4, ' ')

        return posLabel.valueDivider().plus(negLabel)
    }

    fun getChartImpPercentLabel(sharedChartValue : Value, natalChartValue : Value) : String {
        val consPosChange = (sharedChartValue.positive > natalChartValue.positive)
        val consNegChange = (sharedChartValue.positive < natalChartValue.positive)
        val dissPosChange = (sharedChartValue.negative > natalChartValue.negative)
        val dissNegChange = (sharedChartValue.negative < natalChartValue.negative)

        val cons = if (consPosChange)
                (100 * sharedChartValue.positive.toDouble() / natalChartValue.positive.toDouble()).toInt() - 100
            else if (consNegChange)
                (100 * natalChartValue.positive.toDouble() / sharedChartValue.positive.toDouble()).toInt() - 100
            else 0
        val diss = if (dissPosChange)
                (100 * abs(natalChartValue.negative.toDouble()) / abs(sharedChartValue.negative.toDouble())).toInt() - 100
            else if (dissNegChange)
                (100 * abs(sharedChartValue.negative.toDouble()) / abs(natalChartValue.negative.toDouble())).toInt() - 100
            else 0
        val consLabel = cons.toString().padStart(2, ' ')
        val dissLabel = diss.toString().padStart(2, ' ')

        return consLabel.valueDivider().plus(dissLabel)
    }

    fun getChartImpStimLabel(sharedChartValue : Value, natalChartValue : Value) : String {

        val impStim = sharedChartValue.getStimulation() - natalChartValue.getStimulation()
        val impStimLabel = abs(impStim).toString().padStart(4, ' ')

        return impStimLabel
    }
}