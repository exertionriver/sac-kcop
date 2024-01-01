package astro.render

import river.exertion.sac.astro.base.Sign
import river.exertion.sac.astro.render.RenderAspect
import river.exertion.sac.astro.state.*
import river.exertion.sac.astro.Aspect
import org.junit.jupiter.api.Test
import river.exertion.sac.astro.Chart
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.*

@OptIn(ExperimentalUnsignedTypes::class)
class TestRenderAspect {

    @Test
    fun testRenderAspect() {

        val refCelestialAspectMap = SACComponent.refNatCelestialSnapshot.getAspectCelestialLongitudeMap()

        refCelestialAspectMap.entries.forEach {
            println("ac:${it.key}, long:${it.value}")
        }

        val renderAspect = RenderAspect(
            Aspect(
                StateAspect(
                    Sign.signFromCelestialLongitude(refCelestialAspectMap.entries.first().value)
                , refCelestialAspectMap.entries.first().key
                , refCelestialAspectMap.entries.first().value
                , Sign.signFromCelestialLongitude(refCelestialAspectMap.entries.last().value)
                , refCelestialAspectMap.entries.last().key
                , refCelestialAspectMap.entries.last().value
                , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)
            )
        )
        println(renderAspect.getRenderLabel())
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testValueChartRenderAspects() {

        val refCelestialAspectMap = SACComponent.refNatCelestialSnapshot.getAspectCelestialLongitudeMap()

        refCelestialAspectMap.entries.forEach {
            println("ac:${it.key}, long:${it.value}")
        }

        val aspects =
            Chart.getAspects(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        println("aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        aspects.forEach { println(RenderAspect(Aspect(it)).getRenderLabel() ) }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testRenderAspectsAnalysis() {

        val refCelestialAspectMap = SACComponent.refNatCelestialSnapshot.getAspectCelestialLongitudeMap()

        refCelestialAspectMap.entries.forEach {
            println("ac:${it.key}, long:${it.value}")
        }

        val aspects = Chart.getAspects(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        var valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.NO_ANALYSIS).getAspects()

        println("NO_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.CHARACTER_ANALYSIS).getAspects()

        println("CHARACTER_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.ROMANTIC_ANALYSIS).getAspects()

        println("ROMANTIC_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.PLANET_ANALYSIS).getAspects()

        println("PLANET_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.ELEMENT_ANALYSIS).getAspects()

        println("ELEMENT_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.MODE_ANALYSIS).getAspects()

        println("MODE_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }
    }
}