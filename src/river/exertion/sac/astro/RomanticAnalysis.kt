package river.exertion.sac.astro

import river.exertion.sac.astro.base.AspectCelestial
import river.exertion.sac.astro.base.AspectType

//data gathered from https://cafeastrology.com/synastry-2.html
//see: https://cafeastrology.com/termsofuse.html "Limited License"

object RomanticAnalysis {

/*It is desirable to have:

    At least one combination in the Strongest Compatibility (weight 4) category
    At least 4 or 5 in the Very Strong Compatibility (weight 3) category
    Zero or at most 1 in the Red Alert (weight -4) category
    At most 3 in the weight -3 category
    At least 3 but no more than 7 in the weight -2 category [Note that it IS desirable to have some challenging aspects in a relationship.]
*/
        val affinityCelestialAspectModifiers: List<CelestialAspectModifier> = listOf(

//Strongest Compatibility in Synastry (weight 4) // "Blue Ribbons"

//    Sun hard aspect (conjunct, semi-square, square, opposition) Sun/Moon midpoint 4
            CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.CONJUNCTION, 4)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.OPPOSITION, 4)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SQUARE, 4)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SEMISQUARE, 4)
//    Sun in 7th house 4
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SEVENTH_HOUSE, AspectType.CONJUNCTION, 4)
//    Moon hard aspect (conjunct, semi-square, square, opposition) Sun/Moon midpoint 4
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.CONJUNCTION, 4)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.OPPOSITION, 4)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SQUARE, 4)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SEMISQUARE, 4)

//Very Strong Compatibility Factors in Synastry (weight 3)

//    Sun in 1st house 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_FIRST_HOUSE, AspectType.CONJUNCTION, 3)
//    Moon in 7th house 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SEVENTH_HOUSE, AspectType.CONJUNCTION, 3)
//    Moon in 1st house 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_FIRST_HOUSE, AspectType.CONJUNCTION, 3)
//    Venus hard aspect (conjunct, semi-square, square, opposition) Sun/Moon midpoint 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.OPPOSITION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SQUARE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SEMISQUARE, 3)
//    Venus in 7th house 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SEVENTH_HOUSE, AspectType.CONJUNCTION, 3)
//    Mars hard aspect (conjunct, semi-square, square, opposition) Sun/Moon midpoint 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.OPPOSITION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SQUARE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SEMISQUARE, 3)
//    Ascendant hard aspect (conjunct, semi-square, square, opposition) Sun/Moon midpoint 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.OPPOSITION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SQUARE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SEMISQUARE, 3)
//    Ascendant in 7th house 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_SEVENTH_HOUSE, AspectType.CONJUNCTION, 3)
//    Vertex hard aspect (conjunct, semi-square, square, opposition) Sun/Moon midpoint 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VERTEX, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VERTEX, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.OPPOSITION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VERTEX, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SQUARE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VERTEX, AspectCelestial.ASPECT_SUN_MOON_MIDPOINT, AspectType.SEMISQUARE, 3)

//Good Compatibility Factors in Synastry (weight 2)

//    Venus in 1st house 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_FIRST_HOUSE, AspectType.CONJUNCTION, 2)
//    Jupiter in 7th house 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_SEVENTH_HOUSE, AspectType.CONJUNCTION, 2)
        )

        val romanticCelestialAspectModifiers: List<CelestialAspectModifier> = mutableListOf(

//Strongest Compatibility in Synastry (weight 4) // "Blue Ribbons"

//    Sun conjunct Descendant 4
            CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, 4)
//    Sun conjunct or opposition Vertex 4
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, 4)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_VERTEX, AspectType.OPPOSITION, 4)
//    Sun conjunct North Node 4
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NORTH_NODE, AspectType.CONJUNCTION, 4)
//    Moon conjunct Descendant 4
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, 4)
//    Moon conjunct or opposition Vertex 4
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, 4)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_VERTEX, AspectType.OPPOSITION, 4)
//    Moon conjunct North Node 4
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NORTH_NODE, AspectType.CONJUNCTION, 4)
//    Ascendant conjunct North Node 4
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_NORTH_NODE, AspectType.CONJUNCTION, 4)

//Very Strong Compatibility Factors in Synastry (weight 3)

//    Sun trine or sextile Sun 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN, AspectType.SEXTILE, 3)
//    Sun conjunct, trine, or sextile Moon 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MOON, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MOON, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MOON, AspectType.SEXTILE, 3)
//    Sun conjunct Venus 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_VENUS, AspectType.CONJUNCTION, 3)
//    Sun trine Venus 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_VENUS, AspectType.TRINE, 3)
//    Sun trine or sextile Mars 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MARS, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MARS, AspectType.SEXTILE, 3)
//    Sun conjunct, sextile, or trine Jupiter 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_JUPITER, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_JUPITER, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_JUPITER, AspectType.SEXTILE, 3)
//    Sun sextile or trine Saturn 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SATURN, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SATURN, AspectType.SEXTILE, 3)
//    Sun conjunct, trine, or sextile Chiron 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_CHIRON, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_CHIRON, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_CHIRON, AspectType.SEXTILE, 3)
//    Sun conjunct Ascendant 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_ASCENDANT, AspectType.CONJUNCTION, 3)
//    Sun conjunct South Node 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NORTH_NODE, AspectType.OPPOSITION, 3)
//    Moon conjunct, sextile, or trine Moon 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MOON, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MOON, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MOON, AspectType.SEXTILE, 3)
//    Moon conjunct, sextile, or trine Venus 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_VENUS, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_VENUS, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_VENUS, AspectType.SEXTILE, 3)
//    Moon sextile or trine Mars 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MARS, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MARS, AspectType.SEXTILE, 3)
//    Moon conjunct, sextile, or trine Jupiter 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_JUPITER, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_JUPITER, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_JUPITER, AspectType.SEXTILE, 3)
//    Moon sextile or trine Saturn 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SATURN, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SATURN, AspectType.SEXTILE, 3)
//    Moon conjunct, trine, or sextile Chiron 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_CHIRON, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_CHIRON, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_CHIRON, AspectType.SEXTILE, 3)
//    Moon conjunct Ascendant 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_ASCENDANT, AspectType.CONJUNCTION, 3)
//    Moon sextile or trine Ascendant 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_ASCENDANT, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_ASCENDANT, AspectType.SEXTILE, 3)
//    Moon conjunct IC 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.OPPOSITION, 3)
//    Moon conjunct South Node 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NORTH_NODE, AspectType.OPPOSITION, 3)
//    Mercury conjunct, sextile, or trine Jupiter 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_JUPITER, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_JUPITER, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_JUPITER, AspectType.SEXTILE, 3)
//    Venus conjunct, sextile, trine, or opposition Venus 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_VENUS, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_VENUS, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_VENUS, AspectType.SEXTILE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_VENUS, AspectType.OPPOSITION, 3)
//    Venus conjunct, sextile, or trine Mars 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_MARS, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_MARS, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_MARS, AspectType.SEXTILE, 3)
//    Venus conjunct Jupiter 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_JUPITER, AspectType.CONJUNCTION, 3)
//    Venus sextile or trine Saturn 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SATURN, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SATURN, AspectType.SEXTILE, 3)
//    Venus conjunct, trine, or sextile Chiron 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_CHIRON, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_CHIRON, AspectType.TRINE, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_CHIRON, AspectType.SEXTILE, 3)
//    Venus conjunct Ascendant 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_ASCENDANT, AspectType.CONJUNCTION, 3)
//    Venus conjunct Descendant 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, 3)
//    Ascendant conjunct South Node 3
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_NORTH_NODE, AspectType.OPPOSITION, 3)
//    Nodes of the Moon conjunct IC/MC 3
            //n.node
            , CelestialAspectModifier(AspectCelestial.ASPECT_NORTH_NODE, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.CONJUNCTION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_NORTH_NODE, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.OPPOSITION, 3)
            //s.node
            , CelestialAspectModifier(AspectCelestial.ASPECT_NORTH_NODE, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.OPPOSITION, 3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_NORTH_NODE, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.CONJUNCTION, 3)

//Good Compatibility Factors in Synastry (weight 2)

//    Sun opposition Sun 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN, AspectType.OPPOSITION, 2)
//    Sun opposition Moon 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MOON, AspectType.OPPOSITION, 2)
//    Sun conjunct Mercury 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MERCURY, AspectType.CONJUNCTION, 2)
//    Sun sextile or trine Mercury 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MERCURY, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MERCURY, AspectType.SEXTILE, 2)
//    Sun sextile Venus 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_VENUS, AspectType.SEXTILE, 2)
//    Sun conjunct Mars 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MARS, AspectType.CONJUNCTION, 2)
//    Sun sextile or trine Uranus 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_URANUS, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_URANUS, AspectType.SEXTILE, 2)
//    Sun sextile or trine Ascendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_ASCENDANT, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_ASCENDANT, AspectType.SEXTILE, 2)
//    Sun conjunct IC 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.OPPOSITION, 2)
//    Sun sextile or trine Nodes of the Moon 2
            //n.node
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NORTH_NODE, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SEXTILE, 2)
            //s.node duplicates n.node
//    Sun square Nodes of the Moon 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SQUARE, 2)
//    Moon conjunct, sextile, or trine Mercury 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MERCURY, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MERCURY, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MERCURY, AspectType.SEXTILE, 2)
//    Moon sextile or trine Uranus 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_URANUS, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_URANUS, AspectType.SEXTILE, 2)
//    Moon sextile or trine Neptune 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NEPTUNE, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NEPTUNE, AspectType.SEXTILE, 2)
//    Moon sextile or trine Pluto 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_PLUTO, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_PLUTO, AspectType.SEXTILE, 2)
//    Moon trine or sextile Nodes of the Moon 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NORTH_NODE, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SEXTILE, 2)
            //s.node duplicates n.node
//    Moon square Nodes of the Moon 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SQUARE, 2)
            //s.node duplicates n.node
//    Mercury sextile, conjunct, or trine Mercury 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MERCURY, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MERCURY, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MERCURY, AspectType.SEXTILE, 2)
//    Mercury conjunct, sextile, or trine Venus 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_VENUS, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_VENUS, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_VENUS, AspectType.SEXTILE, 2)
//    Mercury sextile or trine Mars 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MARS, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MARS, AspectType.SEXTILE, 2)
//    Mercury conjunct the Ascendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_ASCENDANT, AspectType.CONJUNCTION, 2)
//    Mercury conjunct the Descendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, 2)
//    Mercury conjunct IC or MC 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.OPPOSITION, 2)
//    Venus square or opposition Mars 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_MARS, AspectType.SQUARE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_MARS, AspectType.OPPOSITION, 2)
//    Venus trine or sextile Jupiter 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_JUPITER, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_JUPITER, AspectType.SEXTILE, 2)
//    Venus conjunct Saturn 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SATURN, AspectType.CONJUNCTION, 2)
//    Venus sextile or trine Uranus 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_URANUS, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_URANUS, AspectType.SEXTILE, 2)
//    Venus conjunct Neptune 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NEPTUNE, AspectType.CONJUNCTION, 2)
//    Venus trine or sextile Neptune 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NEPTUNE, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NEPTUNE, AspectType.SEXTILE, 2)
//    Venus conjunct, trine or sextile Pluto 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_PLUTO, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_PLUTO, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_PLUTO, AspectType.SEXTILE, 2)
//    Venus sextile or trine Ascendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_ASCENDANT, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_ASCENDANT, AspectType.SEXTILE, 2)
//    Venus conjunct IC or MC 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_MIDHEAVEN, AspectType.OPPOSITION, 2)
//    Venus conjunct or opposition Vertex 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_VERTEX, AspectType.OPPOSITION, 2)
//    Venus in aspect to Nodes of the Moon 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SEXTILE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SQUARE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.OPPOSITION, 2)
//    Mars conjunct, sextile, or trine Mars 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_MARS, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_MARS, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_MARS, AspectType.SEXTILE, 2)
//    Mars in aspect to Jupiter 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_JUPITER, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_JUPITER, AspectType.SEXTILE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_JUPITER, AspectType.SQUARE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_JUPITER, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_JUPITER, AspectType.OPPOSITION, 2)
//    Mars conjunct, trine, or sextile Pluto 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_PLUTO, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_PLUTO, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_PLUTO, AspectType.SEXTILE, 2)
//    Mars conjunct, sextile, or trine the Ascendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_ASCENDANT, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_ASCENDANT, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_ASCENDANT, AspectType.SEXTILE, 2)
//    Mars conjunct the Descendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, 2)
//    Mars conjunct or opposition Vertex 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_VERTEX, AspectType.OPPOSITION, 2)
//    Jupiter in aspect to Jupiter 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_JUPITER, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_JUPITER, AspectType.SEXTILE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_JUPITER, AspectType.SQUARE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_JUPITER, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_JUPITER, AspectType.OPPOSITION, 2)
//    Jupiter conjunct the Ascendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_ASCENDANT, AspectType.CONJUNCTION, 2)
//    Jupiter conjunct the Descendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, 2)
//    Jupiter conjunct or opposition the Vertex 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_VERTEX, AspectType.OPPOSITION, 2)
//    Ascendant sextile or trine Ascendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_ASCENDANT, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_ASCENDANT, AspectType.SEXTILE, 2)
//    Ascendant opposition Ascendant 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, 2)
//    Ascendant trine or sextile Nodes of the Moon 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_NORTH_NODE, AspectType.TRINE, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SEXTILE, 2)
            //s.node duplicates n.node
//    Ascendant square Nodes of the Moon 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SQUARE, 2)
            //s.node duplicates n.node
//    Vertex conjunct either Node of the Moon 2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VERTEX, AspectCelestial.ASPECT_NORTH_NODE, AspectType.CONJUNCTION, 2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VERTEX, AspectCelestial.ASPECT_NORTH_NODE, AspectType.OPPOSITION, 2)

//Helpful Compatibility Factors in Synastry (weight 1)

//    Sun conjunct Sun 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN, AspectType.CONJUNCTION, 1)
//    Sun square or opposition Jupiter 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_JUPITER, AspectType.OPPOSITION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_JUPITER, AspectType.SQUARE, 1)
//    Sun sextile or trine Neptune 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NEPTUNE, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NEPTUNE, AspectType.SEXTILE, 1)
//    Sun conjunct, sextile or trine Pluto 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_PLUTO, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_PLUTO, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_PLUTO, AspectType.SEXTILE, 1)
//    Moon opposition Moon 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MOON, AspectType.OPPOSITION, 1)
//    Moon square or opposition Venus 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_VENUS, AspectType.OPPOSITION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_VENUS, AspectType.SQUARE, 1)
//    Moon square or opposition Jupiter 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_JUPITER, AspectType.OPPOSITION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_JUPITER, AspectType.SQUARE, 1)
//    Moon conjunct Neptune 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NEPTUNE, AspectType.CONJUNCTION, 1)
//    Moon conjunct Pluto 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_PLUTO, AspectType.CONJUNCTION, 1)
//    Mercury square or opposition Jupiter 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_JUPITER, AspectType.OPPOSITION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_JUPITER, AspectType.SQUARE, 1)
//    Mercury sextile or trine Saturn 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_SATURN, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_SATURN, AspectType.SEXTILE, 1)
//    Mercury in aspect to Uranus 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_URANUS, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_URANUS, AspectType.SEXTILE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_URANUS, AspectType.SQUARE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_URANUS, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_URANUS, AspectType.OPPOSITION, 1)
//    Mercury in aspect to Neptune 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NEPTUNE, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NEPTUNE, AspectType.SEXTILE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NEPTUNE, AspectType.SQUARE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NEPTUNE, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NEPTUNE, AspectType.OPPOSITION, 1)
//    Mercury in aspect to Pluto 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_PLUTO, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_PLUTO, AspectType.SEXTILE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_PLUTO, AspectType.SQUARE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_PLUTO, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_PLUTO, AspectType.OPPOSITION, 1)
//    Mercury sextile or trine Ascendant 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_ASCENDANT, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_ASCENDANT, AspectType.SEXTILE, 1)
//    Mercury conjunct or opposition Vertex 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_VERTEX, AspectType.OPPOSITION, 1)
//    Mercury in aspect to Nodes of the Moon 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NORTH_NODE, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SEXTILE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SQUARE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NORTH_NODE, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NORTH_NODE, AspectType.OPPOSITION, 1)
//    Venus square Venus 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_VENUS, AspectType.SQUARE, 1)
//    Venus square or opposite Jupiter 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_JUPITER, AspectType.OPPOSITION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_JUPITER, AspectType.SQUARE, 1)
//    Venus conjunct Uranus 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_URANUS, AspectType.CONJUNCTION, 1)
//    Venus square Ascendant 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_ASCENDANT, AspectType.SQUARE, 1)
//    Mars sextile or trine Saturn 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_SATURN, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_SATURN, AspectType.SEXTILE, 1)
//    Mars sextile or trine Neptune 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_ASCENDANT, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_ASCENDANT, AspectType.SEXTILE, 1)
//    Mars conjunct, sextile, or trine Uranus 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_URANUS, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_URANUS, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_URANUS, AspectType.SEXTILE, 1)
//    Mars in aspect to Nodes of the Moon 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SEXTILE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SQUARE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_NORTH_NODE, AspectType.OPPOSITION, 1)
//    Jupiter conjunct, sextile, or trine Saturn 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_SATURN, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_SATURN, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_SATURN, AspectType.SEXTILE, 1)
//    Jupiter in aspect to Uranus 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_URANUS, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_URANUS, AspectType.SEXTILE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_URANUS, AspectType.SQUARE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_URANUS, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_URANUS, AspectType.OPPOSITION, 1)
//    Jupiter conjunct, sextile, or trine Neptune 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NEPTUNE, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NEPTUNE, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NEPTUNE, AspectType.SEXTILE, 1)
//    Jupiter conjunct, trine or sextile Pluto 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_PLUTO, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_PLUTO, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_PLUTO, AspectType.SEXTILE, 1)
//    Jupiter sextile, trine, or square Ascendant 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_ASCENDANT, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_ASCENDANT, AspectType.SEXTILE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_ASCENDANT, AspectType.SQUARE, 1)
//    Jupiter in aspect to the Nodes of the Moon 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NORTH_NODE, AspectType.CONJUNCTION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SEXTILE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SQUARE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NORTH_NODE, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NORTH_NODE, AspectType.OPPOSITION, 1)
//    Saturn sextile or trine Saturn 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_SATURN, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_SATURN, AspectType.SEXTILE, 1)
//    Saturn sextile or trine Ascendant 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_ASCENDANT, AspectType.TRINE, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_ASCENDANT, AspectType.SEXTILE, 1)
//    Pluto conjunct Descendant or Vertex 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_PLUTO, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, 1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_PLUTO, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, 1)
//    Ascendant conjunct Ascendant 1
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_ASCENDANT, AspectType.CONJUNCTION, 1)

//Very Challenging Factors in Synastry – Red Alerts (weight -4)

//    Moon square Saturn -4 Red Alert
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SATURN, AspectType.SQUARE, -4)
//    Mercury square Mercury -4 Red Alert
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MERCURY, AspectType.SQUARE, -4)
//    Venus square Saturn -4 Red Alert
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SATURN, AspectType.SQUARE, -4)
//    Mars square or opposition Saturn -4 Red Alert
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_SATURN, AspectType.OPPOSITION, -4)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_SATURN, AspectType.SQUARE, -4)

//Challenging Factors in Synastry (weight -3)

//    Sun `square Moon` -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MOON, AspectType.SQUARE, -3)
//    Moon square Moon -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MOON, AspectType.SQUARE, -3)
//    Moon square Mars -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MARS, AspectType.SQUARE, -3)
//    Moon conjunct or opposition Saturn -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SATURN, AspectType.CONJUNCTION, -3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_SATURN, AspectType.OPPOSITION, -3)
//    Moon square or opposition Neptune -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NEPTUNE, AspectType.OPPOSITION, -3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_NEPTUNE, AspectType.SQUARE, -3)
//    Moon square or opposition Pluto -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_PLUTO, AspectType.OPPOSITION, -3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_PLUTO, AspectType.SQUARE, -3)
//    Mercury square or opposition Neptune -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NEPTUNE, AspectType.OPPOSITION, -3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_NEPTUNE, AspectType.SQUARE, -3)
//    Venus opposition Saturn -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_SATURN, AspectType.OPPOSITION, -3)
//    Venus square or opposition Neptune -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NEPTUNE, AspectType.OPPOSITION, -3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_NEPTUNE, AspectType.SQUARE, -3)
//    Mars conjunct Saturn -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_SATURN, AspectType.CONJUNCTION, -3)
//    Mars square or opposition Pluto -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_PLUTO, AspectType.OPPOSITION, -3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_PLUTO, AspectType.SQUARE, -3)
//    Uranus conjunct Descendant or Vertex -3
            , CelestialAspectModifier(AspectCelestial.ASPECT_URANUS, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, -3)
            , CelestialAspectModifier(AspectCelestial.ASPECT_URANUS, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, -3)

//Somewhat Challenging Factors in Synastry (weight -2)

//    Sun square Sun -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SUN, AspectType.SQUARE, -2)
//    Sun square or opposition Mars -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MARS, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MARS, AspectType.SQUARE, -2)
//    Sun conjunct, square or opposition Saturn -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SATURN, AspectType.CONJUNCTION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SATURN, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_SATURN, AspectType.SQUARE, -2)
//    Sun conjunct, square, or opposition Neptune -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NEPTUNE, AspectType.CONJUNCTION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NEPTUNE, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_NEPTUNE, AspectType.SQUARE, -2)
//    Sun square or opposition Pluto -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_PLUTO, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_PLUTO, AspectType.SQUARE, -2)
//    Sun square Ascendant -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_ASCENDANT, AspectType.SQUARE, -2)
//    Moon square or opposition Mercury -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MERCURY, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MERCURY, AspectType.SQUARE, -2)
//    Moon opposition Mars -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MARS, AspectType.OPPOSITION, -2)
//    Moon square or opposition Uranus -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_URANUS, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_URANUS, AspectType.SQUARE, -2)
//    Moon square Ascendant -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_ASCENDANT, AspectType.SQUARE, -2)
//    Mercury opposition Mercury -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MERCURY, AspectType.OPPOSITION, -2)
//    Mercury square or opposition Mars -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MARS, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MARS, AspectType.SQUARE, -2)
//    Mercury conjunct, square, or opposition Saturn -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_SATURN, AspectType.CONJUNCTION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_SATURN, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_SATURN, AspectType.SQUARE, -2)
//    Venus square or opposition Uranus -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_URANUS, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_URANUS, AspectType.SQUARE, -2)
//    Venus square or opposition Pluto -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_PLUTO, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_VENUS, AspectCelestial.ASPECT_PLUTO, AspectType.SQUARE, -2)
//    Mars square or opposition Mars -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_MARS, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_MARS, AspectType.SQUARE, -2)
//    Mars square or opposition Uranus -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_URANUS, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_URANUS, AspectType.SQUARE, -2)
//    Mars conjunct, square, or opposition Neptune -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_NEPTUNE, AspectType.CONJUNCTION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_NEPTUNE, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_NEPTUNE, AspectType.SQUARE, -2)
//    Saturn conjunct, square or opposition Ascendant -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_ASCENDANT, AspectType.CONJUNCTION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_ASCENDANT, AspectType.SQUARE, -2)
//    Saturn conjunct or opposition Vertex -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_VERTEX, AspectType.OPPOSITION, -2)
//    Saturn conjunct or square Nodes of the Moon -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_NORTH_NODE, AspectType.CONJUNCTION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_NORTH_NODE, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_NORTH_NODE, AspectType.SQUARE, -2)
//    Neptune conjunct Descendant, Ascendant, or Vertex -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_NEPTUNE, AspectCelestial.ASPECT_ASCENDANT, AspectType.CONJUNCTION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_NEPTUNE, AspectCelestial.ASPECT_ASCENDANT, AspectType.OPPOSITION, -2)
            , CelestialAspectModifier(AspectCelestial.ASPECT_NEPTUNE, AspectCelestial.ASPECT_VERTEX, AspectType.CONJUNCTION, -2)
//    Ascendant square Ascendant -2
            , CelestialAspectModifier(AspectCelestial.ASPECT_ASCENDANT, AspectCelestial.ASPECT_ASCENDANT, AspectType.SQUARE, -2)

//Minor Challenges in Synastry (weight -1)

//    Sun square or opposition Mercury -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MERCURY, AspectType.OPPOSITION, -1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_MERCURY, AspectType.SQUARE, -1)
//    Sun square or opposition Venus -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_VENUS, AspectType.OPPOSITION, -1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_VENUS, AspectType.SQUARE, -1)
//    Sun conjunct, square or opposition Uranus -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_URANUS, AspectType.CONJUNCTION, -1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_URANUS, AspectType.OPPOSITION, -1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SUN, AspectCelestial.ASPECT_URANUS, AspectType.SQUARE, -1)
//    Moon conjunct Mars -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_MARS, AspectType.CONJUNCTION, -1)
//    Moon conjunct Uranus -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MOON, AspectCelestial.ASPECT_URANUS, AspectType.CONJUNCTION, -1)
//    Mercury square or opposition Venus -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_VENUS, AspectType.OPPOSITION, -1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_VENUS, AspectType.SQUARE, -1)
//    Mercury conjunct Mars -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_MARS, AspectType.CONJUNCTION, -1)
//    Mercury square Ascendant -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MERCURY, AspectCelestial.ASPECT_ASCENDANT, AspectType.SQUARE, -1)
//    Mars square Ascendant -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_MARS, AspectCelestial.ASPECT_ASCENDANT, AspectType.SQUARE, -1)
//    Jupiter square or opposition Saturn -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_SATURN, AspectType.OPPOSITION, -1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_SATURN, AspectType.SQUARE, -1)
//    Jupiter square or opposition Neptune -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NEPTUNE, AspectType.OPPOSITION, -1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_NEPTUNE, AspectType.SQUARE, -1)
//    Jupiter square or opposition Pluto -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_PLUTO, AspectType.OPPOSITION, -1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_JUPITER, AspectCelestial.ASPECT_PLUTO, AspectType.SQUARE, -1)
//    Saturn square or opposition Saturn -1
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_SATURN, AspectType.OPPOSITION, -1)
            , CelestialAspectModifier(AspectCelestial.ASPECT_SATURN, AspectCelestial.ASPECT_SATURN, AspectType.SQUARE, -1)
        ).plus(affinityCelestialAspectModifiers).toList()

}

