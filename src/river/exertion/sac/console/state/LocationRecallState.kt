package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACInputProcessor

enum class LocationRecallState : State<SACInputProcessor> {
    CUR_NAV_REF { override fun getLabel(): String = "[${SACComponent.curNavEarthLocation.tag}]" }
    , CUR_NAV_REF_SYNCOMP_RECALL { override fun getLabel() = "[${SACComponent.curNavEarthLocation.tag} ${SACInputProcessor.chartStateMachine.currentState.getOperatorLabel()} ${SACComponent.synCompRecallEarthLocation?.tag}]" }
    , CUR_NAV_REF_SYNCOMP_ENTRY { override fun getLabel() = "[${SACComponent.curNavEarthLocation.tag} ${SACInputProcessor.chartStateMachine.currentState.getOperatorLabel()} ?]"
    }
    ;
    abstract fun getLabel(): String

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        fun defaultState() = CUR_NAV_REF
    }
}