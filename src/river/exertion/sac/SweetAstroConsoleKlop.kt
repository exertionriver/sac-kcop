package river.exertion.sac

import river.exertion.kcop.base.Id
import river.exertion.kcop.ecs.EngineHandler
import river.exertion.kcop.ecs.component.IComponent
import river.exertion.kcop.ecs.component.IrlTimeComponent
import river.exertion.kcop.ecs.entity.SubjectEntity
import river.exertion.kcop.ecs.klop.IECSKlop
import river.exertion.kcop.profile.component.ProfileComponent
import river.exertion.kcop.sim.narrative.system.NarrativeTextSystem
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayout
import river.exertion.kcop.view.klop.IDisplayViewKlop
import river.exertion.sac.component.SACComponent
import river.exertion.sac.system.SACSystem

object SweetAstroConsoleKlop : IDisplayViewKlop, IECSKlop {

    override var id = Id.randomId()

    override var tag = "sac"

    override fun load() {
        loadSystems()
    }

    override fun unload() {
        hideView()
        unloadSystems()
    }

    override fun inputProcessor() = SACInputProcessor

    override fun displayViewLayoutHandler() = SACLayout

    override fun loadSystems() {
        EngineHandler.addSystem(SACSystem())

        IComponent.ecsInit<SACComponent>(SubjectEntity.entityName)
    }

    override fun unloadSystems() {
        EngineHandler.removeSystem<SACSystem>()
    }

    override fun dispose() { }
}