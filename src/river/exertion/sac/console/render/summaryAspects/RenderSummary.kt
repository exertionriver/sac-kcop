package river.exertion.sac.console.render.summaryAspects

import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutCell
import river.exertion.kcop.view.layout.displayViewLayout.DVRow
import river.exertion.kcop.view.layout.displayViewLayout.DVTable
import river.exertion.kcop.view.layout.displayViewLayout.DVTextPane
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACInputProcessor

object RenderSummary : IConsoleRender {

    override val layoutTag = "summary"

    override fun setLayout() = DVTable(tableTag = layoutTag, colspan = "5", panes = mutableListOf<DVLayoutCell>().apply {
        var summaryRowIdx = 0

        while (summaryRowIdx < RenderSummaryAspects.summaryMaxEntries) {

            if ( SACInputProcessor.chartStateMachine.isInState(ChartState.NATAL_CHART) ) {
                //spacers if no second location
                when (summaryRowIdx) {
                    0 -> this.add(RenderSummarySumChart.setLayout())
                    else -> this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}" })
                }
            } else {
                when (summaryRowIdx) {
                    0 -> this.add(RenderSummarySumChart.setLayout())
                    1 -> this.add(RenderSummarySumRefNatal.setLayout())
                    2 -> this.add(RenderSummarySumSynNatal.setLayout())
                }

                if (SACInputProcessor.chartStateMachine.isInState(ChartState.COMBINED_CHART)) {
                    when (summaryRowIdx) {
                        4 -> this.add(RenderSummaryConAnSumAppreciation.setLayout())
                        5 -> this.add(RenderSummaryConAnSumAffinity.setLayout())
                        6 -> this.add(RenderSummaryConAnSumCommonality.setLayout())
                        7 -> this.add(RenderSummaryConAnSumCompatibility.setLayout())

                        else -> this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}"})
                    }
                } else if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.ROMANTIC_ANALYSIS)) {
                    when (summaryRowIdx) {
                        4 -> this.add(RenderSummarySumRefImp.setLayout())
                        5 -> this.add(RenderSummarySumSynImp.setLayout())

                        7 -> this.add(RenderSummaryConAnSumChart.setLayout())
                        8 -> this.add(RenderSummaryConAnSumRefNatal.setLayout())
                        9 -> this.add(RenderSummaryConAnSumSynNatal.setLayout())

                        11 -> this.add(RenderSummaryConAnSumRefImp.setLayout())
                        12 -> this.add(RenderSummaryConAnSumSynImp.setLayout())

                        else -> this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}"})
                    }
                } else {
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}"})
                }
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

            if (SACInputProcessor.chartStateMachine.isInState(ChartState.COMBINED_CHART)) {
                RenderSummaryConAnSumAppreciation.setContent()
                RenderSummaryConAnSumAffinity.setContent()
                RenderSummaryConAnSumCommonality.setContent()
                RenderSummaryConAnSumCompatibility.setContent()
            } else if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.ROMANTIC_ANALYSIS)) {
                RenderSummaryConAnSumChart.setContent()

                RenderSummaryConAnSumRefNatal.setContent()
                RenderSummaryConAnSumSynNatal.setContent()

                RenderSummaryConAnSumRefImp.setContent()
                RenderSummaryConAnSumSynImp.setContent()
            }
        }
    }
}