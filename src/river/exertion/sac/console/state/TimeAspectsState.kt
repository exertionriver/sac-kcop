package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_FULL_HGLASS
import river.exertion.sac.Constants.SYM_NEGATION
import river.exertion.sac.view.SACInputProcessor

enum class TimeAspectsState : State<SACInputProcessor> {
    TIME_ASPECTS_ENABLED { override val label = SYM_FULL_HGLASS }
    , TIME_ASPECTS_DISABLED { override val label = " " } ;

    abstract val label: String

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        val defaultState = TIME_ASPECTS_ENABLED

        fun cycleState(timeAspectsState: TimeAspectsState) : TimeAspectsState {
            return when (timeAspectsState) {
                TIME_ASPECTS_DISABLED -> TIME_ASPECTS_ENABLED
                else -> TIME_ASPECTS_DISABLED
            }
        }
    }
}