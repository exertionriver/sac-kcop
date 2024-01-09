package river.exertion.sac.view

import kotlinx.datetime.*
import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.kcop.view.MultiKeys
import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.*
import river.exertion.sac.astro.CelestialData
import river.exertion.sac.astro.base.*
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.celestials.RenderCelestials
import river.exertion.sac.console.render.summaryAspects.RenderSummaryAspects
import river.exertion.sac.console.render.header.RenderHeader
import river.exertion.sac.console.state.EntryState
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.console.state.NavState
import river.exertion.sac.swe.HouseName

object SACCelestialsHousesDVLayout {

    fun housesDetails() : MutableList<DVLayoutCell> {

        val returnCells = mutableListOf<DVLayoutCell>()

        CelestialHouse.entries.forEach { celestialHouseName ->
            returnCells.addAll( mutableListOf(
                DVTextPane().apply { this.tag = "${celestialHouseName}"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.LEFT.tag(); this.padLeft = ".2" },
                DVTextPane().apply { this.tag = "${celestialHouseName}_sign"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "${celestialHouseName}_long"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "${celestialHouseName}_signLong"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".16" },
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
        DVTextPane().apply { this.tag = "houseLatLongHeader"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.CENTER.tag() },
        DVTextPane().apply { this.tag = "houseSign"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
        DVTextPane().apply { this.tag = "houseLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
        DVTextPane().apply { this.tag = "houseSignLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
        DVRow()
    ).apply {this.addAll(housesDetails())}
    )

    fun gridEntries() = AspectCelestial.entries.filter { it.isChartAspectCelestial() }

    fun gridTable() : DVTable = DVTable(tableTag = "grid", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {

        if (SACInputProcessor.chartStateMachine.currentState.isNatComp()) {
            gridEntries().forEachIndexed { rowIdx, chartAspectCelestial ->
                var colIdx = 0

                while (colIdx <= rowIdx) {
                    if (colIdx == rowIdx) {
                        this.add( DVTextPane().apply { this.tag = chartAspectCelestial.name; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
                    } else {
                        this.add( DVTextPane().apply { this.tag = "${chartAspectCelestial.name}_${AspectCelestial.fromOrdinal(colIdx)}"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
                    }
                    colIdx++
                }
                this.add(DVRow())
            }
        } else {
            this.add(DVTextPane().apply { this.tag = "chartSpace"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )

            gridEntries().forEach { aspectCelestialHeader ->
                this.add(DVTextPane().apply { this.tag = "${aspectCelestialHeader.name}_x"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
            }
            this.add(DVRow())

            gridEntries().forEachIndexed { rowIdx, chartAspectCelestialRow ->
                var colIdx = -1

                while (colIdx < gridEntries().size) {
                    if (colIdx == -1) {
                        this.add(DVTextPane().apply { this.tag = "${AspectCelestial.fromOrdinal(rowIdx)!!.name}_y"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
                    } else {
                        this.add( DVTextPane().apply { this.tag = "${chartAspectCelestialRow.name}_${AspectCelestial.fromOrdinal(colIdx)}"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
                    }
                    colIdx++
                }
                this.add(DVRow())
            }
        }
    })

    fun gridSpacer() : DVTable = DVTable(tableTag = "gridSpacer", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
        if (SACInputProcessor.chartStateMachine.currentState.isNatComp()) {
            this.add(
            DVTextPane().apply { this.tag = "gridSpacerPane"; this.width = DVPaneType.DVPDimension.TINY.tag() }
            )
        }
    })

    fun houseGridTables() : DVTable = DVTable(tableTag = "houseGridTables", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>(
        housesTable(),
        gridSpacer(),
        gridTable()
    ))


    fun setLayout() = DVLayout(name = "SACDVLayout", layout = mutableListOf(
        DVTable(tableTag = "dvLayout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            RenderHeader.setLayout(),
            DVRow(),
            RenderCelestials.setLayout(),
            DVRow(),
            houseGridTables()
        )),
        DVTable(tableTag = "davLayout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            RenderSummaryAspects.setLayout()
        ))
    ))

    fun setContent() {

        DVLayoutHandler.currentFontColor = SACLayoutHandler.baseFontColor

        RenderHeader.setContent()
        RenderCelestials.setContent()

        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSystemName", HouseName.getHouseName())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseLatLongHeader", CelestialHouse.celestialHouseLongLatHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSign", CelestialHouse.celestialHouseSignHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseLongitude", CelestialHouse.celestialHouseLongitudeHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSignLongitude", Sign.signLongitudeHeaderLabel)

        val pofData = SACComponent.refNatCelestialSnapshot.partOfFortuneData()
        val posData = SACComponent.refNatCelestialSnapshot.partOfSpiritData()

        CelestialHouse.entries.forEachIndexed { idx, celestialHouse ->
            when {
                (celestialHouse.name == "PART_OF_SPIRIT") -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(celestialHouse.name, celestialHouse.label, SACLayoutHandler.baseValuesFontColor)
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_sign",
                        Sign.signLabelFromCelestialLongitude(posData), Sign.signColor(posData)
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_long", "%1.4f".format(posData), SACLayoutHandler.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_signLong", CelestialData.getFormattedSignLongitude(posData), SACLayoutHandler.baseValuesFontColor )
                }
                (celestialHouse.name == "PART_OF_FORTUNE") -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(celestialHouse.name, celestialHouse.label, SACLayoutHandler.baseValuesFontColor)
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_sign",
                        Sign.signLabelFromCelestialLongitude(pofData), Sign.signColor(pofData)
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_long", "%1.4f".format(pofData), SACLayoutHandler.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_signLong", CelestialData.getFormattedSignLongitude(pofData), SACLayoutHandler.baseValuesFontColor )
                }
                else -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(celestialHouse.name, celestialHouse.label, SACLayoutHandler.baseValuesFontColor)
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_sign",
                        Sign.signLabelFromCelestialLongitude(SACComponent.refNatCelestialSnapshot.refCelestialHouseData[idx]), Sign.signColor(SACComponent.refNatCelestialSnapshot.refCelestialHouseData[idx])
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_long", "%1.4f".format(SACComponent.refNatCelestialSnapshot.refCelestialHouseData[idx]), SACLayoutHandler.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_signLong", CelestialData.getFormattedSignLongitude(SACComponent.refNatCelestialSnapshot.refCelestialHouseData[idx]), SACLayoutHandler.baseValuesFontColor )
                }
            }
        }

        val chartAspects = SACComponent.sacChart.getAspects()

        if (SACInputProcessor.chartStateMachine.currentState.isNatComp()) {
            gridEntries().forEachIndexed { rowIdx, chartAspectCelestial ->
            var colIdx = 0

            while (colIdx <= rowIdx) {
                if (rowIdx == colIdx) {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(chartAspectCelestial.name, chartAspectCelestial.label, SACLayoutHandler.refEarthLocationFontColor)
                } else {
                    val renderAspectType =
                        chartAspects.firstOrNull { it.aspectCelestialFirst == AspectCelestial.fromOrdinal(colIdx) && it.aspectCelestialSecond == chartAspectCelestial }?.aspectAngle?.aspectType ?: AspectType.ASPECT_NONE
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${chartAspectCelestial.name}_${AspectCelestial.fromOrdinal(colIdx)}",
                    renderAspectType.label, renderAspectType.fontColor )
                }
                colIdx++
            } }
        } else {
            gridEntries().forEach { aspectCelestialHeader ->
                DVLayoutHandler.currentDvLayout.setTextPaneContent("${aspectCelestialHeader.name}_x", aspectCelestialHeader.label, SACLayoutHandler.refEarthLocationFontColor)
            }

            gridEntries().forEachIndexed { rowIdx, chartAspectCelestialRow ->
                var colIdx = -1

                while (colIdx < gridEntries().size) {
                    if (colIdx == -1) {
                        DVLayoutHandler.currentDvLayout.setTextPaneContent("${AspectCelestial.fromOrdinal(rowIdx)!!.name}_y", chartAspectCelestialRow.label, SACLayoutHandler.synEarthLocationFontColor)
                    } else {
                        val renderAspectType =
                            chartAspects.firstOrNull { it.aspectCelestialFirst == AspectCelestial.fromOrdinal(colIdx) && it.aspectCelestialSecond == chartAspectCelestialRow }?.aspectAngle?.aspectType ?: AspectType.ASPECT_NONE

                        DVLayoutHandler.currentDvLayout.setTextPaneContent("${chartAspectCelestialRow.name}_${AspectCelestial.fromOrdinal(colIdx)}",
                            renderAspectType.label, renderAspectType.fontColor )
                    }
                    colIdx++
                }
            }
        }

        RenderSummaryAspects.setContent()
    }
}