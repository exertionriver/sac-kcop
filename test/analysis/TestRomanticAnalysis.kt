package analysis

import river.exertion.sac.astro.Aspect.Companion.valueAspectReduceBase
import river.exertion.sac.astro.Aspect.Companion.valueAspectReduceBaseModNet
import org.junit.jupiter.api.Test
import river.exertion.sac.astro.Chart
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.*

@ExperimentalUnsignedTypes
class TestRomanticAnalysis {

    val timeAspectsState = TimeAspectsState.TIME_ASPECTS_ENABLED

    @Test
    fun testCompareNatals() {

        val refNatalChart =
            Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT, AnalysisState.ROMANTIC_ANALYSIS)

        val synNatalChart =
            Chart(SACComponent.synNatCelestialSnapshot, SACComponent.synNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT, AnalysisState.ROMANTIC_ANALYSIS)

        val natal1Aspects = refNatalChart.getAspects()
        val natal2Aspects = synNatalChart.getAspects()

//        println("refNatal valueAspects:")
//        natal1Aspects.filter { it.modValue.getNet() != 0 }.forEach { println(it) }
        println("refNatal base: ${natal1Aspects.valueAspectReduceBase().getLabel()}")
        println("refNatal romantic: ${natal1Aspects.valueAspectReduceBaseModNet().getLabel()}")

//        println("synNatal valueAspects:")
//        natal2Aspects.filter { it.modValue.getNet() != 0 }.forEach { println(it) }
        println("synNatal base: ${natal2Aspects.valueAspectReduceBase().getLabel()}")
        println("synNatal romantic: ${natal2Aspects.valueAspectReduceBaseModNet().getLabel()}")
    }

    @Test
    fun testCompareSynastry() {
        val synChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.synNatCelestialSnapshot, ChartState.SYNASTRY_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT, AnalysisState.ROMANTIC_ANALYSIS)

        val synAspects = synChart.getAspects()
        println("synChart base: ${synAspects.valueAspectReduceBase().getLabel()}")
        println("synChart romantic: ${synAspects.valueAspectReduceBaseModNet().getLabel()}")

    }

    @Test
    fun testCompareComposite() {
        val compChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.synNatCelestialSnapshot, ChartState.COMPOSITE_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT, AnalysisState.ROMANTIC_ANALYSIS)

        val compAspects = compChart.getAspects()
        println("compChart base: ${compAspects.valueAspectReduceBase().getLabel()}")
        println("compChart romantic: ${compAspects.valueAspectReduceBaseModNet().getLabel()}")
    }
}