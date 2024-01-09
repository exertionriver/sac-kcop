package river.exertion.sac.console.render.header

import kotlinx.datetime.*
import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.kcop.view.MultiKeys
import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.EarthLocation
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.state.EntryState
import river.exertion.sac.console.state.NavState
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderHeaderEarthLocationRows : IConsoleRender {

    override val layoutTag = "headerEarthLocationRows"

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {
            this.add(DVTextPane().apply { this.tag = "localTimeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "localTime"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() })
            this.add(DVTextPane().apply { this.tag = "localDateLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "localDate"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() })
            this.add(DVTextPane().apply { this.tag = "localLatitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" })
            this.add(DVTextPane().apply { this.tag = "localLatitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "localLongitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" })
            this.add(DVTextPane().apply { this.tag = "localLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVRow())
            this.add(DVTextPane().apply { this.tag = "utcTimeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "utcTime"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() })
            this.add(DVTextPane().apply { this.tag = "utcDateLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "utcDate"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() })
            this.add(DVTextPane().apply { this.tag = "localTimezoneLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" })
            this.add(DVTextPane().apply { this.tag = "LocalTimezone"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "localAltitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" })
            this.add(DVTextPane().apply { this.tag = "localAltitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
    })

    override fun setContent() {

        DVLayoutHandler.currentDvLayout.setTextPaneContent("localTimeLabel", EarthLocation.getEarthLocalTimeLabel())

        DVLayoutHandler.currentDvLayout.setTextPaneContent("localTime"
            , "${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.hour)}:${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.minute)}:${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.second)}"
            , SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localDateLabel", EarthLocation.getEarthLocalDateLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localDate"
            , "${"%4d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.year)}-${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.monthNumber)}-${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.dayOfMonth)}"
            , SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localTimezoneLabel", EarthLocation.getEarthTimezoneLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("LocalTimezone", SACComponent.refNatCelestialSnapshot.refEarthLocation.getTimezoneOffsetString(), SACLayoutHandler.baseValuesFontColor)

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
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLatitude"
            , "%1.4f".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.latitude), SACLayoutHandler.baseValuesFontColor)

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
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localLongitude", "%1.4f".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.longitude), SACLayoutHandler.baseValuesFontColor)

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
        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTime"
            , "${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.hour)}:${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.minute)}:${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.second)}"
            , SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.TIME_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTimeLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcTime", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("utcTime", DVTextPane.DVTextPaneMode.WRITE, "[0-9]{2}:[0-9]{2}:[0-9]{2}") {
                NavState.curNavInstant = NavState.curNavInstant.toLocalDateTime(TimeZone.UTC).date.atTime(it.toLocalTime()).toInstant(
                    TimeZone.UTC)
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
        DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDate"
            , "${"%4d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.year)}-${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.monthNumber)}-${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.dayOfMonth)}"
            , SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.DATE_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDateLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("utcDate", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("utcDate", DVTextPane.DVTextPaneMode.WRITE, "[0-9]{4}-[0-9]{2}-[0-9]{2}") {
                NavState.curNavInstant = it.toLocalDate().atTime(NavState.curNavInstant.toLocalDateTime(TimeZone.UTC).time).toInstant(
                    TimeZone.UTC)
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
        DVLayoutHandler.currentDvLayout.setTextPaneContent("localAltitude", "%d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.altitude), SACLayoutHandler.baseValuesFontColor)

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
    }
}