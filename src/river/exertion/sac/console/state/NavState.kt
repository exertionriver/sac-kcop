package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import kotlinx.datetime.*
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACInputProcessor

enum class NavState : State<SACInputProcessor> {
    CURRENT {
        override fun getLabel() = "Current Time"
        override fun updCurNavInstant() {
            curNavInstant = Clock.System.now()
            SACComponent.dataChanged = true
        }
        override fun isCurrent() = true
    }
    , NAV_PAUSED {
        override fun getLabel() = "Nav:Paused"
        override fun isPaused() = true
    }
    , NAV_SECOND {
        override fun getLabel() = "Nav:Second"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(SACInputProcessor.navDirStateMachine.currentState.getIncDec(), DateTimeUnit.SECOND)
            SACComponent.dataChanged = true
        }
    }
    , NAV_MINUTE {
        override fun getLabel() = "Nav:Minute"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(SACInputProcessor.navDirStateMachine.currentState.getIncDec(), DateTimeUnit.MINUTE)
            SACComponent.dataChanged = true
        }
    }
    , NAV_HOUR {
        override fun getLabel() = "Nav:Hour"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(SACInputProcessor.navDirStateMachine.currentState.getIncDec(), DateTimeUnit.HOUR)
            SACComponent.dataChanged = true
        }
    }
    , NAV_DAY {
        override fun getLabel() = "Nav:Day"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(DateTimePeriod(days = SACInputProcessor.navDirStateMachine.currentState.getIncDec()), TimeZone.UTC)
            SACComponent.dataChanged = true
        }
    }
    , NAV_WEEK {
        override fun getLabel() = "Nav:Week"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(DateTimePeriod(days = SACInputProcessor.navDirStateMachine.currentState.getIncDec() * 7), TimeZone.UTC)
            SACComponent.dataChanged = true
        }
    }
    , NAV_MONTH {
        override fun getLabel() = "Nav:Month"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(DateTimePeriod(months = SACInputProcessor.navDirStateMachine.currentState.getIncDec()), TimeZone.UTC)
            SACComponent.dataChanged = true
        }
    }
    , NAV_YEAR {
        override fun getLabel() = "Nav:Year"
        override fun updCurNavInstant() {
            curNavInstant = curNavInstant.plus(DateTimePeriod(years = SACInputProcessor.navDirStateMachine.currentState.getIncDec()), TimeZone.UTC)
            SACComponent.dataChanged = true
        }
    }
    , ENTRY_PAUSED {
        override fun getLabel() = "Entry:Paused"
        override fun isPaused() = true
    }
    , LOCATION_RECALL {
        override fun getLabel() = "Location Recalled"
        override fun updCurNavInstant() { curNavInstant = SACComponent.sacEarthLocation.utcDateTime.toInstant(TimeZone.UTC) }
        override fun isPaused() = true
    }
    , LOCATION_STORE {
        override fun getLabel() = "Location Stored"
        override fun updCurNavInstant() { curNavInstant = SACComponent.sacEarthLocation.utcDateTime.toInstant(TimeZone.UTC) }
        override fun isPaused() = true
    }
    ;
    abstract fun getLabel(): String
    open fun isCurrent() = false
    open fun isPaused() = false
    open fun updCurNavInstant() { }

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        var curNavInstant = Clock.System.now()
        fun curNavDateTimeUTC() = curNavInstant.toLocalDateTime(TimeZone.UTC)

        fun defaultState() = CURRENT

        fun cyclePause(navState: NavState) : NavState {
            return when {
                navState.isPaused() -> NAV_SECOND
                else -> NAV_PAUSED
            }
        }
    }
}
