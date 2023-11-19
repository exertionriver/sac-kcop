package river.exertion.sac.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import kotlinx.datetime.*
import ktx.ashley.allOf
import river.exertion.kcop.base.Log
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.sac.console.state.NavState
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACCelestialsHousesDVLayout
import river.exertion.sac.view.SACInputProcessor

class SACSystem : IntervalIteratingSystem(allOf(SACComponent::class).get(), .4f) {

    override fun processEntity(entity: Entity) {

        val sacComponent = SACComponent.getFor(entity)!!

        if (sacComponent.isInitialized) {
            SACInputProcessor.navStateMachine.currentState.updCurNavTime()

            SACComponent.sacUTCDateTime = NavState.curNavTimeUTC()
            sacComponent.sacRecalc()

            SACCelestialsHousesDVLayout.celestialSnapshot = SACComponent.sacCelestialSnapshot
            SACCelestialsHousesDVLayout.stateChart = SACComponent.sacChart

            DisplayView.build()
        }
    }
}