package river.exertion.sac.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import kotlinx.datetime.*
import ktx.ashley.allOf
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.sac.console.state.NavState
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACCelestialsHousesDVLayout
import river.exertion.sac.view.SACInputProcessor

class SACSystem : IntervalIteratingSystem(allOf(SACComponent::class).get(), .1f) {

    override fun processEntity(entity: Entity) {

        val sacComponent = SACComponent.getFor(entity)!!

        if ( (sacComponent.isInitialized) && (nextRender() >= lastRender) ) {
//            Log.debug("lastRender: ${lastRender}")
            lastRender = truncInstant(Clock.System.now())

            SACInputProcessor.navStateMachine.currentState.updCurNavTime()

            SACComponent.sacUTCDateTime = NavState.curNavTimeUTC()
            sacComponent.sacRecalc()

            SACCelestialsHousesDVLayout.celestialSnapshot = SACComponent.sacCelestialSnapshot
            SACCelestialsHousesDVLayout.stateChart = SACComponent.sacChart

            DisplayView.build()
        }
    }

    companion object {
        var lastRender = truncInstant(Clock.System.now())
        val systemTZ = TimeZone.currentSystemDefault()
        fun nextRender() = truncInstant(Clock.System.now()).minus(1, DateTimeUnit.SECOND, systemTZ)

        fun truncInstant(instant: Instant) : Instant {

            val localDateTimeSting = instant.toLocalDateTime(TimeZone.UTC).toString()

            val localDateTimeStringTrunc = "${localDateTimeSting.substring(0, localDateTimeSting.indexOfLast { it == '.' } )}.00Z"

            return localDateTimeStringTrunc.toInstant()

        }
    }
}