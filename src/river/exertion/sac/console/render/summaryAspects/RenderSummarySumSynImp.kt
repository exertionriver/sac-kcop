package river.exertion.sac.console.render.summaryAspects

import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutCell
import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutHandler
import river.exertion.kcop.view.layout.displayViewLayout.DVTable
import river.exertion.kcop.view.layout.displayViewLayout.DVTextPane
import river.exertion.sac.astro.ChartValue
import river.exertion.sac.astro.Value
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.ChartState.Companion.impChartLabel
import river.exertion.sac.view.SACLayoutHandler
import river.exertion.sac.view.SACLayoutHandler.fontColor

object RenderSummarySumSynImp : IConsoleRender {

    override val layoutTag = "summarySumSynImp"

    override fun setLayout() = DVTable(tableTag = layoutTag, colspan = "5", panes = mutableListOf(
        DVTextPane().apply { this.tag = "${layoutTag}_chartLabel"},
        DVTextPane().apply { this.tag = "${layoutTag}_values_pos"},
        DVTextPane().apply { this.tag = "${layoutTag}_values_divider"},
        DVTextPane().apply { this.tag = "${layoutTag}_values_neg"},
        DVTextPane().apply { this.tag = "${layoutTag}_percent_pos"},
        DVTextPane().apply { this.tag = "${layoutTag}_percent_divider"},
        DVTextPane().apply { this.tag = "${layoutTag}_percent_neg"},
        DVTextPane().apply { this.tag = "${layoutTag}_stim"}
    ))

    override fun setContent() {
        val chartValue = SACComponent.sacChart.baseValue
        val natalChartValue = SACComponent.synNatChart.baseValue
        val layoutPanes = setLayout().panes

        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[0].tag!!, ChartState.getChartSumLabel().impChartLabel(), SACLayoutHandler.synEarthLocationFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[1].tag!!, ChartValue.getPosChartImpLabel(chartValue, natalChartValue).second, fontColor(
            ChartValue.getPosChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[2].tag!!, Value.labelDivider, SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[3].tag!!, ChartValue.getNegChartImpLabel(chartValue, natalChartValue).second, fontColor(
            ChartValue.getNegChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[4].tag!!, ChartValue.getConsChartImpLabel(chartValue, natalChartValue).second, fontColor(
            ChartValue.getConsChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[5].tag!!, Value.labelDivider, SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[6].tag!!, ChartValue.getDissChartImpLabel(chartValue, natalChartValue).second, fontColor(
            ChartValue.getDissChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[7].tag!!, ChartValue.getStimChartImpLabel(chartValue, natalChartValue).second, fontColor(
            ChartValue.getStimChartImpLabel(chartValue, natalChartValue).first))
    }
}