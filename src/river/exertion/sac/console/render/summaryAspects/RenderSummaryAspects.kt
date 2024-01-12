package river.exertion.sac.console.render.summaryAspects

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.Aspect.Companion.sortFilterValueAspects
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.console.render.header.RenderHeaderTitleState
import river.exertion.sac.console.state.AnalysisState
import river.exertion.sac.console.state.ChartState
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler
import river.exertion.sac.view.SACLayoutHandler.fontColor
import river.exertion.sac.view.SACLayoutHandler.refFontColor
import river.exertion.sac.view.SACLayoutHandler.synFontColor

object RenderSummaryAspects : IConsoleRender {

    override val layoutTag = "summaryAspects"

    const val col1AspectsMaxEntries = 24
    const val col2AspectsMaxEntries = 66
    const val col3AspectsMaxEntries = 108
    const val col4AspectsMaxEntries = 150

    const val summaryMaxEntries = (col3AspectsMaxEntries - col2AspectsMaxEntries) - col1AspectsMaxEntries - 3

    override fun setLayout() = DVTable(tableTag = layoutTag, panes = mutableListOf<DVLayoutCell>().apply {
        var idx = 0
        val valueAspects = SACComponent.sacChart.getAspects()

        this.add(DVTable(tableTag = "col1aspects", height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
            while (idx <= col1AspectsMaxEntries) {
                this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectCelestial1_$idx"; this.padLeft = ".2"; this.padRight = ".2"; this.align = DVAlign.CENTER.tag() })
                this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectCelestial2_$idx"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.LEFT.tag() })
                this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectModValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                this.add(DVRow())
                idx++
            }
            this.add(RenderSummary.setLayout())
        }))

        this.add(DVTable(tableTag = "col2aspects", height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
            if (valueAspects.size > col1AspectsMaxEntries) {
                while (idx <= col2AspectsMaxEntries) {
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectCelestial1_$idx"; this.padLeft = ".2"; this.padRight = ".2"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectCelestial2_$idx"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.LEFT.tag() })
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                    this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectModValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                    this.add(DVRow())
                    idx++
                }
            }
        }))

        this.add(DVTable(tableTag = "col3aspects", height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
                if (valueAspects.size > col2AspectsMaxEntries) {
                    while (idx <= col3AspectsMaxEntries) {
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectCelestial1_$idx"; this.padLeft = ".2"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectCelestial2_$idx"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.LEFT.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectModValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                        this.add(DVRow())
                        idx++
                    }
                }
            }))

        this.add(DVTable(tableTag = "col4aspects", height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
                if (valueAspects.size > col3AspectsMaxEntries) {
                    while (idx <= col4AspectsMaxEntries) {
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectCelestial1_$idx"; this.padLeft = ".2"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectCelestial2_$idx"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.LEFT.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_aspectModValue_$idx"; this.padLeft = ".1"; this.padRight = ".2"; this.align = DVAlign.RIGHT.tag() })
                        this.add(DVRow())
                        idx++
                    }
                }
            }))

    } )

    override fun setContent() {

        SACComponent.sacChart.chartAspects.sortFilterValueAspects().forEachIndexed { idx, aspect ->

            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_aspectCelestial1_$idx", aspect.getAspectCelestial1Label().second, refFontColor(aspect.getAspectCelestial1Label().first))
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_aspect_$idx", aspect.getAspectTypeLabel().second, fontColor(aspect.getAspectTypeLabel().first))
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_aspectCelestial2_$idx", aspect.getAspectCelestial2Label().second, synFontColor(aspect.getAspectCelestial1Label().first))
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_aspectDelim_$idx", aspect.getAspectDelimLabel(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_aspectValue_$idx", aspect.getAspectValueRenderLabel().second, fontColor(aspect.getAspectValueRenderLabel().first))
            if (SACInputProcessor.chartStateMachine.isInState(ChartState.COMBINED_CHART)) {
                DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_aspectModValue_$idx", aspect.getRenderCharacterModLabel().second, fontColor(aspect.getRenderCharacterModLabel().first))
            } else if (SACInputProcessor.analysisStateMachine.isInState(AnalysisState.ROMANTIC_ANALYSIS)) {
                DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_aspectModValue_$idx", aspect.getRenderRomanticModLabel().second, fontColor(aspect.getRenderRomanticModLabel().first))
            }
        }

        RenderSummary.setContent()
    }
}