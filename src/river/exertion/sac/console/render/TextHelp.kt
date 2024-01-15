package river.exertion.sac.console.render

import river.exertion.sac.Constants

object TextHelp {

    val helpText = "(ESC)ape back to current date / time\n" +
            "Navigate by:\n" +
            "(s)econds, (m)inutes, (h)ours, (d)ays, (w)eeks, m(o)nths, (y)ears.\n" +
            "(space) to pause navigation. (-) to reverse navigation direction.\n" +
            "Manually set navigation:\n" +
            "(D)ate, (T)ime, l(A)titude, l(O)ngitude, time (Z)one.\n" +
            "Change Orb (p)resets between default, selective, hybrid.\n" +
            "Toggle (a)spects between all, major, minor.\n" +
            "Toggle (t)ime aspects between present and removed.\n" +
            "(0-9) recall stored earth location.\n" +
            "(shift)(0-9) store earth location for session.\n" +
            "(+) show synastry chart between current and second earth location.\n" +
            "(=) show composite chart between current and second earth location.\n" +
            "(.) show character chart between current and second earth location.\n" +
            "So(r)t aspects by first celestial, aspect, second celestial, or values.\n" +
            "(f)ilter aspects by absolute value between >0, >20, >50.\n" +
            "Toggle (R)omantic overlay.\n" +
            "Return to fullscreen by clicking upper-right button, below.\n" +
            "${Constants.SYM_PEACE} ${Constants.SYM_LOVE} ${Constants.SYM_HARMONY}"
}