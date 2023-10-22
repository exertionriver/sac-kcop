package river.exertion.sac.console.state

import river.exertion.sac.Constants.KEY_t
import river.exertion.sac.Constants.KEY_x
import river.exertion.sac.astro.state.TimeAspectsState

object ConsoleTimeAspectsState {

    fun getDefaultState() = TimeAspectsState.TIME_ASPECTS_ENABLED

    fun TimeAspectsState.getNextState(input: Int) = this.getState(input)

    fun TimeAspectsState.getState(input: Int): TimeAspectsState {

        return when (input) {
            KEY_t -> if (this == TimeAspectsState.TIME_ASPECTS_DISABLED) TimeAspectsState.TIME_ASPECTS_ENABLED else TimeAspectsState.TIME_ASPECTS_DISABLED

            KEY_x -> getDefaultState()
            else -> this
        }
    }
}