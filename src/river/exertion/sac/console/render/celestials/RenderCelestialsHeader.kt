package river.exertion.sac.console.render.celestials

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.base.Celestial
import river.exertion.sac.astro.base.CelestialHouse
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.render.celestials.RenderCelestialsRows.celestialsRows
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACCelestialsHousesDVLayout
import river.exertion.sac.view.SACInputProcessor

object RenderCelestialsHeader : IConsoleRender {

    override val layoutTag = "celestialsHeader"

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {
        this.addAll( mutableListOf(
            DVTextPane().apply { this.tag = "celestialHeader"; this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "signHeader"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "celestialLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "signLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "celestialHouse"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "celestialDistance"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
            DVTextPane().apply { this.tag = "celestialSpeed"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
            DVTextPane().apply { this.tag = "transitHouse"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "transitCelestials"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVRow()
        ))
    }).apply {this.panes.celestialsRows()}

    override fun setContent() {
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHeader", Celestial.celestialHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signHeader", Celestial.celestialSignHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialLongitude", Celestial.celestialLongitudeHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signLongitude", CelestialHouse.celestialHouseLongitudeHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHouse", Celestial.celestialHouseHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialDistance", Celestial.celestialDistanceHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialSpeed", Celestial.celestialLongitudeSpeedHeaderLabel)

        if (SACInputProcessor.locationRecallStateMachine.isInState(LocationRecallState.CUR_NAV_REF_SYNCOMP_RECALL) ) {
            DVLayoutHandler.currentDvLayout.setTextPaneContent("transitHouse", Celestial.celestialTransitHouseHeaderLabel)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("transitCelestials", Celestial.celestialTransitCelestialHeaderLabel)
        }
    }
}