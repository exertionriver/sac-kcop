package astro.value

import org.junit.jupiter.api.Test
import river.exertion.sac.astro.Sign
import river.exertion.sac.astro.state.*
import river.exertion.sac.astro.value.ValueAspect
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.*

object TestValueAspect {

    @Test
    fun testValueAspect() {

        val refCelestialAspectMap = SACComponent.refNatCelestialSnapshot.getAspectCelestialLongitudeMap()

        refCelestialAspectMap.entries.forEach {
            println("ac:${it.key}, long:${it.value}")
        }

        val valueAspect = ValueAspect(
            StateAspect(
                Sign.signFromCelestialLongitude(refCelestialAspectMap.entries.first().value)
            , refCelestialAspectMap.entries.first().key
            , refCelestialAspectMap.entries.first().value
            , Sign.signFromCelestialLongitude(refCelestialAspectMap.entries.last().value)
            , refCelestialAspectMap.entries.last().key
            , refCelestialAspectMap.entries.last().value
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)
        )

        println(valueAspect)
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testStateChartValueAspects() {

        val refCelestialAspectMap = SACComponent.refNatCelestialSnapshot.getAspectCelestialLongitudeMap()

        refCelestialAspectMap.entries.forEach {
            println("ac:${it.key}, long:${it.value}")
        }

        val valueAspects =
            Chart.getAspects(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        println("aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }
    }

    @Test
    fun testValueAspectsAnalysis() {

        val refCelestialAspectMap = SACComponent.refNatCelestialSnapshot.getAspectCelestialLongitudeMap()

        refCelestialAspectMap.entries.forEach {
            println("ac:${it.key}, long:${it.value}")
        }

        val aspects = Chart.getAspects(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        var valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.NO_ANALYSIS).getValueAspects()

        println("NO_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.CHARACTER_ANALYSIS).getValueAspects()

        println("CHARACTER_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.ROMANTIC_ANALYSIS).getValueAspects()

        println("ROMANTIC_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.PLANET_ANALYSIS).getValueAspects()

        println("PLANET_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.ELEMENT_ANALYSIS).getValueAspects()

        println("ELEMENT_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = Chart(aspects, ChartState.NATAL_CHART, AnalysisState.MODE_ANALYSIS).getValueAspects()

        println("MODE_ANALYSIS aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }
    }
}