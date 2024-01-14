package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_DEFAULT_ORBS
import river.exertion.sac.Constants.SYM_HYBRID_ORBS
import river.exertion.sac.Constants.SYM_NATCOMP_CHART
import river.exertion.sac.Constants.SYM_SELECTIVE_ORBS
import river.exertion.sac.Constants.SYM_SYNASTRY_CHART
import river.exertion.sac.view.SACInputProcessor

//Default Orb defn is based on https://www.astro.com/astrology/in_aspect_e.htm?nhor=432453&nho2=860236
//Selective Orb defn is based on readings found at https://astro.cafeastrology.com/natal.php
//Hybrid Orb defn is an original creation
enum class AspectOverlayState : State<SACInputProcessor>  {
    ASPECT_NATCOMP_OVERLAY_DEFAULT {
        override val label = SYM_DEFAULT_ORBS
        override val isDefault = true
    },
    ASPECT_NATCOMP_OVERLAY_SELECTIVE {
        override val label = SYM_SELECTIVE_ORBS
        override val isSelective = true
    },
    ASPECT_NATCOMP_OVERLAY_HYBRID {
        override val label = SYM_HYBRID_ORBS
    },
    ASPECT_SYNASTRY_OVERLAY_DEFAULT {
        override val label = SYM_DEFAULT_ORBS
        override val isNatComp = false
        override val isDefault = true
    },
    ASPECT_SYNASTRY_OVERLAY_SELECTIVE {
        override val label = SYM_SELECTIVE_ORBS
        override val isNatComp = false
        override val isSelective = true
    },
    ASPECT_SYNASTRY_OVERLAY_HYBRID {
        override val label = SYM_HYBRID_ORBS
        override val isNatComp = false
    };

    abstract val label: String
    open val isNatComp: Boolean = true
    open val isDefault: Boolean = false
    open val isSelective: Boolean = false

    fun isHybrid() = !isDefault && !isSelective

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        val defaultState = ASPECT_NATCOMP_OVERLAY_DEFAULT

        //used when chartState changes
        fun toggleState(chartState: ChartState, aspectOverlayState: AspectOverlayState): AspectOverlayState {

            return when {
                chartState.isNatComp ->
                    when (aspectOverlayState) {
                        ASPECT_NATCOMP_OVERLAY_DEFAULT -> ASPECT_SYNASTRY_OVERLAY_DEFAULT
                        ASPECT_NATCOMP_OVERLAY_SELECTIVE -> ASPECT_SYNASTRY_OVERLAY_SELECTIVE
                        ASPECT_NATCOMP_OVERLAY_HYBRID -> ASPECT_SYNASTRY_OVERLAY_HYBRID
                        else -> aspectOverlayState
                    }
                else ->
                    when (aspectOverlayState) {
                        ASPECT_SYNASTRY_OVERLAY_DEFAULT -> ASPECT_NATCOMP_OVERLAY_DEFAULT
                        ASPECT_SYNASTRY_OVERLAY_SELECTIVE -> ASPECT_NATCOMP_OVERLAY_SELECTIVE
                        ASPECT_SYNASTRY_OVERLAY_HYBRID -> ASPECT_NATCOMP_OVERLAY_HYBRID
                        else -> aspectOverlayState
                    }
            }
        }

        //used when aspectOverlayState changes
        fun cycleState(chartState: ChartState, aspectOverlayState: AspectOverlayState): AspectOverlayState {

            return when {
                chartState.isNatComp ->
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