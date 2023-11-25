package river.exertion.sac.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import ktx.ashley.allOf
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.sac.console.state.NavState
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACCelestialsHousesDVLayout
import river.exertion.sac.view.SACInputProcessor

class SACSystem : IntervalIteratingSystem(allOf(SACComponent::class).get(), .4f) {

    override fun processEntity(entity: Entity) {

        val sacComponent = SACComponent.getFor(entity)!!

        if (sacComponent.isInitialized) {

            if (SACInputProcessor.navStateMachine.isInState(NavState.ENTRY_PAUSED)) {
                if (!entryLock) {
                    DisplayView.build()
                    entryLock = true
                }
            } else {
                SACInputProcessor.navStateMachine.currentState.updCurNavInstant()

                if (!SACInputProcessor.navStateMachine.isInState(NavState.LOCATION_RECALL)) {
                    SACComponent.sacUTCDateTime = NavState.curNavDateTimeUTC()
                }

                sacComponent.sacRecalc()

                SACCelestialsHousesDVLayout.celestialSnapshot = SACComponent.sacCelestialSnapshot
                SACCelestialsHousesDVLayout.stateChart = SACComponent.sacChart

                DisplayView.build()
                entryLock = false
            }
        }
    }

    companion object {
        var entryLock = false
    }
}