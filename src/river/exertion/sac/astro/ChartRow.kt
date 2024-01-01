package river.exertion.sac.astro

data class ChartRow(val rowAspects: List<Aspect>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as ChartRow

        return rowAspects == other.rowAspects
    }

    override fun hashCode(): Int {
        val result = rowAspects.hashCode()
        return result
    }
}