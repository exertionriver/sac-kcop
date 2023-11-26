package river.exertion.sac.astro.render

import river.exertion.sac.astro.value.Value
import river.exertion.sac.Constants
import kotlin.math.abs

data class RenderValue(val value : Value) {

    fun getLabel() : String = when {
        (value.positive > 0) && (value.negative < 0) -> value.positive.toString().padStart(4, ' ').positiveLabel().valueDivider().plus(
            abs(value.negative).toString().padStart(4, ' ').negativeLabel()
        )
        (value.positive > 0) && (value.negative == 0) -> value.positive.toString().padStart(4, ' ').positiveLabel().valueDivider().plus(
            abs(value.negative).toString().padStart(4, ' ').neutralLabel()
        )
        (value.positive == 0) && (value.negative < 0) -> value.positive.toString().padStart(4, ' ').neutralLabel().valueDivider().plus(
            abs(value.negative).toString().padStart(4, ' ').negativeLabel()
        )
        else -> value.positive.toString().padStart(4, ' ').neutralLabel().valueDivider().plus(
            abs(value.negative).toString().padStart(4, ' ').neutralLabel()
        )
    }

    fun getPercentLabel() : String {
        val cons = abs(100 * value.positive.toDouble() / value.getStimulation().toDouble() ).toInt()
        val diss = abs(100 * value.negative.toDouble() / value.getStimulation().toDouble() ).toInt()
        val consLabel = cons.toString().padStart(2, ' ')
        val dissLabel = diss.toString().padStart(2, ' ')

        return consLabel.valueDivider().plus(dissLabel)
    }

    fun getStimLabel() : String {

        //TODO: move label to Astro folder
        return value.getStimulation().toString().padStart(4, ' ')
    }

    companion object {

        fun String.positiveLabel() : String = this
        fun String.neutralLabel() : String = this
        fun String.negativeLabel() : String = this
        fun String.valueDivider() : String = this + "."

    }
}