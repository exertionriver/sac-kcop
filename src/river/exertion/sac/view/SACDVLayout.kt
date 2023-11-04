package river.exertion.sac.view

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.render.RenderCelestial
import river.exertion.sac.astro.render.RenderCelestialHouse

object SACDVLayout {

    fun headerTable() : DVTable = DVTable(tableTag = "header", colspan = "2", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTextPane().apply {
            this.tag = "AppLabel"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        }
    ))

    private fun celestialsTable() : DVTable = DVTable(tableTag = "celestials", colspan = "2", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        celestialsHeader(),
        DVRow(),
        celestialsDetails()
    ))

    //colspan=21
    fun celestialsHeader() : DVTable = DVTable(tableTag = "celestialsHeader", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
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
        }
    ))

    fun celestialsDetails() : DVTable = DVTable(tableTag = "celestialsDetails", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
        RenderCelestial.entries.forEach { renderCelestial ->
            this.addAll(mutableListOf(
                DVTextPane().apply {
                    this.tag = renderCelestial.name
                    this.width = DVPaneType.DVPDimension.UNIT.tag()
                    this.height = DVPaneType.DVPDimension.UNIT.tag()
                },
                DVTextPane().apply {
                    this.tag = "${renderCelestial.name}_sign"
                    this.width = DVPaneType.DVPDimension.TINY.tag()
                    this.height = DVPaneType.DVPDimension.UNIT.tag()
                },
                DVTextPane().apply {
                    this.tag = "${renderCelestial.name}_long"
                    this.width = DVPaneType.DVPDimension.TINY.tag()
                    this.height = DVPaneType.DVPDimension.UNIT.tag()
                },
                DVTextPane().apply {
                    this.tag = "${renderCelestial.name}_signLong"
                    this.width = DVPaneType.DVPDimension.TINY.tag()
                    this.height = DVPaneType.DVPDimension.UNIT.tag()
                },
                DVTextPane().apply {
                    this.tag = "${renderCelestial.name}_house"
                    this.width = DVPaneType.DVPDimension.TINY.tag()
                    this.height = DVPaneType.DVPDimension.UNIT.tag()
                },
                DVTextPane().apply {
                    this.tag = "${renderCelestial.name}_dist"
                    this.width = DVPaneType.DVPDimension.TINY.tag()
                    this.height = DVPaneType.DVPDimension.UNIT.tag()
                },
                DVTextPane().apply {
                    this.tag = "${renderCelestial.name}_speed"
                    this.width = DVPaneType.DVPDimension.TINY.tag()
                    this.height = DVPaneType.DVPDimension.UNIT.tag()
                },
                DVRow()
            ))
        }
    })

    fun housesRows() = RenderCelestialHouse.entries.map {it.name}.toMutableList().apply { this.addAll(mutableListOf("PART_OF_FORTUNE", "PART_OF_SPIRIT")) }

    fun housesDetails() : MutableList<DVLayoutCell> {

        val returnCells = mutableListOf<DVLayoutCell>()

        housesRows().forEach { renderCelestialHouseName ->
            returnCells.addAll(
                mutableListOf(
                    DVTextPane().apply {
                        this.tag = renderCelestialHouseName
                        this.width = DVPaneType.DVPDimension.UNIT.tag()
                        this.height = DVPaneType.DVPDimension.UNIT.tag()
                    },
                    DVTextPane().apply {
                        this.tag = "${renderCelestialHouseName}_sign"
                        this.width = DVPaneType.DVPDimension.TINY.tag()
                        this.height = DVPaneType.DVPDimension.UNIT.tag()
                    },
                    DVTextPane().apply {
                        this.tag = "${renderCelestialHouseName}_long"
                        this.width = DVPaneType.DVPDimension.TINY.tag()
                        this.height = DVPaneType.DVPDimension.UNIT.tag()
                    },
                    DVTextPane().apply {
                        this.tag = "${renderCelestialHouseName}_signLong"
                        this.width = DVPaneType.DVPDimension.TINY.tag()
                        this.height = DVPaneType.DVPDimension.UNIT.tag()
                    },
                    DVRow()
                )
            )
        }
        return returnCells
    }

    fun housesTable() : DVTable = DVTable(tableTag = "houses", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTable(tableTag = "houseSystem", colspan = "4", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            DVTextPane().apply {
                this.tag = "houseSystemName"
                this.width = DVPaneType.DVPDimension.TINY.tag()
                this.height = DVPaneType.DVPDimension.UNIT.tag()
            },
        )),
        DVRow(),
        DVTextPane().apply {
            this.tag = "houseLatLongHeader"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVTextPane().apply {
            this.tag = "houseSign"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVTextPane().apply {
            this.tag = "houseLongitude"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVTextPane().apply {
            this.tag = "houseSignLongitude"
            this.width = DVPaneType.DVPDimension.TINY.tag()
            this.height = DVPaneType.DVPDimension.UNIT.tag()
        },
        DVRow()
    ).apply {this.addAll(housesDetails())}
    )

    fun gridTable() : DVTable = DVTable(tableTag = "grid", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
        AspectCelestial.entries.filter { it.isChartAspectCelestial() }.forEachIndexed { rowIdx, chartAspectCelestial ->
            var colIdx = 0

            while (colIdx <= rowIdx) {
                if (rowIdx == colIdx) {
                    this.add(
                        DVTextPane().apply {
                            this.tag = chartAspectCelestial.name
                            this.width = DVPaneType.DVPDimension.UNIT.tag()
                            this.height = DVPaneType.DVPDimension.UNIT.tag()
                        }
                    )
                } else {
                    this.add(
                        DVTextPane().apply {
                            this.tag = "${chartAspectCelestial.name}_${AspectCelestial.fromOrdinal(colIdx)}"
                            this.width = DVPaneType.DVPDimension.UNIT.tag()
                            this.height = DVPaneType.DVPDimension.UNIT.tag()
                        }
                    )
                }
                colIdx++
            }
            this.add(DVRow())
        } }
    )

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