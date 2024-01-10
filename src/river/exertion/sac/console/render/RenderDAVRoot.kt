package river.exertion.sac.console.render

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.console.render.summaryAspects.RenderSummaryAspects

object RenderDAVRoot : IConsoleRender {

    override val layoutTag = DVLayout.DavLayoutRootTableTag

    override fun setLayout() = DVTable(tableTag = layoutTag, panes = mutableListOf(
        RenderSummaryAspects.setLayout()
    ))

    override fun setContent() {
        RenderSummaryAspects.setContent()
    }
}