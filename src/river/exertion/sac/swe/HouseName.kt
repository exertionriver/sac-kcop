package river.exertion.sac.swe

import river.exertion.sac.astro.base.CelestialHouse
import river.exertion.sac.swe.Swe

object HouseName {

    fun getHouseName() = Swe.sw.swe_house_name(CelestialHouse.getHouseSystem())

}