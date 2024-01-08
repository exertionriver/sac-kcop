package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants
import river.exertion.sac.Constants.SYM_NATCOMP_CHART
import river.exertion.sac.Constants.SYM_SYNASTRY_CHART
import river.exertion.sac.view.SACInputProcessor

enum class ChartState : State<SACInputProcessor> {
    NONE_CHART //for values analysis only
    , NATAL_CHART
    , COMPOSITE_CHART { override fun getOperatorLabel() = "=" }
    , SYNASTRY_CHART { override fun getLabel() = SYM_SYNASTRY_CHART ; override fun getOperatorLabel() = "+"; override fun isNatComp() = false }
    ;

    open fun getLabel() : String = SYM_NATCOMP_CHART
    open fun getOperatorLabel() : String = ""
    open fun isNatComp() : Boolean = true

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        fun defaultState() = NATAL_CHART

        fun getChartSumLabel(overrideState : ChartState? = null) : String = "${Constants.SYM_SIGMA}${overrideState?.getLabel() ?: SACInputProcessor.chartStateMachine.currentState.getLabel()}:"

        fun String.impChartLabel() : String = Constants.SYM_IMPROVEMENT + this

        fun String.conAmChartLabel() : String = when (SACInputProcessor.analysisStateMachine.currentState) {
            AnalysisState.ROMANTIC_ANALYSIS -> Constants.SYM_ROMANTIC + this
            else -> this
        }

        fun String.impConAmChartLabel() : String = this.conAmChartLabel().impChartLabel()

        fun cycleState(chartState : ChartState) : ChartState {
            return when (chartState) {
                SYNASTRY_CHART -> COMPOSITE_CHART
                COMPOSITE_CHART -> SYNASTRY_CHART
                else -> NATAL_CHART
            }
        }

    }
}