package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.kcop.base.str
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACInputProcessor

enum class LocationRecallState : State<SACInputProcessor> {
    CUR_NAV_REF
    , CUR_NAV_REF_SYNCOMP_ENTRY {
        override fun eltRightTag() = "?"
    }
    , CUR_NAV_REF_SYNCOMP_RECALL {
        override fun eltRightTag() = SACComponent.synEarthLocation?.tag ?: "??"
    }
    ;

    val eltLeftSquareLabel = "["
    fun eltLeftTag() : String = SACComponent.sacEarthLocation.tag
    fun eltOpTag() : String = (SACInputProcessor.chartStateMachine.currentState as ChartState).operatorLabel
    open fun eltRightTag() = ""
    val eltRightSquareLabel = "]"

    fun label() = "$eltLeftSquareLabel${eltLeftTag()} ${eltOpTag()} ${eltRightTag()}$eltRightSquareLabel"

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        val defaultState = CUR_NAV_REF
    }
}