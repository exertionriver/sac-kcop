package river.exertion.sac.console.render

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderEarthLocationTags {

    val tableTag = "earthLocationTags"

    fun dvTable() = DVTable(tableTag = tableTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {

        this.add(DVTextPane().apply { this.tag = "eltLeftSquare" })
        this.add(DVTextPane().apply { this.tag = "eltLeftTag" })

        if (!SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF)) {
            this.add(DVTextPane().apply { this.tag = "eltOperatorTag" })
            this.add(DVTextPane().apply { this.tag = "eltRightTag" })
        }

        this.add(DVTextPane().apply { this.tag = "eltRightSquare" })
    } )

    fun setContent() {
        val locationRecallState = SACInputProcessor.locationRecallStateMachine.currentState

        DVLayoutHandler.currentDvLayout.setTextPaneContent("eltLeftSquare", locationRecallState.eltLeftSquareLabel(), SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("eltLeftTag", locationRecallState.eltLeftTag(), SACLayoutHandler.refEarthLocationFontColor)

        if (!SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("eltOperatorTag", locationRecallState.eltOpTag(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("eltRightTag", locationRecallState.eltRightTag(), SACLayoutHandler.synEarthLocationFontColor)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("eltRightSquare", locationRecallState.eltRightSquareLabel(), SACLayoutHandler.baseValuesFontColor)
    }
}