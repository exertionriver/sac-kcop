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

    fun avg(secondValue: Value): Value {
        return Value ((positive + secondValue.positive) / 2, (negative + secondValue.negative) / 2)
    }

    fun getPosValueLabel() : Pair<ValueType, String> = Pair(ValueType.POSITIVE, positive.toString().padStart(4, ' '))
    fun getNegValueLabel() : Pair<ValueType, String> = Pair(ValueType.NEGATIVE, abs(negative).toString().padStart(4, ' '))

    fun getConsPercentLabel() : Pair<ValueType, String> =
        Pair(ValueType.POSITIVE, abs(100 * positive.toDouble() / stimulation.toDouble() ).toInt().toString().padStart(3, ' '))

    fun getDissPercentLabel() : Pair<ValueType, String> =
        Pair(ValueType.NEGATIVE, abs(100 * negative.toDouble() / stimulation.toDouble() ).toInt().toString().padStart(2, ' '))

    fun getStimLabel() : Pair<ValueType, String> =
        Pair(ValueType.POSITIVE, stimulation.toString().padStart(4, ' '))

    fun getLabel() = getPosValueLabel().second + labelDivider + getNegValueLabel().second + getConsPercentLabel().second + labelDivider + getDissPercentLabel().second + getStimLabel().second

    companion object {
        val labelDivider : String = "."

        fun empty() = Value(0, 0)

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

        fun List<Value>.sum() : Value {
            val pos = this.sumOf { it.positive }
            val neg = this.sumOf { it.negative }

            return Value(pos, neg)
        }
    }
}