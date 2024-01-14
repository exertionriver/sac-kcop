package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import kotlinx.datetime.*
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACInputProcessor

enum class NavState : State<SACInputProcessor> {
    CURRENT {
        override val label = "Current Time"
        override fun updCurNavInstant() {
            curNavInstant = Clock.System.now()
            SACComponent.dataChanged = true
        }
        override val isCurrent = true
    }
    , NAV_PAUSED {
        override val label = "Nav:Paused"
        override val isPaused = true
    }
    , NAV_SECOND {
        override val label = "Nav:Second"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus((SACInputProcessor.navDirStateMachine.currentState as NavDirState).incDec, DateTimeUnit.SECOND)
            SACComponent.dataChanged = true
        }
    }
    , NAV_MINUTE {
        override val label = "Nav:Minute"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus((SACInputProcessor.navDirStateMachine.currentState as NavDirState).incDec, DateTimeUnit.MINUTE)
            SACComponent.dataChanged = true
        }
    }
    , NAV_HOUR {
        override val label = "Nav:Hour"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus((SACInputProcessor.navDirStateMachine.currentState as NavDirState).incDec, DateTimeUnit.HOUR)
            SACComponent.dataChanged = true
        }
    }
    , NAV_DAY {
        override val label = "Nav:Day"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(DateTimePeriod(days = (SACInputProcessor.navDirStateMachine.currentState as NavDirState).incDec), TimeZone.UTC)
            SACComponent.dataChanged = true
        }
    }
    , NAV_WEEK {
        override val label = "Nav:Week"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(DateTimePeriod(days = (SACInputProcessor.navDirStateMachine.currentState as NavDirState).incDec * 7), TimeZone.UTC)
            SACComponent.dataChanged = true
        }
    }
    , NAV_MONTH {
        override val label = "Nav:Month"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(DateTimePeriod(months = (SACInputProcessor.navDirStateMachine.currentState as NavDirState).incDec), TimeZone.UTC)
            SACComponent.dataChanged = true
        }
    }
    , NAV_YEAR {
        override val label = "Nav:Year"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(DateTimePeriod(years = (SACInputProcessor.navDirStateMachine.currentState as NavDirState).incDec), TimeZone.UTC)
            SACComponent.dataChanged = true
        }
    }
    , ENTRY_PAUSED {
        override val label = "Entry:Paused"
        override val isPaused = true
    }
    , LOCATION_RECALL {
        override val label = "Location Recalled"
        override fun updCurNavInstant() { curNavInstant = SACComponent.sacEarthLocation.utcDateTime.toInstant(TimeZone.UTC) }
        override val isPaused = true
    }
    , LOCATION_STORE {
        override val label = "Location Stored"
        override fun updCurNavInstant() { curNavInstant = SACComponent.sacEarthLocation.utcDateTime.toInstant(TimeZone.UTC) }
        override val isPaused = true
    }
    ;
    abstract val label: String
    open val isCurrent = false
    open val isPaused = false
    open fun updCurNavInstant() {}

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        var curNavInstant = Clock.System.now()
        fun curNavDateTimeUTC() = curNavInstant.toLocalDateTime(TimeZone.UTC)

        val defaultState = CURRENT

        fun cyclePause(navState: NavState) : NavState {
            return when {
                navState.isPaused -> NAV_SECOND
                else -> NAV_PAUSED
            }
        }
    }
}
