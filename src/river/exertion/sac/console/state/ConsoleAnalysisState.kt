package river.exertion.sac.console.state

import river.exertion.sac.Constants.KEY_C
import river.exertion.sac.Constants.KEY_E
import river.exertion.sac.Constants.KEY_ESC
import river.exertion.sac.Constants.KEY_M
import river.exertion.sac.Constants.KEY_P
import river.exertion.sac.Constants.KEY_R
import river.exertion.sac.Constants.KEY_x
import river.exertion.sac.astro.state.AnalysisState

object ConsoleAnalysisState {

    fun getDefaultState() = AnalysisState.NO_ANALYSIS

    fun AnalysisState.getNextState(input: Int) = this.getState(input)

    fun AnalysisState.getState(input: Int): AnalysisState {

        return when (input) {
            KEY_C -> if (this == AnalysisState.CHARACTER_ANALYSIS) AnalysisState.NO_ANALYSIS else AnalysisState.CHARACTER_ANALYSIS
            KEY_E -> if (this == AnalysisState.ELEMENT_ANALYSIS) AnalysisState.NO_ANALYSIS else AnalysisState.ELEMENT_ANALYSIS
            KEY_M -> if (this == AnalysisState.MODE_ANALYSIS) AnalysisState.NO_ANALYSIS else AnalysisState.MODE_ANALYSIS
            KEY_P -> if (this == AnalysisState.PLANET_ANALYSIS) AnalysisState.NO_ANALYSIS else AnalysisState.PLANET_ANALYSIS
            KEY_R -> if (this == AnalysisState.ROMANTIC_ANALYSIS) AnalysisState.NO_ANALYSIS else AnalysisState.ROMANTIC_ANALYSIS
            KEY_ESC -> AnalysisState.NO_ANALYSIS

            KEY_x -> getDefaultState()
            else -> this
        }
    }
}