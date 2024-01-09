package river.exertion.sac.console.render.celestials

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.view.SACCelestialsHousesDVLayout

object RenderCelestials : IConsoleRender {

    override val layoutTag = "celestials"

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf(
        RenderCelestialsHeader.setLayout()
//      rows are added with header to maintain columns
//        this.add(DVRow())
//        this.add(RenderCelestialsRows.setLayout())
    ))

    override fun setContent() {
        RenderCelestialsHeader.setContent()
        RenderCelestialsRows.setContent()
    }
}