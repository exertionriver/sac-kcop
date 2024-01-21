package river.exertion.sac.swe

import swisseph.SwissLib
import kotlin.math.PI

object SwiLib {

    private val sl = SwissLib()

    fun midpoint(firstDegree : Double, secondDegree : Double) : Double {

        return sl.swe_deg_midp(firstDegree, secondDegree)
    }

    fun normDeg(degree : Double) : Double {

        return sl.swe_degnorm(degree)
    }

    fun radToDeg(radians : Double) : Double {

        return normDeg(radians * 180F / PI)
    }

}