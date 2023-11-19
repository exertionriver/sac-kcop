package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_DEFAULT_ORBS
import river.exertion.sac.Constants.SYM_HYBRID_ORBS
import river.exertion.sac.Constants.SYM_NATCOMP_CHART
import river.exertion.sac.Constants.SYM_SELECTIVE_ORBS
import river.exertion.sac.Constants.SYM_SYNASTRY_CHART
import river.exertion.sac.view.SACInputProcessor

enum class AspectOverlayState : State<SACInputProcessor>  {
    ASPECT_NATCOMP_OVERLAY_DEFAULT {
        override fun getLabel() = SYM_DEFAULT_ORBS
        override fun isDefault() = true
    },
    ASPECT_NATCOMP_OVERLAY_SELECTIVE {
        override fun getLabel() = SYM_SELECTIVE_ORBS
        override fun isSelective() = true
    },
    ASPECT_NATCOMP_OVERLAY_HYBRID {
        override fun getLabel() = SYM_HYBRID_ORBS
    },
    ASPECT_SYNASTRY_OVERLAY_DEFAULT {
        override fun getLabel() = SYM_DEFAULT_ORBS
        override fun isNatComp() = false
        override fun isDefault() = true
    },
    ASPECT_SYNASTRY_OVERLAY_SELECTIVE {
        override fun getLabel() = SYM_SELECTIVE_ORBS
        override fun isNatComp() = false
        override fun isSelective() = true
    },
    ASPECT_SYNASTRY_OVERLAY_HYBRID {
        override fun getLabel() = SYM_HYBRID_ORBS
        override fun isNatComp() = false
    };

    abstract fun getLabel(): String
    open fun isNatComp(): Boolean = true
    open fun isDefault(): Boolean = false
    open fun isSelective(): Boolean = false
    fun isHybrid() = !isDefault() && !isSelective()

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        fun defaultState() = ASPECT_NATCOMP_OVERLAY_DEFAULT

        //used when chartState changes
        fun toggleState(chartState: ChartState, aspectOverlayState: AspectOverlayState): AspectOverlayState {

            return when {
                chartState.isNatComp() ->
                    when (aspectOverlayState) {
                        ASPECT_SYNASTRY_OVERLAY_DEFAULT -> ASPECT_NATCOMP_OVERLAY_DEFAULT
                        ASPECT_SYNASTRY_OVERLAY_SELECTIVE -> ASPECT_NATCOMP_OVERLAY_SELECTIVE
                        ASPECT_SYNASTRY_OVERLAY_HYBRID -> ASPECT_NATCOMP_OVERLAY_HYBRID
                        else -> aspectOverlayState
                    }
                else ->
                    when (aspectOverlayState) {
                        ASPECT_NATCOMP_OVERLAY_DEFAULT -> ASPECT_SYNASTRY_OVERLAY_DEFAULT
                        ASPECT_NATCOMP_OVERLAY_SELECTIVE -> ASPECT_SYNASTRY_OVERLAY_SELECTIVE
                        ASPECT_NATCOMP_OVERLAY_HYBRID -> ASPECT_SYNASTRY_OVERLAY_HYBRID
                        else -> aspectOverlayState
                    }
            }
        }

        //used when aspectOverlayState changes
        fun cycleState(chartState: ChartState, aspectOverlayState: AspectOverlayState): AspectOverlayState {

            return when {
                chartState.isNatComp() ->
                    when (aspectOverlayState) {
                        ASPECT_NATCOMP_OVERLAY_DEFAULT -> ASPECT_NATCOMP_OVERLAY_SELECTIVE
                        ASPECT_NATCOMP_OVERLAY_SELECTIVE -> ASPECT_NATCOMP_OVERLAY_HYBRID
                        ASPECT_NATCOMP_OVERLAY_HYBRID -> ASPECT_NATCOMP_OVERLAY_DEFAULT
                        else -> aspectOverlayState
                    }
                else -> when (aspectOverlayState) {
                    ASPECT_SYNASTRY_OVERLAY_DEFAULT -> ASPECT_SYNASTRY_OVERLAY_SELECTIVE
                    ASPECT_SYNASTRY_OVERLAY_SELECTIVE -> ASPECT_SYNASTRY_OVERLAY_HYBRID
                    ASPECT_SYNASTRY_OVERLAY_HYBRID -> ASPECT_SYNASTRY_OVERLAY_DEFAULT
                    else -> aspectOverlayState
                }
            }
        }
    }
}