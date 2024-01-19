package river.exertion.sac.swe

import river.exertion.sac.astro.base.CelestialHouse

object HouseName {

    fun getHouseName() : String = Swe.sw.swe_house_name(CelestialHouse.houseSystem)

}