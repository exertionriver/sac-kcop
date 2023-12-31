package river.exertion.sac.astro.render

import river.exertion.kcop.base.str
import river.exertion.sac.astro.render.RenderChartStateType.getChartStateTypesLabel
import river.exertion.sac.astro.value.ValueAspect
import river.exertion.sac.console.state.AnalysisState
import kotlin.math.abs

data class RenderAspect(val valueAspect : ValueAspect) {

    val stateAspect = valueAspect.stateAspect

    fun getAspectRenderLabel() : Pair<RenderValueType, String> = when {
        (valueAspect.analysisState != AnalysisState.ROMANTIC_ANALYSIS) -> when {
            (valueAspect.getPositiveBaseValue() > 0) -> Pair(
                RenderValueType.POSITIVE,
                RenderAspectType.fromName(stateAspect.aspectAngle.aspectType.name)!!.getLabel()
            )
            (valueAspect.getNegativeBaseValue() < 0) -> Pair(
                RenderValueType.NEGATIVE,
                RenderAspectType.fromName(stateAspect.aspectAngle.aspectType.name)!!.getLabel()
            )
            else -> Pair(
                RenderValueType.NEUTRAL,
                RenderAspectType.fromName(stateAspect.aspectAngle.aspectType.name)!!.getLabel()
            )
        }
        else -> when { //analysis state
            (valueAspect.getPositiveBaseValue() > 0) && (valueAspect.getAspectModifier() < 0) -> Pair(
                RenderValueType.REVERSAL_TO_NEG,
                RenderAspectType.fromName(stateAspect.aspectAngle.aspectType.toString())!!.getLabel()
            )
            (valueAspect.getNegativeBaseValue() < 0) && (valueAspect.getAspectModifier() > 0) -> Pair(
                RenderValueType.REVERSAL_TO_POS,
                RenderAspectType.fromName(stateAspect.aspectAngle.aspectType.toString())!!.getLabel()
            )
            (valueAspect.baseValue.getNet() != 0) && (valueAspect.baseValue.getNet() == 0) -> Pair(
                RenderValueType.REVERSAL_TO_NEUT,
                RenderAspectType.fromName(stateAspect.aspectAngle.aspectType.toString())!!.getLabel()
            )
            (valueAspect.getPositiveBaseValue() > 0) -> Pair(
                RenderValueType.POSITIVE,
                RenderAspectType.fromName(stateAspect.aspectAngle.aspectType.name)!!.getLabel()
            )
            (valueAspect.getNegativeBaseValue() < 0) -> Pair(
                RenderValueType.NEGATIVE,
                RenderAspectType.fromName(stateAspect.aspectAngle.aspectType.name)!!.getLabel()
            )
            else -> Pair(
                RenderValueType.NEUTRAL,
                RenderAspectType.fromName(stateAspect.aspectAngle.aspectType.name)!!.getLabel()
            )
        }
    }

    fun getAspectValueRenderLabel() : Pair<RenderValueType, String> = getAspectValue().let { Pair(it.first, it.second.toString()) }

    fun getAspectValue() : Pair<RenderValueType, Int> = when {
        (valueAspect.analysisState != AnalysisState.ROMANTIC_ANALYSIS) -> when {
            (valueAspect.getPositiveBaseValue() > 0) -> Pair(
                RenderValueType.POSITIVE,
                valueAspect.getPositiveBaseValue()
            )
            (valueAspect.getNegativeBaseValue() < 0) -> Pair(
                RenderValueType.NEGATIVE,
                (-valueAspect.getNegativeBaseValue())
            )
            else -> Pair(
                RenderValueType.NEUTRAL,
                0
            )
        }
        else -> when { //analysis state
            (valueAspect.getPositiveBaseValue() > 0) && (valueAspect.getAspectModifier() < 0) -> Pair(
                RenderValueType.REVERSAL_TO_NEG,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            (valueAspect.getNegativeBaseValue() < 0) && (valueAspect.getAspectModifier() > 0) -> Pair(
                RenderValueType.REVERSAL_TO_POS,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            (valueAspect.baseValue.getNet() != 0) && (valueAspect.baseValue.getNet() == 0) -> Pair(
                RenderValueType.REVERSAL_TO_NEUT,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            (valueAspect.getPositiveBaseValue() > 0) -> Pair(
                RenderValueType.POSITIVE,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            (valueAspect.getNegativeBaseValue() < 0) -> Pair(
                RenderValueType.NEGATIVE,
                abs(valueAspect.getBaseModNetValue().getNet())
            )
            else -> Pair(
                RenderValueType.NEUTRAL,
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