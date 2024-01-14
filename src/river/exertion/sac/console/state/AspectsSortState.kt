package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_ALL_BOX
import river.exertion.sac.Constants.SYM_DOWN_ARROW
import river.exertion.sac.Constants.SYM_MAJOR_BOX
import river.exertion.sac.Constants.SYM_MINOR_BOX
import river.exertion.sac.Constants.SYM_UP_ARROW
import river.exertion.sac.view.SACInputProcessor

enum class AspectsSortState : State<SACInputProcessor>  {
    FIRST_CELESTIAL { override val label = "1sC $SYM_UP_ARROW" }
    , ASPECT { override val label = "Asp $SYM_UP_ARROW" }
    , SECOND_CELESTIAL { override val label = "2nC $SYM_UP_ARROW" }
    , VALUE_MAGNITUDE { override val label = "|V| $SYM_DOWN_ARROW" }
    , VALUE_POS_TO_NEG { override val label = " V $SYM_UP_ARROW" }
    , VALUE_NEG_TO_POS { override val label = " V $SYM_DOWN_ARROW" }
    ;

    abstract val label : String

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true


    companion object {
        val defaultState = FIRST_CELESTIAL

        fun cycleState(aspectsSortState: AspectsSortState) : AspectsSortState {
            return when (aspectsSortState) {
                FIRST_CELESTIAL -> ASPECT
                ASPECT -> SECOND_CELESTIAL
                SECOND_CELESTIAL -> VALUE_MAGNITUDE
                VALUE_MAGNITUDE -> VALUE_POS_TO_NEG
                VALUE_POS_TO_NEG -> VALUE_NEG_TO_POS
                else -> FIRST_CELESTIAL
            }
        }
    }


}