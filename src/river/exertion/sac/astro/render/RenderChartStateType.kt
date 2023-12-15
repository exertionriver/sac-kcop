package river.exertion.sac.astro.render

import river.exertion.sac.console.state.ChartStateType
import river.exertion.sac.console.state.ChartStateType.Companion.decodeChartStateType

object RenderChartStateType {

    fun getChartStateTypesLabel(chartStateTypeEncoding : Int) : String {
        var returnString = ""

        val chartStateTypes = chartStateTypeEncoding.decodeChartStateType()

        var counter = 0

        chartStateTypes.sortedDescending().filter { it.ordinal <= ChartStateType.renderMaxIdx }.forEach {
            returnString += when (it) {
                ChartStateType.REF_NATAL_CHART -> ""
                ChartStateType.SYN_NATAL_CHART -> ""
                else -> ""
            }

            returnString += it.getChartState().getLabel()

            counter++
        }

        (counter..2).forEach {
            returnString += "  "
        }

        return returnString
    }
}