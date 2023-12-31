package river.exertion.sac.astro.state

import river.exertion.sac.astro.base.Aspect
import river.exertion.sac.astro.AspectAngle
import river.exertion.sac.astro.AspectCelestial
import river.exertion.sac.astro.Sign
import river.exertion.sac.astro.value.Value
import river.exertion.sac.astro.value.ValueAspect
import river.exertion.sac.console.state.*
import kotlin.math.abs

data class StateAspect(val signFirst : Sign
    , val aspectCelestialFirst : AspectCelestial
    , val signSecond : Sign
    , val aspectCelestialSecond: AspectCelestial
    , val aspectAngle : AspectAngle
    , val orb : Double = 0.0
    , val aspectsState : AspectsState
    , val timeAspectsState: TimeAspectsState
    , val aspectOverlayState : AspectOverlayState
    ) {

    fun getStateBaseAspect() = StateBaseAspect(this.aspectCelestialFirst, this.aspectCelestialSecond, this.aspectAngle.aspectType)

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
    ) : this (
        signFirst
        , aspectCelestialFirst
        , signSecond
        , aspectCelestialSecond
        , aspectAngle
        , Aspect.calcOrb(aspectAngle, aspectCelestialFirstAngle, aspectCelestialSecondAngle)
        , aspectsState
        , timeAspectsState
        , aspectOverlayState
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
    )

    override fun toString() = "StateAspect($signFirst $aspectCelestialFirst $signSecond $aspectCelestialSecond $aspectAngle $orb $aspectsState $timeAspectsState $aspectOverlayState)"

    companion object{

        fun getEmptyAspect(firstAspectCelestialOverride : AspectCelestial = AspectCelestial.ASPECT_CELESTIAL_NONE,
                           secondAspectCelestialOverride : AspectCelestial = AspectCelestial.ASPECT_CELESTIAL_NONE) : StateAspect {

            val baseEmptyAspect = Aspect.getEmptyAspect(firstAspectCelestialOverride, secondAspectCelestialOverride)

            return StateAspect(
                baseEmptyAspect.signFirst,
                baseEmptyAspect.aspectCelestialFirst,
                baseEmptyAspect.signSecond,
                baseEmptyAspect.aspectCelestialSecond,
                baseEmptyAspect.aspectAngle,
                baseEmptyAspect.orb,
                AspectsState.defaultState(),
                TimeAspectsState.defaultState(),
                AspectOverlayState.defaultState()
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

            val aspectCelestialAngleDiff = Aspect.calcNormAngleDiff(aspectCelestialFirstAngle, aspectCelestialSecondAngle)
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
                    val aspectAngleOrb = Aspect.calcOrb(aspectAngle, aspectCelestialFirstAngle, aspectCelestialSecondAngle)

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

        fun List<StateAspect>.stateAspectReduceBase(chartState: ChartState = ChartState.NATAL_CHART, analysisState: AnalysisState = AnalysisState.NO_ANALYSIS) : Value {
            val pos = this.sumOf { ValueAspect(it, chartState, analysisState).getPositiveBaseValue() }
            val neg = this.sumOf { ValueAspect(it, chartState, analysisState).getNegativeBaseValue() }

            return Value(pos, neg)
        }

        fun List<StateAspect>.stateAspectReduceMod(chartState: ChartState = ChartState.NATAL_CHART, analysisState: AnalysisState = AnalysisState.NO_ANALYSIS) : Value {
            val pos = this.sumOf { ValueAspect(it, chartState, analysisState).getPositiveModValue() }
            val neg = this.sumOf { ValueAspect(it, chartState, analysisState).getNegativeModValue() }

            return Value(pos, neg)
        }
    }
}