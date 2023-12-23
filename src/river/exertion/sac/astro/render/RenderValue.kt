package river.exertion.sac.astro.render

import river.exertion.sac.astro.value.Value
import kotlin.math.abs

data class RenderValue(val value : Value) {

    constructor(positive : Int, negative : Int) : this(Value(positive, negative))

    fun getPosValueLabel() : Pair<RenderValueType, String> = Pair(RenderValueType.POSITIVE, value.positive.toString().padStart(4, ' '))
    fun getNegValueLabel() : Pair<RenderValueType, String> = Pair(RenderValueType.NEGATIVE, abs(value.negative).toString().padStart(4, ' '))

    fun getConsPercentLabel() : Pair<RenderValueType, String> =
        Pair(RenderValueType.POSITIVE, abs(100 * value.positive.toDouble() / value.getStimulation().toDouble() ).toInt().toString().padStart(3, ' '))

    fun getDissPercentLabel() : Pair<RenderValueType, String> =
        Pair(RenderValueType.NEGATIVE, abs(100 * value.negative.toDouble() / value.getStimulation().toDouble() ).toInt().toString().padStart(2, ' '))

    fun getStimLabel() : Pair<RenderValueType, String> =
        Pair(RenderValueType.POSITIVE, value.getStimulation().toString().padStart(4, ' '))

    companion object {

        fun labelDivider() : String = "."

    }
}