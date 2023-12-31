package river.exertion.sac.astro

import river.exertion.sac.Constants

enum class SignMode {
    CARDINAL_MODE {
        override val label = Constants.SYM_CARDINAL_MODE
    }
    , FIXED_MODE {
        override val label = Constants.SYM_FIXED_MODE
    }
    , MUTABLE_MODE {
        override val label = Constants.SYM_MUTABLE_MODE
    }
    , NONE_MODE
    ;

    open val label : String = Constants.SYM_NONE

    companion object {
        fun fromOrdinal(ordinal: Int) = Sign.entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = Sign.entries.firstOrNull { it.name == name }
    }
}