package river.exertion.sac.astro

import kotlinx.serialization.Serializable
import river.exertion.sac.astro.base.ValueType
import kotlin.math.abs

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class Value (val positive : Int = 0, val negative : Int = 0) {

    val net = positive + negative
    val stimulation = positive + abs(negative)
    val consonance : Double = positive / stimulation.toDouble()

    override fun toString() = "Value(pos:$positive neg:$negative) : net:${net} cons:${consonance} stim:${stimulation}"

    operator fun plus(secondValue: Value): Value {
        return Value (this.positive + secondValue.positive, this.negative + secondValue.negative)
    }

    fun getPosValueLabel() : Pair<ValueType, String> = Pair(ValueType.POSITIVE, positive.toString().padStart(4, ' '))
    fun getNegValueLabel() : Pair<ValueType, String> = Pair(ValueType.NEGATIVE, abs(negative).toString().padStart(4, ' '))

    fun getConsPercentLabel() : Pair<ValueType, String> =
        Pair(ValueType.POSITIVE, abs(100 * positive.toDouble() / stimulation.toDouble() ).toInt().toString().padStart(3, ' '))

    fun getDissPercentLabel() : Pair<ValueType, String> =
        Pair(ValueType.NEGATIVE, abs(100 * negative.toDouble() / stimulation.toDouble() ).toInt().toString().padStart(2, ' '))

    fun getStimLabel() : Pair<ValueType, String> =
        Pair(ValueType.POSITIVE, stimulation.toString().padStart(4, ' '))

    companion object {
        val labelDivider : String = "."
    }
}