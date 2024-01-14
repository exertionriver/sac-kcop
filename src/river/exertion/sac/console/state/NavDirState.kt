package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_RETRO
import river.exertion.sac.view.SACInputProcessor

enum class NavDirState : State<SACInputProcessor> {
    NAV_FORWARD { override val incDec = 1 }
    , NAV_REVERSE { override val label = ":Rev"; override val incDec = -1 }
    ;
    open val label: String = ""
    abstract val incDec: Int

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        val defaultState = NAV_FORWARD

        fun cycleState(navDirState: NavDirState) : NavDirState {
            return when (navDirState) {
                NAV_FORWARD -> NAV_REVERSE
                else -> NAV_FORWARD
            }
        }
    }
}