package river.exertion.sac

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import river.exertion.kcop.asset.AssetManagerHandler
import river.exertion.kcop.asset.klop.IAssetKlop
import river.exertion.kcop.base.Id
import river.exertion.kcop.base.KcopBase
import river.exertion.kcop.ecs.EngineHandler
import river.exertion.kcop.ecs.component.IComponent
import river.exertion.kcop.ecs.entity.SubjectEntity
import river.exertion.kcop.ecs.klop.IECSKlop
import river.exertion.kcop.view.MultiKeys
import river.exertion.kcop.view.klop.IDisplayViewKlop
import river.exertion.sac.asset.EarthLocationLoader
import river.exertion.sac.asset.SACDefaultAssetStore
import river.exertion.sac.astro.EarthLocation
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.EntryState
import river.exertion.sac.console.state.NavState
import river.exertion.sac.system.SACSystem
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object SweetAstroConsoleKlop : IDisplayViewKlop, IAssetKlop, IECSKlop {

    override var id = Id.randomId()

    override var tag = "sac"

    override fun load() {
        loadAssets()
        loadSystems()

        KcopBase.stage.addListener(object : InputListener() {
            override fun keyDown(event : InputEvent, keycode : Int) : Boolean {
                //used to escape from text entry
                if ( (keycode == Input.Keys.ESCAPE) && (SACInputProcessor.navStateMachine.isInState(NavState.ENTRY_PAUSED) ) )  {
                    SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                    SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                    MultiKeys.keysDown.clear()
                }
                return false
            }
        })
    }

    override fun loadAssets() {
        AssetManagerHandler.assets.setLoader(EarthLocation::class.java, EarthLocationLoader(AssetManagerHandler.lfhr))

        SACDefaultAssetStore.loadAll()
    }

    override fun unload() {
        hideView()
        unloadSystems()
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