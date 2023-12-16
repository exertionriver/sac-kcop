package river.exertion.sac.astro.render

import river.exertion.sac.astro.value.Value
import kotlin.math.abs

data class RenderValue(val value : Value) {

    fun getValuesLabel() : Pair<String, String> = Pair(value.positive.toString().padStart(4, ' '), abs(value.negative).toString().padStart(4, ' '))

    fun getPercentLabel() : Pair<String, String> {
        val cons = abs(100 * value.positive.toDouble() / value.getStimulation().toDouble() ).toInt()
        val diss = abs(100 * value.negative.toDouble() / value.getStimulation().toDouble() ).toInt()
        val consLabel = cons.toString().padStart(2, ' ')
        val dissLabel = diss.toString().padStart(2, ' ')

        return Pair(consLabel, dissLabel)
    }

    fun getStimLabel() : String {
        return value.getStimulation().toString().padStart(4, ' ')
    }

    companion object {

        fun labelDivider() : String = "."

    }
}