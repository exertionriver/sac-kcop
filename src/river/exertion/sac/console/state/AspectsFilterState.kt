package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_ALL_BOX
import river.exertion.sac.Constants.SYM_MAJOR_BOX
import river.exertion.sac.Constants.SYM_MINOR_BOX
import river.exertion.sac.view.SACInputProcessor

enum class AspectsFilterState : State<SACInputProcessor>  {
    ALL_VALUES { override val label = " All V " }
    , OVER_TWENTY { override val label = "|V|>=20" }
    , OVER_FIFTY { override val label = "|V|>=50" };

    abstract val label : String

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true


    companion object {
        val defaultState = ALL_VALUES

        fun cycleState(aspectsState: AspectsFilterState) : AspectsFilterState {
            return when (aspectsState) {
                ALL_VALUES -> OVER_TWENTY
                OVER_TWENTY -> OVER_FIFTY
                else -> ALL_VALUES
            }
        }
    }


}