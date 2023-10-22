package river.exertion.sac

import kotlinx.datetime.LocalDateTime
import river.exertion.sac.Constants.ALT_ATX
import river.exertion.sac.Constants.ALT_BCS
import river.exertion.sac.Constants.ALT_MHN
import river.exertion.sac.Constants.ALT_NMA
import river.exertion.sac.Constants.KEY_0
import river.exertion.sac.Constants.KEY_1
import river.exertion.sac.Constants.KEY_2
import river.exertion.sac.Constants.KEY_3
import river.exertion.sac.Constants.KEY_4
import river.exertion.sac.Constants.KEY_5
import river.exertion.sac.Constants.KEY_6
import river.exertion.sac.Constants.KEY_7
import river.exertion.sac.Constants.KEY_8
import river.exertion.sac.Constants.KEY_9
import river.exertion.sac.Constants.KEY_AMPERSAND
import river.exertion.sac.Constants.KEY_ASTERISK
import river.exertion.sac.Constants.KEY_AT
import river.exertion.sac.Constants.KEY_BANG
import river.exertion.sac.Constants.KEY_CARET
import river.exertion.sac.Constants.KEY_CLOSEPARENS
import river.exertion.sac.Constants.KEY_DOLLAR
import river.exertion.sac.Constants.KEY_EQUALS
import river.exertion.sac.Constants.KEY_ESC
import river.exertion.sac.Constants.KEY_HASH
import river.exertion.sac.Constants.KEY_OPENPARENS
import river.exertion.sac.Constants.KEY_PERCENT
import river.exertion.sac.Constants.KEY_PLUS
import river.exertion.sac.Constants.KEY_USCORE
import river.exertion.sac.Constants.LAT_ATX
import river.exertion.sac.Constants.LAT_BCS
import river.exertion.sac.Constants.LAT_MHN
import river.exertion.sac.Constants.LAT_NMA
import river.exertion.sac.Constants.LON_ATX
import river.exertion.sac.Constants.LON_BCS
import river.exertion.sac.Constants.LON_MHN
import river.exertion.sac.Constants.LON_NMA
import river.exertion.sac.Constants.TZ_CDT
import river.exertion.sac.Constants.TZ_CST
import river.exertion.sac.Constants.TZ_EST
import river.exertion.sac.astro.base.CelestialSnapshot
import river.exertion.sac.astro.base.EarthLocation

@ExperimentalUnsignedTypes
enum class Profiles {
    PROFILE_0, PROFILE_1, PROFILE_2, PROFILE_3, PROFILE_4, PROFILE_5, PROFILE_6, PROFILE_7, PROFILE_8, PROFILE_9
    , PROFILE_CUR_NAV {override fun getDefaultName() = "CUR_NAV"}, PROFILE_NONE {override fun getDefaultName() = "?"} ;

    open fun getDefaultName() : String = "<empty>"

    companion object {
        fun fromOrdinal(ordinal: Int) = values().firstOrNull { it.ordinal == ordinal }

        fun isCurNavProfile(profileIdx: Int) = (profileIdx == PROFILE_CUR_NAV.ordinal)

        fun isNoneProfile(profileIdx: Int) = (profileIdx == PROFILE_NONE.ordinal)

        fun isStoredProfile(profileIdx : Int) = ( !isCurNavProfile(profileIdx) && !isNoneProfile(profileIdx) )

        fun getDefaultProfile(profile : Profiles) : Profile {

            return when (profile) {
                PROFILE_1 -> Profile("exR"
                        , EarthLocation(LON_ATX, LAT_ATX, ALT_ATX, TZ_CST
                        , LocalDateTime(1978, 11, 16, 18, 39, 0, 0) ) )
                PROFILE_2 -> Profile("emL"
                        , EarthLocation(LON_BCS, LAT_BCS, ALT_BCS, TZ_CDT
                        , LocalDateTime(1981, 7, 21, 15, 39, 0, 0) ) )
                PROFILE_3 -> Profile("mT"
                        , EarthLocation(LON_MHN, LAT_MHN, ALT_MHN, TZ_EST
                        , LocalDateTime(1990, 2, 18, 13, 16, 0, 0) ) )
                PROFILE_4 -> Profile("mM"
                        , EarthLocation(LON_NMA, LAT_NMA, ALT_NMA, TZ_EST
                        , LocalDateTime(1993, 11, 27, 4, 17, 0, 0) ) )
                PROFILE_CUR_NAV -> Profile(profile.getDefaultName(), EarthLocation() )
                PROFILE_NONE -> Profile(profile.getDefaultName(), EarthLocation(), true )
                else -> Profile(profile.getDefaultName(), EarthLocation() )
            }
        }

        fun getDefaultProfiles() : Array<Profile> {

            return Array(values().size) { profileIdx: Int -> getDefaultProfile(fromOrdinal(profileIdx)!!) }

        }

        fun getCompositeProfile(firstProfile : Profile, secondProfile : Profile) : Profile {

            return Profile(firstProfile.profileName + "-" + secondProfile.profileName
                    , firstProfile.earthLocation, false, secondProfile.earthLocation
                    , CelestialSnapshot.getCompositeSnapshot(firstProfile.celestialSnapshot, secondProfile.celestialSnapshot
                        , firstProfile.earthLocation, secondProfile.earthLocation)
                    )

        }

        fun getCurProfileIdx(prevCurProfileIdx : Int, input: Int): Int {

            return when (input) {
                KEY_1 -> PROFILE_1.ordinal
                KEY_2 -> PROFILE_2.ordinal
                KEY_3 -> PROFILE_3.ordinal
                KEY_4 -> PROFILE_4.ordinal
                KEY_5 -> PROFILE_5.ordinal
                KEY_6 -> PROFILE_6.ordinal
                KEY_7 -> PROFILE_7.ordinal
                KEY_8 -> PROFILE_8.ordinal
                KEY_9 -> PROFILE_9.ordinal
                KEY_0 -> PROFILE_0.ordinal
                KEY_ESC -> PROFILE_CUR_NAV.ordinal
                else -> prevCurProfileIdx
            }
        }

        fun getSynProfileIdx(prevSynProfileIdx : Int, input : Int) : Int {

            return when (input) {
                KEY_PLUS, KEY_EQUALS, KEY_USCORE -> PROFILE_NONE.ordinal
                KEY_ESC -> PROFILE_CUR_NAV.ordinal
                else -> prevSynProfileIdx
            }
        }

        fun getStoreCurInputProfileIdx(input: Int): Int {

            return when (input) {
                KEY_BANG -> PROFILE_1.ordinal
                KEY_AT -> PROFILE_2.ordinal
                KEY_HASH -> PROFILE_3.ordinal
                KEY_DOLLAR -> PROFILE_4.ordinal
                KEY_PERCENT -> PROFILE_5.ordinal
                KEY_CARET -> PROFILE_6.ordinal
                KEY_AMPERSAND -> PROFILE_7.ordinal
                KEY_ASTERISK -> PROFILE_8.ordinal
                KEY_OPENPARENS -> PROFILE_9.ordinal
                KEY_CLOSEPARENS -> PROFILE_0.ordinal
                else -> PROFILE_NONE.ordinal
            }
        }

    }
}
