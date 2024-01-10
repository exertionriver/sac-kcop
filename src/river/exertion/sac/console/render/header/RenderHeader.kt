package river.exertion.sac.console.render.header

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.console.render.IConsoleRender

object RenderHeader : IConsoleRender {

    override val layoutTag = "header"

    override fun setLayout() = DVTable(tableTag = layoutTag, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {
        this.add(RenderHeaderTitleState.setLayout())
        this.add(RenderHeaderEarthLocationTags.setLayout())
        this.add(DVRow())
        this.add(RenderHeaderEarthLocationRows.setLayout())
    } )

    override fun setContent() {
        RenderHeaderTitleState.setContent()
        RenderHeaderEarthLocationTags.setContent()
        RenderHeaderEarthLocationRows.setContent()
    }
}