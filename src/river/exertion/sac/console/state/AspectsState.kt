package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_ALL_BOX
import river.exertion.sac.Constants.SYM_MAJOR_BOX
import river.exertion.sac.Constants.SYM_MINOR_BOX
import river.exertion.sac.view.SACInputProcessor

enum class AspectsState : State<SACInputProcessor>  {
    ALL_ASPECTS { override val label = SYM_ALL_BOX }
    , MAJOR_ASPECTS { override val label = SYM_MAJOR_BOX }
    , MINOR_ASPECTS { override val label = SYM_MINOR_BOX };

    abstract val label : String

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true


    companion object {
        val defaultState = ALL_ASPECTS

        fun cycleState(aspectsState: AspectsState) : AspectsState {
            return when (aspectsState) {
                ALL_ASPECTS -> MAJOR_ASPECTS
                MAJOR_ASPECTS -> MINOR_ASPECTS
                else -> ALL_ASPECTS
            }
        }
    }


}