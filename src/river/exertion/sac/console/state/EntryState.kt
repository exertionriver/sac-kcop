package river.exertion.sac.console.state

import com.badlogic.gdx.ai.fsm.State
import com.badlogic.gdx.ai.msg.Telegram
import river.exertion.sac.view.SACInputProcessor

enum class EntryState : State<SACInputProcessor> {
    NO_ENTRY
    , DATE_ENTRY { override val label = "Date Entry (UTC)"; override val prompt = "yyyy.mm.dd (UTC) : "; override val delim = "." }
    , TIME_ENTRY { override val label = "Time Entry (UTC)"; override val prompt = "hh:mm:ss (UTC) : "; override val delim = ":" }
    , LAT_ENTRY { override val label = "Latitude Entry"; override val prompt = "(-S)xy.z123 : " }
    , LON_ENTRY { override val label = "Longitude Entry"; override val prompt = "(-W)xy.z123 : " }
    , ALT_ENTRY { override val label = "Altitude Entry"; override val prompt = "0.123m : " }
    , TZ_ENTRY { override val label = "Timezone Entry"; override val prompt = "(-W)hh : " }
    ;

    open val label: String = ""
    open val prompt: String = ""
    open val delim: String = ""

    override fun update(sacInputProcessor: SACInputProcessor) { }
    override fun enter(sacInputProcessor: SACInputProcessor?) { }
    override fun exit(sacInputProcessor: SACInputProcessor?) { }
    override fun onMessage(sacInputProcessor: SACInputProcessor?, telegram: Telegram?): Boolean = true

    companion object {
        val defaultState = NO_ENTRY
    }
}