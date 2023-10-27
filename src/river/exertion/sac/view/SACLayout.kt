package river.exertion.sac.view

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Table
import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.kcop.view.klop.IDisplayViewLayoutHandler
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.kcop.view.layout.displayViewLayout.*

object SACLayout : IDisplayViewLayoutHandler {

    var baseColorName = "darkGray"
    var baseColor = ColorPalette.of(baseColorName)

    fun dvLayout() = DVLayout(name = "SACDVLayout", layout = mutableListOf(
        DVTable(tableIdx = "layout", cellType = DVLayoutCell.DVLCellTypes.TABLE, panes = mutableListOf(
            DVTextPane().apply {
                this.idx = "1"
                this.width = DVPaneType.DVPDimension.FULL.tag()
                this.height = DVPaneType.DVPDimension.LARGE.tag()
           }, DVRow(), DVTextPane().apply {
                this.idx = "2"
                this.width = DVPaneType.DVPDimension.FULL.tag()
                this.height = DVPaneType.DVPDimension.TITLE.tag()
            }
        ))
    ))

    override fun build(): Actor {
        DVLayoutHandler.currentDvLayout = dvLayout()
        DVLayoutHandler.currentDvLayout.setTextPaneContent(1,"test234")
        DVLayoutHandler.currentDvLayout.setTextPaneContent(2,"test345")
        return DVLayoutHandler.build()
    }

    override fun clearContent() {
//        sampleSwatchesCtrl.clearChildren()
//        baseSwatchesCtrl.clearChildren()
//        compSwatchesCtrl.clearChildren()
//        triadFirstSwatchesCtrl.clearChildren()
//        triadSecondSwatchesCtrl.clearChildren()

        DisplayView.currentDisplayViewLayoutHandler = null
        DisplayView.build()
    }

    fun setBaseColor(baseColorName: String?, baseColorPalette: ColorPalette?) {
        baseColor = baseColorPalette ?: baseColor
        SACLayout.baseColorName = baseColorName ?: baseColor.tags()[0]

 //       recreateSpectrumSwatches()
    }

}