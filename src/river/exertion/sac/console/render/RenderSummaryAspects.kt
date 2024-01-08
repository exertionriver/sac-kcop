package river.exertion.sac.console.render

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.Aspect.Companion.sortFilterValueAspects
import river.exertion.sac.astro.Chart
import river.exertion.sac.astro.base.ChartValueType
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.summary.RenderSummary
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.console.state.ChartStateType
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
        val valueAspects = SACComponent.sacChart.getAspects()

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

        val aspects = if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
            Chart.getCharacterAspects(SACComponent.sacChart, SACComponent.refNatChart, SACComponent.synNatChart)
        }
        else {
            SACComponent.sacChart.chartAspects
        }

            aspects.sortFilterValueAspects().forEachIndexed { idx, aspect ->

            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectCelestial1_$idx", aspect.getAspectCelestial1Label(), SACLayoutHandler.refEarthLocationFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspect_$idx", aspect.getAspectLabel(), fontColor(aspect.getAspectValueType()))
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectCelestial2_$idx", aspect.getAspectCelestial2Label(),
                if (SACInputProcessor.chartStateMachine.isInState(ChartState.SYNASTRY_CHART)) {
                    SACLayoutHandler.synEarthLocationFontColor
                } else {
                    SACLayoutHandler.refEarthLocationFontColor
                }
            )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectDelim_$idx", aspect.getAspectDelimLabel(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectValue_$idx", aspect.getAspectValueLabel(), fontColor(aspect.getValueValueType()))
            if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.ROMANTIC_ANALYSIS)) {
                DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectModValue_$idx", aspect.getRenderRomanticModLabel().second, fontColor(aspect.getRenderRomanticModLabel().first))
            } else if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.CHARACTER_ANALYSIS)) {
                DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectModValue_$idx", aspect.getRenderCharacterModLabel().second, fontColor(aspect.getRenderCharacterModLabel().first))
            }
        }

        RenderSummary.setContent()
    }
}