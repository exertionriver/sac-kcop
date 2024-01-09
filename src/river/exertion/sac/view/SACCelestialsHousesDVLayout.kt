package river.exertion.sac.view

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.console.render.celestials.RenderCelestials
import river.exertion.sac.console.render.header.RenderHeader
import river.exertion.sac.console.render.housesGrid.RenderHousesGrid
import river.exertion.sac.console.render.summaryAspects.RenderSummaryAspects

object SACCelestialsHousesDVLayout {

    fun setLayout() = DVLayout(name = "SACDVLayout", layout = mutableListOf(
        DVTable(tableTag = "dvLayout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            RenderHeader.setLayout(),
            DVRow(),
            RenderCelestials.setLayout(),
            DVRow(),
            RenderHousesGrid.setLayout()
        )),
        DVTable(tableTag = "davLayout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            RenderSummaryAspects.setLayout()
        ))
    ))

    fun setContent() {
        DVLayoutHandler.currentFontColor = SACLayoutHandler.baseFontColor

        RenderHeader.setContent()
        RenderCelestials.setContent()
        RenderHousesGrid.setContent()

        RenderSummaryAspects.setContent()

    }
}