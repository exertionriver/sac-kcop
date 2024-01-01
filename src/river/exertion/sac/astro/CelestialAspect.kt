package river.exertion.sac.astro

import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.base.AspectType

data class CelestialAspect(val aspectCelestialFirst : AspectCelestial, val aspectCelestialSecond : AspectCelestial, val aspectType : AspectType, val netValue : Value = Value.empty())
