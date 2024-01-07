package river.exertion.sac.astro.base

import river.exertion.sac.Constants
import river.exertion.sac.console.state.AspectOverlayState
import kotlin.math.pow

enum class AspectCelestial {
    ASPECT_SUN {
        override val label = Celestial.SUN.label
        override val weight = 10.0
    }
    , ASPECT_MOON {
        override val label = Celestial.MOON.label
        override val weight = 10.0
    }
    , ASPECT_MERCURY {
        override val label = Celestial.MERCURY.label
        override val weight = 2.9
    }
    , ASPECT_VENUS {
        override val label = Celestial.VENUS.label
        override val weight = 4.8
    }
    , ASPECT_MARS {
        override val label = Celestial.MARS.label
        override val weight = 3.5
    }
    , ASPECT_JUPITER {
        override val label = Celestial.JUPITER.label
        override val weight = 4.9
    }
    , ASPECT_SATURN {
        override val label = Celestial.SATURN.label
        override val weight = 3.8
    }
    , ASPECT_URANUS {
        override val label = Celestial.URANUS.label
        override val weight = 1.7
    }
    , ASPECT_NEPTUNE {
        override val label = Celestial.NEPTUNE.label
        override val weight = 1.1
    }
    , ASPECT_PLUTO {
        override val label = Celestial.PLUTO.label
        override val weight = 0.3
    }
    , ASPECT_NORTH_NODE {
        override val label = Celestial.NORTH_NODE.label
        override val weight = 2.5

        override fun aspectOverlayOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                // 0.5 if isNatComp, 0.25 if is synastry
                aspectOverlayState.isDefault() -> (1).toDouble() / (2.0).pow(2 - aspectOverlayState.isNatComp().compareTo(false))
                else -> super.aspectOverlayOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_CHIRON {
        override val label = Celestial.CHIRON.label
        override val weight = 0.3

        override fun aspectOverlayOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                aspectOverlayState.isSelective() -> 0.0
                else -> super.aspectOverlayOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_PART_OF_FORTUNE {
        override val label = CelestialHouse.PART_OF_FORTUNE.label
        override val isTimeAspect = true
        override val weight = 0.75

        override fun aspectOverlayOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                // 0.5 if isNatComp, 0.25 if is synastry
                aspectOverlayState.isDefault() -> (1).toDouble() / (2.0).pow(2 - aspectOverlayState.isNatComp().compareTo(false))
                aspectOverlayState.isSelective() -> 0.0
                else -> super.aspectOverlayOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_VERTEX {
        override val label = CelestialHouse.VERTEX.label
        override val isTimeAspect = true
        override val weight = 2.5

        override fun aspectOverlayOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                // 0.5 if isNatComp, 0.25 if is synastry
                aspectOverlayState.isDefault() -> (1).toDouble() / (2.0).pow(2 - aspectOverlayState.isNatComp().compareTo(false))
                aspectOverlayState.isSelective() -> 0.0
                else -> super.aspectOverlayOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_BLACK_MOON_LILITH {
        override val label = Celestial.BLACK_MOON_LILITH.label
        override val weight = 2.5

        override fun aspectOverlayOrbModifier(aspectOverlayState: AspectOverlayState): Double {
            return when {
                // 0.5 if isNatComp, 0.25 if is synastry
                aspectOverlayState.isDefault() -> (1).toDouble() / (2.0).pow(2 - aspectOverlayState.isNatComp().compareTo(false))
                else -> super.aspectOverlayOrbModifier(aspectOverlayState)
            }
        }
    }
    , ASPECT_ASCENDANT {
        override val label = CelestialHouse.HOUSE_1_ASC.label
        override val isTimeAspect = true
        override val weight = 4.0
    }
    , ASPECT_MIDHEAVEN {
        override val label = CelestialHouse.HOUSE_10_MC.label
        override val isTimeAspect = true
        override val weight = 4.0
    }
    , ASPECT_CELESTIAL_NONE
//extended aspects are not rendered on chart but can affect detail sums, as in the case of romantic aspects
    , ASPECT_SUN_MOON_MIDPOINT {
        override val label = Celestial.SUN_MOON_MIDPOINT.label
        override val isExtendedAspect = true
        override val weight = 10.0
    }
    , ASPECT_FIRST_HOUSE {
        override val label = CelestialHouse.HOUSE_1_ASC.label
        override val isTimeAspect = true
        override val isExtendedAspect = true
        override val weight = 4.0
    }
    , ASPECT_SEVENTH_HOUSE {
        override val label = CelestialHouse.HOUSE_7.label
        override val isTimeAspect = true
        override val isExtendedAspect = true
        override val weight = 4.0
    }
    ;
    open val isTimeAspect: Boolean = false
    open val isExtendedAspect: Boolean = false
    open val label : String = Constants.SYM_NONE

    //for weights: https://en.wikipedia.org/wiki/Angular_diameter
    //ln(avg(angular diameter)) normalized to sun = 10
    //NN through MC estimated
    //used to calculate aspect values
    open val weight : Double = 0.0 //TODO: use swe function to return apparent size

    fun isChartAspectCelestial() = ( !this.isExtendedAspect && (this != ASPECT_CELESTIAL_NONE) )

    open fun aspectOverlayOrbModifier(aspectOverlayState: AspectOverlayState): Double {
        // 1 if isNatComp, .5 if synastry
        return (2 + aspectOverlayState.isNatComp().compareTo(true) ) / (2).toDouble()
    }

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }

        val chartAspectCelestials = entries.filter { it.isChartAspectCelestial() }
        val chartDimensionSize = chartAspectCelestials.size
    }
}