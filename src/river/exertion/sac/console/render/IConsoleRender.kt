package river.exertion.sac.console.render

import river.exertion.kcop.view.layout.displayViewLayout.DVTable

interface IConsoleRender {
    val layoutTag : String
    fun setLayout() : DVTable
    fun setContent()
}