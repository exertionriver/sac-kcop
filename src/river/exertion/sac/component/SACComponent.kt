package river.exertion.sac.component

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import river.exertion.kcop.base.Id
import river.exertion.kcop.ecs.component.IComponent
import river.exertion.kcop.messaging.MessageChannelHandler
import river.exertion.kcop.profile.Profile
import river.exertion.kcop.profile.asset.ProfileAsset
import river.exertion.kcop.sim.narrative.NarrativeKlop.NarrativeBridge
import river.exertion.kcop.sim.narrative.messaging.NarrativeComponentMessage
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.kcop.view.layout.StatusView
import river.exertion.kcop.view.layout.ViewLayout
import river.exertion.sac.SweetAstroConsoleKlop
import river.exertion.sac.asset.SACDefaultAssetStore
import river.exertion.sac.astro.base.CelestialSnapshot
import river.exertion.sac.astro.base.EarthLocation
import river.exertion.sac.astro.state.StateChart
import river.exertion.sac.console.state.*
import river.exertion.sac.view.SACInputProcessor

class SACComponent : IComponent, Telegraph {

    override var componentId = Id.randomId()
    override var isInitialized = false

    var compositeNoRecalc = false

    fun sacRecalc() {
        curNavEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()
        curNavEarthLocation.recalc()

        sacCelestialSnapshot.refEarthLocation = curNavEarthLocation

        compositeNoRecalc = false

        if (synCompRecallEarthLocation != null) {
            curNavCelestialSnapshot.refEarthLocation = curNavEarthLocation
            curNavCelestialSnapshot.synEarthLocation = synCompRecallEarthLocation!!

            synCompCelestialSnapshot.refEarthLocation = synCompRecallEarthLocation!!
            sacCelestialSnapshot.synEarthLocation = synCompRecallEarthLocation!!

            curNavCelestialSnapshot.recalc()
            synCompCelestialSnapshot.recalc()

            if (SACInputProcessor.chartStateMachine.isInState(ChartState.COMPOSITE_CHART)) {
                sacCelestialSnapshot = CelestialSnapshot.getCompositeSnapshot(curNavCelestialSnapshot, synCompCelestialSnapshot, curNavEarthLocation, synCompRecallEarthLocation!!)
                compositeNoRecalc = true
            }
        }

        if (!compositeNoRecalc) sacCelestialSnapshot.recalc()

        sacChart = StateChart(StateChart.getAspects(
            sacCelestialSnapshot
            , if (synCompRecallEarthLocation != null) synCompCelestialSnapshot else sacCelestialSnapshot
            , SACInputProcessor.chartStateMachine.currentState
            , SACInputProcessor.aspectsStateMachine.currentState
            , SACInputProcessor.timeAspectsStateMachine.currentState
            , SACInputProcessor.aspectOverlayStateMachine.currentState))
    }

    override fun initialize(initData: Any?) {

        super.initialize(initData)

        ViewLayout.currentDisplayViewLayoutHandler = SweetAstroConsoleKlop.displayViewLayoutHandler()

        StatusView.clearStatuses()

        resetEarthLocations()
    }

    override fun handleMessage(msg: Telegram?): Boolean = false

    companion object {
        fun has(entity : Entity) : Boolean = entity.components.firstOrNull{ it is SACComponent } != null
        fun getFor(entity : Entity) : SACComponent? = if (has(entity)) entity.components.first { it is SACComponent } as SACComponent else null

        //TODO: allow location definitions with lat / long / alt / tz
        //TODO: allow location default set in properties file
        var earthLocationArray = Array(10) { idx ->
            SACDefaultAssetStore.entries.filter { it.name != "Default"}[idx].get()
        }

        var curNavEarthLocation = EarthLocation()

        fun resetEarthLocations() {
            curNavEarthLocation.tag = "curNav"
            curNavEarthLocation.longitude = SACDefaultAssetStore.Default.get().longitude
            curNavEarthLocation.latitude = SACDefaultAssetStore.Default.get().latitude
            curNavEarthLocation.altitude = SACDefaultAssetStore.Default.get().altitude
            curNavEarthLocation.timeZone = SACDefaultAssetStore.Default.get().timeZone
            curNavEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()

            synCompRecallEarthLocation = null
        }

        var synCompRecallEarthLocation : EarthLocation? = null

        var curNavCelestialSnapshot = CelestialSnapshot(curNavEarthLocation)
        var sacCelestialSnapshot = CelestialSnapshot(curNavEarthLocation)
        var synCompCelestialSnapshot : CelestialSnapshot = CelestialSnapshot(curNavEarthLocation)

        var sacChart = StateChart(StateChart.getAspects(sacCelestialSnapshot, sacCelestialSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT))

        fun recallRefEarthLocationEntry(recallIdx : Int) {
            curNavEarthLocation.tag = earthLocationArray[recallIdx].tag
            curNavEarthLocation.longitude = earthLocationArray[recallIdx].longitude
            curNavEarthLocation.latitude = earthLocationArray[recallIdx].latitude
            curNavEarthLocation.altitude = earthLocationArray[recallIdx].altitude
            curNavEarthLocation.timeZone = earthLocationArray[recallIdx].timeZone
            curNavEarthLocation.utcDateTime = earthLocationArray[recallIdx].utcDateTime
        }

        fun recallSynCompEarthLocationEntry(recallIdx : Int) {
            synCompRecallEarthLocation = earthLocationArray[recallIdx]
        }

        fun storeRefEarthLocationEntry(storeIdx : Int) {
            earthLocationArray[storeIdx].latitude = curNavEarthLocation.latitude
            earthLocationArray[storeIdx].longitude = curNavEarthLocation.longitude
            earthLocationArray[storeIdx].altitude = curNavEarthLocation.altitude
            earthLocationArray[storeIdx].timeZone = curNavEarthLocation.timeZone
            earthLocationArray[storeIdx].utcDateTime = NavState.curNavDateTimeUTC()
        }
    }
}