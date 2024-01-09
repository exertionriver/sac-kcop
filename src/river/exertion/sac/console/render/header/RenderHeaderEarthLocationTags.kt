package river.exertion.sac.console.render.header

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderHeaderEarthLocationTags : IConsoleRender {

    override val layoutTag = "headerEarthLocationTags"

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {

        this.add(DVTextPane().apply { this.tag = "${layoutTag}_eltLeftSquare" })
        this.add(DVTextPane().apply { this.tag = "${layoutTag}_eltLeftTag" })

        if (!SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF)) {
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_eltOperatorTag" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_eltRightTag" })
        }

        this.add(DVTextPane().apply { this.tag = "${layoutTag}_eltRightSquare" })
    } )

    override fun setContent() {
        val locationRecallState = SACInputProcessor.locationRecallStateMachine.currentState

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_eltLeftSquare", locationRecallState.eltLeftSquareLabel(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_eltLeftTag", locationRecallState.eltLeftTag(), SACLayoutHandler.refEarthLocationFontColor)

        if (!SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_eltOperatorTag", locationRecallState.eltOpTag(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_eltRightTag", locationRecallState.eltRightTag(), SACLayoutHandler.synEarthLocationFontColor)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_eltRightSquare", locationRecallState.eltRightSquareLabel(), SACLayoutHandler.baseValuesFontColor)
    }
}