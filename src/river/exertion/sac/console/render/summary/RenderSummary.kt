package river.exertion.sac.console.render.summary

import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutCell
import river.exertion.kcop.view.layout.displayViewLayout.DVRow
import river.exertion.kcop.view.layout.displayViewLayout.DVTable
import river.exertion.kcop.view.layout.displayViewLayout.DVTextPane
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.render.RenderSummaryAspects
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACInputProcessor

object RenderSummary : IConsoleRender {

    override val layoutTag = "summary"

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, colspan = "5", panes = mutableListOf<DVLayoutCell>().apply {
        var summaryRowIdx = 0

        while (summaryRowIdx < RenderSummaryAspects.summaryMaxEntries) {

            when (summaryRowIdx) {
                0 -> this.add(RenderSummarySumChart.setLayout())
                1 -> this.add(RenderSummarySumRefNatal.setLayout())
                2 -> this.add(RenderSummarySumSynNatal.setLayout())

                4 -> this.add(RenderSummarySumRefImp.setLayout())
                5 -> this.add(RenderSummarySumSynImp.setLayout())

                7 -> this.add(RenderSummaryConAnSumChart.setLayout())
                8 -> this.add(RenderSummaryConAnSumRefNatal.setLayout())
                9 -> this.add(RenderSummaryConAnSumSynNatal.setLayout())

                11 -> this.add(RenderSummaryConAnSumRefImp.setLayout())
                12 -> this.add(RenderSummaryConAnSumSynImp.setLayout())
                else -> this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}"})
            }

            this.add(DVRow())
            summaryRowIdx++
        }
    })

    override fun setContent() {
        RenderSummarySumChart.setContent()
        if (!SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF)) {
            RenderSummarySumRefNatal.setContent()
            RenderSummarySumSynNatal.setContent()

            RenderSummarySumRefImp.setContent()
            RenderSummarySumSynImp.setContent()

            if (!SACInputProcessor.analysisStateMachine.isInState(AnalysisState.NO_ANALYSIS)) {
                RenderSummaryConAnSumChart.setContent()

                RenderSummaryConAnSumRefNatal.setContent()
                RenderSummaryConAnSumSynNatal.setContent()

                RenderSummaryConAnSumRefImp.setContent()
                RenderSummaryConAnSumSynImp.setContent()
            }
        }
    }
}