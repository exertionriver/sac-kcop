package river.exertion.sac.astro.base

import river.exertion.sac.Constants
import river.exertion.sac.console.state.AspectOverlayState
import river.exertion.sac.console.state.AspectsState
import river.exertion.sac.view.SACLayoutHandler.baseFontColor
import river.exertion.sac.view.SACLayoutHandler.negativeFontColor
import river.exertion.sac.view.SACLayoutHandler.neutralFontColor
import river.exertion.sac.view.SACLayoutHandler.positiveFontColor

//Default Orb defn is based on https://www.astro.com/astrology/in_aspect_e.htm?nhor=432453&nho2=860236
//Selective Orb defn is based on readings found at https://astro.cafeastrology.com/natal.php
//Hybrid Orb defn is an original creation
enum class AspectType {
    CONJUNCTION {
        override val isMajor = true
        override val angleDegree = 360.0
        override val label = Constants.SYM_CONJUNCTION
        override val isPositive = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double = 10.0
    }
    , SEXTILE {
        override val isMajor = true
        override val angleDegree = 60.0
        override val label = Constants.SYM_SEXTILE
        override val isPositive = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault -> 6.0
                aspectOverlayState.isSelective && !aspectOverlayState.isNatComp -> 5.0
                else -> 5.0
            }
        }
    }
    , SQUARE {
        override val isMajor = true
        override val angleDegree = 90.0
        override val label = Constants.SYM_SQUARE
        override val isNegative = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault -> 10.0
                else -> 6.0
            }
        }
    }
    , TRINE {
        override val isMajor = true
        override val angleDegree = 120.0
        override val label = Constants.SYM_TRINE
        override val isPositive = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault -> 10.0
                aspectOverlayState.isSelective && !aspectOverlayState.isNatComp -> 7.0
                else -> 8.0
            }
        }
    }
    , OPPOSITION {
        override val isMajor = true
        override val angleDegree = 180.0
        override val label = Constants.SYM_OPPOSITION
        override val isNegative = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault -> 10.0
                else -> 9.0
            }
        }
    }
    , QUINCUNX {
        override val isMinor = true
        override val angleDegree = 150.0
        override val label = Constants.SYM_QUINCUNX
        override val isNeutral = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault -> 3.0
                aspectOverlayState.isHybrid() -> 2.5
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , SEMISEXTILE {
        override val isMinor = true
        override val angleDegree = 30.0
        override val label = Constants.SYM_SEMISEXTILE
        override val isNeutral = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault -> 3.0
                aspectOverlayState.isHybrid() -> 2.5
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , QUINTILE {
        override val isMinor = true
        override val angleDegree = 72.0
        override val label = Constants.SYM_QUINTILE
        override val isPositive = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault || aspectOverlayState.isHybrid() -> 2.0
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , BIQUINTILE {
        override val isMinor = true
        override val angleDegree = 144.0
        override val label = Constants.SYM_BIQUINTILE
        override val isPositive = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault || aspectOverlayState.isHybrid() -> 2.0
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , SEMISQUARE {
        override val isMinor = true
        override val angleDegree = 45.0
        override val label = Constants.SYM_SEMISQUARE
        override val isNegative = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault -> 3.0
                aspectOverlayState.isHybrid() -> 2.5
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , SESQUISQUARE {
        override val isMinor = true
        override val angleDegree = 135.0
        override val label = Constants.SYM_SESQUISQUARE
        override val isNegative = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault -> 3.0
                aspectOverlayState.isHybrid() -> 2.5
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , SEPTILE {
        override val angleDegree = 360.0 / 7
        override val label = Constants.SYM_SEPTILE
        override val isPositive = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isHybrid() -> 1.0
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , NOVILE {
        override val angleDegree = 40.0
        override val label = Constants.SYM_NOVILE
        override val isNeutral = true

        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isHybrid() -> 1.0
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , ASPECT_NONE
    , CONTAINS { //used for houses
        override val label = Constants.SYM_CONTAINS
        override val isPositive = true
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double = 15.0 //evaluated from house midpoint
    }
    ;
    open val isMajor: Boolean = false
    open val isMinor: Boolean = false
    open val angleDegree: Double = 0.0

    open val label: String = Constants.SYM_NONE

    open val isPositive : Boolean = false
    open val isNegative : Boolean = false
    open val isNeutral : Boolean = false

    fun isChartAspectType() = !(this == ASPECT_NONE || this == CONTAINS)

    open val fontColor by lazy { when {
        isPositive -> positiveFontColor
        isNegative -> negativeFontColor
        isNeutral -> neutralFontColor
        else -> baseFontColor
    } }

    open fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double = 0.0

    companion object {
        fun fromOrdinal(ordinal: Int) = AspectCelestial.entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = AspectCelestial.entries.firstOrNull { it.name == name }

        fun filterState(aspectsState: AspectsState) : List<AspectType> {
            return when (aspectsState) {
                AspectsState.MAJOR_ASPECTS -> AspectType.entries.filter { it.isMajor }
                AspectsState.MINOR_ASPECTS -> AspectType.entries.filter { it.isMajor || it.isMinor }
                else -> AspectType.entries.filter { it.isChartAspectType() }
            }
        }
    }
}