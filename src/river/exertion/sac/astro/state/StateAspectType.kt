package river.exertion.sac.astro.state

import river.exertion.sac.astro.base.AspectAngle
import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.base.AspectType
import river.exertion.sac.console.state.AspectOverlayState

//Default Orb defn is based on https://www.astro.com/astrology/in_aspect_e.htm?nhor=432453&nho2=860236
//Selective Orb defn is based on readings found at https://astro.cafeastrology.com/natal.php
//Hybrid Orb defn is an original creation
enum class StateAspectType {
    CONJUNCTION {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double = 10.0
    }
    , SEXTILE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() -> 6.0
                aspectOverlayState.isSelective() && !aspectOverlayState.isNatComp() -> 5.0
                else -> 5.0
            }
        }
    }
    , SQUARE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() -> 10.0
                else -> 6.0
            }
        }
    }
    , TRINE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() -> 10.0
                aspectOverlayState.isSelective() && !aspectOverlayState.isNatComp() -> 7.0
                else -> 8.0
            }
        }
    }
    , OPPOSITION {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() -> 10.0
                else -> 9.0
            }
        }
    }
    , QUINCUNX {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() -> 3.0
                aspectOverlayState.isHybrid() -> 2.5
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , SEMISEXTILE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() -> 3.0
                aspectOverlayState.isHybrid() -> 2.5
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , QUINTILE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() || aspectOverlayState.isHybrid() -> 2.0
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , BIQUINTILE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() || aspectOverlayState.isHybrid() -> 2.0
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , SEMISQUARE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() -> 3.0
                aspectOverlayState.isHybrid() -> 2.5
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , SESQUISQUARE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isDefault() -> 3.0
                aspectOverlayState.isHybrid() -> 2.5
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , SEPTILE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isHybrid() -> 1.0
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , NOVILE {
        override fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isHybrid() -> 1.0
                else -> super.getAspectAngleOrb(aspectOverlayState)
            }
        }
    }
    , ASPECT_NONE
    ;
    open fun getAspectAngleOrb(aspectOverlayState: AspectOverlayState): Double = 0.0

    companion object {
        fun of(aspectAngle: AspectAngle) = StateAspectType.entries.first { aspectAngle.getAspectType().name == it.name }
    }
}