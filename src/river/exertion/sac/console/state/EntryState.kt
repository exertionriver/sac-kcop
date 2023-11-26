package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.view.SACInputProcessor

enum class EntryState : State<SACInputProcessor> {
    NO_ENTRY
    , DATE_ENTRY { override fun getLabel() = "Date Entry (UTC)"; override fun getPrompt() = "yyyy.mm.dd (UTC) : "; override fun getDelim() = "." }
    , TIME_ENTRY { override fun getLabel() = "Time Entry (UTC)"; override fun getPrompt() = "hh:mm:ss (UTC) : "; override fun getDelim() = ":" }
    , LAT_ENTRY { override fun getLabel() = "Latitude Entry"; override fun getPrompt() = "(-S)xy.z123 : " }
    , LON_ENTRY { override fun getLabel() = "Longitude Entry"; override fun getPrompt() = "(-W)xy.z123 : " }
    , ALT_ENTRY { override fun getLabel() = "Altitude Entry"; override fun getPrompt() = "0.123m : " }
    , TZ_ENTRY { override fun getLabel() = "Timezone Entry"; override fun getPrompt() = "(-W)hh : " }
    , LOCATION_NUMBER_ENTRY { override fun getLabel() = "Location Number Entry"; override fun getPrompt() = "location number: " }
    ;

    open fun getLabel(): String = ""
    open fun getPrompt(): String = ""
    open fun getDelim(): String = ""

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        fun defaultState() = NO_ENTRY
    }
}