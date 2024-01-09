package river.exertion.sac.console.render.housesGrid

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.view.SACCelestialsHousesDVLayout
import river.exertion.sac.view.SACInputProcessor

object RenderHousesGrid : IConsoleRender {

    override val layoutTag = "housesGrid"

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {
        this.add(RenderHousesHeader.setLayout())
//      rows are added with header to maintain columns
        if (SACInputProcessor.chartStateMachine.currentState.isNatComp()) {
            this.add(
                DVTextPane().apply { this.tag = "${layoutTag}_gridSpacer"; this.width = DVPaneType.DVPDimension.TINY.tag() }
            )
        }
        this.add(RenderGridRows.setLayout())
    })

    override fun setContent() {
        RenderHousesHeader.setContent()
        RenderHousesRows.setContent()
        RenderGridRows.setContent()
    }
}