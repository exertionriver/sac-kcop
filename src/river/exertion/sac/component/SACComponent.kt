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
import river.exertion.sac.view.SACLayoutHandler

class SACComponent : IComponent, Telegraph {

    override var componentId = Id.randomId()
    override var isInitialized = false

    var noRecalc = false

    fun headerRecalc() {
        SACLayoutHandler.resetTitleState()

        if (synEarthLocation != null) {
            dataChanged = true
        }

        headerChanged = false
    }

    fun sacRecalc() {
        sacEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()
        sacEarthLocation.recalc()

        noRecalc = false

        if (refEarthLocation != null) {
            refNatCelestialSnapshot.refEarthLocation = refEarthLocation!!
        } else {
            refNatCelestialSnapshot.refEarthLocation = sacEarthLocation
        }

        if (synEarthLocation != null) {
            synNatCelestialSnapshot.refEarthLocation = synEarthLocation!!
            refNatCelestialSnapshot.synEarthLocation = synEarthLocation!!

            synNatCelestialSnapshot.recalc()
        }

        refNatCelestialSnapshot.recalc()

        refNatChart = Chart(
            refNatCelestialSnapshot
            , ChartState.NATAL_CHART
            , (SACInputProcessor.aspectsStateMachine.currentState as AspectsState)
            , (SACInputProcessor.timeAspectsStateMachine.currentState as TimeAspectsState)
            , AspectOverlayState.toggleState(ChartState.SYNASTRY_CHART, (SACInputProcessor.aspectOverlayStateMachine.currentState as AspectOverlayState) )
            , (SACInputProcessor.analysisStateMachine.currentState as AnalysisState)
        )

        if (synEarthLocation != null) {
            synNatChart = Chart(
                synNatCelestialSnapshot
                , ChartState.NATAL_CHART
                , (SACInputProcessor.aspectsStateMachine.currentState as AspectsState)
                , (SACInputProcessor.timeAspectsStateMachine.currentState as TimeAspectsState)
                , AspectOverlayState.toggleState(ChartState.SYNASTRY_CHART, (SACInputProcessor.aspectOverlayStateMachine.currentState as AspectOverlayState) )
                , (SACInputProcessor.analysisStateMachine.currentState as AnalysisState)
            )
        }

        sacChart = if (SACInputProcessor.chartStateMachine.currentState == ChartState.COMBINED_CHART) {
            Chart(
                Chart.getCharacterAspects(refNatChart, synNatChart)
                , refNatCelestialSnapshot
                , ChartState.COMBINED_CHART
            )
        } else {
            Chart(
                refNatCelestialSnapshot,
                if (synEarthLocation != null) synNatCelestialSnapshot else refNatCelestialSnapshot
                , (SACInputProcessor.chartStateMachine.currentState as ChartState)
                , (SACInputProcessor.aspectsStateMachine.currentState as AspectsState)
                , (SACInputProcessor.timeAspectsStateMachine.currentState as TimeAspectsState)
                , (SACInputProcessor.aspectOverlayStateMachine.currentState as AspectOverlayState)
                , (SACInputProcessor.analysisStateMachine.currentState as AnalysisState)
            )
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
        var headerChanged = true

        var earthLocationArray = Array(10) { idx ->
            SACDefaultAssetStore.entries.filter { it.name != "Default"}[idx].get()
        }

        var sacEarthLocation = EarthLocation()
        var refEarthLocation : EarthLocation? = null
        var synEarthLocation : EarthLocation? = null

        fun resetCurNav() {
            sacEarthLocation.tag = "curNav"
            sacEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()

            refEarthLocation = null
            synEarthLocation = null
        }

        fun resetEarthLocations() {
            sacEarthLocation.longitude = SACDefaultAssetStore.Default.get().longitude
            sacEarthLocation.latitude = SACDefaultAssetStore.Default.get().latitude
            sacEarthLocation.altitude = SACDefaultAssetStore.Default.get().altitude
            sacEarthLocation.timeZone = SACDefaultAssetStore.Default.get().timeZone

            resetCurNav()
        }

        var sacCelestialSnapshot = CelestialSnapshot(sacEarthLocation)
        var refNatCelestialSnapshot = CelestialSnapshot(sacEarthLocation)
        var synNatCelestialSnapshot = CelestialSnapshot(sacEarthLocation)

        var sacChart = Chart(sacCelestialSnapshot, sacCelestialSnapshot, ChartState.defaultState
                , AspectsState.defaultState, TimeAspectsState.defaultState, AspectOverlayState.defaultState, AnalysisState.defaultState)
        var refNatChart = Chart(refNatCelestialSnapshot, refNatCelestialSnapshot, ChartState.defaultState
                , AspectsState.defaultState, TimeAspectsState.defaultState, AspectOverlayState.defaultState, AnalysisState.defaultState)
        var synNatChart = Chart(synNatCelestialSnapshot, synNatCelestialSnapshot, ChartState.defaultState
                , AspectsState.defaultState, TimeAspectsState.defaultState, AspectOverlayState.defaultState, AnalysisState.defaultState)

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