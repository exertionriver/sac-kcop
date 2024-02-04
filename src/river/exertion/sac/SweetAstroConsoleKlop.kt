package river.exertion.sac

import river.exertion.kcop.asset.AssetManagerHandler
import river.exertion.kcop.asset.klop.IAssetKlop
import river.exertion.kcop.base.Id
import river.exertion.kcop.base.KcopBase
import river.exertion.kcop.ecs.EngineHandler
import river.exertion.kcop.ecs.component.IComponent
import river.exertion.kcop.ecs.entity.SubjectEntity
import river.exertion.kcop.ecs.klop.IECSKlop
import river.exertion.kcop.view.klop.IDisplayViewKlop
import river.exertion.sac.asset.EarthLocationLoader
import river.exertion.sac.asset.SACDefaultAssetStore
import river.exertion.sac.astro.EarthLocation
import river.exertion.sac.component.SACComponent
import river.exertion.sac.system.SACSystem
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler
import java.util.*

object SweetAstroConsoleKlop : IDisplayViewKlop, IAssetKlop, IECSKlop {

    override var id = Id.randomId()
    override var tag = "sac"
    override var name = "Sweet Astro Console (kcop)"
    override var version = "v0.5" //todo: look into tying this to gradle

    override fun load() {
        loadAssets()
        loadSystems()

        KcopBase.stage.addListener(SACInputProcessor.inputBreakListener)
    }

    override fun loadAssets() {
        AssetManagerHandler.assets.setLoader(EarthLocation::class.java, EarthLocationLoader(AssetManagerHandler.lfhr))

        SACDefaultAssetStore.loadAll()
    }

    override fun unload() {
        hideView()
        unloadSystems()

        KcopBase.stage.removeListener(SACInputProcessor.inputBreakListener)
    }

    override fun inputProcessor() = SACInputProcessor

    override fun displayViewLayoutHandler() = SACLayoutHandler

    override fun loadSystems() {
        EngineHandler.addSystem(SACSystem())

        IComponent.ecsInit<SACComponent>(SubjectEntity.entityName)
    }

    override fun unloadSystems() {
        EngineHandler.removeSystem<SACSystem>()
    }

    override fun dispose() { }
}