package river.exertion.sac.astro.render

import river.exertion.sac.astro.render.RenderChartStateType.getChartStateTypesLabel
import river.exertion.sac.astro.render.RenderValue.Companion.negativeLabel
import river.exertion.sac.astro.render.RenderValue.Companion.neutralLabel
import river.exertion.sac.astro.render.RenderValue.Companion.positiveLabel
import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.astro.value.ValueAspect
import river.exertion.sac.Constants
import kotlin.math.abs

data class RenderAspect(val valueAspect : ValueAspect) {

    val stateAspect = valueAspect.stateAspect

    fun getAspectRenderLabel() = when {
        (valueAspect.analysisState != AnalysisState.ROMANTIC_ANALYSIS) -> when {
            (valueAspect.getPositiveBaseValue() > 0) -> RenderAspectType.fromName(
                stateAspect.aspectAngle.getAspectType().toString()
            )!!.getLabel().positiveLabel()
            (valueAspect.getNegativeBaseValue() < 0) -> RenderAspectType.fromName(
                stateAspect.aspectAngle.getAspectType().toString()
            )!!.getLabel().negativeLabel()
            else -> RenderAspectType.fromName(stateAspect.aspectAngle.getAspectType().toString())!!.getLabel().neutralLabel()
        }
        else -> when { //analysis state
            (valueAspect.getPositiveBaseValue() > 0) && (valueAspect.getAspectModifier() < 0) -> RenderAspectType.fromName(
                stateAspect.aspectAngle.getAspectType().toString()
            )!!.getLabel().revLabel()
            (valueAspect.getNegativeBaseValue() < 0) && (valueAspect.getAspectModifier() > 0) -> RenderAspectType.fromName(
                stateAspect.aspectAngle.getAspectType().toString()
            )!!.getLabel().revLabel()
            (valueAspect.baseValue.getNet() != 0) && (valueAspect.baseValue.getNet() == 0) -> RenderAspectType.fromName(
                stateAspect.aspectAngle.getAspectType().toString()
            )!!.getLabel().revLabel()
            (valueAspect.getPositiveBaseValue() > 0) -> RenderAspectType.fromName(
                stateAspect.aspectAngle.getAspectType().toString()
            )!!.getLabel().positiveLabel()
            (valueAspect.getNegativeBaseValue() < 0) -> RenderAspectType.fromName(
                stateAspect.aspectAngle.getAspectType().toString()
            )!!.getLabel().negativeLabel()
            else -> RenderAspectType.fromName(stateAspect.aspectAngle.getAspectType().toString())!!.getLabel().neutralLabel()
        }
    }

    fun getAspectValueRenderLabel() = when {
        (valueAspect.analysisState != AnalysisState.ROMANTIC_ANALYSIS) -> when {
            (valueAspect.getPositiveBaseValue() > 0) -> valueAspect.getPositiveBaseValue().toString().positiveLabel()
            (valueAspect.getNegativeBaseValue() < 0) -> (-valueAspect.getNegativeBaseValue()).toString().negativeLabel()
            else -> "0".neutralLabel()
        }
        else -> when { //analysis state
            (valueAspect.getPositiveBaseValue() > 0) && (valueAspect.getAspectModifier() < 0) -> abs(valueAspect.getBaseModNetValue().getNet()).toString().revLabel()
            (valueAspect.getNegativeBaseValue() < 0) && (valueAspect.getAspectModifier() > 0) -> abs(valueAspect.getBaseModNetValue().getNet()).toString().revLabel()
            (valueAspect.baseValue.getNet() != 0) && (valueAspect.baseValue.getNet() == 0) -> abs(valueAspect.getBaseModNetValue().getNet()).toString().revLabel()
            (valueAspect.getPositiveBaseValue() > 0) -> abs(valueAspect.getBaseModNetValue().getNet()).toString().positiveLabel()
            (valueAspect.getNegativeBaseValue() < 0) -> abs(valueAspect.getBaseModNetValue().getNet()).toString().negativeLabel()
            else -> abs(valueAspect.getBaseModNetValue().getNet()).toString().neutralLabel()
        }
    }

    fun getLabels() : List<String> = listOf(
        RenderAspectCelestial.fromName(stateAspect.aspectCelestialFirst.toString())!!.getLabel(),
        getAspectRenderLabel(),
        RenderAspectCelestial.fromName(stateAspect.aspectCelestialSecond.toString())!!.getLabel(),
        getAspectValueRenderLabel()
    )

    fun getAspectCelestial1Label() = getLabels()[0]
    fun getAspectLabel() = getLabels()[1]
    fun getAspectCelestial2Label() = getLabels()[2]
    fun getAspectDelimLabel() = "="
    fun getAspectValueLabel() = getLabels()[3]

    fun getRenderLabel() : String {

        val firstAspectSpace = if (stateAspect.aspectCelestialFirst != AspectCelestial.ASPECT_SUN_MOON_MIDPOINT) " " else ""
        val secondAspectSpace = if (stateAspect.aspectCelestialSecond != AspectCelestial.ASPECT_SUN_MOON_MIDPOINT) " " else ""

        val commonLabel = //RenderSign.getElementLabel(stateAspect.signFirst) + " " +
                RenderAspectCelestial.fromName(stateAspect.aspectCelestialFirst.toString())!!.getLabel() + firstAspectSpace +
                getAspectRenderLabel() + " " +
                //RenderSign.getElementLabel(stateAspect.signSecond) + " " +
                RenderAspectCelestial.fromName(stateAspect.aspectCelestialSecond.toString())!!.getLabel() + secondAspectSpace

        return commonLabel + "=" + getAspectValueRenderLabel()

   }

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

    @OptIn(ExperimentalUnsignedTypes::class)
    fun getRenderCharacterModLabel() : String {
        if (valueAspect.analysisState != AnalysisState.CHARACTER_ANALYSIS) return ":(**)"

        return getChartStateTypesLabel(valueAspect.getAspectModifier())
    }

    companion object {

        //modifier flips value
        fun String.revLabel() : String = this

        fun getAspectNoneMarkerLabel() : String = RenderAspectType.ASPECT_NONE.getLabel()

        fun getLabelLength() : Int {
            return 23 //default Sign + Celestial + Aspect + Sign + Celestial + value
        }
    }
}