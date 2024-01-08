package river.exertion.sac.console.render.summary

import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutCell
import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutHandler
import river.exertion.kcop.view.layout.displayViewLayout.DVTable
import river.exertion.kcop.view.layout.displayViewLayout.DVTextPane
import river.exertion.sac.astro.Value
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.ChartState.Companion.impConAmChartLabel
import river.exertion.sac.view.SACLayoutHandler
import river.exertion.sac.view.SACLayoutHandler.fontColor

object RenderSummaryConAnSumRefImp : IConsoleRender {

    override val layoutTag = "summaryConAnSumRefImp"

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, colspan = "5", panes = mutableListOf(
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
        val chartValue = SACComponent.sacChart.netValue()
        val natalChartValue = SACComponent.refNatChart.netValue()
        val layoutPanes = setLayout().panes

        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[0].tag!!, ChartState.getChartSumLabel().impConAmChartLabel(), SACLayoutHandler.refEarthLocationFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[1].tag!!, Value.getPosChartImpLabel(chartValue, natalChartValue).second, fontColor(
            Value.getPosChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[2].tag!!, Value.labelDivider, SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[3].tag!!, Value.getNegChartImpLabel(chartValue, natalChartValue).second, fontColor(
            Value.getNegChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[4].tag!!, Value.getConsChartImpLabel(chartValue, natalChartValue).second, fontColor(
            Value.getConsChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[5].tag!!, Value.labelDivider, SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[6].tag!!, Value.getDissChartImpLabel(chartValue, natalChartValue).second, fontColor(
            Value.getDissChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[7].tag!!, Value.getStimChartImpLabel(chartValue, natalChartValue).second, fontColor(
            Value.getStimChartImpLabel(chartValue, natalChartValue).first))
    }
}