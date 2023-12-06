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
        DVLayoutHandler.currentDvLayout.setTextPaneContent("eltLeftSquare", SACInputProcessor.locationRecallStateMachine.currentState.getLabels()[0], SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("eltLeftTag", SACInputProcessor.locationRecallStateMachine.currentState.getLabels()[1], SACLayoutHandler.refEarthLocationFontColor)

        if (!SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("eltOperatorTag", SACInputProcessor.locationRecallStateMachine.currentState.getLabels()[2], SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("eltRightTag", SACInputProcessor.locationRecallStateMachine.currentState.getLabels()[3], SACLayoutHandler.synEarthLocationFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("eltRightSquare", SACInputProcessor.locationRecallStateMachine.currentState.getLabels()[4], SACLayoutHandler.baseValuesFontColor)
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("eltRightSquare", SACInputProcessor.locationRecallStateMachine.currentState.getLabels()[2], SACLayoutHandler.baseValuesFontColor)
        }
    }
}