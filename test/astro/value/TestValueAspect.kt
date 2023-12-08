package astro.value

import org.junit.jupiter.api.Test
import river.exertion.sac.astro.base.Sign
import river.exertion.sac.astro.state.*
import river.exertion.sac.astro.value.ValueAspect
import river.exertion.sac.astro.value.ValueChart
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.*

object TestValueAspect {

    @Test
    fun testValueAspect() {

        val refCelestialAspectMap = SACComponent.sacCelestialSnapshot.getAspectCelestialLongitudeMap()

        refCelestialAspectMap.entries.forEach {
            println("ac:${it.key}, long:${it.value}")
        }

        val valueAspect = ValueAspect(
            StateAspect(
                Sign.getSignFromCelestialLongitude(refCelestialAspectMap.entries.first().value)
            , refCelestialAspectMap.entries.first().key
            , refCelestialAspectMap.entries.first().value
            , Sign.getSignFromCelestialLongitude(refCelestialAspectMap.entries.last().value)
            , refCelestialAspectMap.entries.last().key
            , refCelestialAspectMap.entries.last().value
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)
        )

        println(valueAspect)
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testStateChartValueAspects() {

        val refCelestialAspectMap = SACComponent.sacCelestialSnapshot.getAspectCelestialLongitudeMap()

        refCelestialAspectMap.entries.forEach {
            println("ac:${it.key}, long:${it.value}")
        }

        val valueAspects = ValueChart(
            StateChart.getAspects(SACComponent.sacCelestialSnapshot, SACComponent.sacCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT), ChartState.NATAL_CHART).getValueAspects()

        println("aspects for ${SACComponent.sacCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }
    }

    @Test
    fun testValueAspectsAnalysis() {

        val refCelestialAspectMap = SACComponent.sacCelestialSnapshot.getAspectCelestialLongitudeMap()

        refCelestialAspectMap.entries.forEach {
            println("ac:${it.key}, long:${it.value}")
        }

        val stateAspects = StateChart.getAspects(SACComponent.sacCelestialSnapshot, SACComponent.sacCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        var valueAspects = ValueChart(stateAspects, ChartState.NATAL_CHART, AnalysisState.NO_ANALYSIS).getValueAspects()

        println("NO_ANALYSIS aspects for ${SACComponent.sacCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = ValueChart(stateAspects, ChartState.NATAL_CHART, AnalysisState.CHARACTER_ANALYSIS).getValueAspects()

        println("CHARACTER_ANALYSIS aspects for ${SACComponent.sacCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = ValueChart(stateAspects, ChartState.NATAL_CHART, AnalysisState.ROMANTIC_ANALYSIS).getValueAspects()

        println("ROMANTIC_ANALYSIS aspects for ${SACComponent.sacCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = ValueChart(stateAspects, ChartState.NATAL_CHART, AnalysisState.PLANET_ANALYSIS).getValueAspects()

        println("PLANET_ANALYSIS aspects for ${SACComponent.sacCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = ValueChart(stateAspects, ChartState.NATAL_CHART, AnalysisState.ELEMENT_ANALYSIS).getValueAspects()

        println("ELEMENT_ANALYSIS aspects for ${SACComponent.sacCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }

        valueAspects = ValueChart(stateAspects, ChartState.NATAL_CHART, AnalysisState.MODE_ANALYSIS).getValueAspects()

        println("MODE_ANALYSIS aspects for ${SACComponent.sacCelestialSnapshot.refEarthLocation.tag}")
        valueAspects.forEach { println(it) }
    }
}