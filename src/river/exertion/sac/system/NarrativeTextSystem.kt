package river.exertion.sac.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IntervalIteratingSystem
import ktx.ashley.allOf
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.kcop.view.layout.StatusView
import river.exertion.kcop.view.layout.TextView
import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutHandler

class NarrativeTextSystem {/*: IntervalIteratingSystem(allOf(NarrativeComponent::class).get(), 1/10f) {

    override fun processEntity(entity: Entity) {

        val narrativeComponent = NarrativeComponent.getFor(entity)!!

        if (narrativeComponent.isInitialized) {

            narrativeComponent.executeReadyTimelineEvents()
            narrativeComponent.executeReadyBlockEvents()

            val currentText = narrativeComponent.currentText() + narrativeComponent.currentBlockTimer()

            if (narrativeComponent.changed) {
                DVLayoutHandler.currentText = narrativeComponent.currentDisplayText()
                DVLayoutHandler.currentFontSize = narrativeComponent.currentFontSize()
                DisplayView.build()

                StatusView.build()

                NarrativeComponent.getFor(entity)!!.changed = false
            }

            TextView.currentText = currentText
            TextView.currentPrompts = narrativeComponent.currentPrompts()
            TextView.build()
        }
    }*/
}