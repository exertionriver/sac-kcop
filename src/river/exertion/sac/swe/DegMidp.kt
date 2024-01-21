package river.exertion.sac.swe

import swisseph.SwissLib

object DegMidp {

    private val sl = SwissLib()

    fun getMidpoint(firstDegree : Double, secondDegree : Double) : Double {

        return sl.swe_deg_midp(firstDegree, secondDegree)
    }
}