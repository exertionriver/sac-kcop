package river.exertion.sac.astro.state

import river.exertion.sac.astro.AspectCelestial
import river.exertion.sac.console.state.AspectOverlayState
import kotlin.math.pow


enum class StateAspectCelestial {
    ASPECT_SUN
    , ASPECT_MOON
    , ASPECT_MERCURY
    , ASPECT_VENUS
    , ASPECT_MARS
    , ASPECT_JUPITER
    , ASPECT_SATURN
    , ASPECT_URANUS
    , ASPECT_NEPTUNE
    , ASPECT_PLUTO
    , ASPECT_NORTH_NODE {
        override fun getAspectCelestialOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                // 0.5 if isNatComp, 0.25 if is synastry
                aspectOverlayState.isDefault() -> (1).toDouble() / (2.0).pow(2 - aspectOverlayState.isNatComp().compareTo(false))
                else -> super.getAspectCelestialOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_CHIRON {
        override fun getAspectCelestialOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isSelective() -> 0.0
                else -> super.getAspectCelestialOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_PART_OF_FORTUNE {
        override fun getAspectCelestialOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                // 0.5 if isNatComp, 0.25 if is synastry
                aspectOverlayState.isDefault() -> (1).toDouble() / (2.0).pow(2 - aspectOverlayState.isNatComp().compareTo(false))
                aspectOverlayState.isSelective() -> 0.0
                else -> super.getAspectCelestialOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_VERTEX {
        override fun getAspectCelestialOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                // 0.5 if isNatComp, 0.25 if is synastry
                aspectOverlayState.isDefault() -> (1).toDouble() / (2.0).pow(2 - aspectOverlayState.isNatComp().compareTo(false))
                aspectOverlayState.isSelective() -> 0.0
                else -> super.getAspectCelestialOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_BLACK_MOON_LILITH {
        override fun getAspectCelestialOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                // 0.5 if isNatComp, 0.25 if is synastry
                aspectOverlayState.isDefault() -> (1).toDouble() / (2.0).pow(2 - aspectOverlayState.isNatComp().compareTo(false))
                else -> super.getAspectCelestialOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_ASCENDANT
    , ASPECT_MIDHEAVEN
    , ASPECT_CELESTIAL_NONE
    //extended aspects are not rendered on chart but can affect detail sums, as in the case of romantic aspects
    , ASPECT_SUN_MOON_MIDPOINT
    , ASPECT_FIRST_HOUSE
    , ASPECT_SEVENTH_HOUSE
    ;
    open fun getAspectCelestialOrbModifier(aspectOverlayState: AspectOverlayState): Double {
        // 1 if isNatComp, .5 if synastry
        return (2 + aspectOverlayState.isNatComp().compareTo(true) ) / (2).toDouble()
    }

    companion object {
        fun of(aspectCelestial: AspectCelestial) = StateAspectCelestial.entries.first { aspectCelestial.name == it.name }
    }
}