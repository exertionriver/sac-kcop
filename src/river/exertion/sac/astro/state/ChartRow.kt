package river.exertion.sac.astro.state

data class ChartRow(val initRowAspects: Array<StateAspect>) {

    val rowAspects : Array<StateAspect> = initRowAspects

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as ChartRow

        if (!rowAspects.contentEquals(other.rowAspects)) return false
        return true
    }

    override fun hashCode(): Int {
        val result = rowAspects.contentHashCode()
        return result
    }
}