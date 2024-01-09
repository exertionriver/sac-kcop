package river.exertion.sac.console.render.housesGrid

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.base.AspectType
import river.exertion.sac.component.SACComponent
import river.exertion.sac.console.render.IConsoleRender
import river.exertion.sac.view.SACInputProcessor
import river.exertion.sac.view.SACLayoutHandler

object RenderGridRows : IConsoleRender {

    override val layoutTag = "gridRows"
    private val gridEntries = AspectCelestial.entries.filter { it.isChartAspectCelestial() }

    override fun setLayout() = DVTable(tableTag = layoutTag, cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), panes = mutableListOf<DVLayoutCell>().apply {

        if (SACInputProcessor.chartStateMachine.currentState.isNatComp()) {
            gridEntries.forEachIndexed { rowIdx, chartAspectCelestial ->
                var colIdx = 0

                while (colIdx <= rowIdx) {
                    if (colIdx == rowIdx) {
                        this.add( DVTextPane().apply { this.tag = "${layoutTag}_${chartAspectCelestial.name}"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
                    } else {
                        this.add( DVTextPane().apply { this.tag = "${layoutTag}_${chartAspectCelestial.name}_${AspectCelestial.fromOrdinal(colIdx)}"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
                    }
                    colIdx++
                }
                this.add(DVRow())
            }
        } else {
            this.add(DVTextPane().apply { this.tag = "${layoutTag}_chartSpace"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )

            gridEntries.forEach { aspectCelestialHeader ->
                this.add(DVTextPane().apply { this.tag = "${layoutTag}_${aspectCelestialHeader.name}_x"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
            }
            this.add(DVRow())

            gridEntries.forEachIndexed { rowIdx, chartAspectCelestialRow ->
                var colIdx = -1

                while (colIdx < gridEntries.size) {
                    if (colIdx == -1) {
                        this.add(DVTextPane().apply { this.tag = "${layoutTag}_${AspectCelestial.fromOrdinal(rowIdx)!!.name}_y"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
                    } else {
                        this.add( DVTextPane().apply { this.tag = "${layoutTag}_${chartAspectCelestialRow.name}_${AspectCelestial.fromOrdinal(colIdx)}"; this.padLeft = ".1"; this.padRight = ".1"; this.align = DVAlign.CENTER.tag() } )
                    }
                    colIdx++
                }
                this.add(DVRow())
            }
        }
    } )

    override fun setContent() {

        val chartAspects = SACComponent.sacChart.getAspects()

        if (SACInputProcessor.chartStateMachine.currentState.isNatComp()) {
            gridEntries.forEachIndexed { rowIdx, chartAspectCelestial ->
                var colIdx = 0

                while (colIdx <= rowIdx) {
                    if (rowIdx == colIdx) {
                        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${chartAspectCelestial.name}", chartAspectCelestial.label, SACLayoutHandler.refEarthLocationFontColor)
                    } else {
                        val renderAspectType =
                            chartAspects.firstOrNull { it.aspectCelestialFirst == AspectCelestial.fromOrdinal(colIdx) && it.aspectCelestialSecond == chartAspectCelestial }?.aspectAngle?.aspectType ?: AspectType.ASPECT_NONE
                        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${chartAspectCelestial.name}_${AspectCelestial.fromOrdinal(colIdx)}",
                            renderAspectType.label, renderAspectType.fontColor )
                    }
                    colIdx++
                } }
        } else {
            gridEntries.forEach { aspectCelestialHeader ->
                DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${aspectCelestialHeader.name}_x", aspectCelestialHeader.label, SACLayoutHandler.refEarthLocationFontColor)
            }

            gridEntries.forEachIndexed { rowIdx, chartAspectCelestialRow ->
                var colIdx = -1

                while (colIdx < gridEntries.size) {
                    if (colIdx == -1) {
                        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${AspectCelestial.fromOrdinal(rowIdx)!!.name}_y", chartAspectCelestialRow.label, SACLayoutHandler.synEarthLocationFontColor)
                    } else {
                        val renderAspectType =
                            chartAspects.firstOrNull { it.aspectCelestialFirst == AspectCelestial.fromOrdinal(colIdx) && it.aspectCelestialSecond == chartAspectCelestialRow }?.aspectAngle?.aspectType ?: AspectType.ASPECT_NONE

                        DVLayoutHandler.currentDvLayout.setTextPaneContent("${layoutTag}_${chartAspectCelestialRow.name}_${AspectCelestial.fromOrdinal(colIdx)}",
                            renderAspectType.label, renderAspectType.fontColor )
                    }
                    colIdx++
                }
            }
        }
    }
}