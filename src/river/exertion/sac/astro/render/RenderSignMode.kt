package river.exertion.sac.astro.render

import river.exertion.sac.Constants

enum class RenderSignMode {
    CARDINAL_MODE { override fun getLabel() = Constants.SYM_CARDINAL_MODE }
    , FIXED_MODE { override fun getLabel() = Constants.SYM_FIXED_MODE }
    , MUTABLE_MODE { override fun getLabel() = Constants.SYM_MUTABLE_MODE }
    , NONE_MODE { override fun getLabel() = Constants.SYM_ASPECT_NONE } ;

    abstract fun getLabel(): String

    companion object {
        fun fromOrdinal(ordinal: Int) = entries.firstOrNull { it.ordinal == ordinal }
        fun fromName(name: String) = entries.firstOrNull { it.name == name }
    }
}