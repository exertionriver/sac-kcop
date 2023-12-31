package river.exertion.sac.astro.render

import river.exertion.kcop.base.str
import river.exertion.sac.astro.ValueType
import river.exertion.sac.astro.value.ValueAspect
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.console.state.ChartStateType
import river.exertion.sac.console.state.ChartStateType.Companion.decodeChartStateType
import kotlin.math.abs

data class RenderAspect(val valueAspect : ValueAspect) {

    val stateAspect = valueAspect.stateAspect

    fun getAspectRenderLabel() : Pair<ValueType, String> = when {
        (valueAspect.analysisState != AnalysisState.ROMANTIC_ANALYSIS) -> when {
            (valueAspect.getPositiveBaseValue() > 0) -> Pair(
                ValueType.POSITIVE,
                stateAspect.aspectAngle.aspectType.label
            )
            (valueAspect.getNegativeBaseValue() < 0) -> Pair(
                ValueType.NEGATIVE,
                stateAspect.aspectAngle.aspectType.label
            )
            else -> Pair(
                ValueType.NEUTRAL,
                stateAspect.aspectAngle.aspectType.label
            )
        }
        else -> when { //analysis state
            (valueAspect.getPositiveBaseValue() > 0) && (valueAspect.getAspectModifier() < 0) -> Pair(
                ValueType.REVERSAL_TO_NEG,
                stateAspect.aspectAngle.aspectType.label
            )
            (valueAspect.getNegativeBaseValue() < 0) && (valueAspect.getAspectModifier() > 0) -> Pair(
                ValueType.REVERSAL_TO_POS,
                stateAspect.aspectAngle.aspectType.label
            )
            (valueAspect.baseValue.net != 0) && (valueAspect.baseValue.net == 0) -> Pair(
                ValueType.REVERSAL_TO_NEUT,
                stateAspect.aspectAngle.aspectType.label
            )
            (valueAspect.getPositiveBaseValue() > 0) -> Pair(
                ValueType.POSITIVE,
                stateAspect.aspectAngle.aspectType.label
            )
            (valueAspect.getNegativeBaseValue() < 0) -> Pair(
                ValueType.NEGATIVE,
                stateAspect.aspectAngle.aspectType.label
            )
            else -> Pair(
                ValueType.NEUTRAL,
                stateAspect.aspectAngle.aspectType.label
            )
        }
    }

    fun getAspectValueRenderLabel() : Pair<ValueType, String> = getAspectValue().let { Pair(it.first, it.second.toString()) }

    fun getAspectValue() : Pair<ValueType, Int> = when {
        (valueAspect.analysisState != AnalysisState.ROMANTIC_ANALYSIS) -> when {
            (valueAspect.getPositiveBaseValue() > 0) -> Pair(
                ValueType.POSITIVE,
                valueAspect.getPositiveBaseValue()
            )
            (valueAspect.getNegativeBaseValue() < 0) -> Pair(
                ValueType.NEGATIVE,
                (-valueAspect.getNegativeBaseValue())
            )
            else -> Pair(
                ValueType.NEUTRAL,
                0
            )
        }
        else -> when { //analysis state
            (valueAspect.getPositiveBaseValue() > 0) && (valueAspect.getAspectModifier() < 0) -> Pair(
                ValueType.REVERSAL_TO_NEG,
                abs(valueAspect.getBaseModNetValue().net)
            )
            (valueAspect.getNegativeBaseValue() < 0) && (valueAspect.getAspectModifier() > 0) -> Pair(
                ValueType.REVERSAL_TO_POS,
                abs(valueAspect.getBaseModNetValue().net)
            )
            (valueAspect.baseValue.net != 0) && (valueAspect.baseValue.net == 0) -> Pair(
                ValueType.REVERSAL_TO_NEUT,
                abs(valueAspect.getBaseModNetValue().net)
            )
            (valueAspect.getPositiveBaseValue() > 0) -> Pair(
                ValueType.POSITIVE,
                abs(valueAspect.getBaseModNetValue().net)
            )
            (valueAspect.getNegativeBaseValue() < 0) -> Pair(
                ValueType.NEGATIVE,
                abs(valueAspect.getBaseModNetValue().net)
            )
            else -> Pair(
                ValueType.NEUTRAL,
                abs(valueAspect.getBaseModNetValue().net)
            )
        }
    }

    fun getLabels() : List<String> = listOf(
        stateAspect.aspectCelestialFirst.label,
        getAspectRenderLabel().second,
        stateAspect.aspectCelestialSecond.label,
        getAspectValueRenderLabel().second,
    )

    fun getAspectValueType() = getAspectRenderLabel().first
    fun getValueValueType() = getAspectValueRenderLabel().first

    fun getAspectCelestial1Label() = getLabels()[0]
    fun getAspectLabel() = getLabels()[1]
    fun getAspectCelestial2Label() = getLabels()[2]
    fun getAspectDelimLabel() = "="
    fun getAspectValueLabel() = getLabels()[3]

    fun getRenderLabel() : String = getLabels().str()

    fun getRenderRomanticModLabel() : Pair<ValueType, String> {
        if (valueAspect.analysisState != AnalysisState.ROMANTIC_ANALYSIS) return Pair(ValueType.NEUTRAL, "      ")

        return when {
            (valueAspect.getAspectModifier() > 0) -> Pair(
                ValueType.POSITIVE, when {
                (valueAspect.getAspectModifier() == 4) -> ":(+4) "
                else -> ":(+${valueAspect.getAspectModifier()}) "
            })
            (valueAspect.getAspectModifier() < 0) -> Pair(
                ValueType.NEGATIVE, when {
                (valueAspect.getAspectModifier() == -4) -> ":(-4) "
                else -> ":(${valueAspect.getAspectModifier()}) "
            })
            else -> Pair(ValueType.NEUTRAL, " ")
            }
    }

    fun getRenderCharacterModLabel() : String {
        if (valueAspect.analysisState != AnalysisState.CHARACTER_ANALYSIS) return ":(**)"

        return getChartStateTypesLabel(valueAspect.getAspectModifier())
    }

    companion object {
        fun getChartStateTypesLabel(chartStateTypeEncoding : Int) : String {
            var returnString = ""

            val chartStateTypes = chartStateTypeEncoding.decodeChartStateType()

            var counter = 0

            chartStateTypes.sortedDescending().filter { it.ordinal <= ChartStateType.renderMaxIdx }.forEach {
                returnString += when (it) {
                    ChartStateType.REF_NATAL_CHART -> ""
                    ChartStateType.SYN_NATAL_CHART -> ""
                    else -> ""
                }

                returnString += it.getChartState().getLabel()

                counter++
            }

            (counter..2).forEach {
                returnString += "  "
            }

            return returnString
        }
    }
}