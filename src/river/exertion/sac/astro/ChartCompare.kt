package river.exertion.sac.astro

import river.exertion.sac.astro.state.Chart
import kotlin.math.abs
import kotlin.math.max

object ChartCompare {

    lateinit var modValueChart : Chart
    lateinit var firstNatalValueChart : Chart
    lateinit var secondNatalValueChart : Chart

    fun getRefImprovedStim() : Double = modValueChart.getModValue().stimulation / firstNatalValueChart.getModValue().stimulation * 100.0
    fun getEvalImprovedStim() : Double = modValueChart.getModValue().stimulation / secondNatalValueChart.getModValue().stimulation * 100.0

    fun getRefImprovedPos() : Double = modValueChart.getModValue().positive / firstNatalValueChart.getModValue().positive * 100.0
    fun getEvalImprovedPos() : Double = modValueChart.getModValue().positive / secondNatalValueChart.getModValue().positive * 100.0

    fun getRefImprovedCons() : Double = modValueChart.getModValue().consonance / firstNatalValueChart.getModValue().consonance * 100.0
    fun getEvalImprovedCons() : Double = modValueChart.getModValue().consonance / secondNatalValueChart.getModValue().consonance * 100.0

//    fun getRefImprovedAvg() = doubleArrayOf(getRefImprovedStim(), getRefImprovedPos(), getRefImprovedCons()).average()

    fun getMaxStim() = max(getRefImprovedStim(), getEvalImprovedStim())
    fun getMaxPos() = max(getRefImprovedPos(), getEvalImprovedPos())
    fun getMaxCons() = max(getRefImprovedCons(), getEvalImprovedCons())

    fun getRangeStim() = abs(getRefImprovedStim() - getEvalImprovedStim())
    fun getRangePos() = abs(getRefImprovedPos() - getEvalImprovedPos())
    fun getRangeCons() = abs(getRefImprovedCons() - getEvalImprovedCons())

    fun getParityStimMod() = getMaxStim() / (getMaxStim() + getRangeStim())
    fun getParityPosMod() = getMaxPos() / (getMaxPos() + getRangePos())
    fun getParityConsMod() = getMaxCons() / (getMaxCons() + getRangeCons())

    fun getBalanceStim() = (getRefImprovedStim() + getEvalImprovedStim()) / 2
    fun getBalancePos() = (getRefImprovedPos() + getEvalImprovedPos()) / 2
    fun getBalanceCons() = (getRefImprovedCons() + getEvalImprovedCons()) / 2

    fun getRefParityStim() = (getBalanceStim() * getParityStimMod())
    fun getRefParityPos() = (getBalancePos() * getParityPosMod())
    fun getRefParityCons() = (getBalanceCons() * getParityConsMod())

    fun getRefParityAvg() = doubleArrayOf(getRefParityStim(), getRefParityPos(), getRefParityCons()).average()

    fun getWorthwhile() =
        doubleArrayOf(
            getRefParityStim(), getRefParityPos(), getRefParityCons()
            , getRefImprovedStim(), getRefImprovedPos(), getRefImprovedCons()
        ).average()
}
