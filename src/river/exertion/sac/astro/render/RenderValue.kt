package river.exertion.sac.astro.render

import river.exertion.sac.astro.ValueType
import river.exertion.sac.astro.Value
import kotlin.math.abs

data class RenderValue(val value : Value) {

    constructor(positive : Int, negative : Int) : this(Value(positive, negative))

    fun getPosValueLabel() : Pair<ValueType, String> = Pair(ValueType.POSITIVE, value.positive.toString().padStart(4, ' '))
    fun getNegValueLabel() : Pair<ValueType, String> = Pair(ValueType.NEGATIVE, abs(value.negative).toString().padStart(4, ' '))

    fun getConsPercentLabel() : Pair<ValueType, String> =
        Pair(ValueType.POSITIVE, abs(100 * value.positive.toDouble() / value.stimulation.toDouble() ).toInt().toString().padStart(3, ' '))

    fun getDissPercentLabel() : Pair<ValueType, String> =
        Pair(ValueType.NEGATIVE, abs(100 * value.negative.toDouble() / value.stimulation.toDouble() ).toInt().toString().padStart(2, ' '))

    fun getStimLabel() : Pair<ValueType, String> =
        Pair(ValueType.POSITIVE, value.stimulation.toString().padStart(4, ' '))

    fun getLabel() = getPosValueLabel().second + labelDivider() + getNegValueLabel().second + getConsPercentLabel().second + labelDivider() + getDissPercentLabel().second + getStimLabel().second

    companion object {
        fun labelDivider() : String = "."
    }
}