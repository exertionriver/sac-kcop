package river.exertion.sac.astro

import river.exertion.kcop.base.str
import river.exertion.sac.Constants
import river.exertion.sac.Constants.InvalidOrb
import river.exertion.sac.Constants.normalizeDeg
import river.exertion.sac.astro.base.*
import river.exertion.sac.console.state.*
import river.exertion.sac.console.state.ChartStateType.Companion.decodeChartStateType
import kotlin.math.abs

//fourth chart aspects are for the opposite of current chartType for compSyn charts
//if chartState == SYNASTRY_CHART, fourth chart aspects are aspects that the composite chart shares with the natal charts
//if chartState == COMPOSITE_CHART, fourth chart aspects are aspects that the synastry chart shares with the natal charts
data class Aspect (val signFirst : Sign
                   , val aspectCelestialFirst : AspectCelestial
                   , val signSecond : Sign
                   , val aspectCelestialSecond: AspectCelestial
                   , val aspectAngle : AspectAngle
                   , val orb : Double = 0.0
                   , val aspectsState : AspectsState
                   , val timeAspectsState: TimeAspectsState
                   , val aspectOverlayState : AspectOverlayState
                   , val chartState : ChartState
                   , val analysisState: AnalysisState
) {

    //calcOrb constructor
    constructor(
        signFirst : Sign
        , aspectCelestialFirst : AspectCelestial
        , aspectCelestialFirstAngle : Double
        , signSecond : Sign
        , aspectCelestialSecond: AspectCelestial
        , aspectCelestialSecondAngle : Double
        , aspectAngle : AspectAngle
        , aspectsState : AspectsState
        , timeAspectsState: TimeAspectsState
        , aspectOverlayState : AspectOverlayState
        , chartState : ChartState
        , analysisState: AnalysisState
    ) : this (
        signFirst
        , aspectCelestialFirst
        , signSecond
        , aspectCelestialSecond
        , aspectAngle
        , calcOrb(aspectAngle, aspectCelestialFirstAngle, aspectCelestialSecondAngle)
        , aspectsState
        , timeAspectsState
        , aspectOverlayState
        , chartState
        , analysisState
    )

    //findAspectAngle constructor
    constructor(
        signFirst : Sign
        , aspectCelestialFirst : AspectCelestial
        , aspectCelestialFirstAngle : Double
        , signSecond : Sign
        , aspectCelestialSecond: AspectCelestial
        , aspectCelestialSecondAngle : Double
        , aspectsState : AspectsState
        , timeAspectsState: TimeAspectsState
        , aspectOverlayState : AspectOverlayState
        , chartState : ChartState
        , analysisState: AnalysisState
    ) : this (
        signFirst
        , aspectCelestialFirst
        , aspectCelestialFirstAngle
        , signSecond
        , aspectCelestialSecond
        , aspectCelestialSecondAngle
        , findAspectAngle(aspectCelestialFirst, aspectCelestialFirstAngle, aspectCelestialSecond, aspectCelestialSecondAngle, aspectsState, timeAspectsState, aspectOverlayState)
        , aspectsState
        , timeAspectsState
        , aspectOverlayState
        , chartState
        , analysisState
    )

    //val stateAspect : StateAspect, val chartState: ChartState = ChartState.NATAL_CHART, val analysisState: AnalysisState = AnalysisState.NO_ANALYSIS, val characterModifier: Int = 0, val fourthChartAspect: Boolean = false) {

    val baseValue = getAspectBaseValue()
    val modValue = if (chartState == ChartState.SYNASTRY_CHART) Value(getPositiveModValue() / 2, getNegativeModValue() / 2) else Value(getPositiveModValue(), getNegativeModValue())
    val netValue = Value(baseValue.positive + modValue.positive, baseValue.negative + modValue.negative)

    fun celestialAspect() = CelestialAspect(aspectCelestialFirst, aspectCelestialSecond, aspectAngle.aspectType, netValue)

    fun getAspectModifier() = when (analysisState) {
        AnalysisState.ROMANTIC_ANALYSIS -> getRomanticModifier()
//        AnalysisState.CHARACTER_ANALYSIS -> characterModifier
        else -> 0
    }

    fun getSignFirstModifier() = when (analysisState) {
        AnalysisState.ELEMENT_ANALYSIS -> getElementModifier(signFirst.signElement).toInt()
        AnalysisState.MODE_ANALYSIS -> getModeModifier(signFirst.signMode).toInt()
        else -> 0
    }
    fun getSignSecondModifier() = when (analysisState) {
        AnalysisState.ELEMENT_ANALYSIS -> getElementModifier(signSecond.signElement).toInt()
        AnalysisState.MODE_ANALYSIS -> getModeModifier(signSecond.signMode).toInt()
        else -> 0
    }
    fun getAspectCelestialFirstModifier() = when (analysisState) {
        AnalysisState.PLANET_ANALYSIS -> getCelestialModifier(aspectCelestialFirst).toInt()
        else -> 0
    }
    fun getAspectCelestialSecondModifier() = when (analysisState) {
        AnalysisState.PLANET_ANALYSIS -> getCelestialModifier(aspectCelestialSecond).toInt()
        else -> 0
    }

    private fun getRomanticModifier() : Int {

        return RomanticAnalysis.celestialAspectModifiers.firstOrNull {
            (   (   ( (it.aspectCelestialFirst == aspectCelestialFirst) && (it.aspectCelestialSecond == aspectCelestialSecond) ) ||
                    ( (it.aspectCelestialFirst == aspectCelestialSecond) && (it.aspectCelestialSecond == aspectCelestialFirst) ) )
                    && (it.aspectType == aspectAngle.aspectType) )}?.modifier ?: 0
    }

//    private fun getCharacterModifier() = characterModifier

    private fun getElementModifier(signElement : SignElement) : Double {
        val aspectCelestialFirstWeight = aspectCelestialFirst.weight
        val aspectCelestialSecondWeight = aspectCelestialSecond.weight
        val aspectCelestialBothWeight = aspectCelestialFirstWeight + aspectCelestialSecondWeight
        var returnWeight = 0.0

        if (signFirst.signElement == signElement) returnWeight += aspectCelestialFirstWeight
        if (signSecond.signElement == signElement) returnWeight += aspectCelestialSecondWeight

        return returnWeight / aspectCelestialBothWeight
    }

    private fun getModeModifier(signMode : SignMode) : Double {
        val aspectCelestialFirstWeight = aspectCelestialFirst.weight
        val aspectCelestialSecondWeight = aspectCelestialSecond.weight
        val aspectCelestialBothWeight = aspectCelestialFirstWeight + aspectCelestialSecondWeight
        var returnWeight = 0.0

        if (signFirst.signMode == signMode) returnWeight += aspectCelestialFirstWeight
        if (signSecond.signMode == signMode) returnWeight += aspectCelestialSecondWeight

        return returnWeight / aspectCelestialBothWeight
    }

    private fun getCelestialModifier(aspectCelestial: AspectCelestial) : Double {
        val aspectCelestialFirstWeight = aspectCelestialFirst.weight
        val aspectCelestialSecondWeight = aspectCelestialSecond.weight
        val aspectCelestialBothWeight = aspectCelestialFirstWeight + aspectCelestialSecondWeight
        var returnWeight = 0.0

        if (aspectCelestialFirst == aspectCelestial) returnWeight += aspectCelestialFirstWeight
        if (aspectCelestialSecond == aspectCelestial) returnWeight += aspectCelestialSecondWeight

        return returnWeight / aspectCelestialBothWeight
    }

    fun getPositiveModValue() : Int {

        var modPos = 0

        if (analysisState == AnalysisState.CHARACTER_ANALYSIS) return modPos

        modPos += when {
            (getAspectModifier() > 0) -> (baseValue.positive * abs(getAspectModifier()) * .25).toInt()
            (getAspectModifier() < 0) -> (baseValue.positive * -abs(getAspectModifier()) * .25).toInt()
            else -> 0
        }

        modPos += when {
            (baseValue.net > 0) -> when {
                (getSignFirstModifier() > 0) -> (baseValue.positive * abs(getSignFirstModifier()) * .125).toInt()
                (getSignFirstModifier() < 0) -> (baseValue.positive * -abs(getSignFirstModifier()) * .125).toInt()
                else -> 0
            }
            else -> 0
        }
        modPos += when {
            (baseValue.net > 0) -> when {
                (getSignSecondModifier() > 0) -> (baseValue.positive * abs(getSignSecondModifier()) * .125).toInt()
                (getSignSecondModifier() < 0) -> (baseValue.positive * -abs(getSignSecondModifier()) * .125).toInt()
                else -> 0
            }
            else -> 0
        }
        modPos += when {
            (baseValue.net > 0) -> when {
                (getAspectCelestialFirstModifier() > 0) -> (baseValue.positive * abs(getAspectCelestialFirstModifier()) * .125).toInt()
                (getAspectCelestialFirstModifier() < 0) -> (baseValue.positive * -abs(getAspectCelestialFirstModifier()) * .125).toInt()
                else -> 0
            }
            else -> 0
        }
        modPos += when {
            (baseValue.net > 0) -> when {
                (getAspectCelestialSecondModifier() > 0) -> (baseValue.positive * abs(getAspectCelestialSecondModifier()) * .125).toInt()
                (getAspectCelestialSecondModifier() < 0) -> (baseValue.positive * -abs(getAspectCelestialSecondModifier()) * .125).toInt()
                else -> 0
            }
            else -> 0
        }

        return modPos
    }

    fun getNegativeModValue() : Int {

        var modNeg = 0

        if (analysisState == AnalysisState.CHARACTER_ANALYSIS) return modNeg

        modNeg += when {
            (getAspectModifier() > 0) -> (baseValue.negative * -abs(getAspectModifier()) * .25).toInt()
            (getAspectModifier() < 0) -> (baseValue.negative * abs(getAspectModifier()) * .25).toInt()
            else -> 0
        }

        modNeg += when {
            (baseValue.net < 0) -> when {
                (getSignFirstModifier() > 0) -> (baseValue.negative * -abs(getSignFirstModifier()) * .125).toInt()
                (getSignFirstModifier() < 0) -> (baseValue.negative * abs(getSignFirstModifier()) * .125).toInt()
                else -> 0
            }
            else -> 0
        }
        modNeg += when {
            (baseValue.net < 0) -> when {
                (getSignSecondModifier() > 0) -> (baseValue.negative * -abs(getSignSecondModifier()) * .125).toInt()
                (getSignSecondModifier() < 0) -> (baseValue.negative * abs(getSignSecondModifier()) * .125).toInt()
                else -> 0
            }
            else -> 0
        }
        modNeg += when {
            (baseValue.net < 0) -> when {
                (getAspectCelestialFirstModifier() > 0) -> (baseValue.negative * -abs(getAspectCelestialFirstModifier()) * .125).toInt()
                (getAspectCelestialFirstModifier() < 0) -> (baseValue.negative * abs(getAspectCelestialFirstModifier()) * .125).toInt()
                else -> 0
            }
            else -> 0
        }
        modNeg += when {
            (baseValue.net < 0) -> when {
                (getAspectCelestialSecondModifier() > 0) -> (baseValue.negative * -abs(getAspectCelestialSecondModifier()) * .125).toInt()
                (getAspectCelestialSecondModifier() < 0) -> (baseValue.negative * abs(getAspectCelestialSecondModifier()) * .125).toInt()
                else -> 0
            }
            else -> 0
        }

        return modNeg
    }

    fun getAspectTypeLabel() : Pair<ValueType, String> = Pair(
        when {
            (analysisState != AnalysisState.ROMANTIC_ANALYSIS) ->
                when {
                    (baseValue.net > 0) -> ValueType.POSITIVE
                    (baseValue.net < 0) -> ValueType.NEGATIVE
                    else -> ValueType.NEUTRAL
                }
            else ->
                when { //analysis state
                    (baseValue.net >= 0) && (modValue.net < 0) -> ValueType.REVERSAL_TO_NEG
                    (baseValue.net <= 0) && (modValue.net > 0) -> ValueType.REVERSAL_TO_POS
                    (baseValue.net != 0) && (netValue.net == 0) -> ValueType.REVERSAL_TO_NEUT
                    (netValue.net > 0) -> ValueType.POSITIVE
                    (netValue.net < 0) -> ValueType.NEGATIVE
                    else -> ValueType.NEUTRAL
                }
            }
        , aspectAngle.aspectType.label)

    fun getAspectValueRenderLabel() : Pair<ValueType, String> = getAspectValue().let { Pair(it.first, it.second.toString()) }

    fun getAspectValue() : Pair<ValueType, Int> = Pair(
        when {
            (analysisState != AnalysisState.ROMANTIC_ANALYSIS) ->
                when {
                    (baseValue.net > 0) -> ValueType.POSITIVE
                    (baseValue.net < 0) -> ValueType.NEGATIVE
                    else -> ValueType.NEUTRAL
                }
            else ->
                when { //analysis state
                    (baseValue.net >= 0) && (netValue.net < 0) -> ValueType.REVERSAL_TO_NEG
                    (baseValue.net <= 0) && (netValue.net > 0) -> ValueType.REVERSAL_TO_POS
                    (baseValue.net != 0) && (netValue.net == 0) -> ValueType.REVERSAL_TO_NEUT
                    (netValue.net > 0) -> ValueType.POSITIVE
                    (netValue.net < 0) -> ValueType.NEGATIVE
                    else -> ValueType.NEUTRAL
                }
            }
        , abs(netValue.net))

    fun getLabels() : List<String> = listOf(
        aspectCelestialFirst.label,
        getAspectTypeLabel().second,
        aspectCelestialSecond.label,
        getAspectValueRenderLabel().second,
    )

    fun getAspectValueType() = getAspectTypeLabel().first
    fun getValueValueType() = getAspectValueRenderLabel().first

    fun getAspectCelestial1Label() = getLabels()[0]
    fun getAspectLabel() = getLabels()[1]
    fun getAspectCelestial2Label() = getLabels()[2]
    fun getAspectDelimLabel() = "="
    fun getAspectValueLabel() = getLabels()[3]

    fun getRenderLabel() : String = getLabels().str()

    fun getRenderRomanticModLabel() : Pair<ValueType, String> {
        if (analysisState != AnalysisState.ROMANTIC_ANALYSIS) return Pair(ValueType.NEUTRAL, "      ")

        return when {
            (getAspectModifier() > 0) -> Pair(
                ValueType.POSITIVE, when {
                    (getAspectModifier() == 4) -> ":(+4) "
                    else -> ":(+${getAspectModifier()}) "
                })
            (getAspectModifier() < 0) -> Pair(
                ValueType.NEGATIVE, when {
                    (getAspectModifier() == -4) -> ":(-4) "
                    else -> ":(${getAspectModifier()}) "
                })
            else -> Pair(ValueType.NEUTRAL, " ")
        }
    }

    fun getRenderCharacterModLabel() : String {
        if (analysisState != AnalysisState.CHARACTER_ANALYSIS) return ":(**)"

        return getChartStateTypesLabel(getAspectModifier())
    }

    private fun getAspectBaseValue() : Value {

        if (aspectAngle.aspectType.isNeutral) return Value(0, 0)

        if (aspectCelestialFirst.isExtendedAspect || aspectCelestialSecond.isExtendedAspect) return Value(0, 0)

        val weightFirst = aspectCelestialFirst.weight
        val weightSecond = aspectCelestialSecond.weight
        val weightAspect = aspectAngle.aspectType.getAspectAngleOrb(aspectOverlayState)

        //full weightAspect at orb = 0, down to 0 weightAspect at the cusp of the orb
        val weightOrbAspect = ((60 * weightAspect) - (60 * orb)) / (60 * weightAspect)

        //      debugging with lldb shows rounding error between AstroSWE and SAC -- e.g. aspectWeight for 4.9 in SAC is 4.89999999998 in AstroSWE, leading to rounding diffs
        var aspectValue = ( (weightFirst * weightSecond) / 2 * weightAspect * weightOrbAspect).toInt()

        //  halve for synastry chart
        if (chartState == ChartState.SYNASTRY_CHART) aspectValue /= 2

//        println (" orb)" + orb + ": 1W)" + weightFirst + " 2W)" + weightSecond + " aspectW)" + weightAspect + " orbAspectW)" + weightOrbAspect + " value)" + aspectValue )

        return when {
            //hard aspects to sun / moon midpoint are positive
            ( (aspectCelestialFirst == AspectCelestial.ASPECT_SUN_MOON_MIDPOINT
                    || aspectCelestialSecond == AspectCelestial.ASPECT_SUN_MOON_MIDPOINT) && (
                    (aspectAngle.aspectType == AspectType.CONJUNCTION)
                            || (aspectAngle.aspectType == AspectType.OPPOSITION)
                            || (aspectAngle.aspectType == AspectType.SEMISQUARE)
                            || (aspectAngle.aspectType == AspectType.SQUARE) ) ) -> Value(aspectValue, 0)
            //      https://en.wikipedia.org/wiki/Astrological_aspect#Conjunction
            //      In particular, conjunctions involving the Sun, Venus, and/or Jupiter, in any of the three possible conjunction combinations, are
            //      considered highly favourable, while conjunctions involving the Moon, Mars, and/or Saturn, again in any of the three possible
            //      conjunction combinations, are considered highly unfavourable.
            ( (aspectAngle.aspectType == AspectType.CONJUNCTION) &&
                    (aspectCelestialFirst == AspectCelestial.ASPECT_MOON && (aspectCelestialSecond == AspectCelestial.ASPECT_MARS || aspectCelestialSecond == AspectCelestial.ASPECT_SATURN) )
                    || (aspectCelestialFirst == AspectCelestial.ASPECT_MARS && (aspectCelestialSecond == AspectCelestial.ASPECT_MOON || aspectCelestialSecond == AspectCelestial.ASPECT_SATURN) )
                    || (aspectCelestialFirst == AspectCelestial.ASPECT_SATURN && (aspectCelestialSecond == AspectCelestial.ASPECT_MOON || aspectCelestialSecond == AspectCelestial.ASPECT_MARS) ) ) -> Value(0, -aspectValue)
            (aspectAngle.aspectType.isPositive) -> Value(aspectValue, 0)
            (aspectAngle.aspectType.isNegative) -> Value(0, -aspectValue)
            else -> Value(0, 0)
        }
    }
    override fun toString() = "Aspect($signFirst $aspectCelestialFirst $signSecond $aspectCelestialSecond $aspectAngle $orb $aspectsState $timeAspectsState $aspectOverlayState $chartState $analysisState) : baseValue:${baseValue} modValue:${modValue} AspectMod:${getAspectModifier()} signFirstMod:${getSignFirstModifier()} signSecondMod:${getSignSecondModifier()} aspectCelFirstMod:${getAspectCelestialFirstModifier()} aspectCelSecondMod:${getAspectCelestialSecondModifier()}"

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
        private const val defaultOrbLimit = 8.0

        fun calcNormAngleDiff(aspectCelestialFirstAngle : Double, aspectCelestialSecondAngle : Double) = (aspectCelestialSecondAngle - aspectCelestialFirstAngle).normalizeDeg()

        fun calcOrb(aspectAngle : AspectAngle, aspectCelestialFirstAngle : Double, aspectCelestialSecondAngle : Double) =
            if (aspectAngle == AspectAngle.ASPECT_ANGLE_NONE) Constants.InvalidOrb
            else abs(aspectAngle.angleDegree - calcNormAngleDiff(aspectCelestialFirstAngle, aspectCelestialSecondAngle) )


        fun getEmptyAspect(firstAspectCelestialOverride : AspectCelestial = AspectCelestial.ASPECT_CELESTIAL_NONE,
                           secondAspectCelestialOverride : AspectCelestial = AspectCelestial.ASPECT_CELESTIAL_NONE) : Aspect {

            return Aspect(
                Sign.SIGN_NONE,
                firstAspectCelestialOverride,
                Sign.SIGN_NONE,
                secondAspectCelestialOverride,
                AspectAngle.ASPECT_ANGLE_NONE,
                InvalidOrb,
                AspectsState.defaultState(),
                TimeAspectsState.defaultState(),
                AspectOverlayState.defaultState(),
                ChartState.defaultState(),
                AnalysisState.defaultState()
            )
        }

        fun calcOrbLimit (aspectOverlayState : AspectOverlayState
                          , aspectCelestialFirst : AspectCelestial
                          , aspectCelestialSecond : AspectCelestial
                          , aspectAngle : AspectAngle
        ) =
            aspectAngle.aspectType.getAspectAngleOrb(aspectOverlayState) * minOf(
                aspectCelestialFirst.aspectOverlayOrbModifier(aspectOverlayState),
                aspectCelestialSecond.aspectOverlayOrbModifier(aspectOverlayState)
            )

        fun findAspectAngle(aspectCelestialFirst : AspectCelestial
                            , aspectCelestialFirstAngle : Double
                            , aspectCelestialSecond : AspectCelestial
                            , aspectCelestialSecondAngle : Double
                            , aspectsState : AspectsState
                            , timeAspectsState: TimeAspectsState
                            , aspectOverlayState : AspectOverlayState
        ) : AspectAngle {

//            println("$aspectCelestialFirst $aspectCelestialFirstAngle $aspectCelestialSecond $aspectCelestialSecondAngle $aspectsState $timeAspectsState $aspectOverlayState")
            var returnAspectAngle = AspectAngle.ASPECT_ANGLE_NONE
            var minOrb = 360.0

            val maxOrbLimit = AspectAngle.CONJUNCTION_0.aspectType.getAspectAngleOrb(aspectOverlayState)

            val aspectCelestialAngleDiff = calcNormAngleDiff(aspectCelestialFirstAngle, aspectCelestialSecondAngle)
//             println("findAspectAngle() aspectCelestialAngleDiff: $aspectCelestialAngleDiff")
//             println(AspectAngle.values().filter { it.isAspectAngle() }.sortedByDescending { it.getAngleDegree() })

            //iterate through aspectAngles
            for (aspectAngle in AspectAngle.entries.filter { it.isAspectAngle() }.sortedByDescending { it.angleDegree } ) {
                if ((aspectsState == AspectsState.MAJOR_ASPECTS) && (!aspectAngle.aspectType.isMajor )) continue ;
                if ((aspectsState == AspectsState.MINOR_ASPECTS) && (!aspectAngle.aspectType.isMajor && !aspectAngle.aspectType.isMinor) ) continue ;
                if ((timeAspectsState == TimeAspectsState.TIME_ASPECTS_DISABLED) && (aspectCelestialFirst.isTimeAspect)) continue ;
                if ((timeAspectsState == TimeAspectsState.TIME_ASPECTS_DISABLED) && (aspectCelestialSecond.isTimeAspect)) continue ;

                val orbLimit = calcOrbLimit(aspectOverlayState, aspectCelestialFirst, aspectCelestialSecond, aspectAngle)

//                println("findAspectAngle() iteration: $aspectAngle, ${abs(aspectAngle.getAngleDegree() - aspectCelestialAngleDiff)}, ${orbLimit}")

                if (abs(aspectAngle.angleDegree - aspectCelestialAngleDiff) <= orbLimit) {
                    val aspectAngleOrb = calcOrb(aspectAngle, aspectCelestialFirstAngle, aspectCelestialSecondAngle)

//                     println("$aspectAngleOrb, $minOrb")

                    if (aspectAngleOrb < minOrb) {
                        returnAspectAngle = aspectAngle
                        minOrb = aspectAngleOrb
                    }
                }
            }

//             println("selected aspectAngle:$returnAspectAngle, orb: $minOrb")
            return returnAspectAngle
        }
    }
}