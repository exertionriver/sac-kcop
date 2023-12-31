package river.exertion.sac.astro

import kotlinx.serialization.Serializable
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

    fun avg(secondValue: Value): Value {
        return Value ((positive + secondValue.positive) / 2, (negative + secondValue.negative) / 2)
    }

    companion object {
        fun empty() = Value(0, 0)
    }
}