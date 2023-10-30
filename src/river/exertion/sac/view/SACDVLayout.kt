package river.exertion.sac.view

import river.exertion.kcop.view.layout.displayViewLayout.*

object SACDVLayout {

    fun headerTable() = DVTable(tableTag = "header", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply {
            this.tag = "AppLabel"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.TINY.tag()
        }
    ))

    fun celestialsTable() = DVTable(tableTag = "celestials", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply {
            this.tag = "AppLabel2"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.TINY.tag()
        }
    ))

    fun housesTable() = DVTable(tableTag = "houses", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply {
            this.tag = "AppLabel3"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.TINY.tag()
        }
    ))

    fun gridTable() = DVTable(tableTag = "grid", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply {
            this.tag = "AppLabel4"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.TINY.tag()
        }
    ))

    fun dvLayout() = DVLayout(name = "SACDVLayout", layout = mutableListOf(
        DVTable(tableTag = "layout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            headerTable(),
            DVRow(),
            celestialsTable(),
            DVRow(),
            housesTable(),
            gridTable()
        ))
    ))
}