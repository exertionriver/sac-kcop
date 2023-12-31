package river.exertion.sac.swe

import river.exertion.sac.astro.CelestialHouse

object HouseName {

    fun getHouseName() = Swe.sw.swe_house_name(CelestialHouse.houseSystem)

}