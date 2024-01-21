package astro

import org.junit.jupiter.api.Test
import river.exertion.sac.astro.Aspect
import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.base.AspectType
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.console.state.AspectOverlayState
import river.exertion.sac.console.state.AspectsState
import river.exertion.sac.console.state.ChartState
import kotlin.test.assertEquals

class TestAspect {

    @Test
    fun testCalcOrb() {

        var testAspect = Aspect(AspectCelestial.ASPECT_SUN, 65.0, AspectCelestial.ASPECT_MOON, 118.0
            , AspectsState.defaultState, AspectOverlayState.defaultState, ChartState.defaultState, AnalysisState.defaultState )

        println("testAspect: $testAspect")
        assertEquals(7.0, testAspect.orb)
        assertEquals(AspectType.SEXTILE, testAspect.aspectType)
    }
/*
    @Test
    fun testFindAspectAngle() {

        var testCalc = Aspect.findAspectAngle(AspectCelestial.ASPECT_SUN, 120.0, AspectCelestial.ASPECT_MOON, 90.0
            , AspectsState.defaultState, TimeAspectsState.defaultState, AspectOverlayState.defaultState)

        println("findAspectAngle (orb ok, desc): $testCalc")
        assertEquals(AspectAngle.SEMISEXTILE_330, testCalc)

        testCalc = Aspect.findAspectAngle(90.0, 120.0, 31.0)

        println("findAspectAngle (orb ok, asc): $testCalc")
        assertEquals(AspectAngle.SEMISEXTILE_30, testCalc)

        testCalc = Aspect.findAspectAngle(120.0, 144.0, 5.0)

        println("findAspectAngle (orb excl): $testCalc")
        assertEquals(AspectAngle.ASPECT_ANGLE_NONE, testCalc)
    }

    @Test
    fun testConstructor() {

        var testBaseAspect = Aspect(emptyAspect.signFirst
            , AspectCelestial.ASPECT_SUN
            , emptyAspect.signFirst
            , AspectCelestial.ASPECT_MOON
            , AspectAngle.OPPOSITION_180
            ,5.5)

        println("test base aspect setting orb : $testBaseAspect")
        assertEquals(185.5,testBaseAspect.aspectAngle.angleDegree + testBaseAspect.orb)

        testBaseAspect = Aspect(emptyAspect.signFirst
            , AspectCelestial.ASPECT_SUN
            , 90.0
            , emptyAspect.signFirst
            , AspectCelestial.ASPECT_MOON
            , 90.0 - 5.5)

        println("test base aspect calc orb (asc): $testBaseAspect")
        assertEquals(AspectAngle.CONJUNCTION_360, testBaseAspect.aspectAngle)
        assertEquals(5.5, testBaseAspect.orb)

        testBaseAspect = Aspect(emptyAspect.signFirst
            , AspectCelestial.ASPECT_SUN
            , 270.0
            , emptyAspect.signFirst
            , AspectCelestial.ASPECT_MOON
            , 270.0 + 4.5)

        println("test base aspect calc orb (desc): $testBaseAspect")
        assertEquals(AspectAngle.CONJUNCTION_0, testBaseAspect.aspectAngle)
        assertEquals(4.5, testBaseAspect.orb)

        testBaseAspect = Aspect(emptyAspect.signFirst
            , AspectCelestial.ASPECT_SUN
            , 120.0
            , emptyAspect.signFirst
            , AspectCelestial.ASPECT_MOON
            , 120.0 + 6.5
            , 3.5)

        println("test base aspect calc orb limit: $testBaseAspect")
        assertEquals(AspectAngle.ASPECT_ANGLE_NONE, testBaseAspect.aspectAngle)
        assertEquals(InvalidOrb, testBaseAspect.orb)
    }

*/
}