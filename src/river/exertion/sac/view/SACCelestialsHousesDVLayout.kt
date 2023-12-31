package river.exertion.sac.view

import kotlinx.datetime.*
import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.kcop.view.MultiKeys
import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.Constants
import river.exertion.sac.astro.*
import river.exertion.sac.astro.base.CelestialData
import river.exertion.sac.astro.CelestialSnapshot
import river.exertion.sac.astro.state.StateChart
import river.exertion.sac.astro.value.ValueChart
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.RenderEarthLocationTags
import river.exertion.sac.console.render.RenderSummaryAspects
import river.exertion.sac.console.state.EntryState
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.console.state.NavState
import river.exertion.sac.swe.HouseName

object SACCelestialsHousesDVLayout {

    fun celestialsDetails() : MutableList<DVLayoutCell> {

        val returnCells = mutableListOf<DVLayoutCell>()

        Celestial.entries.forEach { renderCelestial ->
            returnCells.addAll( mutableListOf(
                DVTextPane().apply { this.tag = renderCelestial.name; this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_sign"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag(); this.padLeft = ".3" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_long"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".16" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_signLong"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".16" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_house"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag(); this.padLeft = ".32" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_dist"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".22" },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_speed"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".22" },
            ))
            returnCells.addAll( mutableListOf(
                DVTextPane().apply { this.tag = "${renderCelestial.name}_transitHouse"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "${renderCelestial.name}_transitCelestials"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() }
            ))
            returnCells.add(DVRow())
        }
        return returnCells
    }

    fun transitTable() : DVTable = DVTable(tableTag = "transitTable", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
        this.add(
            DVTextPane().apply { this.tag = "transitSpacerPane"; this.width = DVPaneType.DVPDimension.LARGE.tag() }
        )
    })

    private fun celestialsTable() : DVTable = DVTable(tableTag = "celestials", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTable(tableTag = "celestialsContent", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
            this.addAll( mutableListOf(
                DVTextPane().apply { this.tag = "celestialHeader"; this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "signHeader"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "celestialLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "signLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "celestialHouse"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "celestialDistance"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
                DVTextPane().apply { this.tag = "celestialSpeed"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  }
            ))
            this.addAll( mutableListOf(
                DVTextPane().apply { this.tag = "transitHouse"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
                DVTextPane().apply { this.tag = "transitCelestials"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() }
            ))
            this.add(DVRow())
            this.addAll(celestialsDetails())
        }
    )))

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

    fun headerTable() : DVTable = DVTable(tableTag = "header", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
        DVTable(tableTag = "headerText", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            DVTable(tableTag = "titleState", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
                DVTextPane().apply { this.tag = "appTitleVersion"; this.width = DVPaneType.DVPDimension.MEDIUM.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "appState"; this.width = DVPaneType.DVPDimension.MEDIUM.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "appStateAttributes"; this.width = DVPaneType.DVPDimension.SMALL.tag(); this.align = DVAlign.LEFT.tag() },
                RenderEarthLocationTags.setLayout()
            )),
            DVRow(),
            DVTable(tableTag = "infoRows", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
                DVTextPane().apply { this.tag = "localTimeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "localTime"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag()  },
                DVTextPane().apply { this.tag = "localDateLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "localDate"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "localLatitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "localLatitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "localLongitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "localLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVRow(),
                DVTextPane().apply { this.tag = "utcTimeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "utcTime"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "utcDateLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "utcDate"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() },
                DVTextPane().apply { this.tag = "localTimezoneLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "LocalTimezone"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
                DVTextPane().apply { this.tag = "localAltitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
                DVTextPane().apply { this.tag = "localAltitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" },
            )),
        )),
    ))

    fun dvLayout() = DVLayout(name = "SACDVLayout", layout = mutableListOf(
        DVTable(tableTag = "dvLayout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            headerTable(),
            DVRow(),
            celestialsTable(),
            DVRow(),
            houseGridTables()
        )),
        DVTable(tableTag = "davLayout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            RenderSummaryAspects.setLayout()
        ))
    ))

    var celestialSnapshot : CelestialSnapshot = SACComponent.refNatCelestialSnapshot
    var stateChart : StateChart = SACComponent.sacStateChart
    var valueChart : ValueChart = SACComponent.sacValueChart

    fun rebuild() {
        DVLayoutHandler.currentFontColor = SACLayoutHandler.baseFontColor

        DVLayoutHandler.currentDvLayout.setTextPaneContent("appTitleVersion","${Constants.APP_NAME} v${Constants.APP_VERSION}")
        DVLayoutHandler.currentDvLayout.setTextPaneContent("appState",SACInputProcessor.navStateMachine.currentState.getLabel()+SACInputProcessor.navDirStateMachine.currentState.getLabel(), SACLayoutHandler.baseValuesFontColor)

        val stateAttributes =
            SACInputProcessor.aspectOverlayStateMachine.currentState.getLabel() +
            SACInputProcessor.chartStateMachine.currentState.getLabel() +
            SACInputProcessor.aspectsStateMachine.currentState.getLabel() +
            SACInputProcessor.timeAspectsStateMachine.currentState.getLabel()

        DVLayoutHandler.currentDvLayout.setTextPaneContent("appStateAttributes",stateAttributes, SACLayoutHandler.baseValuesFontColor)
        RenderEarthLocationTags.setContent()

        DVLayoutHandler.currentDvLayout.setTextPaneContent("localTimeLabel", EarthLocation.getEarthLocalTimeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localTime", "${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.hour)}:${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.minute)}:${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.second)}", SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localDateLabel", EarthLocation.getEarthLocalDateLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localDate", "${"%4d".format(celestialSnapshot.refEarthLocation.localDateTime.year)}-${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.monthNumber)}-${"%02d".format(celestialSnapshot.refEarthLocation.localDateTime.dayOfMonth)}", SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localTimezoneLabel", EarthLocation.getEarthTimezoneLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("LocalTimezone", celestialSnapshot.refEarthLocation.getTimezoneOffsetString(), SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.TZ_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localTimezoneLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("LocalTimezone", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("LocalTimezone", DVTextPane.DVTextPaneMode.WRITE, "UTC([-+]((1[0-8])|([0-9])))?") {
                SACComponent.sacEarthLocation.timeZone = TimeZone.of(it)
                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localTimezoneLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("LocalTimezone", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLatitudeLabel", EarthLocation.getEarthLatitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLatitude", "%1.4f".format(celestialSnapshot.refEarthLocation.latitude), SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.LAT_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localLatitudeLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localLatitude", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("localLatitude", DVTextPane.DVTextPaneMode.WRITE, "(-)?[0-9]{1,3}(.[0-9]{1,4})?") {
                SACComponent.sacEarthLocation.latitude = if (it.toDouble() < 0) it.toDouble().mod(-90.0) else it.toDouble().mod(90.0)
                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localLatitudeLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("localLatitude", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLongitudeLabel", EarthLocation.getEarthLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLongitude", "%1.4f".format(celestialSnapshot.refEarthLocation.longitude), SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.LON_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localLongitudeLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localLongitude", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("localLongitude", DVTextPane.DVTextPaneMode.WRITE, "(-)?[0-9]{1,3}(.[0-9]{1,4})?") {
                SACComponent.sacEarthLocation.longitude = if (it.toDouble() < 0) it.toDouble().mod(-180.0) else it.toDouble().mod(180.0)
                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localLongitudeLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("localLongitude", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTimeLabel", EarthLocation.getEarthUTCTimeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTime", "${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.hour)}:${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.minute)}:${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.second)}", SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.TIME_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTimeLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTime", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("utcTime", DVTextPane.DVTextPaneMode.WRITE, "[0-9]{2}:[0-9]{2}:[0-9]{2}") {
                NavState.curNavInstant = NavState.curNavInstant.toLocalDateTime(TimeZone.UTC).date.atTime(it.toLocalTime()).toInstant(TimeZone.UTC)
                SACComponent.sacEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()

                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTimeLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("utcTime", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDateLabel", EarthLocation.getEarthUTCDateLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDate", "${"%4d".format(celestialSnapshot.refEarthLocation.utcDateTime.year)}-${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.monthNumber)}-${"%02d".format(celestialSnapshot.refEarthLocation.utcDateTime.dayOfMonth)}", SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.DATE_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDateLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDate", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("utcDate", DVTextPane.DVTextPaneMode.WRITE, "[0-9]{4}-[0-9]{2}-[0-9]{2}") {
                NavState.curNavInstant = it.toLocalDate().atTime(NavState.curNavInstant.toLocalDateTime(TimeZone.UTC).time).toInstant(TimeZone.UTC)
                SACComponent.sacEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()

                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDateLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("utcDate", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("localAltitudeLabel", EarthLocation.getEarthAltitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localAltitude", "%d".format(celestialSnapshot.refEarthLocation.altitude), SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.ALT_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localAltitudeLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localAltitude", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("localAltitude", DVTextPane.DVTextPaneMode.WRITE, "[0-9]{1,4}") {
                SACComponent.sacEarthLocation.altitude = it.toInt()
                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("localAltitudeLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("localAltitude", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHeader",Celestial.celestialHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signHeader",Celestial.celestialSignHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialLongitude",Celestial.celestialLongitudeHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signLongitude", CelestialHouse.celestialHouseLongitudeHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHouse",Celestial.celestialHouseHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialDistance",Celestial.celestialDistanceHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialSpeed",Celestial.celestialLongitudeSpeedHeaderLabel)

        if (SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF_SYNCOMP_RECALL) ) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("transitHouse",Celestial.celestialTransitHouseHeaderLabel)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("transitCelestials",Celestial.celestialTransitCelestialHeaderLabel)
        }

        Celestial.entries.forEachIndexed { idx, renderCelestial ->
            DVLayoutHandler.currentDvLayout.setTextPaneContent(renderCelestial.name, renderCelestial.label, SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_sign",
                Sign.signLabelFromCelestialLongitude(
                    celestialSnapshot.refCelestialData[idx].longitude,
                    celestialSnapshot.refCelestialData[idx].longitudeSpeed
                ), Sign.signColor(celestialSnapshot.refCelestialData[idx].longitude)
            )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_long", "%1.4f".format(celestialSnapshot.refCelestialData[idx].longitude), SACLayoutHandler.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_signLong", CelestialData.getFormattedSignLongitude(celestialSnapshot.refCelestialData[idx].longitude), SACLayoutHandler.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_house", CelestialHouse.celestialHouseLabel(celestialSnapshot.refCelestialData[idx].celestialHouse), SACLayoutHandler.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_dist", "%1.4f".format(celestialSnapshot.refCelestialData[idx].distance), SACLayoutHandler.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_speed", "%1.4f".format(celestialSnapshot.refCelestialData[idx].longitudeSpeed), SACLayoutHandler.baseValuesFontColor )

            if (SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF_SYNCOMP_RECALL) && idx <= Celestial.transitMax.ordinal) {
                DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_transitHouse", CelestialHouse.celestialHouseLabel(celestialSnapshot.refCelestialData[idx].transitHouse), SACLayoutHandler.baseValuesFontColor )
                DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_transitCelestials", Celestial.getTransitCelestialsLabel(celestialSnapshot.refCelestialData[idx].transitHouse.toInt(), SACComponent.synNatCelestialSnapshot.refCelestialData), SACLayoutHandler.baseValuesFontColor )
            }
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSystemName", HouseName.getHouseName())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseLatLongHeader",CelestialHouse.celestialHouseLongLatHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSign",CelestialHouse.celestialHouseSignHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseLongitude",CelestialHouse.celestialHouseLongitudeHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSignLongitude", Sign.signLongitudeHeaderLabel)

        val pofData = celestialSnapshot.partOfFortuneData()
        val posData = celestialSnapshot.partOfSpiritData()

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
                        Sign.signLabelFromCelestialLongitude(celestialSnapshot.refCelestialHouseData[idx]), Sign.signColor(celestialSnapshot.refCelestialHouseData[idx])
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_long", "%1.4f".format(celestialSnapshot.refCelestialHouseData[idx]), SACLayoutHandler.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${celestialHouse}_signLong", CelestialData.getFormattedSignLongitude(celestialSnapshot.refCelestialHouseData[idx]), SACLayoutHandler.baseValuesFontColor )
                }
            }
        }

        val chartAspects = stateChart.getStateAspects()

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