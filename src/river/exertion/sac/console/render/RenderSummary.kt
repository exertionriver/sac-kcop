package river.exertion.sac.console.render

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.astro.render.RenderChartState
import river.exertion.sac.astro.render.RenderValue
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderSummary {

    val tableTag = "summary"

    fun dvTable() = DVTable(tableTag = tableTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, colspan = "5", panes = mutableListOf<DVLayoutCell>().apply {
        this.addAll(summarySumLineLayout())
        this.add(DVRow())
        this.addAll(summaryNatal1LineLayout())
        this.add(DVRow())
        this.addAll(summaryNatal2LineLayout())
    })

    fun setContent() {
        summarySumLineSetContent()
        if (!SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF)) {
            summaryNatal1LineSetContent()
            summaryNatal2LineSetContent()
        }
    }

    fun summarySumLineLayout() = mutableListOf(
        DVTextPane().apply { this.tag = "summarySumLine_chartLabel"},
        DVTextPane().apply { this.tag = "summarySumLine_values_pos"},
        DVTextPane().apply { this.tag = "summarySumLine_divider1"},
        DVTextPane().apply { this.tag = "summarySumLine_values_neg"},
        DVTextPane().apply { this.tag = "summarySumLine_percent_pos"},
        DVTextPane().apply { this.tag = "summarySumLine_divider2"},
        DVTextPane().apply { this.tag = "summarySumLine_percent_neg"},
        DVTextPane().apply { this.tag = "summarySumLine_stim"}
    )

    fun summarySumLineSetContent() {
        val summarySumLine = summarySumLine()

        DVLayoutHandler.currentDvLayout.setTextPaneContent("summarySumLine_chartLabel", summarySumLine[0], SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summarySumLine_values_pos", summarySumLine[1], SACLayoutHandler.positiveFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summarySumLine_divider1", RenderValue.valueDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summarySumLine_values_neg", summarySumLine[2], SACLayoutHandler.negativeFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summarySumLine_percent_pos", summarySumLine[3], SACLayoutHandler.positiveFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summarySumLine_divider2", RenderValue.valueDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summarySumLine_percent_neg", summarySumLine[4], SACLayoutHandler.negativeFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summarySumLine_stim", summarySumLine[5], SACLayoutHandler.airFontColor)
    }

    fun summarySumLine() : List<String> {
        val chartValue = RenderValue(SACComponent.sacValueChart.getBaseValue())

        return mutableListOf(
            RenderChartState.getChartSumLabel(),
            chartValue.getValuesLabel().first,
            chartValue.getValuesLabel().second,
            chartValue.getPercentLabel().first,
            chartValue.getPercentLabel().second,
            chartValue.getStimLabel()
        )
    }

    fun summaryNatal1LineLayout() = mutableListOf(
        DVTextPane().apply { this.tag = "summaryNatal1Line_chartLabel"},
        DVTextPane().apply { this.tag = "summaryNatal1Line_values_pos"},
        DVTextPane().apply { this.tag = "summaryNatal1Line_divider1"},
        DVTextPane().apply { this.tag = "summaryNatal1Line_values_neg"},
        DVTextPane().apply { this.tag = "summaryNatal1Line_percent_pos"},
        DVTextPane().apply { this.tag = "summaryNatal1Line_divider2"},
        DVTextPane().apply { this.tag = "summaryNatal1Line_percent_neg"},
        DVTextPane().apply { this.tag = "summaryNatal1Line_stim"}
    )

    fun summaryNatal1LineSetContent() {
        val summaryNatal1Line = summaryNatal1Line()

        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal1Line_chartLabel", summaryNatal1Line[0], SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal1Line_values_pos", summaryNatal1Line[1], SACLayoutHandler.positiveFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal1Line_divider1", RenderValue.valueDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal1Line_values_neg", summaryNatal1Line[2], SACLayoutHandler.negativeFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal1Line_percent_pos", summaryNatal1Line[3], SACLayoutHandler.positiveFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal1Line_divider2", RenderValue.valueDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal1Line_percent_neg", summaryNatal1Line[4], SACLayoutHandler.negativeFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal1Line_stim", summaryNatal1Line[5], SACLayoutHandler.airFontColor)
    }

    fun summaryNatal1Line() : List<String> {
        val chartValue = RenderValue(SACComponent.refNatValueChart.getBaseValue())

        return mutableListOf(
            RenderChartState.getChartSumLabel(ChartState.NATAL_CHART),
            chartValue.getValuesLabel().first,
            chartValue.getValuesLabel().second,
            chartValue.getPercentLabel().first,
            chartValue.getPercentLabel().second,
            chartValue.getStimLabel()
        )
    }


    fun summaryNatal2LineLayout() = mutableListOf(
        DVTextPane().apply { this.tag = "summaryNatal2Line_chartLabel"},
        DVTextPane().apply { this.tag = "summaryNatal2Line_values_pos"},
        DVTextPane().apply { this.tag = "summaryNatal2Line_divider1"},
        DVTextPane().apply { this.tag = "summaryNatal2Line_values_neg"},
        DVTextPane().apply { this.tag = "summaryNatal2Line_percent_pos"},
        DVTextPane().apply { this.tag = "summaryNatal2Line_divider2"},
        DVTextPane().apply { this.tag = "summaryNatal2Line_percent_neg"},
        DVTextPane().apply { this.tag = "summaryNatal2Line_stim"}
    )

    fun summaryNatal2LineSetContent() {
        val summaryNatal2Line = summaryNatal2Line()

        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal2Line_chartLabel", summaryNatal2Line[0], SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal2Line_values_pos", summaryNatal2Line[1], SACLayoutHandler.positiveFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal2Line_divider1", RenderValue.valueDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal2Line_values_neg", summaryNatal2Line[2], SACLayoutHandler.negativeFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal2Line_percent_pos", summaryNatal2Line[3], SACLayoutHandler.positiveFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal2Line_divider2", RenderValue.valueDivider(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal2Line_percent_neg", summaryNatal2Line[4], SACLayoutHandler.negativeFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("summaryNatal2Line_stim", summaryNatal2Line[5], SACLayoutHandler.airFontColor)
    }

    fun summaryNatal2Line() : List<String> {
        val chartValue = RenderValue(SACComponent.synNatValueChart.getBaseValue())

        return mutableListOf(
            RenderChartState.getChartSumLabel(ChartState.NATAL_CHART),
            chartValue.getValuesLabel().first,
            chartValue.getValuesLabel().second,
            chartValue.getPercentLabel().first,
            chartValue.getPercentLabel().second,
            chartValue.getStimLabel()
        )
    }
}