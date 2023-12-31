package river.exertion.sac.console.render

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.render.RenderAspect
import river.exertion.sac.console.render.summary.RenderSummary
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.view.SACCelestialsHousesDVLayout
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler
import river.exertion.sac.view.SACLayoutHandler.fontColor

object RenderSummaryAspects : IConsoleRender {

    override val layoutTag = "summaryAspects"

    const val col1AspectsMaxEntries = 24
    const val col2AspectsMaxEntries = 66
    const val col3AspectsMaxEntries = 108
    const val col4AspectsMaxEntries = 150

    const val summaryMaxEntries = (col3AspectsMaxEntries - col2AspectsMaxEntries) - col1AspectsMaxEntries - 3

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
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
                this.add(DVTextPane().apply { this.tag = "aspectModValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                this.add(DVRow())
                idx++
            }
            this.add(RenderSummary.setLayout())
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
                    this.add(DVTextPane().apply { this.tag = "aspectModValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
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
                        this.add(DVTextPane().apply { this.tag = "aspectModValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
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
                        this.add(DVTextPane().apply { this.tag = "aspectModValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                        this.add(DVRow())
                        idx++
                    }
                }
            }))

    } )

    override fun setContent() {

        DVLayoutHandler.currentDvLayout.setTextPaneContent("col1Summary", "summary", SACLayoutHandler.baseValuesFontColor)

        SACCelestialsHousesDVLayout.valueChart.getSortedFilteredValueAspects().forEachIndexed { idx, valueAspect ->

            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectCelestial1_$idx", RenderAspect(valueAspect).getAspectCelestial1Label(), SACLayoutHandler.refEarthLocationFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspect_$idx", RenderAspect(valueAspect).getAspectLabel(), fontColor(RenderAspect(valueAspect).getAspectValueType()))
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectCelestial2_$idx", RenderAspect(valueAspect).getAspectCelestial2Label(),
                if (SACInputProcessor.chartStateMachine.isInState(ChartState.SYNASTRY_CHART)) {
                    SACLayoutHandler.synEarthLocationFontColor
                } else {
                    SACLayoutHandler.refEarthLocationFontColor
                }
            )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectDelim_$idx", RenderAspect(valueAspect).getAspectDelimLabel(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectValue_$idx", RenderAspect(valueAspect).getAspectValueLabel(), fontColor(RenderAspect(valueAspect).getValueValueType()))
            if (!SACInputProcessor.analysisStateMachine.isInState(AnalysisState.NO_ANALYSIS)) {
                DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectModValue_$idx", RenderAspect(valueAspect).getRenderRomanticModLabel().second, fontColor(RenderAspect(valueAspect).getRenderRomanticModLabel().first))
            }
        }

        RenderSummary.setContent()
    }
}