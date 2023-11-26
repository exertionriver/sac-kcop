package river.exertion.sac.component

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import kotlinx.datetime.TimeZone
import river.exertion.kcop.asset.AssetManagerHandler
import river.exertion.kcop.base.Id
import river.exertion.kcop.ecs.EngineHandler
import river.exertion.kcop.ecs.component.IComponent
import river.exertion.kcop.messaging.MessageChannelHandler
import river.exertion.kcop.profile.Profile
import river.exertion.kcop.profile.asset.ProfileAsset
import river.exertion.kcop.sim.narrative.NarrativeKlop.NarrativeBridge
import river.exertion.kcop.sim.narrative.messaging.NarrativeComponentMessage
import river.exertion.kcop.view.asset.SoundAssetStore
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.kcop.view.layout.StatusView
import river.exertion.sac.Constants
import river.exertion.sac.SweetAstroConsoleKlop
import river.exertion.sac.asset.SACDefaultAssetStore
import river.exertion.sac.astro.base.CelestialSnapshot
import river.exertion.sac.astro.base.EarthLocation
import river.exertion.sac.astro.state.StateChart
import river.exertion.sac.console.state.*

class SACComponent : IComponent, Telegraph {

    var refProfile : Profile
        get() = ProfileAsset.currentProfileAsset.profile
        set(value) { ProfileAsset.currentProfileAsset.profile = value }

    override var componentId = Id.randomId()
    override var isInitialized = false

    @OptIn(ExperimentalUnsignedTypes::class)
    fun sacRecalc() {
        sacEarthLocation.latitude = sacLatitude
        sacEarthLocation.longitude = sacLongitude
        sacEarthLocation.altitude = sacAltitude
        sacEarthLocation.timeZone = sacTimezone
        sacEarthLocation.utcDateTime = sacUTCDateTime

        sacCelestialSnapshot.refEarthLocation = sacEarthLocation
        sacCelestialSnapshot.recalc()
        sacChart = StateChart(StateChart.getAspects(sacCelestialSnapshot, sacCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT))
    }

    override fun initialize(initData: Any?) {

        super.initialize(initData)

        DisplayView.currentDisplayViewLayoutHandler = SweetAstroConsoleKlop.displayViewLayoutHandler()

        StatusView.clearStatuses()

        ProfileAsset.currentProfileAsset.profile.execSettings()


    }

    override fun handleMessage(msg: Telegram?): Boolean {
        if (msg != null) {
            if (MessageChannelHandler.isType(NarrativeBridge, msg.message) && isInitialized) {
                val narrativeComponentMessage: NarrativeComponentMessage = MessageChannelHandler.receiveMessage(NarrativeBridge, msg.extraInfo)

             /*   when (narrativeComponentMessage.narrativeMessageType) {

                    NarrativeComponentMessage.NarrativeMessageType.RemoveBlockCumlTimer -> {
                        blockCumlTimer = false
                    }
                    NarrativeComponentMessage.NarrativeMessageType.ReplaceCumlTimer -> {
                        blockCumlTimer = false
                        IComponent.ecsInit<ImmersionTimerComponent>(initData = ImmersionTimerPair(instImmersionTimer, cumlImmersionTimer))
                    }
                    NarrativeComponentMessage.NarrativeMessageType.ReplaceBlockCumlTimer -> {
                        blockCumlTimer = true
                        IComponent.ecsInit<ImmersionTimerComponent>(initData = ImmersionTimerPair(blockInstImmersionTimer(), blockCumlImmersionTimer()))
                    }
                    NarrativeComponentMessage.NarrativeMessageType.Pause -> pause()
                    NarrativeComponentMessage.NarrativeMessageType.Unpause -> unpause()
                    NarrativeComponentMessage.NarrativeMessageType.Inactivate -> inactivate()
                    NarrativeComponentMessage.NarrativeMessageType.Next -> if (narrativeComponentMessage.promptNext != null) next(narrativeComponentMessage.promptNext)
                    NarrativeComponentMessage.NarrativeMessageType.Refresh -> {
                        this.narrativeState.blockFlags.clear()
                        this.changed = true
                    }
                }*/
                return true
            }
        }
        return false
    }

    companion object {
        fun has(entity : Entity) : Boolean = entity.components.firstOrNull{ it is SACComponent } != null
        fun getFor(entity : Entity) : SACComponent? = if (has(entity)) entity.components.first { it is SACComponent } as SACComponent else null

        //TODO: allow location definitions with lat / long / alt / tz
        //TODO: allow location default set in properties file
        var sacLatitude = SACDefaultAssetStore.Default.get().latitude
        var sacLongitude = SACDefaultAssetStore.Default.get().longitude
        var sacAltitude : Int = SACDefaultAssetStore.Default.get().altitude
        var sacTimezone = SACDefaultAssetStore.Default.get().timeZone
        var sacUTCDateTime = NavState.curNavDateTimeUTC()

        var earthLocationArray = Array(10) { idx ->
            SACDefaultAssetStore.entries.filter { it.name != "Default"}[idx].get()
        }

        var sacEarthLocation = EarthLocation("curNav", sacLongitude, sacLatitude, sacAltitude, sacTimezone, sacUTCDateTime)
        var sacCelestialSnapshot = CelestialSnapshot(sacEarthLocation)
        var sacChart = StateChart(StateChart.getAspects(sacCelestialSnapshot, sacCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT))

        @OptIn(ExperimentalUnsignedTypes::class)
        fun recallEarthLocationEntry(recallIdx : Int) {
            sacLatitude = earthLocationArray[recallIdx].latitude
            sacLongitude = earthLocationArray[recallIdx].longitude
            sacAltitude = earthLocationArray[recallIdx].altitude
            sacTimezone = earthLocationArray[recallIdx].timeZone
            sacUTCDateTime = earthLocationArray[recallIdx].utcDateTime
        }

        @OptIn(ExperimentalUnsignedTypes::class)
        fun storeEarthLocationEntry(storeIdx : Int) {
            earthLocationArray[storeIdx].latitude = sacLatitude
            earthLocationArray[storeIdx].longitude = sacLongitude
            earthLocationArray[storeIdx].altitude = sacAltitude
            earthLocationArray[storeIdx].timeZone = sacTimezone
            earthLocationArray[storeIdx].utcDateTime = sacUTCDateTime
        }


        fun ecsInit() {
            //inactivate current narrative
//            MessageChannelHandler.send(NarrativeBridge, NarrativeComponentMessage(NarrativeComponentMessage.NarrativeMessageType.Inactivate))

            EngineHandler.replaceComponent<SACComponent>()
        }
    }
}