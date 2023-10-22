package river.exertion.sac.astro.state

import river.exertion.sac.Constants.LABEL_SPACE
import river.exertion.sac.Constants.SYM_RETRO

enum class NavDirState {
    NAV_FORWARD { override fun getIncDec() = 1 }
    , NAV_REVERSE { override fun getLabel() = SYM_RETRO; override fun getIncDec() = -1 } ;

    open fun getLabel(): String = LABEL_SPACE.padEnd(2, ' ')
    abstract fun getIncDec(): Int
}