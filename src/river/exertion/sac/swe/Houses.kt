package river.exertion.sac.swe

import river.exertion.sac.Constants.degrees
import river.exertion.sac.astro.base.CelestialHouse
import swisseph.SweConst.SEFLG_RADIANS
import swisseph.SweConst.SE_VERTEX

object Houses {

    fun getCelestialHousesData(julianUtcTimeDecimal : Double, earthLatitude : Double, earthLongitude : Double) : DoubleArray {

        var houseIdx : Int
        var cuspIdx : Int

        var houseCusps = DoubleArray(13)
        var ascmc = DoubleArray(10) //used for vertex.
        var retVal = 0

        val celestialHousesData = DoubleArray(CelestialHouse.entries.size)

        retVal = Swe.sw.swe_houses(julianUtcTimeDecimal, SEFLG_RADIANS, earthLatitude, earthLongitude, CelestialHouse.houseSystem.code, houseCusps, ascmc)
        if (retVal< 0) println("error: <populateCelestialHousePositionData>\n" ) // TODO : put this to a logger

        for (house in CelestialHouse.entries) {
            houseIdx = house.ordinal
            cuspIdx = houseIdx + 1

            celestialHousesData[houseIdx] =
                when (house) {
                    CelestialHouse.VERTEX -> ascmc[SE_VERTEX].degrees()
                    CelestialHouse.PART_OF_FORTUNE -> 0.0
                    CelestialHouse.PART_OF_SPIRIT -> 0.0
                    else -> houseCusps[cuspIdx].degrees()
                }
        }

        return celestialHousesData
    }

    fun getCompositeCelestialHousesData(firstCelestialHousesData: DoubleArray, secondCelestialHouses: DoubleArray) : DoubleArray {

        val compositeCelestialHousesData = DoubleArray(CelestialHouse.entries.size)

        for (house in CelestialHouse.entries ) {
            compositeCelestialHousesData[house.ordinal] =
                DegMidp.getMidpoint(firstCelestialHousesData[house.ordinal], secondCelestialHouses[house.ordinal])
        }

        return compositeCelestialHousesData
    }
}