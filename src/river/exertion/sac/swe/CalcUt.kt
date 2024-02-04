package river.exertion.sac.swe

import river.exertion.kcop.base.Log
import river.exertion.sac.astro.base.Celestial
import river.exertion.sac.astro.base.CelestialData
import river.exertion.sac.astro.base.CelestialHouse
import swisseph.SweConst.*


object CalcUt {
    // NOT-TESTED
    fun getSweCelestialIdx(celestialIdx: Int) : Int {

        return when (Celestial.fromOrdinal(celestialIdx)!!) {
            Celestial.SUN -> SE_SUN
            Celestial.MOON -> SE_MOON
            Celestial.MERCURY -> SE_MERCURY
            Celestial.VENUS -> SE_VENUS
            Celestial.MARS -> SE_MARS
            Celestial.JUPITER -> SE_JUPITER
            Celestial.SATURN -> SE_SATURN
            Celestial.URANUS -> SE_URANUS
            Celestial.NEPTUNE -> SE_NEPTUNE
            Celestial.PLUTO -> SE_PLUTO
            Celestial.NORTH_NODE -> SE_TRUE_NODE
            Celestial.BLACK_MOON_LILITH -> SE_MEAN_APOG
            Celestial.CHIRON -> SE_CHIRON
            Celestial.CERES -> SE_CERES
            Celestial.PALLAS -> SE_PALLAS
            Celestial.JUNO -> SE_JUNO
            Celestial.VESTA -> SE_VESTA
            Celestial.SUN_MOON_MIDPOINT -> SE_SUN //no SE entry for midpoint
        }
    }

    // TESTED-BY TestCalcUt::testGetCelestialData()
    fun getCelestialData(julday : Double, celestialIdx : Int, celestialHousesData : DoubleArray, synCelestialHousesData : DoubleArray) : CelestialData {

        val errorOut = StringBuffer()

        val celestialData = DoubleArray(CalcUtDatas.EXT_SIZE) //for house and transitHouse data

        if (celestialIdx != Celestial.SUN_MOON_MIDPOINT.ordinal) {
            val calcUtDatas = DoubleArray(6)

            val retVal = Swe.sw.swe_calc_ut(julday, getSweCelestialIdx(celestialIdx), SEFLG_SPEED, calcUtDatas, errorOut)
            if (retVal < 0) {
                Log.debug("getCelestialData", "julday: $julday, celestialIdx: $celestialIdx, celestialHousesData: $celestialHousesData, synCelestialHousesData: $synCelestialHousesData" )
            }

            for (data in CalcUtDatas.entries) {
                celestialData[data.ordinal] = calcUtDatas[data.ordinal]
            }

            celestialData[CalcUtDatas.HOUSE_DATA_IDX] = CelestialHouse.getHouseData(celestialData[CalcUtDatas.LONGITUDE_DATA.ordinal], celestialHousesData)
            celestialData[CalcUtDatas.TRANSIT_HOUSE_DATA_IDX] = CelestialHouse.getHouseData(celestialData[CalcUtDatas.LONGITUDE_DATA.ordinal], synCelestialHousesData)
        } else {
            val sunCalcUtDatas = DoubleArray(6)
            val moonCalcUtDatas = DoubleArray(6)

            val sunRetVal = Swe.sw.swe_calc_ut(julday, SE_SUN, SEFLG_SPEED, sunCalcUtDatas, errorOut)
            if (sunRetVal < 0) {
                Log.debug("getCelestialData", "julday: $julday, celestialIdx: $celestialIdx, celestialHousesData: $celestialHousesData, synCelestialHousesData: $synCelestialHousesData" )
            }

            val moonRetVal = Swe.sw.swe_calc_ut(julday, SE_MOON, SEFLG_SPEED, moonCalcUtDatas, errorOut)
            if (moonRetVal < 0) {
                Log.debug("getCelestialData", "julday: $julday, celestialIdx: $celestialIdx, celestialHousesData: $celestialHousesData, synCelestialHousesData: $synCelestialHousesData" )
            }

            celestialData[CalcUtDatas.LONGITUDE_DATA.ordinal] = SwiLib.midpoint(
                sunCalcUtDatas[CalcUtDatas.LONGITUDE_DATA.ordinal],
                moonCalcUtDatas[CalcUtDatas.LONGITUDE_DATA.ordinal]
            )

            celestialData[CalcUtDatas.LATITUDE_DATA.ordinal] = SwiLib.midpoint(
                sunCalcUtDatas[CalcUtDatas.LATITUDE_DATA.ordinal],
                moonCalcUtDatas[CalcUtDatas.LATITUDE_DATA.ordinal]
            )

            (CalcUtDatas.DISTANCE_DATA.ordinal .. CalcUtDatas.DIST_SPEED_DATA.ordinal).forEach { idx ->
                celestialData[idx] = sunCalcUtDatas[idx]
            }

            celestialData[CalcUtDatas.HOUSE_DATA_IDX] = CelestialHouse.getHouseData(celestialData[CalcUtDatas.LONGITUDE_DATA.ordinal], celestialHousesData)
            celestialData[CalcUtDatas.TRANSIT_HOUSE_DATA_IDX] = CelestialHouse.getHouseData(celestialData[CalcUtDatas.LONGITUDE_DATA.ordinal], synCelestialHousesData)

        }

        return CelestialData(celestialData)
    }

    // TESTED-BY TestCalcUt::testGetCelestialsData()
    fun getCelestialsData(julianUtcTimeDecimal : Double, celestialHousesData : DoubleArray, synCelestialHousesData : DoubleArray = celestialHousesData) : Array<CelestialData> {

        return Array(Celestial.entries.size) { celestialIdx:Int -> getCelestialData(julianUtcTimeDecimal, celestialIdx, celestialHousesData, synCelestialHousesData) }
    }

    // TESTED-BY TestCalcUt::testGetCompositeCelestialData()
    fun getCompositeCelestialData(celestialIdx : Int, compositeCelestialHousesData : DoubleArray, firstCelestialsData: Array<CelestialData>, secondCelestialsData: Array<CelestialData>) : CelestialData {

        val compositeCelestialData = DoubleArray(CalcUtDatas.EXT_SIZE)
        val calcUtDatasExtSizeIdx = CalcUtDatas.EXT_SIZE - 1

        for (datasIdx in 0..calcUtDatasExtSizeIdx)
            when(datasIdx) {
                CalcUtDatas.LONGITUDE_DATA.ordinal -> compositeCelestialData[datasIdx] = SwiLib.midpoint(
                    firstCelestialsData[celestialIdx].longitude,
                    secondCelestialsData[celestialIdx].longitude
                )
                CalcUtDatas.LATITUDE_DATA.ordinal -> compositeCelestialData[datasIdx] = SwiLib.midpoint(
                    firstCelestialsData[celestialIdx].latitude,
                    secondCelestialsData[celestialIdx].latitude
                )
                CalcUtDatas.DISTANCE_DATA.ordinal -> compositeCelestialData[datasIdx] = (firstCelestialsData[celestialIdx].distance + secondCelestialsData[celestialIdx].distance) / 2
                CalcUtDatas.LONG_SPEED_DATA.ordinal -> compositeCelestialData[datasIdx] = (firstCelestialsData[celestialIdx].longitudeSpeed + secondCelestialsData[celestialIdx].longitudeSpeed) / 2
                CalcUtDatas.LAT_SPEED_DATA.ordinal -> compositeCelestialData[datasIdx] = (firstCelestialsData[celestialIdx].latitudeSpeed + secondCelestialsData[celestialIdx].latitudeSpeed) / 2
                CalcUtDatas.DIST_SPEED_DATA.ordinal -> compositeCelestialData[datasIdx] = (firstCelestialsData[celestialIdx].distanceSpeed + secondCelestialsData[celestialIdx].distanceSpeed) / 2
                CalcUtDatas.HOUSE_DATA_IDX -> compositeCelestialData[datasIdx] = CelestialHouse.getHouseData(compositeCelestialData[CalcUtDatas.LONGITUDE_DATA.ordinal], compositeCelestialHousesData)
                //transit_house_data doesn't have a value on a composite chart
                CalcUtDatas.TRANSIT_HOUSE_DATA_IDX -> compositeCelestialData[datasIdx] = 0.0
            }

        return CelestialData(compositeCelestialData)
    }

    // TESTED-BY TestCalcUt::testGetCompositeCelestialsData()
    fun getCompositeCelestialsData(compositeCelestialHousesData : DoubleArray, firstCelestialsData: Array<CelestialData>, secondCelestialsData: Array<CelestialData>) : Array<CelestialData> {

        return Array(Celestial.entries.size) { celestialIdx:Int -> getCompositeCelestialData(celestialIdx, compositeCelestialHousesData, firstCelestialsData, secondCelestialsData) }

    }

}
