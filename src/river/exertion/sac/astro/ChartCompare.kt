package river.exertion.sac.astro

import kotlin.math.abs
import kotlin.math.max

object ChartCompare {

    lateinit var modValueChart : Chart
    lateinit var firstNatalValueChart : Chart
    lateinit var secondNatalValueChart : Chart

    fun getRefImprovedStim() : Double = modValueChart.modValue.stimulation / firstNatalValueChart.modValue.stimulation * 100.0
    fun getEvalImprovedStim() : Double = modValueChart.modValue.stimulation / secondNatalValueChart.modValue.stimulation * 100.0

    fun getRefImprovedPos() : Double = modValueChart.modValue.positive / firstNatalValueChart.modValue.positive * 100.0
    fun getEvalImprovedPos() : Double = modValueChart.modValue.positive / secondNatalValueChart.modValue.positive * 100.0

    fun getRefImprovedCons() : Double = modValueChart.modValue.consonance / firstNatalValueChart.modValue.consonance * 100.0
    fun getEvalImprovedCons() : Double = modValueChart.modValue.consonance / secondNatalValueChart.modValue.consonance * 100.0

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
