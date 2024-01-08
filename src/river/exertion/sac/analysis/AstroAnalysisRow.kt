package river.exertion.sac.analysis

import kotlinx.datetime.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import river.exertion.sac.astro.CelestialSnapshot
import river.exertion.sac.astro.Chart
import river.exertion.sac.astro.EarthLocation
import river.exertion.sac.astro.Value
import river.exertion.sac.console.state.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Suppress("PROVIDED_RUNTIME_TOO_LOW")
@Serializable
data class AstroAnalysisRow(val refUTC : LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                            val range: DateTimePeriod = DateTimePeriod(),
                            val granularity: DateTimePeriod = DateTimePeriod(),
                            val chartState : ChartState = ChartState.NONE_CHART,
                            private val initDataRows : List<AstroDataRow> = listOf(AstroDataRow())) {

    enum class GroupBy { DAY, MONTH, YEAR }

    private var sortedDataRows = initDataRows.sortedBy { it.pollUTC }
    fun sortedDataRows() : List<AstroDataRow> {
        return if (sortedDataRows.isNotEmpty()) sortedDataRows else throw Exception("AstroAnalysisRow: sortedDataRows is empty")
    }

    private var workingDataRows = sortedDataRows()
    private fun workingDataRows() : List<AstroDataRow> {
        return if (workingDataRows.isNotEmpty()) workingDataRows else listOf(AstroDataRow())
    }

    fun resetWorkingDataRows() { workingDataRows = sortedDataRows() }

    // byUTC narrows data set
    fun byYear(year: Int) {
        workingDataRows = workingDataRows.filter { it.pollUTC.year == year }
    }

    fun byMonthNumber(monthNumber: Int) {
        workingDataRows = workingDataRows.filter { it.pollUTC.monthNumber == monthNumber }
    }

    fun byDayOfMonth(dayOfMonth: Int) {
        workingDataRows = workingDataRows.filter { it.pollUTC.dayOfMonth == dayOfMonth }
    }

//    fun byAspectCelestialIn(aspectCelestial : AspectCelestial, sign : Sign) {
//        workingDataRows = workingDataRows.filter { Sign.getSignFromCelestialLongitude(it.pollAspectCelestials[aspectCelestial]!!) == sign }
//    }

    fun generateAnalysisRowsGroupBy(groupBy : GroupBy = GroupBy.MONTH) : MutableList<AstroAnalysisRow> {

        val returnAnalysisRows = mutableListOf<AstroAnalysisRow>()

        val backwardMax = sortedDataRows().first().pollUTC
        val forwardMax = sortedDataRows().last().pollUTC

        val years = forwardMax.year - backwardMax.year

        if (years > 0) {

            val firstYear = backwardMax.year
            val lastYear = forwardMax.year

            (firstYear .. lastYear).forEach { year ->

                if (groupBy == GroupBy.YEAR) {
                    this.resetWorkingDataRows()
                    returnAnalysisRows.add(AstroAnalysisRow(this.refUTC, this.range, this.granularity, this.chartState, this.apply { byYear(year) }.workingDataRows()))
                } else {
                    var firstMonth = 1
                    var lastMonth = 12

                    if (year == firstYear) firstMonth = backwardMax.monthNumber
                    if (year == lastYear) lastMonth = forwardMax.monthNumber

                    (firstMonth .. lastMonth).forEach { monthNumber ->

                        if (groupBy == GroupBy.MONTH) {
                            this.resetWorkingDataRows()
                            returnAnalysisRows.add(AstroAnalysisRow(this.refUTC, this.range, this.granularity, this.chartState, this.apply { byYear(year) }.apply { byMonthNumber(monthNumber) }.workingDataRows()))
                        } else if (groupBy == GroupBy.DAY) {
                            var firstDay = 1
                            var lastDay = if (monthNumber < 12) LocalDate(year, monthNumber, firstDay).daysUntil(LocalDate(year, monthNumber + 1, firstDay))
                            else LocalDate(year, monthNumber, firstDay).daysUntil(LocalDate(year + 1, 1, firstDay))

                            if (year == firstYear && monthNumber == firstMonth) firstDay = backwardMax.dayOfMonth
                            if (year == lastYear && monthNumber == lastMonth) lastDay = forwardMax.dayOfMonth

                            (firstDay .. lastDay).forEach { dayOfMonth ->
                                this.resetWorkingDataRows()
                                returnAnalysisRows.add(AstroAnalysisRow(this.refUTC, this.range, this.granularity, this.chartState, this.apply { this.byYear(year) }.apply { this.byMonthNumber(monthNumber) }.apply { this.byDayOfMonth(dayOfMonth) }.workingDataRows()))
                            }
                        }
                    }
                }
            }

        } else {
            val months = forwardMax.monthNumber - backwardMax.monthNumber

            if (months > 0) {

                if (groupBy == GroupBy.YEAR) {
                    this.resetWorkingDataRows()
                    returnAnalysisRows.add(this)
                }

                val year = forwardMax.year

                val firstMonth = backwardMax.monthNumber
                val lastMonth = firstMonth + months

                (firstMonth .. lastMonth).forEach { monthNumber ->

                    if (groupBy == GroupBy.MONTH) {
                        this.resetWorkingDataRows()
                        returnAnalysisRows.add(AstroAnalysisRow(this.refUTC, this.range, this.granularity, this.chartState, this.apply { this.byMonthNumber(monthNumber) }.workingDataRows()))
                    } else if (groupBy == GroupBy.DAY) {
                        var firstDay = 1
                        var lastDay = if (monthNumber < 12) LocalDate(year, monthNumber, firstDay).daysUntil(LocalDate(year, monthNumber + 1, firstDay))
                        else LocalDate(year, monthNumber, firstDay).daysUntil(LocalDate(year + 1, 1, firstDay))

                        if (monthNumber == firstMonth) firstDay = backwardMax.dayOfMonth
                        if (monthNumber == lastMonth) lastDay = forwardMax.dayOfMonth

                        (firstDay .. lastDay).forEach { dayOfMonth ->
                            this.resetWorkingDataRows()
                            returnAnalysisRows.add(AstroAnalysisRow(this.refUTC, this.range, this.granularity, this.chartState, this.apply { this.byMonthNumber(monthNumber) }.apply { this.byDayOfMonth(dayOfMonth) }.workingDataRows()))
                        }
                    }
                }

            } else if ( (groupBy == GroupBy.YEAR) || (groupBy == GroupBy.MONTH) ) {
                this.resetWorkingDataRows()
                returnAnalysisRows.add(this)
            } else {
                val firstDay = backwardMax.dayOfMonth
                val lastDay = forwardMax.dayOfMonth

                (firstDay .. lastDay).forEach { dayOfMonth ->
                    this.resetWorkingDataRows()
                    returnAnalysisRows.add(AstroAnalysisRow(this.refUTC, this.range, this.granularity, this.chartState, this.apply { this.byDayOfMonth(dayOfMonth) }.workingDataRows()))
                }
            }
        }

        this.resetWorkingDataRows()
        return returnAnalysisRows
    }

    fun minPollUTC() : LocalDateTime = workingDataRows().minOf { it.pollUTC }
    fun maxPollUTC() : LocalDateTime = workingDataRows().maxOf { it.pollUTC }

    fun maxStimulation() = workingDataRows().maxBy { it.value.stimulation }
    fun avgStimulation() : AstroDataRow {
        val avg = workingDataRows().sumOf { it.value.stimulation } / workingDataRows().size
        return workingDataRows().sortedBy { it.value.stimulation }.firstOrNull { it.value.stimulation >= avg } ?: workingDataRows().last()
    }
    fun minStimulation() = workingDataRows().minBy { it.value.stimulation }
    fun medStimulation() = workingDataRows().sortedBy { it.value.stimulation }[workingDataRows().size / 2]
    fun rankStimulation(evalStimulation : Int) : Double {
        val stimEntries = workingDataRows().sortedBy { it.value.stimulation }
        val entry = stimEntries.firstOrNull { it.value.stimulation >= evalStimulation } ?: stimEntries.last()
        val stimUnder = 100.0 * (stimEntries.indexOf(entry) + 1) / stimEntries.size
        return if (stimUnder < 100.0) stimUnder else
            100.0 * (evalStimulation - minStimulation().value.stimulation) / (maxStimulation().value.stimulation - minStimulation().value.stimulation)
    }

    fun maxPositive() = workingDataRows().maxBy { it.value.positive }
    fun avgPositive() : AstroDataRow {
        val avg = workingDataRows().sumOf { it.value.positive } / workingDataRows().size
        return workingDataRows().sortedBy { it.value.positive }.firstOrNull { it.value.positive >= avg } ?: workingDataRows().last()
    }
    fun minPositive() = workingDataRows().minBy { it.value.positive }
    fun medPositive() = workingDataRows().sortedBy { it.value.positive }[workingDataRows().size / 2]
    fun rankPositive(evalPositive : Int) : Double {
        val posEntries = workingDataRows().sortedBy { it.value.positive }
        val entry = posEntries.firstOrNull { it.value.positive >= evalPositive } ?: posEntries.last()
        val posUnder = 100.0 * (posEntries.indexOf(entry) + 1) / posEntries.size
        return if (posUnder < 100.0) posUnder else
            100.0 * (evalPositive - minPositive().value.positive) / (maxPositive().value.positive - minPositive().value.positive)
    }

    fun maxConsonance() = workingDataRows().maxBy { it.value.consonance }
    fun avgConsonance() : AstroDataRow {
        val avg = workingDataRows().sumOf { it.value.consonance } / workingDataRows().size
        return workingDataRows().sortedBy { it.value.consonance }.firstOrNull { it.value.consonance >= avg } ?: workingDataRows().last()
    }
    fun minConsonance() = workingDataRows().minBy { it.value.consonance }
    fun medConsonance() = workingDataRows().sortedBy { it.value.consonance }[workingDataRows().size / 2]
    fun rankConsonance(evalConsonance : Double) : Double {
        val consEntries = workingDataRows().sortedBy { it.value.consonance }
        val entry = consEntries.firstOrNull { it.value.consonance >= evalConsonance } ?: consEntries.last()
        val consUnder = 100.0 * (consEntries.indexOf(entry) + 1) / consEntries.size
        return if (consUnder < 100.0) consUnder else
            100.0 * (evalConsonance - minConsonance().value.consonance) / (maxConsonance().value.consonance - minConsonance().value.consonance)
    }

    fun score(astroRefValues: AstroRefValues) : Double {
        return ((100.0 - rankStimulation(astroRefValues.stimulation)) * 2
                + (100.0 - rankPositive(astroRefValues.positive)) * 5
                + (100.0 - rankConsonance(astroRefValues.consonance)) * 3)
    }

    fun rankingReport(astroRefValues: AstroRefValues) {
            println ("Ranking Report for $refUTC with $chartState")
            println ("Poll Range: min " + minPollUTC() + " / max " + maxPollUTC())
            println ("ranking Stimulation: ${astroRefValues.stimulation} (${rankStimulation(astroRefValues.stimulation)})")
            println ("ranking Positive: ${astroRefValues.positive} (${rankPositive(astroRefValues.positive)})")
            println ("ranking Consonance: ${astroRefValues.consonance} (${rankConsonance(astroRefValues.consonance)})")
            println ("ranking Score: ${score(astroRefValues)}")
            println ("count: ${workingDataRows().size}")
    }

    fun fullReport(astroRefValues: AstroRefValues? = null) {
        println ("Report for $refUTC with $chartState")
        println ("Poll Range: min " + minPollUTC() + " / max " + maxPollUTC())
        println ("max Stimulation: " + maxStimulation().value.stimulation + " @" + maxStimulation().pollUTC.toString())
        println ("min Stimulation: " + minStimulation().value.stimulation + " @" + minStimulation().pollUTC.toString())
        println ("med Stimulation: " + medStimulation().value.stimulation + " @" + medStimulation().pollUTC.toString())
        println ("avg Stimulation: " + avgStimulation().value.stimulation + " @" + avgStimulation().pollUTC.toString())
        if (astroRefValues != null) println ("ranking Stimulation: ${astroRefValues.stimulation} (${rankStimulation(astroRefValues.stimulation)})")
        println ("max Positive: " + maxPositive().value.positive + " @" + maxPositive().pollUTC.toString())
        println ("min Positive: " + minPositive().value.positive + " @" + minPositive().pollUTC.toString())
        println ("med Positive: " + medPositive().value.positive + " @" + medPositive().pollUTC.toString())
        println ("avg Positive: " + avgPositive().value.positive + " @" + avgPositive().pollUTC.toString())
        if (astroRefValues != null) println ("ranking Positive: ${astroRefValues.positive} (${rankPositive(astroRefValues.positive)})")
        println ("max Consonance: " + maxConsonance().value.consonance + " @" + maxConsonance().pollUTC.toString())
        println ("min Consonance: " + minConsonance().value.consonance + " @" + minConsonance().pollUTC.toString())
        println ("med Consonance: " + medConsonance().value.consonance + " @" + medConsonance().pollUTC.toString())
        println ("avg Consonance: " + avgConsonance().value.consonance + " @" + avgConsonance().pollUTC.toString())
        if (astroRefValues != null) println ("ranking Consonance: ${astroRefValues.consonance} (${rankConsonance(astroRefValues.consonance)})")
        println ("count: ${workingDataRows().size}")
    }

    override fun hashCode(): Int {
        return refUTC.hashCode()
    }

    companion object {

        val json = Json { encodeDefaults = true; ignoreUnknownKeys = true }

        @Suppress("NewApi")
        @OptIn(ExperimentalUnsignedTypes::class)
        fun generateAnalysisRowFiles (
            refSnapshot: CelestialSnapshot,
            range: DateTimePeriod,
            granularity: DateTimePeriod,
            utcPivot : LocalDateTime = refSnapshot.refEarthLocation.utcDateTime,
            chartType : ChartState = ChartState.SYNASTRY_CHART,
            timeAspectsState: TimeAspectsState = TimeAspectsState.TIME_ASPECTS_ENABLED
        ) {

            val backwardMax = utcPivot.toInstant(TimeZone.UTC).minus(range, TimeZone.UTC)
            val forwardMax = utcPivot.toInstant(TimeZone.UTC).plus(range, TimeZone.UTC)

            var forwardPoll = backwardMax

            val dataRows = mutableListOf<AstroDataRow>()
            var analysisRow : AstroAnalysisRow
            var outputBA : ByteArray
            var rowCount = 0
            var fileCount = 0

            val outpath = "./datafiles/output_" + Clock.System.now().toLocalDateTime(TimeZone.UTC)
            Files.createDirectory(Path.of(outpath))

            var pollSnapshot : CelestialSnapshot
            var refPollChart : Chart
            var synPollChart : Chart
            var pollValue : Value

            while (forwardPoll <= forwardMax) {

                pollSnapshot = CelestialSnapshot(
                    EarthLocation(
                        longitude = refSnapshot.refEarthLocation.longitude,
                        latitude = refSnapshot.refEarthLocation.latitude,
                        altitude = refSnapshot.refEarthLocation.altitude,
                        timeZone = refSnapshot.refEarthLocation.timeZone,
                        utcDateTime = forwardPoll.toLocalDateTime(TimeZone.UTC)
                    )
                )

                pollValue = when (chartType) {
/*                    ChartState.COMBINED_CHART ->
                        Chart(
                            refSnapshot,
                            pollSnapshot,
                            ChartState.COMPOSITE_CHART,
                            AspectsState.ALL_ASPECTS,
                            timeAspectsState,
                            AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT
                            , AnalysisState.NO_ANALYSIS
                    ).baseValue.avg(
                        Chart(
                            refSnapshot,
                            pollSnapshot,
                            ChartState.SYNASTRY_CHART,
                            AspectsState.ALL_ASPECTS,
                            timeAspectsState,
                            AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT
                            , AnalysisState.NO_ANALYSIS
                    ).baseValue )
  */                  ChartState.SYNASTRY_CHART ->
                        Chart(
                            refSnapshot,
                            pollSnapshot,
                            chartType,
                            AspectsState.ALL_ASPECTS,
                            timeAspectsState,
                            AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT
                            , AnalysisState.NO_ANALYSIS
                    ).baseValue
                    ChartState.COMPOSITE_CHART ->
                        Chart(
                            refSnapshot,
                            pollSnapshot,
                            chartType,
                            AspectsState.ALL_ASPECTS,
                            timeAspectsState,
                            AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT
                            , AnalysisState.NO_ANALYSIS
                    ).baseValue
                    else -> //natal
                            Chart(
                                refSnapshot,
                                pollSnapshot,
                                ChartState.NATAL_CHART,
                                AspectsState.ALL_ASPECTS,
                                timeAspectsState,
                                AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT
                                , AnalysisState.NO_ANALYSIS
                        ).baseValue
                }

                dataRows.add(
                    AstroDataRow(
                        pollSnapshot.refEarthLocation.utcDateTime,
                        pollValue
                    )
                )

                forwardPoll = forwardPoll.plus(granularity, TimeZone.UTC)
                rowCount++
                if ((rowCount % 1000) == 0) {
                    println("dataRow count $rowCount at ${pollSnapshot.refEarthLocation.utcDateTime}")
                }

                if ((rowCount % 10000) == 0) {
                    fileCount++

                    analysisRow = AstroAnalysisRow(refSnapshot.refEarthLocation.utcDateTime, range, granularity, chartType, dataRows)
                    val outfileName = "part$fileCount.json"

                    outputBA = json.encodeToJsonElement(analysisRow).toString().toByteArray()
//                    println("json encoded")

                    File("$outpath/$outfileName").writeBytes(outputBA)
//                    println("file written")

                    println("added dataRows to $outfileName at ${pollSnapshot.refEarthLocation.utcDateTime}")
                    dataRows.clear()
                }
            }

            fileCount++

            analysisRow = AstroAnalysisRow(refSnapshot.refEarthLocation.utcDateTime, range, granularity, chartType, dataRows)
            val outfileName = "part$fileCount.json"

            outputBA = json.encodeToJsonElement(analysisRow).toString().toByteArray()
//                    println("json encoded")

            File("$outpath/$outfileName").writeBytes(outputBA)
//                    println("file written")

            println("added final dataRows to $outfileName at ${forwardMax.toLocalDateTime(TimeZone.UTC)}")
        }

        @Suppress("NewApi")
        fun generateAnalysisRowFromFiles(datafileSubpaths : List<String>) : AstroAnalysisRow {

            var fileAnalysisRow : AstroAnalysisRow

            var returnAnalysisRow = AstroAnalysisRow()
            val returnDataRows = mutableSetOf<AstroDataRow>()

            // ./datafiles/$it
            datafileSubpaths.forEach { datafileSubpath ->

                Files.walk(Paths.get("./datafiles/$datafileSubpath")).filter { Files.isRegularFile(it) }.forEach {
                    val file = it.toFile()

                    fileAnalysisRow = json.decodeFromString(file.readLines().first())

                    println("${it.fileName} read : ${fileAnalysisRow.sortedDataRows.first().pollUTC}")

                    if (returnAnalysisRow.chartState == ChartState.NONE_CHART)
                        returnAnalysisRow = fileAnalysisRow

                    returnDataRows.addAll(fileAnalysisRow.sortedDataRows())
                }
            }

//            returnAnalysisRow.sortedDataRows = returnDataRows.sortedBy { it.pollUTC }

            return AstroAnalysisRow(returnAnalysisRow.refUTC, returnAnalysisRow.range, returnAnalysisRow.granularity, returnAnalysisRow.chartState,
                returnDataRows.toList() )
        }

    }
}