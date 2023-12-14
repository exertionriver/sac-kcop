package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_ALL_BOX
import river.exertion.sac.Constants.SYM_MAJOR_BOX
import river.exertion.sac.Constants.SYM_MINOR_BOX
import river.exertion.sac.view.SACInputProcessor

enum class AspectsFilterState : State<SACInputProcessor>  {
    ALL_VALUES { override fun getLabel() = SYM_ALL_BOX }
    , OVER_TWENTY { override fun getLabel() = SYM_MAJOR_BOX }
    , OVER_FIFTY { override fun getLabel() = SYM_MINOR_BOX };

    abstract fun getLabel() : String

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true


    companion object {
        fun defaultState() = ALL_VALUES

        fun cycleState(aspectsState: AspectsFilterState) : AspectsFilterState {
            return when (aspectsState) {
                ALL_VALUES -> OVER_TWENTY
                OVER_TWENTY -> OVER_FIFTY
                else -> ALL_VALUES
            }
        }
    }


}