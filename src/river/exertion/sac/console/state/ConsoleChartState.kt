package river.exertion.sac.console.state

import river.exertion.sac.Constants.KEY_EQUALS
import river.exertion.sac.Constants.KEY_ESC
import river.exertion.sac.Constants.KEY_PLUS
import river.exertion.sac.Constants.KEY_USCORE
import river.exertion.sac.Constants.KEY_x
import river.exertion.sac.astro.state.ChartState

@ExperimentalUnsignedTypes
object ConsoleChartState {

    fun getDefaultState() = ChartState.NATAL_CHART

    fun ChartState.getNextState(input: Int) = this.getState(input)

    fun ChartState.getState(input: Int): ChartState {

        return when (input) {
            KEY_PLUS -> ChartState.SYNASTRY_CHART
            KEY_EQUALS -> ChartState.COMPOSITE_CHART
            KEY_USCORE -> ChartState.NATAL_CHART
            KEY_ESC -> ChartState.NATAL_CHART

            KEY_x -> getDefaultState()
            else -> this
        }
    }

    fun isPresentChart(curChartState: ChartState, curProfileIdx: Int, synProfileIdx: Int) : Boolean {
        return (curChartState == ChartState.NATAL_CHART) //&& (Profiles.isCurNavProfile(curProfileIdx)) && (!Profiles.isStoredProfile(synProfileIdx)))
    }
    fun isPresentCompositeChart(curChartState: ChartState, curProfileIdx: Int, synProfileIdx: Int) : Boolean {
        return (curChartState == ChartState.COMPOSITE_CHART) //&& (Profiles.isCurNavProfile(curProfileIdx))  && (Profiles.isStoredProfile(synProfileIdx)) )
    }
    fun isPresentTransitChart(curChartState: ChartState, curProfileIdx: Int, synProfileIdx: Int) : Boolean {
        return (curChartState == ChartState.SYNASTRY_CHART) //&& (Profiles.isCurNavProfile(curProfileIdx))  && (Profiles.isStoredProfile(synProfileIdx)) )
    }
    fun isProfileChart(curChartState: ChartState, curProfileIdx: Int, synProfileIdx: Int) : Boolean {
        return (curChartState == ChartState.NATAL_CHART) //&& (Profiles.isStoredProfile(curProfileIdx)) && (!Profiles.isStoredProfile(synProfileIdx)))
    }
    fun isProfileCompositeChart(curChartState: ChartState, curProfileIdx: Int, synProfileIdx: Int) : Boolean {
        return (curChartState == ChartState.COMPOSITE_CHART) //&& (Profiles.isStoredProfile(curProfileIdx)) && (Profiles.isStoredProfile(synProfileIdx) ) )
    }
    fun isProfileSynastryChart(curChartState: ChartState, curProfileIdx: Int, synProfileIdx: Int) : Boolean {
        return (curChartState == ChartState.SYNASTRY_CHART) //&& (Profiles.isStoredProfile(curProfileIdx)) && (Profiles.isStoredProfile(synProfileIdx)) )
    }
}