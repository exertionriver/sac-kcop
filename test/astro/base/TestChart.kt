package astro.base

import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.base.CelestialSnapshot
import river.exertion.sac.astro.base.Chart
import org.junit.jupiter.api.Test
import river.exertion.sac.component.SACComponent

@OptIn(ExperimentalUnsignedTypes::class)
object TestChart {

    @Test
    fun testChartGetAspectsChart() {

        val aspects = Chart.getAspects(SACComponent.sacCelestialSnapshot)

        val aspectsChart = Chart.getAspectsChart(aspects)

        println("aspects for ${SACComponent.sacCelestialSnapshot.refEarthLocation.tag}:")
        aspects.forEach { println(it) }

        println("aspectsChart for ${SACComponent.sacCelestialSnapshot.refEarthLocation.tag}")
        aspectsChart.forEach { println(it) }
    }

    @OptIn(ExperimentalUnsignedTypes::class)
    @Test
    fun testChart() {

        val natalChart = Chart(SACComponent.sacCelestialSnapshot)

        println("natal Chart:")
        for (verticalIdx in 0 until AspectCelestial.getChartSize())
            for (horizontalIdx in 0..verticalIdx) {
                println("[$horizontalIdx, $verticalIdx]: ${natalChart.chartRows[horizontalIdx].rowAspects[verticalIdx]}")
            }

        val compChart = Chart(CelestialSnapshot.getCompositeSnapshot(SACComponent.sacCelestialSnapshot, SACComponent.sacCelestialSnapshot) )

        println("comp Chart:")
        for (verticalIdx in 0 until AspectCelestial.getChartSize())
            for (horizontalIdx in 0..verticalIdx) {
                println("[$horizontalIdx, $verticalIdx]: ${compChart.chartRows[horizontalIdx].rowAspects[verticalIdx]}")
            }

        val synChart = Chart(SACComponent.sacCelestialSnapshot, SACComponent.sacCelestialSnapshot)

        println("syn Chart:")
        for (verticalIdx in 0 until AspectCelestial.getChartSize())
            for (horizontalIdx in 0 until AspectCelestial.getChartSize()) {
                println("[$horizontalIdx, $verticalIdx]: ${synChart.chartRows[horizontalIdx].rowAspects[verticalIdx]}")
            }

    }
}