package river.exertion.sac.console.render.summary

import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutCell
import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutHandler
import river.exertion.kcop.view.layout.displayViewLayout.DVTable
import river.exertion.kcop.view.layout.displayViewLayout.DVTextPane
import river.exertion.sac.astro.render.RenderChartState
import river.exertion.sac.astro.render.RenderChartState.impChartLabel
import river.exertion.sac.astro.render.RenderValue
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.view.SACLayoutHandler
import river.exertion.sac.view.SACLayoutHandler.fontColor
import kotlin.math.abs

object RenderSummarySumRefImp : IConsoleRender {

    override val layoutTag = "summarySumRefImp"

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
        val chartValue = SACComponent.sacValueChart.getBaseValue()
        val natalChartValue = SACComponent.refNatValueChart.getBaseValue()
        val layoutPanes = setLayout().panes

        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[0].tag!!, RenderChartState.getChartSumLabel().impChartLabel(), SACLayoutHandler.refEarthLocationFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[1].tag!!, RenderChartState.getPosChartImpLabel(chartValue, natalChartValue).second, fontColor(RenderChartState.getPosChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[2].tag!!, RenderValue.labelDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[3].tag!!, RenderChartState.getNegChartImpLabel(chartValue, natalChartValue).second, fontColor(RenderChartState.getNegChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[4].tag!!, RenderChartState.getConsChartImpLabel(chartValue, natalChartValue).second, fontColor(RenderChartState.getConsChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[5].tag!!, RenderValue.labelDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[6].tag!!, RenderChartState.getDissChartImpLabel(chartValue, natalChartValue).second, fontColor(RenderChartState.getDissChartImpLabel(chartValue, natalChartValue).first))
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[7].tag!!, RenderChartState.getStimChartImpLabel(chartValue, natalChartValue).second, fontColor(RenderChartState.getStimChartImpLabel(chartValue, natalChartValue).first))
    }
}