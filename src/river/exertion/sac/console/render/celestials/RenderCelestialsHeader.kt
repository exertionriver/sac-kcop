package river.exertion.sac.console.render.celestials

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.base.Celestial
import river.exertion.sac.astro.base.CelestialHouse
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.render.celestials.RenderCelestialsRows.celestialsRows
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACInputProcessor

object RenderCelestialsHeader : IConsoleRender {

    override val layoutTag = "celestialsHeader"

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf(
        DVTextPane().apply { this.tag = "${layoutTag}_celestialHeader"; this.align = DVAlign.CENTER.tag() },
        DVTextPane().apply { this.tag = "${layoutTag}_signHeader"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
        DVTextPane().apply { this.tag = "${layoutTag}_celestialLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
        DVTextPane().apply { this.tag = "${layoutTag}_signLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
        DVTextPane().apply { this.tag = "${layoutTag}_celestialHouse"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
        DVTextPane().apply { this.tag = "${layoutTag}_celestialDistance"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
        DVTextPane().apply { this.tag = "${layoutTag}_celestialSpeed"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
        DVTextPane().apply { this.tag = "${layoutTag}_transitHouse"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
        DVTextPane().apply { this.tag = "${layoutTag}_transitCelestials"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
        DVRow()
    ).apply { this.addAll(celestialsRows()) } )

    override fun setContent() {
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_celestialHeader", Celestial.celestialHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_signHeader", Celestial.celestialSignHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_celestialLongitude", Celestial.celestialLongitudeHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_signLongitude", CelestialHouse.celestialHouseLongitudeHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_celestialHouse", Celestial.celestialHouseHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_celestialDistance", Celestial.celestialDistanceHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_celestialSpeed", Celestial.celestialLongitudeSpeedHeaderLabel)

        if (SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF_SYNCOMP_RECALL) ) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_transitHouse", Celestial.celestialTransitHouseHeaderLabel)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_transitCelestials", Celestial.celestialTransitCelestialHeaderLabel)
        }
    }
}