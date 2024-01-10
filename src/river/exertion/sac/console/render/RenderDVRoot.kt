package river.exertion.sac.console.render

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.console.render.celestials.RenderCelestials
import river.exertion.sac.console.render.header.RenderHeader
import river.exertion.sac.console.render.header.RenderHeaderTitleState
import river.exertion.sac.console.render.housesGrid.RenderHousesGrid

object RenderDVRoot : IConsoleRender {

    override val layoutTag = DVLayout.DvLayoutRootTableTag

    override fun setLayout() = DVTable(tableTag = layoutTag, panes = mutableListOf(
        RenderHeader.setLayout(),
        DVRow(),
        RenderCelestials.setLayout(),
        DVRow(),
        RenderHousesGrid.setLayout()
    ))

    override fun setContent() {
        RenderHeader.setContent()
        RenderCelestials.setContent()
        RenderHousesGrid.setContent()
    }

    fun resetTitleState() {
        RenderHeaderTitleState.setContent()
    }
}