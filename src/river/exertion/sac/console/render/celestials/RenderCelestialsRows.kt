package river.exertion.sac.console.render.celestials

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.base.CelestialData
import river.exertion.sac.astro.base.Celestial
import river.exertion.sac.astro.base.CelestialHouse
import river.exertion.sac.astro.base.Sign
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderCelestialsRows : IConsoleRender {

    override val layoutTag = "celestialsRows"

    override fun setLayout() = DVTable(tableTag = layoutTag)

    fun celestialsRows() = buildList { Celestial.entries.forEach { celestial ->
        this.addAll( mutableListOf(
            DVTextPane().apply { this.tag = "${layoutTag}_${celestial.name}"; this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestial.name}_sign"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag(); this.padLeft = ".3" },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestial.name}_long"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".16" },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestial.name}_signLong"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".16" },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestial.name}_house"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.LEFT.tag(); this.padLeft = ".32" },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestial.name}_dist"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".22" },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestial.name}_speed"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".22" },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestial.name}_transitHouse"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestial.name}_transitCelestials"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVRow()
        ) )
    } }

    override fun setContent() {
        val refCelestialData = SACComponent.sacChart.firstCelestialSnapshot.refCelestialData

        Celestial.entries.forEachIndexed { idx, celestial ->
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestial.name}", celestial.label
                , if (SACInputProcessor.chartStateMachine.isInState(ChartState.COMPOSITE_CHART) )
                        SACLayoutHandler.compCelesitalSnapshotFontColor
                    else
                        SACLayoutHandler.refEarthLocationFontColor
            )

            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestial.name}_sign",
                Sign.signLabelFromCelestialLongitude(refCelestialData[idx].longitude, refCelestialData[idx].longitudeSpeed
                ), Sign.signColor(refCelestialData[idx].longitude)
            )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestial.name}_long"
                , "%1.4f".format(refCelestialData[idx].longitude), SACLayoutHandler.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestial.name}_signLong"
                , CelestialData.getFormattedSignLongitude(refCelestialData[idx].longitude), SACLayoutHandler.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestial.name}_house"
                , CelestialHouse.celestialHouseLabel(refCelestialData[idx].celestialHouse), SACLayoutHandler.refCelestialSnapshotFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestial.name}_dist"
                , "%1.4f".format(refCelestialData[idx].distance), SACLayoutHandler.baseValuesFontColor )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestial.name}_speed"
                , "%1.4f".format(refCelestialData[idx].longitudeSpeed), SACLayoutHandler.baseValuesFontColor )

            if ( SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF_SYNCOMP_RECALL) &&
                    SACInputProcessor.chartStateMachine.isInState(ChartState.SYNASTRY_CHART) &&
                    (idx <= Celestial.transitMax.ordinal) ) {
                DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestial.name}_transitHouse"
                    , CelestialHouse.celestialHouseLabel(refCelestialData[idx].transitHouse), SACLayoutHandler.synCelestialSnapshotFontColor )
                DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestial.name}_transitCelestials"
                    , Celestial.getTransitCelestialsLabel(refCelestialData[idx].transitHouse.toInt(), SACComponent.synNatCelestialSnapshot.refCelestialData), SACLayoutHandler.synCelestialSnapshotFontColor )
            }
        }
    }
}