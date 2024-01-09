package river.exertion.sac.console.render.header

import kotlinx.datetime.*
import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.kcop.view.MultiKeys
import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.EarthLocation
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.render.celestials.RenderCelestialsRows
import river.exertion.sac.console.state.EntryState
import river.exertion.sac.console.state.NavState
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderHeaderEarthLocationRows : IConsoleRender {

    override val layoutTag = "headerEarthLocationRows"

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localTimeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localTime"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localDateLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localDate"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localLatitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localLatitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localLongitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVRow())
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_utcTimeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_utcTime"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_utcDateLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_utcDate"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag() })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localTimezoneLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localTimezone"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localAltitudeLabel"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" })
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_localAltitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".2" })
    })

    override fun setContent() {

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localTimeLabel", EarthLocation.getEarthLocalTimeLabel())

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localTime"
            , "${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.hour)}:${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.minute)}:${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.second)}"
            , SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localDateLabel", EarthLocation.getEarthLocalDateLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localDate"
            , "${"%4d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.year)}-${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.monthNumber)}-${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.localDateTime.dayOfMonth)}"
            , SACLayoutHandler.baseValuesFontColor)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localTimezoneLabel", EarthLocation.getEarthTimezoneLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localTimezone", SACComponent.refNatCelestialSnapshot.refEarthLocation.getTimezoneOffsetString(), SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.TZ_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localTimezoneLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localTimezone", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_localTimezone", DVTextPane.DVTextPaneMode.WRITE, "UTC([-+]((1[0-8])|([0-9])))?") {
                SACComponent.sacEarthLocation.timeZone = TimeZone.of(it)
                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localTimezoneLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_localTimezone", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLatitudeLabel", EarthLocation.getEarthLatitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLatitude"
            , "%1.4f".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.latitude), SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.LAT_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLatitudeLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLatitude", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_localLatitude", DVTextPane.DVTextPaneMode.WRITE, "(-)?[0-9]{1,3}(.[0-9]{1,4})?") {
                SACComponent.sacEarthLocation.latitude = if (it.toDouble() < 0) it.toDouble().mod(-90.0) else it.toDouble().mod(90.0)
                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLatitudeLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_localLatitude", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLongitudeLabel", EarthLocation.getEarthLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLongitude", "%1.4f".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.longitude), SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.LON_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLongitudeLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLongitude", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_localLongitude", DVTextPane.DVTextPaneMode.WRITE, "(-)?[0-9]{1,3}(.[0-9]{1,4})?") {
                SACComponent.sacEarthLocation.longitude = if (it.toDouble() < 0) it.toDouble().mod(-180.0) else it.toDouble().mod(180.0)
                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localLongitudeLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_localLongitude", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcTimeLabel", EarthLocation.getEarthUTCTimeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcTime"
            , "${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.hour)}:${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.minute)}:${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.second)}"
            , SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.TIME_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcTimeLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcTime", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_utcTime", DVTextPane.DVTextPaneMode.WRITE, "[0-9]{2}:[0-9]{2}:[0-9]{2}") {
                NavState.curNavInstant = NavState.curNavInstant.toLocalDateTime(TimeZone.UTC).date.atTime(it.toLocalTime()).toInstant(
                    TimeZone.UTC)
                SACComponent.sacEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()

                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcTimeLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_utcTime", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcDateLabel", EarthLocation.getEarthUTCDateLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcDate"
            , "${"%4d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.year)}-${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.monthNumber)}-${"%02d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.utcDateTime.dayOfMonth)}"
            , SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.DATE_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcDateLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcDate", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_utcDate", DVTextPane.DVTextPaneMode.WRITE, "[0-9]{4}-[0-9]{2}-[0-9]{2}") {
                NavState.curNavInstant = it.toLocalDate().atTime(NavState.curNavInstant.toLocalDateTime(TimeZone.UTC).time).toInstant(
                    TimeZone.UTC)
                SACComponent.sacEarthLocation.utcDateTime = NavState.curNavDateTimeUTC()

                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_utcDateLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_utcDate", DVTextPane.DVTextPaneMode.READ)
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localAltitudeLabel", EarthLocation.getEarthAltitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localAltitude", "%d".format(SACComponent.refNatCelestialSnapshot.refEarthLocation.altitude), SACLayoutHandler.baseValuesFontColor)

        if (SACInputProcessor.entryStateMachine.isInState(EntryState.ALT_ENTRY)) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localAltitudeLabel", colorOverride = SACLayoutHandler.highlightFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localAltitude", colorOverride = ColorPalette.of("green"))

            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_localAltitude", DVTextPane.DVTextPaneMode.WRITE, "[0-9]{1,4}") {
                SACComponent.sacEarthLocation.altitude = it.toInt()
                SACInputProcessor.entryStateMachine.changeState(EntryState.NO_ENTRY)
                SACInputProcessor.navStateMachine.changeState(NavState.NAV_PAUSED)
                MultiKeys.keysDown.clear()
            }
        } else {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_localAltitudeLabel", colorOverride = SACLayoutHandler.baseFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneMode("${layoutTag}_localAltitude", DVTextPane.DVTextPaneMode.READ)
        }
    }
}