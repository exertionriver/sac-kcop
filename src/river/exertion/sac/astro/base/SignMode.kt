package river.exertion.sac.astro.base

enum class SignMode {
    CARDINAL_MODE
    , FIXED_MODE
    , MUTABLE_MODE
    , NONE_MODE
    ;

    companion object {
        fun fromOrdinal(ordinal: Int) = Sign.entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = Sign.entries.firstOrNull { it.name == name }
    }
}