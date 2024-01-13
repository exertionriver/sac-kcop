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
    FIRST_CELESTIAL { override fun getLabel() = "1sC $SYM_UP_ARROW" }
    , ASPECT { override fun getLabel() = "Asp $SYM_UP_ARROW" }
    , SECOND_CELESTIAL { override fun getLabel() = "2nC $SYM_UP_ARROW" }
    , VALUE_MAGNITUDE { override fun getLabel() = "|V| $SYM_DOWN_ARROW" }
    , VALUE_POS_TO_NEG { override fun getLabel() = " V $SYM_UP_ARROW" }
    , VALUE_NEG_TO_POS { override fun getLabel() = " V $SYM_DOWN_ARROW" }
    ;

    abstract fun getLabel() : String

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true


    companion object {
        fun defaultState() = FIRST_CELESTIAL

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