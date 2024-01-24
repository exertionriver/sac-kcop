package river.exertion.sac.astro

import river.exertion.sac.astro.base.*
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.console.state.AspectsFilterState
import river.exertion.sac.console.state.AspectsSortState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.view.SACInputProcessor
import kotlin.math.abs

object AspectValue {

    fun Aspect.getAspectBaseValue() : Value {

        if (this.aspectType.isNeutral || (this.aspectType == AspectType.ASPECT_NONE) ) {
            return Value(0, 0)
        }

        if (this.aspectCelestialFirst.isExtendedAspect || this.aspectCelestialSecond.isExtendedAspect) {
            return Value(0, 0)
        }

        val weightFirst = this.aspectCelestialFirst.weight
        val weightSecond = this.aspectCelestialSecond.weight
        val weightAspect = this.aspectType.getAspectAngleOrb(this.aspectOverlayState)

        //full weightAspect at orb = 0, down to 0 weightAspect at the cusp of the orb
        val weightOrbAspect = (weightAspect - this.orb) / weightAspect

        var aspectValue = ( (weightFirst * weightSecond) / 2 * weightAspect * weightOrbAspect).toInt()

        //  halve for synastry chart
        if (this.chartState == ChartState.SYNASTRY_CHART) aspectValue /= 2

//        println (" orb)" + orb + ": 1W)" + weightFirst + " 2W)" + weightSecond + " aspectW)" + weightAspect + " orbAspectW)" + weightOrbAspect + " value)" + aspectValue )

        return when {
            //hard aspects to sun / moon midpoint are positive
            ( (this.aspectCelestialFirst == AspectCelestial.ASPECT_SUN_MOON_MIDPOINT
                    || this.aspectCelestialSecond == AspectCelestial.ASPECT_SUN_MOON_MIDPOINT) && (
                    (this.aspectType == AspectType.CONJUNCTION)
                            || (this.aspectType == AspectType.OPPOSITION)
                            || (this.aspectType == AspectType.SEMISQUARE)
                            || (this.aspectType == AspectType.SQUARE) ) ) -> Value(aspectValue, 0)
            //      https://en.wikipedia.org/wiki/Astrological_aspect#Conjunction
            //      In particular, conjunctions involving the Sun, Venus, and/or Jupiter, in any of the three possible conjunction combinations, are
            //      considered highly favourable, while conjunctions involving the Moon, Mars, and/or Saturn, again in any of the three possible
            //      conjunction combinations, are considered highly unfavourable.
            ( (this.aspectType == AspectType.CONJUNCTION) &&
                    (this.aspectCelestialFirst == AspectCelestial.ASPECT_MOON && (this.aspectCelestialSecond == AspectCelestial.ASPECT_MARS || this.aspectCelestialSecond == AspectCelestial.ASPECT_SATURN) )
                    || (this.aspectCelestialFirst == AspectCelestial.ASPECT_MARS && (this.aspectCelestialSecond == AspectCelestial.ASPECT_MOON || this.aspectCelestialSecond == AspectCelestial.ASPECT_SATURN) )
                    || (this.aspectCelestialFirst == AspectCelestial.ASPECT_SATURN && (this.aspectCelestialSecond == AspectCelestial.ASPECT_MOON || this.aspectCelestialSecond == AspectCelestial.ASPECT_MARS) ) ) -> Value(0, -aspectValue)
            (this.aspectType.isPositive) -> Value(aspectValue, 0)
            (this.aspectType.isNegative) -> Value(0, -aspectValue)
            else -> Value(0, 0)
        }
    }

    fun Aspect.getAspectModValue() = if (chartState == ChartState.SYNASTRY_CHART) Value(getPositiveModValue() / 2, getNegativeModValue() / 2) else Value(getPositiveModValue(), getNegativeModValue())

    fun Aspect.getAspectNetValue() = Value(baseValue.positive + modValue.positive, baseValue.negative + modValue.negative)

    fun Aspect.getAspectModifier() = when (analysisState) {
        AnalysisState.ROMANTIC_ANALYSIS -> getRomanticModifier()
        else -> 0
    }

    fun Aspect.getSignFirstModifier() = when (analysisState) {
        AnalysisState.ELEMENT_ANALYSIS -> getElementModifier(signFirst.signElement).toInt()
        AnalysisState.MODE_ANALYSIS -> getModeModifier(signFirst.signMode).toInt()
        else -> 0
    }

    fun Aspect.getSignSecondModifier() = when (analysisState) {
        AnalysisState.ELEMENT_ANALYSIS -> getElementModifier(signSecond.signElement).toInt()
        AnalysisState.MODE_ANALYSIS -> getModeModifier(signSecond.signMode).toInt()
        else -> 0
    }

    fun Aspect.getAspectCelestialFirstModifier() = when (analysisState) {
        AnalysisState.PLANET_ANALYSIS -> getCelestialModifier(aspectCelestialFirst).toInt()
        else -> 0
    }

    fun Aspect.getAspectCelestialSecondModifier() = when (analysisState) {
        AnalysisState.PLANET_ANALYSIS -> getCelestialModifier(aspectCelestialSecond).toInt()
        else -> 0
    }

    fun Aspect.getRomanticModifier() : Int {

        return RomExtAspects.celestialAspectModifiers.firstOrNull {
            (   (   ( (it.aspectCelestialFirst == aspectCelestialFirst) && (it.aspectCelestialSecond == aspectCelestialSecond) ) ||
                    ( (it.aspectCelestialFirst == aspectCelestialSecond) && (it.aspectCelestialSecond == aspectCelestialFirst) ) )
                    && (it.aspectType == aspectType) )}?.modifier ?: 0
    }

    fun Aspect.getElementModifier(signElement : SignElement) : Double {
        val aspectCelestialFirstWeight = aspectCelestialFirst.weight
        val aspectCelestialSecondWeight = aspectCelestialSecond.weight
        val aspectCelestialBothWeight = aspectCelestialFirstWeight + aspectCelestialSecondWeight
        var returnWeight = 0.0

        if (signFirst.signElement == signElement) returnWeight += aspectCelestialFirstWeight
        if (signSecond.signElement == signElement) returnWeight += aspectCelestialSecondWeight

        return returnWeight / aspectCelestialBothWeight
    }

    fun Aspect.getModeModifier(signMode : SignMode) : Double {
        val aspectCelestialFirstWeight = aspectCelestialFirst.weight
        val aspectCelestialSecondWeight = aspectCelestialSecond.weight
        val aspectCelestialBothWeight = aspectCelestialFirstWeight + aspectCelestialSecondWeight
        var returnWeight = 0.0

        if (signFirst.signMode == signMode) returnWeight += aspectCelestialFirstWeight
        if (signSecond.signMode == signMode) returnWeight += aspectCelestialSecondWeight

        return returnWeight / aspectCelestialBothWeight
    }

    fun Aspect.getCelestialModifier(aspectCelestial: AspectCelestial) : Double {
        val aspectCelestialFirstWeight = aspectCelestialFirst.weight
        val aspectCelestialSecondWeight = aspectCelestialSecond.weight
        val aspectCelestialBothWeight = aspectCelestialFirstWeight + aspectCelestialSecondWeight
        var returnWeight = 0.0

        if (aspectCelestialFirst == aspectCelestial) returnWeight += aspectCelestialFirstWeight
        if (aspectCelestialSecond == aspectCelestial) returnWeight += aspectCelestialSecondWeight

        return returnWeight / aspectCelestialBothWeight
    }

    fun Aspect.getPositiveModValue() : Int {

        var modPos = 0

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

    fun Aspect.getNegativeModValue() : Int {

        var modNeg = 0

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

    fun Aspect.getAspectTypeLabel() : Pair<ValueType, String> = Pair(
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
        , aspectType.label)

    fun Aspect.getAspectCelestial1Label() : Pair<AspectValueType, String> = Pair(aspectValueType, aspectCelestialFirst.label)

    fun Aspect.getAspectCelestial2Label() : Pair<AspectValueType, String> = Pair(aspectValueType, aspectCelestialSecond.label)

    fun Aspect.getAspectValue() : Pair<ValueType, Int> = Pair(
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
        , abs(netValue.net)
    )

    fun Aspect.getAspectDelimLabel() = "="

    fun Aspect.getAspectValueRenderLabel() : Pair<ValueType, String> = getAspectValue().let { Pair(it.first, it.second.toString()) }

    fun Aspect.getRenderRomanticModLabel() : Pair<ValueType, String> {
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

    fun Aspect.getRenderCharacterModLabel() : Pair<ValueType, String> {
        if (aspectValueType == AspectValueType.VALUE_TYPE_NONE) return Pair(ValueType.NEUTRAL, ":(***)")

        val fontColor = when {
            (netValue.net > 0) -> ValueType.POSITIVE
            (netValue.net < 0) -> ValueType.NEGATIVE
            else -> ValueType.NEUTRAL
        }

        val label = ":(${aspectValueType.label}) "

        return Pair(fontColor, label)
    }

    fun List<Aspect>.sortFilterValueAspects() : List<Aspect> {

        val returnList = this

        //filter
        val filterList = when {
            SACInputProcessor.aspectsFilterStateMachine.isInState(AspectsFilterState.OVER_TWENTY) -> returnList.filter { it.getAspectValue().second >= 20 }
            SACInputProcessor.aspectsFilterStateMachine.isInState(AspectsFilterState.OVER_FIFTY) -> returnList.filter { it.getAspectValue().second >= 50 }
            else -> returnList
        }

        //then sort
        val sortList = when {
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.ASPECT) -> filterList.sortedBy { it.aspectType }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.SECOND_CELESTIAL) -> filterList.sortedBy { it.aspectCelestialSecond }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.VALUE_MAGNITUDE) -> filterList.sortedByDescending { it.getAspectValue().second }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.VALUE_POS_TO_NEG) -> {
                filterList.filter { it.getAspectValue().first == ValueType.POSITIVE || it.getAspectValue().first == ValueType.REVERSAL_TO_POS }.sortedByDescending { it.getAspectValue().second }.sortedBy { it.getAspectValue().first }.plus(
                    filterList.filter { it.getAspectValue().first == ValueType.NEUTRAL || it.getAspectValue().first == ValueType.REVERSAL_TO_NEUT }.sortedBy { it.aspectType }.sortedBy { it.getAspectValue().first }.plus(
                        filterList.filter { it.getAspectValue().first == ValueType.NEGATIVE || it.getAspectValue().first == ValueType.REVERSAL_TO_NEG }.sortedBy { it.getAspectValue().second }.sortedBy { it.getAspectValue().first }
                    ))
            }
            SACInputProcessor.aspectsSortStateMachine.isInState(AspectsSortState.VALUE_NEG_TO_POS) -> {
                filterList.filter { it.getAspectValue().first == ValueType.NEGATIVE || it.getAspectValue().first == ValueType.REVERSAL_TO_NEG }.sortedByDescending { it.getAspectValue().second }.sortedBy { it.getAspectValue().first }.plus(
                    filterList.filter { it.getAspectValue().first == ValueType.NEUTRAL || it.getAspectValue().first == ValueType.REVERSAL_TO_NEUT }.sortedBy { it.aspectType }.sortedBy { it.getAspectValue().first }.plus(
                        filterList.filter { it.getAspectValue().first == ValueType.POSITIVE || it.getAspectValue().first == ValueType.REVERSAL_TO_POS }.sortedBy { it.getAspectValue().second }.sortedBy { it.getAspectValue().first }
                    ))
            }
            else -> filterList.sortedBy { it.aspectCelestialFirst }
        }

        return sortList
    }
}