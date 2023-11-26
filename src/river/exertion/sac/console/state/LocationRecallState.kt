package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_RETRO
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACInputProcessor

enum class LocationRecallState : State<SACInputProcessor> {
    CUR_NAV { override fun getLabel(): String = "[${SACComponent.sacEarthLocation.tag}]" }
    , CUR_NAV_SYNCOMP_RECALL { override fun getLabel() = "[${SACComponent.sacEarthLocation.tag} ${SACInputProcessor.chartStateMachine.currentState.getOperatorLabel()} ${SACComponent.synCompRecall?.tag}]" }
    , CUR_NAV_SYNCOMP_ENTRY { override fun getLabel() = "[${SACComponent.sacEarthLocation.tag} ${SACInputProcessor.chartStateMachine.currentState.getOperatorLabel()} ?]"
        override fun isEntry(): Boolean = true
    }
    , REF_RECALL { override fun getLabel() = "[${SACComponent.refRecall?.tag}]" }
    , REF_SYNCOMP_RECALL { override fun getLabel() = "[${SACComponent.refRecall?.tag} ${SACInputProcessor.chartStateMachine.currentState.getOperatorLabel()} ${SACComponent.synCompRecall?.tag}]" }
    , REF_SYNCOMP_ENTRY { override fun getLabel() = "[${SACComponent.refRecall?.tag} ${SACInputProcessor.chartStateMachine.currentState.getOperatorLabel()} ?]"
        override fun isEntry(): Boolean = true
    }
    ;
    abstract fun getLabel(): String
    open fun isEntry() = false

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        fun defaultState() = CUR_NAV

        fun setState(navState: NavState, entryState: EntryState) : LocationRecallState {
            return when {
                ( (navState == NavState.LOCATION_STORE) || (navState == NavState.LOCATION_RECALL) ) -> REF_RECALL
                (entryState == EntryState.LOCATION_NUMBER_ENTRY) -> REF_SYNCOMP_ENTRY

                else -> CUR_NAV
            }
        }
    }
}