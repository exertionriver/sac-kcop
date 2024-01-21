package river.exertion.sac.swe

import river.exertion.kcop.base.Log
import river.exertion.sac.astro.base.CelestialHouse
import swisseph.SweConst.SEFLG_RADIANS
import swisseph.SweConst.SE_VERTEX

object Houses {

    //TESTED-BY TestHouses::testGetCelestialHousesData()
    fun getCelestialHousesData(julday : Double, earthLatitude : Double, earthLongitude : Double) : DoubleArray {

        var houseIdx : Int
        var cuspIdx : Int

        val houseCusps = DoubleArray(13)
        val ascmc = DoubleArray(10) //used for vertex.

        val celestialHousesData = DoubleArray(CelestialHouse.entries.size)

        val retVal = Swe.sw.swe_houses(julday, SEFLG_RADIANS, earthLatitude, earthLongitude, CelestialHouse.houseSystem.code, houseCusps, ascmc)
        if (retVal < 0) {
            Log.debug("getCelestialHousesData", "julday: $julday, earthLatitude: $earthLatitude, earthLongitude: $earthLongitude" )
        }

        for (house in CelestialHouse.entries) {
            houseIdx = house.ordinal
            cuspIdx = houseIdx + 1

            celestialHousesData[houseIdx] =
                when (house) {
                    CelestialHouse.VERTEX -> SwiLib.radToDeg(ascmc[SE_VERTEX])
                    CelestialHouse.PART_OF_FORTUNE -> 0.0
                    CelestialHouse.PART_OF_SPIRIT -> 0.0
                    else -> SwiLib.radToDeg(houseCusps[cuspIdx])
                }
        }

        return celestialHousesData
    }

    //TESTED-BY TestHouses::testGetCompositeCelestialHousesData()
    fun getCompositeCelestialHousesData(firstCelestialHousesData: DoubleArray, secondCelestialHouses: DoubleArray) : DoubleArray {

        val compositeCelestialHousesData = DoubleArray(CelestialHouse.entries.size)

        for (house in CelestialHouse.entries ) {
            compositeCelestialHousesData[house.ordinal] =
                SwiLib.midpoint(firstCelestialHousesData[house.ordinal], secondCelestialHouses[house.ordinal])
        }

        return compositeCelestialHousesData
    }
}