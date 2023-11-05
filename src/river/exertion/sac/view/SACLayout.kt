package river.exertion.sac.view

import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import kotlinx.datetime.LocalDateTime
import river.exertion.kcop.asset.AssetManagerHandler
import river.exertion.kcop.asset.view.ColorPalette
import river.exertion.kcop.view.KcopFont
import river.exertion.kcop.view.klop.IDisplayViewLayoutHandler
import river.exertion.kcop.view.layout.DisplayView
import river.exertion.kcop.view.layout.displayViewLayout.*
import river.exertion.sac.Constants
import river.exertion.sac.astro.base.*
import river.exertion.sac.astro.render.*
import river.exertion.sac.astro.render.RenderSign.Companion.getSignLabelFromCelestialLongitude
import river.exertion.sac.astro.state.*
import river.exertion.sac.swe.CalcUt
import river.exertion.sac.swe.HouseName
import river.exertion.sac.swe.Houses
import river.exertion.sac.swe.Julday

object SACLayout : IDisplayViewLayoutHandler {

    var baseColorName = "darkGray"
    var baseColor = ColorPalette.of(baseColorName)

    override fun build(): Actor {
        DVLayoutHandler.currentDvLayout = SACDVLayout.dvLayout()
        DVLayoutHandler.currentFontSize = KcopFont.TEXT
        val testLDT = LocalDateTime(1978,11,16,18,39,0,0)
        val testLat = Constants.LAT_ATX
        val testLong = Constants.LON_ATX
        val testAlt = Constants.ALT_ATX
        val testEarthLocation = EarthLocation(testLong, testLat, testAlt, Constants.TZ_CST, testLDT)

        val uniTimeDec = Julday.getJulianUTCTimeDecimal(testLDT, Julday.UNIVERSAL_TIME)
        val uniTimeHouses = Houses.getCelestialHousesData(uniTimeDec, testLat, testLong)
        val uniCelestials = CalcUt.getCelestialsData(uniTimeDec, uniTimeHouses)

        KcopFont.TEXT.font = AssetManagerHandler.getAssets<BitmapFont>().firstOrNull { it.data.name.contains("CODE2000") }.apply { this?.data?.setScale(KcopFont.TEXT.fontScale) }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("AppLabel","%1.4f".format(uniCelestials[0].longitude))
        DVLayoutHandler.currentDvLayout.setTextPaneContent("AppLabel3","%1.4f".format(uniCelestials[2].longitude))

        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHeader",RenderCelestial.getCelestialsLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signHeader",RenderCelestial.getCelestialsSignLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialLongitude",RenderCelestial.getCelestialsLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("signLongitude", RenderCelestialHouse.getHousesLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialHouse",RenderCelestial.getCelestialsHouseLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialDistance",RenderCelestial.getCelestialsDistanceLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("celestialSpeed",RenderCelestial.getCelestialsLongitudeSpeedLabel())
        RenderCelestial.entries.forEachIndexed { idx, renderCelestial ->
            DVLayoutHandler.currentDvLayout.setTextPaneContent(renderCelestial.name, renderCelestial.getLabel())
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_sign", getSignLabelFromCelestialLongitude(uniCelestials[idx].longitude, uniCelestials[idx].longitudeSpeed) )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_long", "%1.4f".format(uniCelestials[idx].longitude) )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_signLong", CelestialData.getFormattedSignLongitude(uniCelestials[idx].longitude) )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_house", RenderCelestialHouse.celestialHouseLabel(uniCelestials[idx].celestialHouse) )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_dist", "%1.4f".format(uniCelestials[idx].distance) )
            DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestial.name}_speed", "%1.4f".format(uniCelestials[idx].longitudeSpeed) )
        }

        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSystemName", HouseName.getHouseName())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseLatLongHeader",RenderCelestialHouse.getHousesLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSign",RenderCelestialHouse.getHousesSignLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseLongitude",RenderCelestialHouse.getHousesLongitudeLabel())
        DVLayoutHandler.currentDvLayout.setTextPaneContent("houseSignLongitude", RenderSign.getSignLongitudeLabel())

        val pofData = CelestialSnapshot.getPartOfFortuneData(uniCelestials[Celestial.SUN.ordinal].longitude
            , uniCelestials[Celestial.MOON.ordinal].longitude
            , uniTimeHouses[CelestialHouse.HOUSE_1_ASC.ordinal]
            , uniCelestials[Celestial.SUN.ordinal].celestialHouse)
        val posData = CelestialSnapshot.getPartOfSpiritData(uniCelestials[Celestial.SUN.ordinal].longitude
            , uniCelestials[Celestial.MOON.ordinal].longitude
            , uniTimeHouses[CelestialHouse.HOUSE_1_ASC.ordinal]
            , uniCelestials[Celestial.SUN.ordinal].celestialHouse)

        SACDVLayout.housesRows().forEachIndexed { idx, renderCelestialHouseName ->
            when {
                (renderCelestialHouseName == "PART_OF_SPIRIT") -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(renderCelestialHouseName, RenderCelestialHouse.getPartOfSpiritLabel())
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_sign", getSignLabelFromCelestialLongitude(posData) )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_long", "%1.4f".format(posData) )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_signLong", CelestialData.getFormattedSignLongitude(posData) )
                }
                (renderCelestialHouseName == "PART_OF_FORTUNE") -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(renderCelestialHouseName, RenderCelestialHouse.getPartOfFortuneLabel())
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_sign", getSignLabelFromCelestialLongitude(pofData) )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_long", "%1.4f".format(pofData) )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_signLong", CelestialData.getFormattedSignLongitude(pofData) )
                }
                else -> {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(renderCelestialHouseName, RenderCelestialHouse.fromOrdinal(idx)!!.getLabel())
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_sign", getSignLabelFromCelestialLongitude(uniTimeHouses[idx]) )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_long", "%1.4f".format(uniTimeHouses[idx]) )
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${renderCelestialHouseName}_signLong", CelestialData.getFormattedSignLongitude(uniTimeHouses[idx]) )
                }
            }
        }

    val natalSnapshot = CelestialSnapshot(refEarthLocation = testEarthLocation, refCelestialData = uniCelestials, refCelestialHouseData = uniTimeHouses)

    val natalChartAspects = StateChart.getAspects(natalSnapshot, natalSnapshot, ChartState.NATAL_CHART
            , AspectsState.ALL_ASPECTS, TimeAspectsState.TIME_ASPECTS_ENABLED, AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT)

        AspectCelestial.entries.filter { it.isChartAspectCelestial() }.forEachIndexed { rowIdx, chartAspectCelestial ->
            var colIdx = 0

            while (colIdx <= rowIdx) {
                if (rowIdx == colIdx) {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent(chartAspectCelestial.name, chartAspectCelestial.renderAspectCelestial().getLabel())
                } else {
                    DVLayoutHandler.currentDvLayout.setTextPaneContent("${chartAspectCelestial.name}_${AspectCelestial.fromOrdinal(colIdx)}",
                        RenderAspectType.fromName(
                            natalChartAspects.firstOrNull { it.aspectCelestialFirst == AspectCelestial.fromOrdinal(colIdx) && it.aspectCelestialSecond == chartAspectCelestial }?.aspectAngle?.getAspectType()?.name ?: RenderAspectType.ASPECT_NONE.name
                        )!!.getLabel() )
                }
                colIdx++
            }
        }

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