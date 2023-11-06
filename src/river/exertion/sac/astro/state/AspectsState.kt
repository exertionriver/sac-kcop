package river.exertion.sac.astro.state

import river.exertion.sac.Constants.SYM_ALL_BOX
import river.exertion.sac.Constants.SYM_MAJOR_BOX
import river.exertion.sac.Constants.SYM_MINOR_BOX

enum class AspectsState {
    ALL_ASPECTS { override fun getLabel() = SYM_ALL_BOX }
    , MAJOR_ASPECTS { override fun getLabel() = SYM_MAJOR_BOX }
    , MINOR_ASPECTS { override fun getLabel() = SYM_MINOR_BOX };

    abstract fun getLabel() : String

}