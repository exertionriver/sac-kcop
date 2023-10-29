package river.exertion.sac.view

import com.badlogic.gdx.scenes.scene2d.Actor
import kotlinx.datetime.LocalDateTime
import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.kcop.view.KcopFont
import river.exertion.kcop.view.klop.IDisplayViewLayoutHandler
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutHandler
import river.exertion.sac.swe.CalcUt
import river.exertion.sac.swe.Houses
import river.exertion.sac.swe.Julday

object SACLayout : IDisplayViewLayoutHandler {

    var baseColorName = "darkGray"
    var baseColor = ColorPalette.of(baseColorName)

    override fun build(): Actor {
        DVLayoutHandler.currentDvLayout = SACDVLayout.dvLayout()
        DVLayoutHandler.currentFontSize = KcopFont.TEXT
        val testLDT = LocalDateTime(1978,11,16,18,39,0,0)
        val testLat = 30.2667
        val testLong = -97.75

        val uniTimeDec = Julday.getJulianUTCTimeDecimal(testLDT, Julday.UNIVERSAL_TIME)
        val uniTimeHouses = Houses.getCelestialHousesData(uniTimeDec, testLat, testLong)
        val uniCelestials = CalcUt.getCelestialsData(uniTimeDec, uniTimeHouses)

        DVLayoutHandler.currentDvLayout.setTextPaneContent("AppLabel","%1.4f".format(uniCelestials[0].longitude))
        DVLayoutHandler.currentDvLayout.setTextPaneContent("AppLabel2","%1.4f".format(uniCelestials[1].longitude))
        return DVLayoutHandler.build()
    }

    override fun clearContent() {
        DVLayoutHandler.currentDvLayout.clearContent()
        DisplayView.build()
    }

    fun setBaseColor(baseColorName: String?, baseColorPalette: ColorPalette?) {
        baseColor = baseColorPalette ?: baseColor
        SACLayout.baseColorName = baseColorName ?: baseColor.tags()[0]

 //       recreateSpectrumSwatches()
    }

}