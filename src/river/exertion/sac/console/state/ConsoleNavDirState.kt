package river.exertion.sac.console.state

import river.exertion.sac.Constants.KEY_MINUS
import river.exertion.sac.Constants.KEY_x
import river.exertion.sac.astro.state.NavDirState

object ConsoleNavDirState {

    fun getDefaultState() = NavDirState.NAV_FORWARD

    fun NavDirState.getNextState(input: Int) = this.getState(input)

    fun NavDirState.getState(input: Int): NavDirState {

        return when (input) {
            KEY_MINUS -> if (this == NavDirState.NAV_FORWARD) NavDirState.NAV_REVERSE else NavDirState.NAV_FORWARD

            KEY_x -> getDefaultState()
            else -> this
        }
    }
}