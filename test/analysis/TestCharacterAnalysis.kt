package analysis

import org.junit.jupiter.api.Test
import river.exertion.sac.astro.Aspect
import river.exertion.sac.astro.Chart
import river.exertion.sac.astro.Value.Companion.sum
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.AspectOverlayState
import river.exertion.sac.console.state.AspectsState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.TimeAspectsState

@ExperimentalUnsignedTypes
class TestCharacterAnalysis {

    val timeAspectsState = TimeAspectsState.TIME_ASPECTS_DISABLED

    //TODO: pick two celestial snapshots to use for longitude checks

    @Test
    fun testCompareNatals() {
        val refNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val synNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val refNatalAspects = refNatalChart.getAspects().map { it.celestialAspect() }
        val synNatalAspects = synNatalChart.getAspects().map { it.celestialAspect() }

        val sharedNatalAspects = synNatalAspects.filter { refNatalAspects.contains(it) }.toMutableList()
        sharedNatalAspects.addAll(refNatalAspects.filter { synNatalAspects.contains(it) })

        println("natal1 shared with natal2")
        sharedNatalAspects.forEach { println(it.toString()) }

        println("appreciation: ${sharedNatalAspects.map { it.netValue }.sum().getLabel()}")
    }

    @Test
    fun testCompareSynastry() {
        val synChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.SYNASTRY_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT)

        val refNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val synNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val synAspects = synChart.getAspects().map { it.celestialAspect() }
        val refNatalAspects = refNatalChart.getAspects().map { it.celestialAspect() }
        val synNatalAspects = synNatalChart.getAspects().map { it.celestialAspect() }

        val sharedNatal1Aspects = refNatalChart.getStateAspects().filter { synAspects.contains(it.getStateBaseAspect()) }.toMutableList()
        sharedNatal1Aspects.addAll(synChart.getStateAspects().filter { refNatalAspects.contains(it.getStateBaseAspect()) })

        val sharedNatal2Aspects = synNatalChart.getStateAspects().filter { synAspects.contains(it.getStateBaseAspect()) }.toMutableList()
        sharedNatal2Aspects.addAll(synChart.getStateAspects().filter { synNatalAspects.contains(it.getStateBaseAspect()) })

        println("natal1 shared with synastry chart")
        sharedNatal1Aspects.forEach { println(RenderAspect(Aspect(it)).getRenderLabel()) }

        println("natal2 shared with synastry chart")
        sharedNatal2Aspects.forEach { println(RenderAspect(Aspect(it)).getRenderLabel()) }

        println("affinity: ${sharedNatal1Aspects.plus(sharedNatal2Aspects).stateAspectReduceBase().getLabel()}")

    }

    @Test
    fun testCompareComposite() {
        val compChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.COMPOSITE_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val refNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val synNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val compAspects = compChart.getStateAspects().stateBaseAspects()
        val refNatalAspects = refNatalChart.getStateAspects().stateBaseAspects()
        val synNatalAspects = synNatalChart.getStateAspects().stateBaseAspects()

        val sharedNatal1Aspects = refNatalChart.getStateAspects().filter { compAspects.contains(it.getStateBaseAspect()) }.toMutableList()
        sharedNatal1Aspects.addAll(compChart.getStateAspects().filter { refNatalAspects.contains(it.getStateBaseAspect()) })

        val sharedNatal2Aspects = synNatalChart.getStateAspects().filter { compAspects.contains(it.getStateBaseAspect()) }.toMutableList()
        sharedNatal2Aspects.addAll(compChart.getStateAspects().filter { synNatalAspects.contains(it.getStateBaseAspect()) })

        println("natal1 shared with composite chart")
        sharedNatal1Aspects.forEach { println(RenderAspect(Aspect(it)).getRenderLabel()) }

        println("natal2 shared with composite chart")
        sharedNatal2Aspects.forEach { println(RenderAspect(Aspect(it)).getRenderLabel()) }

        println("commonality: ${sharedNatal1Aspects.plus(sharedNatal2Aspects).stateAspectReduceBase().getLabel()}")
    }

    @Test
    fun testCompareSynastryComposite() {
        val synChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.SYNASTRY_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT)

        val compChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.COMPOSITE_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val compAspects = compChart.getStateAspects().stateBaseAspects()
        val synAspects = synChart.getStateAspects().stateBaseAspects()

        val sharedAspects = synChart.getStateAspects().filter { compAspects.contains(it.getStateBaseAspect()) }.toMutableList()
        sharedAspects.addAll(compChart.getStateAspects().filter { synAspects.contains(it.getStateBaseAspect()) })

        println("shared between synastry and composite chart")
        sharedAspects.forEach { println(RenderAspect(Aspect(it)).getRenderLabel()) }

        println("compatibility: ${sharedAspects.stateAspectReduceBase().getLabel()}")

    }

    @Test
    fun testCompareSynastryValueChart() {
        val refNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val synNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val synChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.SYNASTRY_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT)

        val compChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.COMPOSITE_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

//        val valueChart = Chart(synChart.chartState, synChart, compChart, refNatalChart, synNatalChart)

        println("character analysis synastry value chart")
  //      valueChart.getValueAspects().forEach { println(RenderAspect(it).getRenderLabel() + ":" + RenderAspect(it).getRenderCharacterModLabel()) }
    }

    @Test
    fun testCompareCompositeValueChart() {
        val refNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val synNatalChart = Chart(SACComponent.refNatCelestialSnapshot, ChartState.NATAL_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        val synChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.SYNASTRY_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT)

        val compChart = Chart(SACComponent.refNatCelestialSnapshot, SACComponent.refNatCelestialSnapshot, ChartState.COMPOSITE_CHART,
            AspectsState.ALL_ASPECTS, timeAspectsState, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

//        val valueChart = ValueChart(compChart.chartState, synChart, compChart, refNatalChart, synNatalChart)

        println("character analysis composite value chart")
  //      valueChart.getValueAspects().forEach { println(RenderAspect(it).getRenderLabel() + ":" + RenderAspect(it).getRenderCharacterModLabel()) }
    }

}