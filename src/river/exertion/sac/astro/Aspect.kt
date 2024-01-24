package river.exertion.sac.astro

import river.exertion.sac.astro.AspectValue.getAspectBaseValue
import river.exertion.sac.astro.AspectValue.getAspectModValue
import river.exertion.sac.astro.AspectValue.getAspectNetValue
import river.exertion.sac.astro.base.*
import river.exertion.sac.console.state.*
import river.exertion.sac.view.SACInputProcessor
import kotlin.math.abs
import kotlin.math.min

data class Aspect (val aspectCelestialFirst : AspectCelestial
                    , val aspectCelestialFirstLong : Double
                    , val aspectCelestialSecond: AspectCelestial
                    , val aspectCelestialSecondLong : Double
                    , val aspectsState : AspectsState
                    , val aspectOverlayState : AspectOverlayState
                    , val chartState : ChartState
                    , val analysisState: AnalysisState
) {

    //copy constructor
    constructor(aspect : Aspect) : this (
        aspectCelestialFirst = aspect.aspectCelestialFirst
        , aspectCelestialFirstLong = aspect.aspectCelestialFirstLong
        , aspectCelestialSecond = aspect.aspectCelestialSecond
        , aspectCelestialSecondLong = aspect.aspectCelestialSecondLong
        , aspectsState = aspect.aspectsState
        , aspectOverlayState = aspect.aspectOverlayState
        , chartState = aspect.chartState
        , analysisState = aspect.analysisState
    )

    val signFirst = Sign.signFromCelestialLongitude(aspectCelestialFirstLong)
    val signSecond = Sign.signFromCelestialLongitude(aspectCelestialSecondLong)

    var aspectType = AspectType.ASPECT_NONE //needs to precede calcOrb()
    fun celestialAspect() = CelestialAspect(aspectCelestialFirst, aspectCelestialSecond, aspectType)
    val orb = this.calcOrb()

    val baseValue = this.getAspectBaseValue()
    val modValue = this.getAspectModValue()
    val netValue = this.getAspectNetValue()

    var aspectValueType : AspectValueType = AspectValueType.VALUE_TYPE_NONE

    override fun toString() = "Aspect($signFirst $aspectCelestialFirst $aspectType $orb $signSecond $aspectCelestialSecond $aspectsState $aspectOverlayState $chartState $analysisState) : baseValue:${baseValue} modValue:${modValue} netValue:${netValue}}"

    companion object {

        fun List<Aspect>.aspectFromCelestialAspect(celestialAspect: CelestialAspect) = this.firstOrNull { it.celestialAspect() == celestialAspect }

        fun Aspect.calcOrb() : Double {

            val aspectTypes = AspectType.filterState(this.aspectsState).sortedByDescending { it.angleDegree }
            var checkAspectTypeIdx = 0

            val aspectCelestialFirstOrbModifier = this.aspectCelestialFirst.aspectOverlayOrbModifier(this.aspectOverlayState)
            val aspectCelestialSecondOrbModifier = this.aspectCelestialSecond.aspectOverlayOrbModifier(this.aspectOverlayState)

            val aspectCelestialDiff = abs(aspectCelestialFirstLong - aspectCelestialSecondLong)

            while (checkAspectTypeIdx < aspectTypes.size) {
                val aspectCelestialTypeDiff = aspectCelestialDiff / 360.0
                val aspectTypeAngle = aspectTypes[checkAspectTypeIdx].angleDegree / 360.0

                val aspectCelestialFirstBound = (aspectTypes[checkAspectTypeIdx].getAspectAngleOrb(aspectOverlayState) * aspectCelestialFirstOrbModifier) / 360.0
                val aspectCelestialSecondBound = (aspectTypes[checkAspectTypeIdx].getAspectAngleOrb(aspectOverlayState) * aspectCelestialSecondOrbModifier) / 360.0

                val orbLimitMax = aspectCelestialTypeDiff + min(aspectCelestialFirstBound, aspectCelestialSecondBound)
                val orbLimitMin = aspectCelestialTypeDiff - min(aspectCelestialFirstBound, aspectCelestialSecondBound)

                if (aspectTypeAngle in orbLimitMin..orbLimitMax) {
                    this.aspectType = aspectTypes[checkAspectTypeIdx]
                    return abs(aspectTypes[checkAspectTypeIdx].angleDegree - aspectCelestialDiff)
                } else {
                    checkAspectTypeIdx++
                }
            }

            return 0.0
        }
    }
}