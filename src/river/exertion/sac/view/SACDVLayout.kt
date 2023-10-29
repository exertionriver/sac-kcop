package river.exertion.sac.view

import river.exertion.kcop.view.layout.displayViewLayout.*

object SACDVLayout {

    fun dvLayout() = DVLayout(name = "SACDVLayout", layout = mutableListOf(
        DVTable(tableTag = "layout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            DVTextPane().apply {
                this.tag = "AppLabel"
                this.width = DVPaneType.DVPDimension.FULL.tag()
                this.height = DVPaneType.DVPDimension.LARGE.tag()
            },
            DVRow(),
            DVTextPane().apply {
                this.tag = "AppLabel2"
                this.width = DVPaneType.DVPDimension.FULL.tag()
                this.height = DVPaneType.DVPDimension.TITLE.tag()
            }
        ))
    ))
}