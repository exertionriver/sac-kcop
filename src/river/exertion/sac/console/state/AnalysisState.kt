package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.Constants.SYM_CHARACTER
import river.exertion.sac.Constants.SYM_ELEMENT
import river.exertion.sac.Constants.SYM_MODE
import river.exertion.sac.Constants.SYM_PLANET
import river.exertion.sac.Constants.SYM_ROMANTIC
import river.exertion.sac.view.SACInputProcessor

enum class AnalysisState : State<SACInputProcessor> {
    NO_ANALYSIS
    , ROMANTIC_ANALYSIS { override val label = SYM_ROMANTIC }
    , PLANET_ANALYSIS { override val label = SYM_PLANET }
    , ELEMENT_ANALYSIS { override val label = SYM_ELEMENT }
    , MODE_ANALYSIS { override val label = SYM_MODE }
    ;

    open val label : String = " "

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        val defaultState = NO_ANALYSIS
/*
        fun cycleCharacterState(analysisState: AnalysisState) : AnalysisState {
            return when (analysisState) {
                CHARACTER_ANALYSIS -> NO_ANALYSIS
                else -> CHARACTER_ANALYSIS
            }
        }
*/
        fun cycleElementState(analysisState: AnalysisState) : AnalysisState {
            return when (analysisState) {
                ELEMENT_ANALYSIS -> NO_ANALYSIS
                else -> ELEMENT_ANALYSIS
            }
        }

        fun cycleModeState(analysisState: AnalysisState) : AnalysisState {
            return when (analysisState) {
                MODE_ANALYSIS -> NO_ANALYSIS
                else -> MODE_ANALYSIS
            }
        }

        fun cyclePlanetState(analysisState: AnalysisState) : AnalysisState {
            return when (analysisState) {
                PLANET_ANALYSIS -> NO_ANALYSIS
                else -> PLANET_ANALYSIS
            }
        }

        fun cycleRomanticState(analysisState: AnalysisState) : AnalysisState {
            return when (analysisState) {
                ROMANTIC_ANALYSIS -> NO_ANALYSIS
                else -> ROMANTIC_ANALYSIS
            }
        }
    }
}