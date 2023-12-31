package river.exertion.sac.astro

import river.exertion.sac.Constants

enum class Eighths {
    ZERO_EIGHTHS {
        override val cusp = 0.0
    }
    , ONE_EIGHTH {
        override val cusp = 0.125
        override val label = Constants.SYM_ONE_EIGHTH
    }
    , ONE_QUARTER {
        override val cusp = 0.25
        override val label = Constants.SYM_ONE_QUARTER
    }
    , THREE_EIGHTHS {
        override val cusp = 0.375
        override val label = Constants.SYM_THREE_EIGHTHS
    }
    , ONE_HALF {
        override val cusp = 0.5
        override val label = Constants.SYM_ONE_HALF
    }
    , FIVE_EIGHTHS {
        override val cusp = 0.625
        override val label = Constants.SYM_FIVE_EIGHTHS
    }
    , THREE_QUARTERS {
        override val cusp = 0.75
        override val label = Constants.SYM_THREE_QUARTERS
    }
    , SEVEN_EIGHTHS {
        override val cusp = 0.875
        override val label = Constants.SYM_SEVEN_EIGHTHS
    }
    , EIGHT_EIGHTHS
    ;

    open val cusp: Double = 1.0
    open val label : String = " "

    companion object {
        fun fromOrdinal(value: Int) = entries.firstOrNull { it.ordinal == value }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }

        fun fractionToEighth(fraction : Double) : Eighths {
            var nextEighth : Eighths
            for (eighth in entries) {
                if (eighth == EIGHT_EIGHTHS) break

                nextEighth = fromOrdinal( eighth.ordinal + 1 )!!

                if ( (fraction >= eighth.cusp ) && (fraction < nextEighth.cusp ) ) {
                    return eighth
                }
            }
            return ZERO_EIGHTHS
        }
    }
}