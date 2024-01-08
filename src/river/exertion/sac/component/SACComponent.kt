package river.exertion.sac.component

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.ai.msg.Telegram
import com.badlogic.gdx.ai.msg.Telegraph
import river.exertion.kcop.base.Id
import river.exertion.kcop.ecs.component.IComponent
import river.exertion.kcop.view.layout.StatusView
import river.exertion.kcop.view.layout.ViewLayout
import river.exertion.sac.SweetAstroConsoleKlop
import river.exertion.sac.asset.SACDefaultAssetStore
import river.exertion.sac.astro.CelestialSnapshot
import river.exertion.sac.astro.EarthLocation
import river.exertion.sac.astro.Chart
import river.exertion.sac.console.state.*
import river.exertion.sac.view.SACInputProcessor

class SACComponent : IComponent, Telegraph {

    override var componentId = Id.randomId()
    override var isInitialized = false

    var compositeNoRecalc = false

    fun sacRecalc() {
        sacEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()
        sacEarthLocation.recalc()

        compositeNoRecalc = false

        if (refEarthLocation != null) {
            refNatCelestialSnapshot.refEarthLocation = refEarthLocation!!
        } else {
            refNatCelestialSnapshot.refEarthLocation = sacEarthLocation
        }

        refNatCelestialSnapshot.recalc()

        if (synEarthLocation != null) {
            sacCelestialSnapshot.refEarthLocation = sacEarthLocation
            sacCelestialSnapshot.synEarthLocation = synEarthLocation!!

            synNatCelestialSnapshot.refEarthLocation = synEarthLocation!!

            sacCelestialSnapshot.recalc()
            synNatCelestialSnapshot.recalc()

            if (SACInputProcessor.chartStateMachine.isInState(ChartState.COMPOSITE_CHART)) {
                sacCelestialSnapshot = CelestialSnapshot.getCompositeSnapshot(sacCelestialSnapshot, synNatCelestialSnapshot, sacEarthLocation, synEarthLocation!!)
                compositeNoRecalc = true
            }
        }

        if (!compositeNoRecalc) sacCelestialSnapshot.recalc()

        //TODO: implement recalc for chart
        sacChart = Chart(
            Chart.getAspects(
                refNatCelestialSnapshot
                , if (synEarthLocation != null) synNatCelestialSnapshot else refNatCelestialSnapshot
                , SACInputProcessor.chartStateMachine.currentState
                , SACInputProcessor.aspectsStateMachine.currentState
                , SACInputProcessor.timeAspectsStateMachine.currentState
                , SACInputProcessor.aspectOverlayStateMachine.currentState
                , SACInputProcessor.analysisStateMachine.currentState)
            , refNatCelestialSnapshot, if (synEarthLocation != null) synNatCelestialSnapshot else refNatCelestialSnapshot
            , SACInputProcessor.chartStateMachine.currentState, SACInputProcessor.analysisStateMachine.currentState)

        refNatChart = Chart(
            Chart.getAspects(
                refNatCelestialSnapshot
                , refNatCelestialSnapshot
                , ChartState.NATAL_CHART
                , SACInputProcessor.aspectsStateMachine.currentState
                , SACInputProcessor.timeAspectsStateMachine.currentState
                , AspectOverlayState.toggleState(ChartState.SYNASTRY_CHART, SACInputProcessor.aspectOverlayStateMachine.currentState)
                , SACInputProcessor.analysisStateMachine.currentState)
            , refNatCelestialSnapshot, refNatCelestialSnapshot
            , ChartState.NATAL_CHART, SACInputProcessor.analysisStateMachine.currentState)

        if (synEarthLocation != null) {
            synNatChart = Chart(
                Chart.getAspects(
                    synNatCelestialSnapshot
                    , synNatCelestialSnapshot
                    , ChartState.NATAL_CHART
                    , SACInputProcessor.aspectsStateMachine.currentState
                    , SACInputProcessor.timeAspectsStateMachine.currentState
                    , AspectOverlayState.toggleState(ChartState.SYNASTRY_CHART, SACInputProcessor.aspectOverlayStateMachine.currentState)
                    , SACInputProcessor.analysisStateMachine.currentState)
                , synNatCelestialSnapshot, synNatCelestialSnapshot
                , ChartState.NATAL_CHART, SACInputProcessor.analysisStateMachine.currentState)
        }

        dataChanged = false
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

        var dataChanged = true

        //TODO: allow location definitions with lat / long / alt / tz
        //TODO: allow location default set in properties file
        var earthLocationArray = Array(10) { idx ->
            SACDefaultAssetStore.entries.filter { it.name != "Default"}[idx].get()
        }

        var sacEarthLocation = EarthLocation()
        var refEarthLocation : EarthLocation? = null
        var synEarthLocation : EarthLocation? = null

        fun resetEarthLocations() {
            sacEarthLocation.tag = "curNav"
            sacEarthLocation.longitude = SACDefaultAssetStore.Default.get().longitude
            sacEarthLocation.latitude = SACDefaultAssetStore.Default.get().latitude
            sacEarthLocation.altitude = SACDefaultAssetStore.Default.get().altitude
            sacEarthLocation.timeZone = SACDefaultAssetStore.Default.get().timeZone
            sacEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()

            refEarthLocation = null
            synEarthLocation = null
        }

        var sacCelestialSnapshot = CelestialSnapshot(sacEarthLocation)
        var refNatCelestialSnapshot = CelestialSnapshot(sacEarthLocation)
        var synNatCelestialSnapshot = CelestialSnapshot(sacEarthLocation)

        var sacChart = Chart(sacCelestialSnapshot, sacCelestialSnapshot, ChartState.defaultState()
                , AspectsState.defaultState(), TimeAspectsState.defaultState(), AspectOverlayState.defaultState(), AnalysisState.defaultState())
        var refNatChart = Chart(refNatCelestialSnapshot, refNatCelestialSnapshot, ChartState.defaultState()
                , AspectsState.defaultState(), TimeAspectsState.defaultState(), AspectOverlayState.defaultState(), AnalysisState.defaultState())
        var synNatChart = Chart(synNatCelestialSnapshot, synNatCelestialSnapshot, ChartState.defaultState()
                , AspectsState.defaultState(), TimeAspectsState.defaultState(), AspectOverlayState.defaultState(), AnalysisState.defaultState())

        fun recallRefEarthLocationEntry(recallIdx : Int) {
            sacEarthLocation.tag = earthLocationArray[recallIdx].tag
            sacEarthLocation.longitude = earthLocationArray[recallIdx].longitude
            sacEarthLocation.latitude = earthLocationArray[recallIdx].latitude
            sacEarthLocation.altitude = earthLocationArray[recallIdx].altitude
            sacEarthLocation.timeZone = earthLocationArray[recallIdx].timeZone
            sacEarthLocation.utcDateTime = earthLocationArray[recallIdx].utcDateTime

            refEarthLocation = EarthLocation(
                earthLocationArray[recallIdx].tag
                , earthLocationArray[recallIdx].longitude
                , earthLocationArray[recallIdx].latitude
                , earthLocationArray[recallIdx].altitude
                , earthLocationArray[recallIdx].timeZone
                , earthLocationArray[recallIdx].utcDateTime
            )
        }

        fun recallSynCompEarthLocationEntry(recallIdx : Int) {
            synEarthLocation = EarthLocation(
                earthLocationArray[recallIdx].tag
                , earthLocationArray[recallIdx].longitude
                , earthLocationArray[recallIdx].latitude
                , earthLocationArray[recallIdx].altitude
                , earthLocationArray[recallIdx].timeZone
                , earthLocationArray[recallIdx].utcDateTime
            )
        }

        fun storeRefEarthLocationEntry(storeIdx : Int) {
            earthLocationArray[storeIdx].latitude = sacEarthLocation.latitude
            earthLocationArray[storeIdx].longitude = sacEarthLocation.longitude
            earthLocationArray[storeIdx].altitude = sacEarthLocation.altitude
            earthLocationArray[storeIdx].timeZone = sacEarthLocation.timeZone
            earthLocationArray[storeIdx].utcDateTime = NavState.curNavDateTimeUTC()
        }
    }
}