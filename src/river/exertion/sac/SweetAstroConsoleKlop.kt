package river.exertion.sac

import river.exertion.kcop.base.Id
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayout
import river.exertion.kcop.view.klop.IDisplayViewKlop

object SweetAstroConsoleKlop : IDisplayViewKlop {

    override var id = Id.randomId()

    override var tag = "sac"

    override fun load() { }

    override fun unload() { hideView() }

    override fun inputProcessor() = SACInputProcessor

    override fun displayViewLayoutHandler() = SACLayout

    override fun dispose() { }
}