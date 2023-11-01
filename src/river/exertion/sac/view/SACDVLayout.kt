package river.exertion.sac.view

import river.exertion.kcop.view.layout.displayViewLayout.*

object SACDVLayout {

    fun headerTable() = DVTable(tableTag = "header", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply {
            this.tag = "AppLabel"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        }
    ))

    fun celestialsTable() = DVTable(tableTag = "celestials", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        celestialHeader()
    ))

    //colspan=21
    fun celestialHeader() = DVTable(tableTag = "celestialHeader", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply {
            this.tag = "celestialHeader"
            this.width = DVPaneType.DVPDimension.UNIT.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVTextPane().apply {
            this.tag = "signHeader"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVTextPane().apply {
            this.tag = "celestialLongitude"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVTextPane().apply {
            this.tag = "signLongitude"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVTextPane().apply {
            this.tag = "celestialHouse"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVTextPane().apply {
            this.tag = "celestialDistance"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVTextPane().apply {
            this.tag = "celestialSpeed"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
    ))


    fun housesTable() = DVTable(tableTag = "houses", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply {
            this.tag = "AppLabel3"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        }
    ))

    fun gridTable() = DVTable(tableTag = "grid", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply {
            this.tag = "AppLabel4"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
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