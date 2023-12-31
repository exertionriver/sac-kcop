package astro.base

import river.exertion.sac.astro.AspectCelestial
import river.exertion.sac.astro.CelestialSnapshot
import river.exertion.sac.astro.base.Chart
import org.junit.jupiter.api.Test
import river.exertion.sac.component.SACComponent

@OptIn(ExperimentalUnsignedTypes::class)
object TestChart {

    @Test
    fun testChartGetAspectsChart() {

        val aspects = Chart.getAspects(SACComponent.refNatCelestialSnapshot)

        val aspectsChart = Chart.getAspectsChart(aspects)

        println("aspects for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}:")
        aspects.forEach { println(it) }

        println("aspectsChart for ${SACComponent.refNatCelestialSnapshot.refEarthLocation.tag}")
        aspectsChart.forEach { println(it) }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testChart() {

        val natalChart = Chart(SACComponent.refNatCelestialSnapshot)

        println("natal Chart:")
        for (verticalIdx in 0 until AspectCelestial.getChartSize())
            for (horizontalIdx in 0..verticalIdx) {
                println("[$horizontalIdx, $verticalIdx]: ${natalChart.chartRows[horizontalIdx].rowAspects[verticalIdx]}")
            }

        val compChart = Chart(CelestialSnapshot.getCompositeSnapshot(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot) )

        println("comp Chart:")
        for (verticalIdx in 0 until AspectCelestial.getChartSize())
            for (horizontalIdx in 0..verticalIdx) {
                println("[$horizontalIdx, $verticalIdx]: ${compChart.chartRows[horizontalIdx].rowAspects[verticalIdx]}")
            }

        val synChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot)

        println("syn Chart:")
        for (verticalIdx in 0 until AspectCelestial.getChartSize())
            for (horizontalIdx in 0 until AspectCelestial.getChartSize()) {
                println("[$horizontalIdx, $verticalIdx]: ${synChart.chartRows[horizontalIdx].rowAspects[verticalIdx]}")
            }

    }
}