package river.exertion.sac.console.state

import kotlinx.datetime.*
import river.exertion.sac.Constants.KEY_0
import river.exertion.sac.Constants.KEY_1
import river.exertion.sac.Constants.KEY_2
import river.exertion.sac.Constants.KEY_3
import river.exertion.sac.Constants.KEY_4
import river.exertion.sac.Constants.KEY_5
import river.exertion.sac.Constants.KEY_6
import river.exertion.sac.Constants.KEY_7
import river.exertion.sac.Constants.KEY_8
import river.exertion.sac.Constants.KEY_9
import river.exertion.sac.Constants.KEY_AMPERSAND
import river.exertion.sac.Constants.KEY_ASTERISK
import river.exertion.sac.Constants.KEY_CARET
import river.exertion.sac.Constants.KEY_CLOSEPARENS
import river.exertion.sac.Constants.KEY_OPENPARENS
import river.exertion.sac.Constants.KEY_PERCENT
import river.exertion.sac.Constants.KEY_x
import river.exertion.sac.Constants.KEY_A
import river.exertion.sac.Constants.KEY_AT
import river.exertion.sac.Constants.KEY_BANG
import river.exertion.sac.Constants.KEY_D
import river.exertion.sac.Constants.KEY_DOLLAR
import river.exertion.sac.Constants.KEY_ESC
import river.exertion.sac.Constants.KEY_HASH
import river.exertion.sac.Constants.KEY_MINUS
import river.exertion.sac.Constants.KEY_O
import river.exertion.sac.Constants.KEY_SPACE
import river.exertion.sac.Constants.KEY_T
import river.exertion.sac.Constants.KEY_Z
import river.exertion.sac.Constants.KEY_d
import river.exertion.sac.Constants.KEY_h
import river.exertion.sac.Constants.KEY_m
import river.exertion.sac.Constants.KEY_o
import river.exertion.sac.Constants.KEY_s
import river.exertion.sac.Constants.KEY_w
import river.exertion.sac.Constants.KEY_y
import river.exertion.sac.astro.state.EntryState
import river.exertion.sac.astro.state.NavDirState
import river.exertion.sac.astro.state.NavState
import kotlin.time.ExperimentalTime

@ExperimentalUnsignedTypes
object ConsoleNavState {

    fun getDefaultState() = NavState.CURRENT

    fun NavState.getNextState(input: Int) = this.getState(input)

    fun NavState.getNextState(entryState: EntryState) = this.getNextStateFromEntryState(entryState)

//    @ExperimentalTime
//    fun NavState.getCurNavTime(navDirState: NavDirState, curNavTime: LocalDateTime) = this.getCurNavTime(navDirState, curNavTime)

    fun NavState.getNextStateFromEntryState(entryState: EntryState) : NavState {

        return when (entryState) {
            EntryState.PROFILE_ENTRY -> NavState.PROFILE_RECALL
            EntryState.PROFILE_NUMBER_ENTRY -> this
            EntryState.NO_ENTRY -> this
            else -> NavState.NAV_PAUSED
        }
    }

    fun NavState.getState(input: Int): NavState {

        return when (input) {
            KEY_SPACE -> if (this.isPaused()) NavState.NAV_SECOND else NavState.NAV_PAUSED
            KEY_ESC -> NavState.CURRENT
            KEY_MINUS -> if (this == NavState.CURRENT) NavState.NAV_SECOND else this //curNavDirState reverses direction
            KEY_s -> NavState.NAV_SECOND
            KEY_m -> NavState.NAV_MINUTE
            KEY_h -> NavState.NAV_HOUR
            KEY_d -> NavState.NAV_DAY
            KEY_w -> NavState.NAV_WEEK
            KEY_o -> NavState.NAV_MONTH
            KEY_y -> NavState.NAV_YEAR
//entry states
            KEY_D, KEY_T, KEY_O, KEY_A, KEY_Z -> NavState.ENTRY_PAUSED
//profile entry
            KEY_BANG, KEY_AT, KEY_HASH, KEY_DOLLAR, KEY_PERCENT
                , KEY_CARET, KEY_AMPERSAND, KEY_ASTERISK, KEY_OPENPARENS
                , KEY_CLOSEPARENS -> NavState.ENTRY_PAUSED
//profile recall
            KEY_1, KEY_2, KEY_3, KEY_4, KEY_5, KEY_6
                , KEY_7, KEY_8, KEY_9, KEY_0 -> NavState.PROFILE_RECALL

            KEY_x -> getDefaultState()
            else -> return this // no change
        }
    }

    @ExperimentalTime
    fun NavState.getCurNavTime(navDirState: NavDirState, curNavTime: LocalDateTime): LocalDateTime {

        val curNavTimeInstant = curNavTime.toInstant(TimeZone.UTC)

        return when (this) {
            NavState.CURRENT -> Clock.System.now().toLocalDateTime(TimeZone.UTC)
            NavState.NAV_SECOND -> curNavTimeInstant.plus(navDirState.getIncDec(), DateTimeUnit.SECOND).toLocalDateTime(
                TimeZone.UTC)
            NavState.NAV_MINUTE -> curNavTimeInstant.plus(navDirState.getIncDec(), DateTimeUnit.MINUTE).toLocalDateTime(
                TimeZone.UTC)
            NavState.NAV_HOUR -> curNavTimeInstant.plus(navDirState.getIncDec(), DateTimeUnit.HOUR).toLocalDateTime(
                TimeZone.UTC)
            NavState.NAV_DAY -> curNavTimeInstant.plus(DateTimePeriod(days = navDirState.getIncDec()), TimeZone.UTC).toLocalDateTime(
                TimeZone.UTC)
            NavState.NAV_WEEK -> curNavTimeInstant.plus(DateTimePeriod(days = navDirState.getIncDec() * 7), TimeZone.UTC).toLocalDateTime(
                TimeZone.UTC)
            NavState.NAV_MONTH -> curNavTimeInstant.plus(DateTimePeriod(months = navDirState.getIncDec()), TimeZone.UTC).toLocalDateTime(
                TimeZone.UTC)
            NavState.NAV_YEAR -> curNavTimeInstant.plus(DateTimePeriod(years = navDirState.getIncDec()), TimeZone.UTC).toLocalDateTime(
                TimeZone.UTC)
            else -> curNavTime
        }
    }
}