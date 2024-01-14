package river.exertion.sac.console.render.header

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.Constants
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.state.*
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderHeaderTitleState : IConsoleRender {

    override val layoutTag = "headerTitleState"

    override fun setLayout() = DVTable(tableTag = layoutTag, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {
        this.add(DVTextPane().apply { this.tag = "${layoutTag}_appTitleVersion"; this.width = DVPaneType.DVPDimension.MEDIUM.tag(); this.align = DVAlign.LEFT.tag() })
        this.add(DVTextPane().apply { this.tag = "${layoutTag}_appNavState"; this.width = DVPaneType.DVPDimension.SMALL.tag(); this.align = DVAlign.LEFT.tag() })
        this.add(DVTextPane().apply { this.tag = "${layoutTag}_appStateAttributes"; this.width = DVPaneType.DVPDimension.SMALL.tag(); this.align = DVAlign.LEFT.tag() })
    } )

    override fun setContent() {
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_appTitleVersion"
            ,"${Constants.APP_NAME} v${Constants.APP_VERSION}")
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_appNavState"
            ,"${(SACInputProcessor.navStateMachine.currentState as NavState).label}+${(SACInputProcessor.navDirStateMachine.currentState as NavDirState).label}", SACLayoutHandler.baseValuesFontColor)

        val stateAttributes =
            (SACInputProcessor.aspectOverlayStateMachine.currentState as AspectOverlayState).label +
            (SACInputProcessor.chartStateMachine.currentState as ChartState).label +
            (SACInputProcessor.aspectsStateMachine.currentState as AspectsState).label +
            (SACInputProcessor.timeAspectsStateMachine.currentState as TimeAspectsState).label +
            (SACInputProcessor.aspectsSortStateMachine.currentState as AspectsSortState).label +
            (SACInputProcessor.aspectsFilterStateMachine.currentState as AspectsFilterState).label

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_appStateAttributes",stateAttributes, SACLayoutHandler.baseValuesFontColor)

        RenderHeaderEarthLocationTags.setContent()
    }
}