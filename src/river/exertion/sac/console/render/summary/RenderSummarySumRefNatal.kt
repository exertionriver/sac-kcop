package river.exertion.sac.console.render.summary

import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutCell
import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutHandler
import river.exertion.kcop.view.layout.displayViewLayout.DVTable
import river.exertion.kcop.view.layout.displayViewLayout.DVTextPane
import river.exertion.sac.astro.render.RenderChartState
import river.exertion.sac.astro.render.RenderValue
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.view.SACLayoutHandler

object RenderSummarySumRefNatal : IConsoleRender {

    override val layoutTag = "summarySumRefNatal"

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
        val chartValue = RenderValue(SACComponent.refNatValueChart.getBaseValue())
        val layoutPanes = setLayout().panes

        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[0].tag!!, RenderChartState.getChartSumLabel(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[1].tag!!, chartValue.getValuesLabel().first, SACLayoutHandler.positiveFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[2].tag!!, RenderValue.labelDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[3].tag!!, chartValue.getValuesLabel().second, SACLayoutHandler.negativeFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[4].tag!!, chartValue.getPercentLabel().first, SACLayoutHandler.positiveFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[5].tag!!, RenderValue.labelDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[6].tag!!, chartValue.getPercentLabel().second, SACLayoutHandler.negativeFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent(layoutPanes[7].tag!!, chartValue.getStimLabel(), SACLayoutHandler.airFontColor)
    }
}