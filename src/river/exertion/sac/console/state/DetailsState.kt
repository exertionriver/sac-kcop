package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.view.SACInputProcessor

//used for?
enum class DetailsState : State<SACInputProcessor> {
    NO_DETAILS
    , SHOW_DETAILS;

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        val defaultState = SHOW_DETAILS

        fun cycleState(detailsState: DetailsState) : DetailsState {
            return when (detailsState) {
                SHOW_DETAILS -> NO_DETAILS
                else -> SHOW_DETAILS
            }
        }
    }
}