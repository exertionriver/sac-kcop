package river.exertion.sac.console.render.housesGrid

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.base.CelestialHouse
import river.exertion.sac.astro.base.Sign
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.render.housesGrid.RenderHousesRows.housesRows
import river.exertion.sac.swe.HouseName

object RenderHousesHeader : IConsoleRender {

    override val layoutTag = "housesHeader"

    override fun setLayout() = DVTable(tableTag = layoutTag, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf(
        DVTable(tableTag = "${layoutTag}_houseSystem", colspan = "4", panes = mutableListOf(
            DVTextPane().apply { this.tag = "${layoutTag}_houseSystemName" },
        )),
        DVRow(),
        DVTextPane().apply { this.tag = "${layoutTag}_houseLatLongHeader"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.CENTER.tag() },
        DVTextPane().apply { this.tag = "${layoutTag}_houseSign"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
        DVTextPane().apply { this.tag = "${layoutTag}_houseLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
        DVTextPane().apply { this.tag = "${layoutTag}_houseSignLongitude"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag()  },
        DVRow()
    ).apply { this.addAll(housesRows()) } )

    override fun setContent() {
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_houseSystemName", HouseName.getHouseName())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_houseLatLongHeader", CelestialHouse.celestialHouseLongLatHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_houseSign", CelestialHouse.celestialHouseSignHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_houseLongitude", CelestialHouse.celestialHouseLongitudeHeaderLabel)
        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_houseSignLongitude", Sign.signLongitudeHeaderLabel)
    }
}