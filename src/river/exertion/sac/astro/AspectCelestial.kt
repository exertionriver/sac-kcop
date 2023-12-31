package river.exertion.sac.astro

import river.exertion.sac.Constants
import river.exertion.sac.astro.render.RenderCelestialHouse

enum class AspectCelestial {
    ASPECT_SUN {
        override val label = Celestial.SUN.label
    }
    , ASPECT_MOON {
        override val label = Celestial.MOON.label
    }
    , ASPECT_MERCURY {
        override val label = Celestial.MERCURY.label
    }
    , ASPECT_VENUS {
        override val label = Celestial.VENUS.label
    }
    , ASPECT_MARS {
        override val label = Celestial.MARS.label
    }
    , ASPECT_JUPITER {
        override val label = Celestial.JUPITER.label
    }
    , ASPECT_SATURN {
        override val label = Celestial.SATURN.label
    }
    , ASPECT_URANUS {
        override val label = Celestial.URANUS.label
    }
    , ASPECT_NEPTUNE {
        override val label = Celestial.NEPTUNE.label
    }
    , ASPECT_PLUTO {
        override val label = Celestial.PLUTO.label
    }
    , ASPECT_NORTH_NODE {
        override val label = Celestial.NORTH_NODE.label
    }
    , ASPECT_CHIRON {
        override val label = Celestial.CHIRON.label
    }
    , ASPECT_PART_OF_FORTUNE {
        override val label = CelestialHouse.PART_OF_FORTUNE.label
        override val isTimeAspect = true
    }
    , ASPECT_VERTEX {
        override val label = CelestialHouse.VERTEX.label
        override val isTimeAspect = true
    }
    , ASPECT_BLACK_MOON_LILITH {
        override val label = Celestial.BLACK_MOON_LILITH.label
    }
    , ASPECT_ASCENDANT {
        override val label = CelestialHouse.HOUSE_1_ASC.label
        override val isTimeAspect = true
    }
    , ASPECT_MIDHEAVEN {
        override val label = CelestialHouse.HOUSE_10_MC.label
        override val isTimeAspect = true
    }
    , ASPECT_CELESTIAL_NONE
//extended aspects are not rendered on chart but can affect detail sums, as in the case of romantic aspects
    , ASPECT_SUN_MOON_MIDPOINT {
        override val label = Celestial.SUN.label + "/" + Celestial.MOON.label
        override val isExtendedAspect = true
    }
    , ASPECT_FIRST_HOUSE {
        override val label = CelestialHouse.HOUSE_1_ASC.label
        override val isTimeAspect = true
        override val isExtendedAspect = true
    }
    , ASPECT_SEVENTH_HOUSE {
        override val label = CelestialHouse.HOUSE_7.label
        override val isTimeAspect = true
        override val isExtendedAspect = true
    }
    ;
    open val isTimeAspect: Boolean = false
    open val isExtendedAspect: Boolean = false
    open val label : String = Constants.SYM_NONE

    fun isChartAspectCelestial() = ( !this.isExtendedAspect && (this != ASPECT_CELESTIAL_NONE) )

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }

        fun getChartSize() = entries.filter { !it.isExtendedAspect && it.isChartAspectCelestial() }.size
    }
}