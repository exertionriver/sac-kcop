package river.exertion.sac.view

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.Constants
import river.exertion.sac.astro.base.*
import river.exertion.sac.astro.render.*
import river.exertion.sac.astro.state.*
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.state.AspectOverlayState
import river.exertion.sac.console.state.AspectsState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.TimeAspectsState
import river.exertion.sac.swe.HouseName

object SACCelestialsHousesDVLayout {

    fun celestialsDetails() : MutableList<DVLayoutCell> {

        val returnCells = mutableListOf<DVLayoutCell>()

        RenderCelestial.entries.forEach { renderCelestial ->
            returnCells.addAll( mutableListOf(
                DVTextPane().apply { this.tag = renderCelestial.name; this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_sign"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag(); this.padLeft = ".3" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_long"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".16" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_signLong"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".16" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_house"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag(); this.padLeft = ".32" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_dist"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".22" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_speed"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".22" },
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
            DVTextPane().apply { this.tag = "celestialHeader"; this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "signHeader"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "celestialLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "signLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "celestialHouse"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "celestialDistance"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
            DVTextPane().apply { this.tag = "celestialSpeed"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
            DVRow(),
        ).apply {this.addAll(celestialsDetails())}),
        transitTable()
    ))

    fun housesRows() = RenderCelestialHouse.entries.map {it.name}.toMutableList().apply { this.addAll(mutableListOf("PART_OF_FORTUNE", "PART_OF_SPIRIT")) }

    fun housesDetails() : MutableList<DVLayoutCell> {

        val returnCells = mutableListOf<DVLayoutCell>()

        housesRows().forEach { renderCelestialHouseName ->
            returnCells.addAll( mutableListOf(
                DVTextPane().apply { this.tag = renderCelestialHouseName; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.LEFT.tag(); this.padLeft = ".2" },
                DVTextPane().apply { this.tag = "${renderCelestialHouseName}_sign"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "${renderCelestialHouseName}_long"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "${renderCelestialHouseName}_signLong"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".16" },
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

    fun headerTable() : DVTable = DVTable(tableTag = "header", colspan = "2", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTable(tableTag = "headerText", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            DVTable(tableTag = "titleState", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
                DVTextPane().apply { this.tag = "appTitleVersion"; this.width = DVPaneType.DVPDimension.MEDIUM.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "appState"; this.width = DVPaneType.DVPDimension.MEDIUM.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "appStateAttributes"; this.width = DVPaneType.DVPDimension.SMALL.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "appProfiles"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() }
            )),
            DVRow(),
            DVTable(tableTag = "infoRows", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
                DVTextPane().apply { this.tag = "localTimeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "localTime"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag()  },
                DVTextPane().apply { this.tag = "localDateLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "localDate"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "localTimezoneLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "LocalTimezone"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "localLatitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "localLatitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVRow(),
                DVTextPane().apply { this.tag = "utcTimeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "utcTime"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "utcDateLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "utcDate"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "utcTimezoneLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "utcTimezone"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "localLongitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "localLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
            )),
        )),
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

    var celestialSnapshot : CelestialSnapshot = SACComponent.sacCelestialSnapshot
    var stateChart : StateChart = SACComponent.sacChart

    fun rebuild() {
        DVLayoutHandler.currentFontColor = SACLayout.baseFontColor

        DVLayoutHandler.currentDvLayout.setTextPaneContent("appTitleVersion","${Constants.APP_NAME} v${Constants.APP_VERSION}")
        DVLayoutHandler.currentDvLayout.setTextPaneContent("appState",SACInputProcessor.navStateMachine.currentState.getLabel(), SACLayout.baseValuesFontColor)

        val stateAttributes =
            SACInputProcessor.aspectOverlayStateMachine.currentState.getLabel() +
            SACInputProcessor.chartStateMachine.currentState.getLabel() +
            SACInputProcessor.aspectsStateMachine.currentState.getLabel() +
            SACInputProcessor.timeAspectsStateMachine.currentState.getLabel()

        DVLayoutHandler.currentDvLayout.setTextPaneContent("appStateAttributes",stateAttributes, SACLayout.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("appProfiles", "[exR]", SACLayout.baseValuesFontColor)

        DVLayoutHandler.currentDvLayout.setTextPaneContent("localTimeLabel", RenderEarthLocation.getEarthLocalTimeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localTime", "${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.hour)}:${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.minute)}:${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.second)}", SACLayout.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localDateLabel", RenderEarthLocation.getEarthLocalDateLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localDate", "${"%4d".format(celestialSnapshot.refEarthLocation.localDateTime.year)}.${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.monthNumber)}.${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.dayOfMonth)}", SACLayout.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localTimezoneLabel", RenderEarthLocation.getEarthTimezoneLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("LocalTimezone", celestialSnapshot.refEarthLocation.getTimezoneOffsetString(), SACLayout.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLatitudeLabel", RenderEarthLocation.getEarthLatitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLatitude", "%1.4f".format(celestialSnapshot.refEarthLocation.latitude), SACLayout.baseValuesFontColor)

        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTimeLabel", RenderEarthLocation.getEarthUTCTimeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTime", "${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.hour)}:${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.minute)}:${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.second)}", SACLayout.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDateLabel", RenderEarthLocation.getEarthUTCDateLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDate", "${"%4d".format(celestialSnapshot.refEarthLocation.utcDateTime.year)}.${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.monthNumber)}.${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.dayOfMonth)}", SACLayout.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTimezoneLabel", RenderEarthLocation.getUTCLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTimezone", "0.0")
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLongitudeLabel", RenderEarthLocation.getEarthLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLongitude", "%1.4f".format(celestialSnapshot.refEarthLocation.longitude), SACLayout.baseValuesFontColor)


        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHeader",RenderCelestial.getCelestialsLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signHeader",RenderCelestial.getCelestialsSignLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialLongitude",RenderCelestial.getCelestialsLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signLongitude", RenderCelestialHouse.getHousesLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHouse",RenderCelestial.getCelestialsHouseLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialDistance",RenderCelestial.getCelestialsDistanceLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialSpeed",RenderCelestial.getCelestialsLongitudeSpeedLabel())
        RenderCelestial.entries.forEachIndexed { idx, renderCelestial ->
            DVLayoutHandler.currentDvLayout.setTextPaneContent(renderCelestial.name, renderCelestial.getLabel(), SACLayout.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_sign",
                RenderSign.getSignLabelFromCelestialLongitude(
                    celestialSnapshot.refCelestialData[idx].longitude,
                    celestialSnapshot.refCelestialData[idx].longitudeSpeed
                ), RenderSign.getSignColor(celestialSnapshot.refCelestialData[idx].longitude)
            )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_long", "%1.4f".format(celestialSnapshot.refCelestialData[idx].longitude), SACLayout.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_signLong", CelestialData.getFormattedSignLongitude(celestialSnapshot.refCelestialData[idx].longitude), SACLayout.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_house", RenderCelestialHouse.celestialHouseLabel(celestialSnapshot.refCelestialData[idx].celestialHouse), SACLayout.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_dist", "%1.4f".format(celestialSnapshot.refCelestialData[idx].distance), SACLayout.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_speed", "%1.4f".format(celestialSnapshot.refCelestialData[idx].longitudeSpeed), SACLayout.baseValuesFontColor )
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSystemName", HouseName.getHouseName())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseLatLongHeader",RenderCelestialHouse.getHousesLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSign",RenderCelestialHouse.getHousesSignLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseLongitude",RenderCelestialHouse.getHousesLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSignLongitude", RenderSign.getSignLongitudeLabel())

        val pofData = celestialSnapshot.partOfFortuneData()
        val posData = celestialSnapshot.partOfSpiritData()

        housesRows().forEachIndexed { idx, renderCelestialHouseName ->
            when {
                (renderCelestialHouseName == "PART_OF_SPIRIT") -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(renderCelestialHouseName, RenderCelestialHouse.getPartOfSpiritLabel(), SACLayout.baseValuesFontColor)
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_sign",
                        RenderSign.getSignLabelFromCelestialLongitude(posData), RenderSign.getSignColor(posData)
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_long", "%1.4f".format(posData), SACLayout.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_signLong", CelestialData.getFormattedSignLongitude(posData), SACLayout.baseValuesFontColor )
                }
                (renderCelestialHouseName == "PART_OF_FORTUNE") -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(renderCelestialHouseName, RenderCelestialHouse.getPartOfFortuneLabel(), SACLayout.baseValuesFontColor)
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_sign",
                        RenderSign.getSignLabelFromCelestialLongitude(pofData), RenderSign.getSignColor(pofData)
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_long", "%1.4f".format(pofData), SACLayout.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_signLong", CelestialData.getFormattedSignLongitude(pofData), SACLayout.baseValuesFontColor )
                }
                else -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(renderCelestialHouseName, RenderCelestialHouse.fromOrdinal(idx)!!.getLabel(), SACLayout.baseValuesFontColor)
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_sign",
                        RenderSign.getSignLabelFromCelestialLongitude(celestialSnapshot.refCelestialHouseData[idx]), RenderSign.getSignColor(celestialSnapshot.refCelestialHouseData[idx])
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_long", "%1.4f".format(celestialSnapshot.refCelestialHouseData[idx]), SACLayout.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_signLong", CelestialData.getFormattedSignLongitude(celestialSnapshot.refCelestialHouseData[idx]), SACLayout.baseValuesFontColor )
                }
            }
        }

        val chartAspects = stateChart.getStateAspects()

        AspectCelestial.entries.filter { it.isChartAspectCelestial() }.forEachIndexed { rowIdx, chartAspectCelestial ->
            var colIdx = 0

            while (colIdx <= rowIdx) {
                if (rowIdx == colIdx) {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(chartAspectCelestial.name, chartAspectCelestial.renderAspectCelestial().getLabel())
                } else {
                    val renderAspectType = RenderAspectType.fromName(
                        chartAspects.firstOrNull { it.aspectCelestialFirst == AspectCelestial.fromOrdinal(colIdx) && it.aspectCelestialSecond == chartAspectCelestial }?.aspectAngle?.getAspectType()?.name ?: RenderAspectType.ASPECT_NONE.name
                    )!!
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${chartAspectCelestial.name}_${AspectCelestial.fromOrdinal(colIdx)}",
                    renderAspectType.getLabel(), renderAspectType.getLabelColor() )
                }
                colIdx++
            }
        }

    }
}