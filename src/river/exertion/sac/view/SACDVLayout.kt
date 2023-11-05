package river.exertion.sac.view

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.render.RenderCelestial
import river.exertion.sac.astro.render.RenderCelestialHouse

object SACDVLayout {

    fun celestialsDetails() : MutableList<DVLayoutCell> {

        val returnCells = mutableListOf<DVLayoutCell>()

        RenderCelestial.entries.forEach { renderCelestial ->
            returnCells.addAll( mutableListOf(
                DVTextPane().apply { this.tag = renderCelestial.name },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_sign" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_long"; this.width = DVPaneType.DVPDimension.TINY.tag() },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_signLong"; this.width = DVPaneType.DVPDimension.TINY.tag() },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_house" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_dist"; this.width = DVPaneType.DVPDimension.TINY.tag() },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_speed"; this.width = DVPaneType.DVPDimension.TINY.tag() },
                DVRow()
            ))
        }
        return returnCells
    }

    fun transitTable() : DVTable = DVTable(tableTag = "transitTable", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
        this.add(
            DVTextPane().apply { this.tag = "transitSpacerPane"; this.width = DVPaneType.DVPDimension.LARGE.tag() }
        )
    })

    private fun celestialsTable() : DVTable = DVTable(tableTag = "celestials", colspan = "3", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTable(tableTag = "celestialsContent", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            DVTextPane().apply { this.tag = "celestialHeader" },
            DVTextPane().apply { this.tag = "signHeader" },
            DVTextPane().apply { this.tag = "celestialLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag()  },
            DVTextPane().apply { this.tag = "signLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag() },
            DVTextPane().apply { this.tag = "celestialHouse" },
            DVTextPane().apply { this.tag = "celestialDistance"; this.width = DVPaneType.DVPDimension.TINY.tag()  },
            DVTextPane().apply { this.tag = "celestialSpeed"; this.width = DVPaneType.DVPDimension.TINY.tag()  },
            DVRow(),
        ).apply {this.addAll(celestialsDetails())}),
        transitTable()
    ))

    fun housesRows() = RenderCelestialHouse.entries.map {it.name}.toMutableList().apply { this.addAll(mutableListOf("PART_OF_FORTUNE", "PART_OF_SPIRIT")) }

    fun housesDetails() : MutableList<DVLayoutCell> {

        val returnCells = mutableListOf<DVLayoutCell>()

        housesRows().forEach { renderCelestialHouseName ->
            returnCells.addAll( mutableListOf(
                DVTextPane().apply { this.tag = renderCelestialHouseName },
                DVTextPane().apply { this.tag = "${renderCelestialHouseName}_sign" },
                DVTextPane().apply { this.tag = "${renderCelestialHouseName}_long"; this.width = DVPaneType.DVPDimension.TINY.tag() },
                DVTextPane().apply { this.tag = "${renderCelestialHouseName}_signLong"; this.width = DVPaneType.DVPDimension.TINY.tag() },
                DVRow()
            ))
        }
        return returnCells
    }

    fun housesTable() : DVTable = DVTable(tableTag = "houses", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTable(tableTag = "houseSystem", colspan = "4", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            DVTextPane().apply { this.tag = "houseSystemName" },
        )),
        DVRow(),
        DVTextPane().apply { this.tag = "houseLatLongHeader"},
        DVTextPane().apply { this.tag = "houseSign" },
        DVTextPane().apply { this.tag = "houseLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag() },
        DVTextPane().apply { this.tag = "houseSignLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag() },
        DVRow()
    ).apply {this.addAll(housesDetails())}
    )

    fun gridEntries() = AspectCelestial.entries.filter { it.isChartAspectCelestial() }

    fun gridTable() : DVTable = DVTable(tableTag = "grid", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
        gridEntries().forEachIndexed { rowIdx, chartAspectCelestial ->
            var colIdx = 0

            while (colIdx <= rowIdx) {
                if (rowIdx == colIdx) {
                    this.add( DVTextPane().apply { this.tag = chartAspectCelestial.name } )
                } else {
                    this.add( DVTextPane().apply { this.tag = "${chartAspectCelestial.name}_${AspectCelestial.fromOrdinal(colIdx)}" } )
                }
                colIdx++
            }
            this.add(DVRow())
        } }
    )

    fun gridSpacer() : DVTable = DVTable(tableTag = "gridSpacer", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
        this.add(
            DVTextPane().apply { this.tag = "gridSpacerPane"; this.width = DVPaneType.DVPDimension.UNIT.tag() }
        )
    })

    fun headerTable() : DVTable = DVTable(tableTag = "header", colspan = "3", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply { this.tag = "AppLabel" }
    ))

    fun dvLayout() = DVLayout(name = "SACDVLayout", layout = mutableListOf(
        DVTable(tableTag = "layout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            headerTable(),
            DVRow(),
            celestialsTable(),
            DVRow(),
            housesTable(),
            gridSpacer(),
            gridTable()
        ))
    ))
}