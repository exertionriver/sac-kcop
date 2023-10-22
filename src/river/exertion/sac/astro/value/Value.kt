package river.exertion.sac.astro.value

import kotlinx.serialization.Serializable
import kotlin.math.abs

@Serializable
data class Value (val positive : Int = 0, val negative : Int = 0) {

    fun getNet() = positive + negative
    fun getStimulation() = positive + abs(negative)
    fun getConsonance() : Double = positive / getStimulation().toDouble()

    override fun toString() = "Value(pos:$positive neg:$negative) : net:${getNet()} stimulation:${getStimulation()}"
    operator fun plus(baseValue: Value): Value {
        return Value (this.positive + baseValue.positive, this.negative + baseValue.negative)
    }

    companion object {
        fun getEmptyValue() = Value(0, 0)

        fun avg(firstValue : Value, secondValue: Value): Value {
            return Value ((firstValue.positive + secondValue.positive) / 2, (firstValue.negative + secondValue.negative) / 2)
        }
    }

}