package river.exertion.sac.console.render

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.render.RenderAspect
import river.exertion.sac.astro.render.RenderValueType
import river.exertion.sac.console.state.AspectsSortState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.LocationRecallState
import river.exertion.sac.view.SACCelestialsHousesDVLayout
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderSummaryAspects {

    val tableTag = "summaryAspects"

    private val col1AspectsMaxEntries = 24
    private val col2AspectsMaxEntries = 66
    private val col3AspectsMaxEntries = 108
    private val col4AspectsMaxEntries = 150

    fun dvTable() = DVTable(tableTag = tableTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
        var idx = 0
        val valueAspects = SACCelestialsHousesDVLayout.valueChart.getValueAspects()

        this.add(DVTable(tableTag = "col1aspects", cellType = DVLayoutCell.DVLCellTypes.TABLE, height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
            while (idx <= col1AspectsMaxEntries) {
                this.add(DVTextPane().apply { this.tag = "aspectCelestial1_$idx"; this.padLeft = ".2"; this.padRight = ".2"; this.align = DVAlign.CENTER.tag() })
                this.add(DVTextPane().apply { this.tag = "aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                this.add(DVTextPane().apply { this.tag = "aspectCelestial2_$idx"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.LEFT.tag() })
                this.add(DVTextPane().apply { this.tag = "aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                this.add(DVTextPane().apply { this.tag = "aspectValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                this.add(DVRow())
                idx++
            }
            this.add(DVTable(tableTag = "summary", cellType = DVLayoutCell.DVLCellTypes.TABLE, colspan = "5", panes = mutableListOf<DVLayoutCell>().apply {
                this.add(DVTextPane().apply { this.tag = "col1Summary"; this.height = DVPaneType.DVPDimension.LARGE.tag() })
            }))
        }))

        this.add(DVTable(tableTag = "col2aspects", cellType = DVLayoutCell.DVLCellTypes.TABLE, height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
            if (valueAspects.size > col1AspectsMaxEntries) {
                while (idx <= col2AspectsMaxEntries) {
                    this.add(DVTextPane().apply { this.tag = "aspectCelestial1_$idx"; this.padLeft = ".2"; this.padRight = ".2"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspectCelestial2_$idx"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.LEFT.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspectValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                    this.add(DVRow())
                    idx++
                }
            }
        }))

        this.add(DVTable(tableTag = "col3aspects", cellType = DVLayoutCell.DVLCellTypes.TABLE, height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
                if (valueAspects.size > col2AspectsMaxEntries) {
                    while (idx <= col3AspectsMaxEntries) {
                        this.add(DVTextPane().apply { this.tag = "aspectCelestial1_$idx"; this.padLeft = ".2"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectCelestial2_$idx"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.LEFT.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                        this.add(DVRow())
                        idx++
                    }
                }
            }))

        this.add(DVTable(tableTag = "col4aspects", cellType = DVLayoutCell.DVLCellTypes.TABLE, height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
                if (valueAspects.size > col3AspectsMaxEntries) {
                    while (idx <= col4AspectsMaxEntries) {
                        this.add(DVTextPane().apply { this.tag = "aspectCelestial1_$idx"; this.padLeft = ".2"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectCelestial2_$idx"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.LEFT.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                        this.add(DVRow())
                        idx++
                    }
                }
            }))

    } )

    fun setContent() {

        DVLayoutHandler.currentDvLayout.setTextPaneContent("col1Summary", "summary", SACLayoutHandler.baseValuesFontColor)

        SACCelestialsHousesDVLayout.valueChart.getSortedFilteredValueAspects().forEachIndexed { idx, valueAspect ->

            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectCelestial1_$idx", RenderAspect(valueAspect).getAspectCelestial1Label(), SACLayoutHandler.refEarthLocationFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspect_$idx", RenderAspect(valueAspect).getAspectLabel(), aspectValueColor(RenderAspect(valueAspect).getAspectValueType()))
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectCelestial2_$idx", RenderAspect(valueAspect).getAspectCelestial2Label(),
                if (SACInputProcessor.chartStateMachine.isInState(ChartState.SYNASTRY_CHART)) {
                    SACLayoutHandler.synEarthLocationFontColor
                } else {
                    SACLayoutHandler.refEarthLocationFontColor
                }
            )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectDelim_$idx", RenderAspect(valueAspect).getAspectDelimLabel(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectValue_$idx", RenderAspect(valueAspect).getAspectValueLabel(), aspectValueColor(RenderAspect(valueAspect).getValueValueType()))
        }
    }

    fun aspectValueColor(renderValueType: RenderValueType) =
        when (renderValueType){
            RenderValueType.POSITIVE -> SACLayoutHandler.positiveFontColor
            RenderValueType.NEGATIVE -> SACLayoutHandler.negativeFontColor
            RenderValueType.NEUTRAL -> SACLayoutHandler.neutralFontColor
            else -> SACLayoutHandler.reversalFontColor
        }
}