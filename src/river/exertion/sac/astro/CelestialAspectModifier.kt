package river.exertion.sac.astro

import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.base.AspectType

data class CelestialAspectModifier(val aspectCelestialFirst : AspectCelestial, val aspectCelestialSecond : AspectCelestial, val aspectType : AspectType, val modifier : Int) {

    constructor(celestialAspect: CelestialAspect, modifier: Int) : this(celestialAspect.aspectCelestialFirst, celestialAspect.aspectCelestialSecond, celestialAspect.aspectType, modifier)
}