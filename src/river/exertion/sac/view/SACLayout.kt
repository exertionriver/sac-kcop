package river.exertion.sac.view

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import kotlinx.datetime.LocalDateTime
import river.exertion.kcop.asset.AssetManagerHandler
import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.kcop.view.KcopFont
import river.exertion.kcop.view.klop.IDisplayViewLayoutHandler
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.kcop.view.layout.displayViewLayout.DVLayoutHandler
import river.exertion.sac.Constants
import river.exertion.sac.astro.render.RenderCelestial
import river.exertion.sac.astro.render.RenderCelestialHouse
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

        KcopFont.TEXT.font = AssetManagerHandler.getAssets<BitmapFont>().firstOrNull { it.data.name.contains("CODE2000") }.apply { this?.data?.setScale(KcopFont.TEXT.fontScale) }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("AppLabel","%1.4f".format(uniCelestials[0].longitude))
        DVLayoutHandler.currentDvLayout.setTextPaneContent("AppLabel3","%1.4f".format(uniCelestials[2].longitude))
        DVLayoutHandler.currentDvLayout.setTextPaneContent("AppLabel4","%1.4f".format(uniCelestials[3].longitude))



        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHeader",RenderCelestial.getCelestialsLabel() + "↑")
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signHeader",RenderCelestial.getCelestialsSignLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialLongitude",RenderCelestial.getCelestialsLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signLongitude", RenderCelestialHouse.getHousesLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHouse",RenderCelestial.getCelestialsHouseLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialDistance",RenderCelestial.getCelestialsDistanceLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialSpeed",RenderCelestial.getCelestialsLongitudeSpeedLabel())

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