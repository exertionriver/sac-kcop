package river.exertion.sac.astro.render

import river.exertion.kcop.base.str
import river.exertion.sac.astro.render.RenderChartStateType.getChartStateTypesLabel
import river.exertion.sac.astro.value.ValueAspect
import river.exertion.sac.console.state.AnalysisState
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
            (valueAspect.baseValue.getNet() != 0) && (valueAspect.baseValue.getNet() == 0) -> Pair(
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
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            (valueAspect.getNegativeBaseValue() < 0) && (valueAspect.getAspectModifier() > 0) -> Pair(
                ValueType.REVERSAL_TO_POS,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            (valueAspect.baseValue.getNet() != 0) && (valueAspect.baseValue.getNet() == 0) -> Pair(
                ValueType.REVERSAL_TO_NEUT,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            (valueAspect.getPositiveBaseValue() > 0) -> Pair(
                ValueType.POSITIVE,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            (valueAspect.getNegativeBaseValue() < 0) -> Pair(
                ValueType.NEGATIVE,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            else -> Pair(
                ValueType.NEUTRAL,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
        }
    }

    fun getLabels() : List<String> = listOf(
        stateAspect.aspectCelestialFirst.label,
        getAspectRenderLabel().second,
        stateAspect.aspectCelestialSecond.label,
        getAspectValueRenderLabel().second
    )

    fun getAspectValueType() = getAspectRenderLabel().first
    fun getValueValueType() = getAspectValueRenderLabel().first

    fun getAspectCelestial1Label() = getLabels()[0]
    fun getAspectLabel() = getLabels()[1]
    fun getAspectCelestial2Label() = getLabels()[2]
    fun getAspectDelimLabel() = "="
    fun getAspectValueLabel() = getLabels()[3]

    fun getRenderLabel() : String = getLabels().str()

    fun getRenderRomanticModLabel() : String {
        if (valueAspect.analysisState != AnalysisState.ROMANTIC_ANALYSIS) return ":(**) "

        return when {
            (valueAspect.getAspectModifier() > 0) -> when {
                (valueAspect.getAspectModifier() == 4) -> ":(+4) "
                else -> ":(+${valueAspect.getAspectModifier()}) "
            }
            (valueAspect.getAspectModifier() < 0) -> when {
                (valueAspect.getAspectModifier() == -4) -> ":(-4) "
                else -> ":(${valueAspect.getAspectModifier()}) "
            }
            else -> " "
        }
    }

    fun getRenderCharacterModLabel() : String {
        if (valueAspect.analysisState != AnalysisState.CHARACTER_ANALYSIS) return ":(**)"

        return getChartStateTypesLabel(valueAspect.getAspectModifier())
    }
}