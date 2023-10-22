package river.exertion.sac.console.state

import river.exertion.sac.Constants.KEY_e
import river.exertion.sac.Constants.KEY_x
import river.exertion.sac.astro.state.DetailsState

object ConsoleDetailsState {

    fun getDefaultState() = DetailsState.SHOW_DETAILS

    fun DetailsState.getNextState(input: Int) = this.getState(input)

    fun DetailsState.getState(input: Int): DetailsState {

        return when (input) {
            KEY_e -> if (this == DetailsState.SHOW_DETAILS) DetailsState.NO_DETAILS else DetailsState.SHOW_DETAILS

            KEY_x -> getDefaultState()
            else -> this
        }
    }
}