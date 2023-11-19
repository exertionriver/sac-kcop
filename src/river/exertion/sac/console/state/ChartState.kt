package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import kotlinx.serialization.Serializable
import river.exertion.sac.Constants.SYM_NATCOMP_CHART
import river.exertion.sac.Constants.SYM_SYNASTRY_CHART
import river.exertion.sac.view.SACInputProcessor

@Serializable
enum class ChartState : State<SACInputProcessor> {
    NONE_CHART //for values analysis only
    , NATAL_CHART
    , COMPOSITE_CHART { override fun getOperatorLabel() = "=" }
    , SYNASTRY_CHART { override fun getLabel() = SYM_SYNASTRY_CHART.plus(" ") ; override fun getOperatorLabel() = "+"; override fun isNatComp() = false }
    , COMBINED_CHART //for values analysis only
    ;

    open fun getLabel() : String = SYM_NATCOMP_CHART.plus(" ")
    open fun getOperatorLabel() : String = ""
    open fun isNatComp() : Boolean = true

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        fun defaultState() = NATAL_CHART

        fun cycleSynastry(chartState: ChartState) : ChartState {
            return when (chartState) {
                SYNASTRY_CHART -> NATAL_CHART
                else -> SYNASTRY_CHART
            }
        }

        fun cycleComposite(chartState: ChartState) : ChartState {
            return when (chartState) {
                COMPOSITE_CHART -> NATAL_CHART
                else -> COMPOSITE_CHART
            }
        }
    }
}