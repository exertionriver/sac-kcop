package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants
import river.exertion.sac.Constants.SYM_COMBINED_CHART
import river.exertion.sac.Constants.SYM_NATCOMP_CHART
import river.exertion.sac.Constants.SYM_SYNASTRY_CHART
import river.exertion.sac.view.SACInputProcessor

enum class ChartState : State<SACInputProcessor> {
    NONE_CHART //for values analysis only
    , NATAL_CHART
    , COMPOSITE_CHART { override val operatorLabel = "=" }
    , SYNASTRY_CHART { override val label = SYM_SYNASTRY_CHART ; override val operatorLabel = "+"; override val isNatComp = false }
    , COMBINED_CHART { override val label = SYM_COMBINED_CHART ; override val operatorLabel = "."; override val isNatComp = true }
    ;

    open val label : String = SYM_NATCOMP_CHART
    open val operatorLabel : String = ""
    open val isNatComp : Boolean = true

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        val defaultState = NATAL_CHART

        fun getChartSumLabel(overrideState : ChartState? = null) : String = "${Constants.SYM_SIGMA}${overrideState?.label ?: (SACInputProcessor.chartStateMachine.currentState as ChartState).label}:"

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