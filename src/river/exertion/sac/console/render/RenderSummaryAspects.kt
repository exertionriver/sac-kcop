package river.exertion.sac.console.render

import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.kcop.view.layout.displayViewLayout.asset.DVAlign
import river.exertion.sac.astro.render.RenderAspect
import river.exertion.sac.view.SACCelestialsHousesDVLayout
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

        this.add(DVTable(tableTag = "col1aspects", cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
            while ( (idx <= col1AspectsMaxEntries) && (idx < valueAspects.size) ) {
//                this.add(DVTable(tableTag = "aspect_$idx", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
                    this.add(DVTextPane().apply { this.tag = "aspectCelestial1_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspectCelestial2_$idx"; this.align = DVAlign.LEFT.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspectValue_$idx"; this.padLeft = ".2"; this.align = DVAlign.RIGHT.tag() })
  //              }))
                this.add(DVRow())
                idx++
            }
            this.add(DVTable(tableTag = "summary", cellType = DVLayoutCell.DVLCellTypes.TABLE, colspan = "5", panes = mutableListOf<DVLayoutCell>().apply {
                this.add(DVTextPane().apply { this.tag = "col1Summary"; this.height = DVPaneType.DVPDimension.LARGE.tag() })
            }))
        }))

        this.add(DVTable(tableTag = "col2aspects", cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
            while ( (idx <= col2AspectsMaxEntries) && (idx < valueAspects.size) ) {
//                this.add(DVTable(tableTag = "aspect_$idx", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
                    this.add(DVTextPane().apply { this.tag = "aspectCelestial1_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspectCelestial2_$idx"; this.align = DVAlign.LEFT.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                    this.add(DVTextPane().apply { this.tag = "aspectValue_$idx"; this.align = DVAlign.RIGHT.tag() })
  //              }))
                this.add(DVRow())
                idx++
            }
        }))

        this.add(DVTable(tableTag = "col3aspects", cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
                while ( (idx <= col3AspectsMaxEntries) && (idx < valueAspects.size) ) {
    //                this.add(DVTable(tableTag = "aspect_$idx", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
                        this.add(DVTextPane().apply { this.tag = "aspectCelestial1_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspect_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectCelestial2_$idx"; this.align = DVAlign.LEFT.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectValue_$idx"; this.align = DVAlign.RIGHT.tag() })
      //              }))
                    this.add(DVRow())
                    idx++
                }
            }))

        this.add(DVTable(tableTag = "col4aspects", cellType = DVLayoutCell.DVLCellTypes.TABLE, width = DVPaneType.DVPDimension.SMALL.tag(), height = DVPaneType.DVPDimension.FULL.tag(),
            panes = mutableListOf<DVLayoutCell>().apply {
                while ( (idx <= col4AspectsMaxEntries) && (idx < valueAspects.size) ) {
        //            this.add(DVTable(tableTag = "aspect_$idx", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf<DVLayoutCell>().apply {
                        this.add(DVTextPane().apply { this.tag = "aspectCelestial1_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspect_$idx"; this.align = DVAlign.CENTER.tag()})
                        this.add(DVTextPane().apply { this.tag = "aspectCelestial2_$idx"; this.align = DVAlign.LEFT.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectDelim_$idx"; this.align = DVAlign.CENTER.tag() })
                        this.add(DVTextPane().apply { this.tag = "aspectValue_$idx"; this.align = DVAlign.RIGHT.tag() })
        //            }))
                    this.add(DVRow())
                    idx++
                }
            }))

    } )

    fun setContent() {

        DVLayoutHandler.currentDvLayout.setTextPaneContent("col1Summary", "summary", SACLayoutHandler.baseValuesFontColor)

        SACCelestialsHousesDVLayout.valueChart.getValueAspects().forEachIndexed { idx, valueAspect ->

            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectCelestial1_$idx", RenderAspect(valueAspect).getAspectCelestial1Label(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspect_$idx", RenderAspect(valueAspect).getAspectLabel(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectCelestial2_$idx", RenderAspect(valueAspect).getAspectCelestial2Label(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectDelim_$idx", RenderAspect(valueAspect).getAspectDelimLabel(), SACLayoutHandler.baseValuesFontColor)
            DVLayoutHandler.currentDvLayout.setTextPaneContent("aspectValue_$idx", RenderAspect(valueAspect).getAspectValueLabel(), SACLayoutHandler.baseValuesFontColor)
        }
    }
}