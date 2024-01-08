package river.exertion.sac.console.render.summary

import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutCell
import river.exertion.kcop.view.layout.displayViewLayout.DVRow
import river.exertion.kcop.view.layout.displayViewLayout.DVTable
import river.exertion.kcop.view.layout.displayViewLayout.DVTextPane
import river.exertion.sac.component.SACComponent
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

                4 -> if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                    this.add(RenderSummaryConAnSumAppreciation.setLayout())
                } else {
                    this.add(RenderSummarySumRefImp.setLayout())
                }

                5 -> if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                    this.add(RenderSummaryConAnSumAffinity.setLayout())
                }
                else {
                    this.add(RenderSummarySumSynImp.setLayout())
                }

                6 -> if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                    this.add(RenderSummaryConAnSumCommonality.setLayout())
                } else {
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}"})
                }

                7 -> if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                    this.add(RenderSummaryConAnSumCompatibility.setLayout())
                } else {
                    this.add(RenderSummaryConAnSumChart.setLayout())
                }

                8 -> if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}"})
                } else {
                    this.add(RenderSummaryConAnSumRefNatal.setLayout())
                }

                9 -> if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}"})
                } else {
                    this.add(RenderSummaryConAnSumSynNatal.setLayout())
                }

                11 -> if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}"})
                } else {
                    this.add(RenderSummaryConAnSumRefImp.setLayout())
                }

                12 -> if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_spacer${summaryRowIdx}"})
                } else {
                    this.add(RenderSummaryConAnSumSynImp.setLayout())
                }

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

            if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.ROMANTIC_ANALYSIS)) {
                RenderSummaryConAnSumChart.setContent()

                RenderSummaryConAnSumRefNatal.setContent()
                RenderSummaryConAnSumSynNatal.setContent()

                RenderSummaryConAnSumRefImp.setContent()
                RenderSummaryConAnSumSynImp.setContent()
            } else if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                    RenderSummaryConAnSumAppreciation.setContent()
                    RenderSummaryConAnSumAffinity.setContent()
                    RenderSummaryConAnSumCommonality.setContent()
                    RenderSummaryConAnSumCompatibility.setContent()
            }
        }
    }
}