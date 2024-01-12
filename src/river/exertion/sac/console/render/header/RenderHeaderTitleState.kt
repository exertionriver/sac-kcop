package river.exertion.sac.console.render.header

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.Constants
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderHeaderTitleState : IConsoleRender {

    override val layoutTag = "headerTitleState"

    override fun setLayout() = DVTable(tableTag = layoutTag, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {
        this.add(DVTextPane().apply { this.tag = "${layoutTag}_appTitleVersion"; this.width = DVPaneType.DVPDimension.MEDIUM.tag(); this.align = DVAlign.LEFT.tag() })
        this.add(DVTextPane().apply { this.tag = "${layoutTag}_appState"; this.width = DVPaneType.DVPDimension.MEDIUM.tag(); this.align = DVAlign.LEFT.tag() })
        this.add(DVTextPane().apply { this.tag = "${layoutTag}_appStateAttributes"; this.width = DVPaneType.DVPDimension.SMALL.tag(); this.align = DVAlign.LEFT.tag() })
    } )

    override fun setContent() {
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_appTitleVersion","${Constants.APP_NAME} v${Constants.APP_VERSION}")
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_appState",SACInputProcessor.navStateMachine.currentState.getLabel()+SACInputProcessor.navDirStateMachine.currentState.getLabel(), SACLayoutHandler.baseValuesFontColor)

        val stateAttributes =
            SACInputProcessor.aspectOverlayStateMachine.currentState.getLabel() +
                    SACInputProcessor.chartStateMachine.currentState.getLabel() +
                    SACInputProcessor.aspectsStateMachine.currentState.getLabel() +
                    SACInputProcessor.timeAspectsStateMachine.currentState.getLabel()

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_appStateAttributes",stateAttributes, SACLayoutHandler.baseValuesFontColor)

        RenderHeaderEarthLocationTags.setContent()
    }
}