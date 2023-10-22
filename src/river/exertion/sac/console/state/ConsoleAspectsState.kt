package river.exertion.sac.console.state

import river.exertion.sac.Constants.KEY_a
import river.exertion.sac.Constants.KEY_x
import river.exertion.sac.astro.state.AspectsState

object ConsoleAspectsState {

    fun getDefaultState() = AspectsState.ALL_ASPECTS

    fun AspectsState.getNextState(input: Int) = this.getState(input)

    fun AspectsState.getState(input: Int): AspectsState {

        return when (input) {
            KEY_a -> when (this) {
                AspectsState.ALL_ASPECTS -> AspectsState.MAJOR_ASPECTS
                AspectsState.MAJOR_ASPECTS -> AspectsState.MINOR_ASPECTS
                else -> AspectsState.ALL_ASPECTS
            }

            KEY_x -> getDefaultState()
            else -> this
        }
    }
}