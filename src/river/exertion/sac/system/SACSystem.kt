package river.exertion.sac.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import ktx.ashley.allOf
import river.exertion.kcop.asset.irlTime.IrlTime
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.sac.component.SACComponent
import river.exertion.sac.view.SACCelestialsHousesDVLayout
import river.exertion.sac.view.SACLayout

class SACSystem : IntervalIteratingSystem(allOf(SACComponent::class).get(), 1/10f) {

    override fun processEntity(entity: Entity) {

        val sacComponent = SACComponent.getFor(entity)!!

        if (sacComponent.isInitialized) {
            SACComponent.sacUTCDateTime = IrlTime.utcLocalDateTime()
            sacComponent.sacRecalc()

            SACCelestialsHousesDVLayout.celestialSnapshot = SACComponent.sacCelestialSnapshot
            SACCelestialsHousesDVLayout.stateChart = SACComponent.sacChart

            DisplayView.build()
        }
    }
}