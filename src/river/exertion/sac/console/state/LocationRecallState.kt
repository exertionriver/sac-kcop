package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.kcop.base.str
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACInputProcessor

enum class LocationRecallState : State<SACInputProcessor> {
    CUR_NAV_REF { override fun getLabels() : List<String> = listOf(SACComponent.sacEarthLocation.tag) }
    , CUR_NAV_REF_SYNCOMP_RECALL { override fun getLabels() : List<String> = listOf(SACComponent.sacEarthLocation.tag, SACInputProcessor.chartStateMachine.currentState.getOperatorLabel(), SACComponent.synEarthLocation?.tag ?: "??") }
    , CUR_NAV_REF_SYNCOMP_ENTRY { override fun getLabels() : List<String> = listOf(SACComponent.sacEarthLocation.tag, SACInputProcessor.chartStateMachine.currentState.getOperatorLabel(), "?", ) }
    ;
    abstract fun getLabels() : List<String>
    fun getLabel(): String = getLabels().str()

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    fun eltLeftSquareLabel() = "["
    fun eltLeftTag() = getLabels()[0]
    fun eltOpTag() = getLabels()[1]
    fun eltRightTag() = getLabels()[2]
    fun eltRightSquareLabel() = "]"

    companion object {
        fun defaultState() = CUR_NAV_REF
    }
}