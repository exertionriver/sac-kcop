package astro.state

import org.junit.jupiter.api.Test
import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.Chart
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.AspectOverlayState
import river.exertion.sac.console.state.AspectsState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.TimeAspectsState

object TestChart {

    @Test
    fun testStateChartGetAspectsChart() {

        val stateAspects = Chart.getAspects(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val stateAspectsChart = Chart.getAspectsChart(stateAspects)

        println("stateAspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}:")
        stateAspects.forEach { println(it) }

        println("stateAspectsChart for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        stateAspectsChart.forEach { println(it) }
    }

    @Test
    fun testStateChartGetExtendedAspectsChart() {
        val stateAspects = Chart.getAspects(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val stateExtendedAspects = Chart.getExtendedAspects(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val stateAspectsChart = Chart(stateAspects.plus(stateExtendedAspects))

        println("stateAspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        stateAspectsChart.getStateAspects().forEach { println(it) }
    }

    @Test
    fun testStateChart() {

        val natalChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        println("natal Chart:")
        for (verticalIdx in 0 until AspectCelestial.chartDimensionSize())
            for (horizontalIdx in 0..verticalIdx) {
                println("[$horizontalIdx, $verticalIdx]: ${natalChart.chartRows[horizontalIdx].rowAspects[verticalIdx]}")
            }
        //extended aspects stored in last row
        natalChart.chartRows[AspectCelestial.chartDimensionSize()].rowAspects.forEachIndexed { idx, it -> println( "[$idx, ${AspectCelestial.chartDimensionSize()}]: $it") }

        val compChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.COMPOSITE_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        println("comp Chart:")
        for (verticalIdx in 0 until AspectCelestial.chartDimensionSize())
            for (horizontalIdx in 0..verticalIdx) {
                println("[$horizontalIdx, $verticalIdx]: ${compChart.chartRows[horizontalIdx].rowAspects[verticalIdx]}")
            }
        //extended aspects stored in last row
        compChart.chartRows[AspectCelestial.chartDimensionSize()].rowAspects.forEachIndexed { idx, it -> println( "[$idx, ${AspectCelestial.chartDimensionSize()}]: $it") }

        val synChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.SYNASTRY_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT)

        println("syn Chart:")
        for (verticalIdx in 0 until AspectCelestial.chartDimensionSize())
            for (horizontalIdx in 0 until AspectCelestial.chartDimensionSize()) {
                println("[$horizontalIdx, $verticalIdx]: ${synChart.chartRows[horizontalIdx].rowAspects[verticalIdx]}")
            }
        //extended aspects stored in last row
        synChart.chartRows[AspectCelestial.chartDimensionSize()].rowAspects.forEachIndexed { idx, it -> println( "[$idx, ${AspectCelestial.chartDimensionSize()}]: $it") }
    }
}