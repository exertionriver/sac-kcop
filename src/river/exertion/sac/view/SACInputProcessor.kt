package river.exertion.sac.view

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.ai.fsm.DefaultStateMachine
import river.exertion.kcop.view.MultiKeys
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.*

object SACInputProcessor : InputProcessor {

    val navStateMachine = DefaultStateMachine(this, NavState.defaultState())
    val navDirStateMachine = DefaultStateMachine(this, NavDirState.defaultState())
    val entryStateMachine = DefaultStateMachine(this, EntryState.defaultState())
    val timeAspectsStateMachine = DefaultStateMachine(this, TimeAspectsState.defaultState())
    val analysisStateMachine = DefaultStateMachine(this, AnalysisState.defaultState())
    val detailsStateMachine = DefaultStateMachine(this, DetailsState.defaultState())
    val chartStateMachine = DefaultStateMachine(this, ChartState.defaultState())
    val aspectsStateMachine = DefaultStateMachine(this, AspectsState.defaultState())
    val aspectOverlayStateMachine = DefaultStateMachine(this, AspectOverlayState.defaultState())
    val locationRecallStateMachine = DefaultStateMachine(this, LocationRecallState.defaultState())

    override fun keyDown(keycode: Int): Boolean {

        MultiKeys.keysDown.add(keycode)

        when {
            //escape to console starting state
            MultiKeys.ESCAPE.keyDown() -> {
                navStateMachine.changeState(NavState.defaultState())
                navDirStateMachine.changeState(NavDirState.defaultState())
                entryStateMachine.changeState(EntryState.defaultState())
                timeAspectsStateMachine.changeState(TimeAspectsState.defaultState())
                analysisStateMachine.changeState(AnalysisState.defaultState())
                detailsStateMachine.changeState(DetailsState.defaultState())
                chartStateMachine.changeState(ChartState.defaultState())
                aspectsStateMachine.changeState(AspectsState.defaultState())
                aspectOverlayStateMachine.changeState(AspectOverlayState.defaultState())
                locationRecallStateMachine.changeState(LocationRecallState.defaultState())

                SACComponent.resetEarthLocations()
            }

            //remove modifications, leave navigation
            MultiKeys.USCORE.keysDown() -> {
                entryStateMachine.changeState(EntryState.defaultState())
                timeAspectsStateMachine.changeState(TimeAspectsState.defaultState())
                analysisStateMachine.changeState(AnalysisState.defaultState())
                detailsStateMachine.changeState(DetailsState.defaultState())
                chartStateMachine.changeState(ChartState.defaultState())
                aspectsStateMachine.changeState(AspectsState.defaultState())
                aspectOverlayStateMachine.changeState(AspectOverlayState.defaultState())
            }

            MultiKeys.SPACE.keyDown() -> {
                navStateMachine.changeState(NavState.cyclePause(navStateMachine.currentState))
            }

            MultiKeys.PLUS.keysDown() -> {
                if (chartStateMachine.isInState(ChartState.COMPOSITE_CHART)) {
                    aspectOverlayStateMachine.changeState(AspectOverlayState.toggleState(chartStateMachine.currentState, aspectOverlayStateMachine.currentState))
                }

                chartStateMachine.changeState(ChartState.SYNASTRY_CHART)

                locationRecallStateMachine.changeState(LocationRecallState.CUR_NAV_REF_SYNCOMP_ENTRY)
            }

            MultiKeys.EQUALS.keysDown() -> {
                if (chartStateMachine.isInState(ChartState.SYNASTRY_CHART)) {
                    aspectOverlayStateMachine.changeState(AspectOverlayState.toggleState(chartStateMachine.currentState, aspectOverlayStateMachine.currentState))
                }

                chartStateMachine.changeState(ChartState.COMPOSITE_CHART)

                locationRecallStateMachine.changeState(LocationRecallState.CUR_NAV_REF_SYNCOMP_ENTRY)
            }

            MultiKeys.MINUS.keyDown() -> {
                navDirStateMachine.changeState(NavDirState.cycleState(navDirStateMachine.currentState))

                if (navStateMachine.currentState.isCurrent() ) navStateMachine.changeState(NavState.NAV_SECOND)
            }

            MultiKeys.a.keyDown() -> aspectsStateMachine.changeState(AspectsState.cycleState(aspectsStateMachine.currentState))

            MultiKeys.s.keyDown() -> navStateMachine.changeState(NavState.NAV_SECOND)
            MultiKeys.m.keyDown() -> navStateMachine.changeState(NavState.NAV_MINUTE)
            MultiKeys.h.keyDown() -> navStateMachine.changeState(NavState.NAV_HOUR)
            MultiKeys.d.keyDown() -> navStateMachine.changeState(NavState.NAV_DAY)
            MultiKeys.w.keyDown() -> navStateMachine.changeState(NavState.NAV_WEEK)
            MultiKeys.o.keyDown() -> navStateMachine.changeState(NavState.NAV_MONTH)
            MultiKeys.y.keyDown() -> navStateMachine.changeState(NavState.NAV_YEAR)

            MultiKeys.t.keyDown() -> timeAspectsStateMachine.changeState(TimeAspectsState.cycleState(timeAspectsStateMachine.currentState))

            MultiKeys.p.keyDown() -> aspectOverlayStateMachine.changeState(AspectOverlayState.cycleState(chartStateMachine.currentState, aspectOverlayStateMachine.currentState))

            MultiKeys.C.keysDown() -> analysisStateMachine.changeState(AnalysisState.cycleCharacterState(analysisStateMachine.currentState))
            MultiKeys.E.keysDown() -> analysisStateMachine.changeState(AnalysisState.cycleElementState(analysisStateMachine.currentState))
            MultiKeys.M.keysDown() -> analysisStateMachine.changeState(AnalysisState.cycleModeState(analysisStateMachine.currentState))
            MultiKeys.P.keysDown() -> analysisStateMachine.changeState(AnalysisState.cyclePlanetState(analysisStateMachine.currentState))
            MultiKeys.R.keysDown() -> analysisStateMachine.changeState(AnalysisState.cycleRomanticState(analysisStateMachine.currentState))

            MultiKeys.e.keyDown() -> detailsStateMachine.changeState(DetailsState.cycleState(detailsStateMachine.currentState))

            MultiKeys.numKeyDown() -> {
                val recallIdx = MultiKeys.numPressed()

                if (locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF_SYNCOMP_ENTRY)) {
                    SACComponent.recallSynCompEarthLocationEntry(recallIdx)

                    locationRecallStateMachine.changeState(LocationRecallState.CUR_NAV_REF_SYNCOMP_RECALL)
                } else {
                    navStateMachine.changeState(NavState.LOCATION_RECALL)

                    SACComponent.recallRefEarthLocationEntry(recallIdx)
                }
            }

            //setting numbered profiles
            MultiKeys.numPunctDown() -> {
                navStateMachine.changeState(NavState.LOCATION_STORE)

                val storeIdx = MultiKeys.numPressed()

                SACComponent.storeRefEarthLocationEntry(storeIdx)
            }

            MultiKeys.D.keysDown() -> {
                navStateMachine.changeState(NavState.ENTRY_PAUSED)
                entryStateMachine.changeState(EntryState.DATE_ENTRY)
            }
            MultiKeys.T.keysDown() -> {
                navStateMachine.changeState(NavState.ENTRY_PAUSED)
                entryStateMachine.changeState(EntryState.TIME_ENTRY)
            }
            MultiKeys.O.keysDown() -> {
                navStateMachine.changeState(NavState.ENTRY_PAUSED)
                entryStateMachine.changeState(EntryState.LON_ENTRY)
            }
            MultiKeys.A.keysDown() -> {
                navStateMachine.changeState(NavState.ENTRY_PAUSED)
                entryStateMachine.changeState(EntryState.LAT_ENTRY)
            }
            MultiKeys.L.keysDown() -> {
                navStateMachine.changeState(NavState.ENTRY_PAUSED)
                entryStateMachine.changeState(EntryState.ALT_ENTRY)
            }
            MultiKeys.Z.keysDown() -> {
                navStateMachine.changeState(NavState.ENTRY_PAUSED)
                entryStateMachine.changeState(EntryState.TZ_ENTRY)
            }
        }

        return false
    }

    override fun keyUp(keycode: Int): Boolean {
        MultiKeys.keysDown.remove(keycode)
        return false
    }

    override fun keyTyped(character: Char): Boolean { return false }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean { return false }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean { return false }

    override fun scrolled(amountX: Float, amountY: Float): Boolean { return false }
}