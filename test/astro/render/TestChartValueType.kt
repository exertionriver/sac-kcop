package astro.render

import org.junit.jupiter.api.Test
import river.exertion.sac.astro.render.RenderAspect

object TestChartValueType {

    @Test
    fun testRenderChartStateTypeDecoding() {
        val firstDecoding = RenderAspect.getChartStateTypesLabel(6)
        println ("firstDecodedChartLabel: $firstDecoding")

        val firstDecodingExclusive = RenderAspect.getChartStateTypesLabel(6)
        println ("firstDecodedExclusiveChartLabel: $firstDecodingExclusive")

        val secondDecoding = RenderAspect.getChartStateTypesLabel(9)
        println ("secondDecodedChartLabel: $secondDecoding")

        val thirdDecoding = RenderAspect.getChartStateTypesLabel(15)
        println ("thirdDecodedChartLabel: $thirdDecoding")

        val thirdDecodingSwitchChart = RenderAspect.getChartStateTypesLabel(15)
        println ("thirdDecodedSwitchChartLabel: $thirdDecodingSwitchChart")
    }

}