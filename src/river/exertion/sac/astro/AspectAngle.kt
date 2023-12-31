package river.exertion.sac.astro

import river.exertion.sac.Constants.InvalidOrb

enum class AspectAngle {
    CONJUNCTION_0 { override val aspectType = AspectType.CONJUNCTION; override val angleDegree = 0.0 }
    , CONJUNCTION_360 { override val aspectType = AspectType.CONJUNCTION; override val angleDegree = 360.0 }
    , SEXTILE_60 { override val aspectType = AspectType.SEXTILE; override val angleDegree = 60.0 }
    , SEXTILE_300 { override val aspectType = AspectType.SEXTILE; override val angleDegree = 300.0  }
    , SQUARE_90 { override val aspectType = AspectType.SQUARE; override val angleDegree = 90.0 }
    , SQUARE_270 { override val aspectType = AspectType.SQUARE; override val angleDegree = 270.0 }
    , TRINE_120 { override val aspectType = AspectType.TRINE; override val angleDegree = 120.0 }
    , TRINE_240 { override val aspectType = AspectType.TRINE; override val angleDegree = 240.0 }
    , OPPOSITION_180 { override val aspectType = AspectType.OPPOSITION; override val angleDegree = 180.0 }
    , QUINCUNX_150 { override val aspectType = AspectType.QUINCUNX; override val angleDegree = 150.0 }
    , QUINCUNX_210 { override val aspectType = AspectType.QUINCUNX; override val angleDegree = 210.0 }
    , SEMISEXTILE_30 { override val aspectType = AspectType.SEMISEXTILE; override val angleDegree = 30.0 }
    , SEMISEXTILE_330 { override val aspectType = AspectType.SEMISEXTILE; override val angleDegree = 330.0 }
    , QUINTILE_72 { override val aspectType = AspectType.QUINTILE; override val angleDegree = 72.0 }
    , QUINTILE_288 { override val aspectType = AspectType.QUINTILE; override val angleDegree = 288.0 }
    , BIQUINTILE_144 { override val aspectType = AspectType.BIQUINTILE; override val angleDegree = 144.0 }
    , BIQUINTILE_216 { override val aspectType = AspectType.BIQUINTILE; override val angleDegree = 216.0 }
    , SEMISQUARE_45 { override val aspectType = AspectType.SEMISQUARE; override val angleDegree = 45.0 }
    , SEMISQUARE_315 { override val aspectType = AspectType.SEMISQUARE; override val angleDegree = 315.0 }
    , SESQUISQUARE_135 { override val aspectType = AspectType.SESQUISQUARE; override val angleDegree = 135.0 }
    , SESQUISQUARE_225 { override val aspectType = AspectType.SESQUISQUARE; override val angleDegree = 225.0 }
    , SEPTILE_51 { override val aspectType = AspectType.SEPTILE; override val angleDegree = 360.0 / 7 * 1 }
    , SEPTILE_103 { override val aspectType = AspectType.SEPTILE; override val angleDegree = 360.0 / 7 * 2 }
    , SEPTILE_154 { override val aspectType = AspectType.SEPTILE; override val angleDegree = 360.0 / 7 * 3 }
    , SEPTILE_206 { override val aspectType = AspectType.SEPTILE; override val angleDegree = 360.0 / 7 * 4 }
    , SEPTILE_257 { override val aspectType = AspectType.SEPTILE; override val angleDegree = 360.0 / 7 * 5 }
    , SEPTILE_309 { override val aspectType = AspectType.SEPTILE; override val angleDegree = 360.0 / 7 * 6 }
    , NOVILE_40 { override val aspectType = AspectType.NOVILE; override val angleDegree = 40.0 }
    , NOVILE_80 { override val aspectType = AspectType.NOVILE; override val angleDegree = 80.0 }
    , NOVILE_160 { override val aspectType = AspectType.NOVILE; override val angleDegree = 160.0 }
    , NOVILE_200 { override val aspectType = AspectType.NOVILE; override val angleDegree = 200.0 }
    , NOVILE_280 { override val aspectType = AspectType.NOVILE; override val angleDegree = 280.0 }
    , NOVILE_320 { override val aspectType = AspectType.NOVILE; override val angleDegree = 320.0 }
    , ASPECT_ANGLE_NONE
    ;

    open val aspectType: AspectType = AspectType.ASPECT_NONE
    open val angleDegree: Double = InvalidOrb

    fun isAspectAngle() = (this != ASPECT_ANGLE_NONE)

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }
    }
}