package river.exertion.sac.console.render.housesGrid

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.CelestialData
import river.exertion.sac.astro.base.CelestialHouse
import river.exertion.sac.astro.base.Sign
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.render.celestials.RenderCelestialsRows
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderHousesRows : IConsoleRender {

    override val layoutTag = "housesRows"

    override fun setLayout() = DVTable(tableTag = RenderCelestialsRows.layoutTag)

    fun housesRows() = buildList { CelestialHouse.entries.forEach { celestialHouse ->
        this.addAll( mutableListOf(
            DVTextPane().apply { this.tag = "${layoutTag}_${celestialHouse}"; this.width = DVPaneType.DVPDimension.UNIT.tag(); this.align = DVAlign.LEFT.tag(); this.padLeft = ".2" },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestialHouse}_sign"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.CENTER.tag() },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestialHouse}_long"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".1" },
            DVTextPane().apply { this.tag = "${layoutTag}_${celestialHouse}_signLong"; this.width = DVPaneType.DVPDimension.TINY.tag(); this.align = DVAlign.RIGHT.tag(); this.padRight = ".16" },
            DVRow()
        ) )
    } }

    override fun setContent() {
        val celestialSnapshot = SACComponent.sacChart.firstCelestialSnapshot
        val celestialHouseData = celestialSnapshot.refCelestialHouseData

        val pofData = celestialSnapshot.partOfFortuneData()
        val posData = celestialSnapshot.partOfSpiritData()

        CelestialHouse.entries.forEachIndexed { idx, celestialHouse ->
            when {
                (celestialHouse.name == "PART_OF_SPIRIT") -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}"
                        , celestialHouse.label, if (SACInputProcessor.chartStateMachine.isInState(
                                ChartState.COMPOSITE_CHART) )
                            SACLayoutHandler.compCelesitalSnapshotFontColor
                        else
                            SACLayoutHandler.refEarthLocationFontColor
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}_sign"
                        , Sign.signLabelFromCelestialLongitude(posData), Sign.signColor(posData) )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}_long"
                        , "%1.4f".format(posData), SACLayoutHandler.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}_signLong"
                        , CelestialData.getFormattedSignLongitude(posData), SACLayoutHandler.baseValuesFontColor )
                }
                (celestialHouse.name == "PART_OF_FORTUNE") -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}"
                        , celestialHouse.label, if (SACInputProcessor.chartStateMachine.isInState(
                                ChartState.COMPOSITE_CHART) )
                            SACLayoutHandler.compCelesitalSnapshotFontColor
                        else
                            SACLayoutHandler.refEarthLocationFontColor
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}_sign"
                        , Sign.signLabelFromCelestialLongitude(pofData), Sign.signColor(pofData) )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}_long"
                        , "%1.4f".format(pofData), SACLayoutHandler.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}_signLong"
                        , CelestialData.getFormattedSignLongitude(pofData), SACLayoutHandler.baseValuesFontColor )
                }
                else -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}"
                        , celestialHouse.label, if (SACInputProcessor.chartStateMachine.isInState(
                                ChartState.COMPOSITE_CHART) )
                            SACLayoutHandler.compCelesitalSnapshotFontColor
                        else
                            SACLayoutHandler.refEarthLocationFontColor
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}_sign"
                        , Sign.signLabelFromCelestialLongitude(celestialHouseData[idx])
                        , Sign.signColor(celestialHouseData[idx])
                    )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}_long"
                        , "%1.4f".format(celestialHouseData[idx]), SACLayoutHandler.baseValuesFontColor )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${celestialHouse}_signLong"
                        , CelestialData.getFormattedSignLongitude(celestialHouseData[idx]), SACLayoutHandler.baseValuesFontColor )
                }
            }
        }
    }
}