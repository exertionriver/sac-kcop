package river.exertion.sac.view

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import river.exertion.kcop.asset.AssetManagerHandler
import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.kcop.view.KcopFont
import river.exertion.kcop.view.klop.IDisplayViewLayoutHandler
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutHandler

object SACLayout : IDisplayViewLayoutHandler {

    var baseColorName = "darkGray"
    var baseColor = ColorPalette.of(baseColorName)
    var baseFontColor = ColorPalette.of("cornflowerBlue")
    var baseValuesFontColor = ColorPalette.of("paleGoldenrod")

    var fireFontColor = ColorPalette.of("maroon")
    var earthFontColor = ColorPalette.of("olive")
    var airFontColor = ColorPalette.of("green")
    var waterFontColor = ColorPalette.of("navy")

    var positiveFontColor = ColorPalette.of("green")
    var negativeFontColor = ColorPalette.of("maroon")
    var neutralFontColor = ColorPalette.of("paleGoldenrod")

    override fun build(): Actor {
        DVLayoutHandler.currentDvLayout = SACCelestialsHousesDVLayout.dvLayout()
        DVLayoutHandler.currentFontSize = KcopFont.TEXT

        KcopFont.TEXT.font = AssetManagerHandler.getAssets<BitmapFont>().firstOrNull { it.data.name.contains("CODE2000") }.apply { this?.data?.setScale(KcopFont.TEXT.fontScale) }

        SACCelestialsHousesDVLayout.rebuild()

        return DVLayoutHandler.build()
    }

    override fun clearContent() {
        DVLayoutHandler.currentDvLayout.clearContent()
        DisplayView.build()
    }

    fun setBaseColor(baseColorName: String?, baseColorPalette: ColorPalette?) {
        baseColor = baseColorPalette ?: baseColor
        SACLayout.baseColorName = baseColorName ?: baseColor.tags()[0]
    }

}