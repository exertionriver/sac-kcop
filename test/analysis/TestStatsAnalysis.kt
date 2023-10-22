package analysis

import kotlinx.datetime.*
import org.junit.jupiter.api.Test
import river.exertion.sac.Constants.ALT_ATX
import river.exertion.sac.Constants.LAT_ATX
import river.exertion.sac.Constants.LON_ATX
import river.exertion.sac.analysis.AstroAnalysisRow
import river.exertion.sac.analysis.AstroAnalysisRow.Companion.generateAnalysisRowFromFiles
import river.exertion.sac.analysis.AstroDataRow
import river.exertion.sac.analysis.AstroRefValues
import river.exertion.sac.astro.base.CelestialSnapshot
import river.exertion.sac.astro.base.EarthLocation
import river.exertion.sac.astro.state.*
import river.exertion.sac.astro.value.ValueChart
import river.exertion.sac.Profiles

@ExperimentalUnsignedTypes
class TestStatsAnalysis {

    @Test
    fun testGenerateAnalysis() {

        val refProfile = Profiles.getDefaultProfile(Profiles.PROFILE_3)

        val utcPivot = LocalDateTime(
            1990
            , 2
            , 18
            , 12, 0, 0
        )

        val fullRow = AstroAnalysisRow.generateAnalysisRowFiles(refProfile.celestialSnapshot, range = DateTimePeriod(years = 14), granularity = DateTimePeriod(hours = 1), chartType = ChartState.COMBINED_CHART, utcPivot = utcPivot)
        println("row generated")
    }

    @Test
    fun testLoadAnalysis() {
/*comp        val stimSearch = 1610
        val posSearch = 1356
        val consSearch = .84223
*/
/*syn        val stimSearch = 1304
        val posSearch = 871
        val consSearch = .6679
*/
/*comb*//*
        val stimSearch = 2914
        val posSearch = 2227
        val consSearch = .7642
*/ //6/20/05 * 10.40a
        val stimSearch = 1219
        val posSearch = 752
        val consSearch = .6172

        val astroRefValues = AstroRefValues(stimSearch, posSearch, consSearch)

        val scoreThreshold = 700.0

        val analysisRow = generateAnalysisRowFromFiles(listOf("mt_comb"))

        println("full report:")
        analysisRow.fullReport(astroRefValues = AstroRefValues(stimSearch, posSearch, consSearch))
        analysisRow.rankingReport(astroRefValues = AstroRefValues(stimSearch, posSearch, consSearch))

        val byYear = analysisRow.generateAnalysisRowsGroupBy(AstroAnalysisRow.GroupBy.YEAR).filter { it.score(astroRefValues) >= scoreThreshold }
        println("by year (${byYear.size}):")
        byYear.forEach { yearRow ->
            yearRow.rankingReport(astroRefValues = AstroRefValues(stimSearch, posSearch, consSearch))
        }

        val byMonth = analysisRow.generateAnalysisRowsGroupBy(AstroAnalysisRow.GroupBy.MONTH).filter { it.score(astroRefValues) >= scoreThreshold }
        println("by month (${byMonth.size}):")
        byMonth.forEach { monthRow ->
            monthRow.rankingReport(astroRefValues = AstroRefValues(stimSearch, posSearch, consSearch))
        }
/*
        val byDay = analysisRow.generateAnalysisRowsGroupBy(AstroAnalysisRow.GroupBy.DAY).filter { it.score(astroRefValues) >= scoreThreshold }
        println("by day (${byDay.size}):")
        byDay.forEach { dayRow ->
            dayRow.rankingReport(astroRefValues = AstroRefValues(stimSearch, posSearch, consSearch))
        }*/
    }

    @Test
    fun testApproxAnalysis() {

        val refProfile = Profiles.getDefaultProfile(Profiles.PROFILE_1)
        val synProfile = Profiles.getDefaultProfile(Profiles.PROFILE_2)

        val granularity = DateTimePeriod(hours = 1)
        val duration = DateTimePeriod(hours = 24)

        val firstDatePivot = LocalDate(refProfile.earthLocation.utcDateTime.year, refProfile.earthLocation.utcDateTime.monthNumber, refProfile.earthLocation.utcDateTime.dayOfMonth)
        val secondDatePivot = LocalDate(synProfile.earthLocation.utcDateTime.year, synProfile.earthLocation.utcDateTime.monthNumber, synProfile.earthLocation.utcDateTime.dayOfMonth)

        val firstTimeZone = refProfile.earthLocation.timeZone
        val secondTimeZone = synProfile.earthLocation.timeZone

        val firstPollStart = LocalDateTime(firstDatePivot.year, firstDatePivot.monthNumber, firstDatePivot.dayOfMonth, 0, 0, 0).toInstant(TimeZone.UTC).toLocalDateTime(firstTimeZone)
        val secondPollStart = LocalDateTime(secondDatePivot.year, secondDatePivot.monthNumber, secondDatePivot.dayOfMonth, 0, 0, 0).toInstant(TimeZone.UTC).toLocalDateTime(secondTimeZone)

        val firstPollEnd = firstPollStart.toInstant(firstTimeZone).plus(duration, firstTimeZone).toLocalDateTime(firstTimeZone)
        val secondPollEnd = secondPollStart.toInstant(secondTimeZone).plus(duration, secondTimeZone).toLocalDateTime(secondTimeZone)

        var firstPoll = firstPollStart
        var secondPoll : LocalDateTime

        val dataRows = mutableSetOf<AstroDataRow>()

        println ("$firstPollStart - $firstPollEnd, $secondPollStart - $secondPollEnd")

        while (firstPoll < firstPollEnd) {

            val firstPollSnapshot = CelestialSnapshot(
                EarthLocation(
                    refProfile.earthLocation.longitude,
                    refProfile.earthLocation.latitude,
                    refProfile.earthLocation.altitude,
                    firstTimeZone,
                    firstPoll
                )
            )

            secondPoll = secondPollStart

            while (secondPoll < secondPollEnd) {
                val secondPollSnapshot = CelestialSnapshot(
                    EarthLocation(
                        synProfile.earthLocation.longitude,
                        synProfile.earthLocation.latitude,
                        synProfile.earthLocation.altitude,
                        secondTimeZone,
                        secondPoll
                    )
                )

                val pollValue = ValueChart(
                        StateChart(
                            firstPollSnapshot,
                            secondPollSnapshot,
                            ChartState.SYNASTRY_CHART,
                            AspectsState.ALL_ASPECTS,
                            TimeAspectsState.TIME_ASPECTS_ENABLED,
                            AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT
                        ), AnalysisState.NO_ANALYSIS
                    ).getBaseValue()
                    ValueChart(
                        StateChart(
                            firstPollSnapshot,
                            secondPollSnapshot,
                            ChartState.SYNASTRY_CHART,
                            AspectsState.ALL_ASPECTS,
                            TimeAspectsState.TIME_ASPECTS_ENABLED,
                            AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT
                        ), AnalysisState.ROMANTIC_ANALYSIS).getModValue()

                println ("$firstPoll, $secondPoll")

                dataRows.add(AstroDataRow(secondPoll, pollValue))

                secondPoll = secondPoll.toInstant(firstTimeZone).plus(granularity, firstTimeZone).toLocalDateTime(firstTimeZone)
            }

            firstPoll = firstPoll.toInstant(secondTimeZone).plus(granularity, secondTimeZone).toLocalDateTime(secondTimeZone)

        }

        AstroAnalysisRow(firstPollStart, initDataRows = dataRows.toList()).fullReport()
        println("row generated")

    }

    @Test
    fun testApproxAnalysisMS() {

        val granularity = DateTimePeriod(hours = 1)
        val duration = DateTimePeriod(hours = 24)

        val firstDatePivot = LocalDate(1988, 9, 1)
        val secondDatePivot = LocalDate(1963, 4, 19)

        val firstTimeZone = TimeZone.of("America/Denver")
        val secondTimeZone = TimeZone.of("America/Denver")

        val firstPollStart = LocalDateTime(firstDatePivot.year, firstDatePivot.monthNumber, firstDatePivot.dayOfMonth, 0, 0, 0).toInstant(TimeZone.UTC).toLocalDateTime(firstTimeZone)
        val secondPollStart = LocalDateTime(secondDatePivot.year, secondDatePivot.monthNumber, secondDatePivot.dayOfMonth, 0, 0, 0).toInstant(TimeZone.UTC).toLocalDateTime(secondTimeZone)

        val firstPollEnd = firstPollStart.toInstant(firstTimeZone).plus(duration, firstTimeZone).toLocalDateTime(firstTimeZone)
        val secondPollEnd = secondPollStart.toInstant(secondTimeZone).plus(duration, secondTimeZone).toLocalDateTime(secondTimeZone)

        var firstPoll = firstPollStart
        var secondPoll : LocalDateTime

        val dataRows = mutableSetOf<AstroDataRow>()

        println ("$firstPollStart - $firstPollEnd, $secondPollStart - $secondPollEnd")

        while (firstPoll < firstPollEnd) {

            val firstPollSnapshot = CelestialSnapshot(
                EarthLocation(
                    -108.7426,
                    35.5281,
                    1971.00,
                    firstTimeZone,
                    firstPoll
                )
            )

            secondPoll = secondPollStart

            while (secondPoll < secondPollEnd) {
                val secondPollSnapshot = CelestialSnapshot(
                    EarthLocation(
                        -110.9742,
                        32.2540,
                        806.00,
                        secondTimeZone,
                        secondPoll
                    )
                )

                val pollValue = ValueChart(
                        StateChart(
                            firstPollSnapshot,
                            secondPollSnapshot,
                            ChartState.COMPOSITE_CHART,
                            AspectsState.ALL_ASPECTS,
                            TimeAspectsState.TIME_ASPECTS_ENABLED,
                            AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT
                        ), AnalysisState.NO_ANALYSIS
                    ).getBaseValue() /*+
                        ValueChart(
                        StateChart(
                            firstPollSnapshot,
                            secondPollSnapshot,
                            ChartState.SYNASTRY_CHART,
                            AspectsState.ALL_ASPECTS,
                            TimeAspectsState.TIME_ASPECTS_ENABLED,
                            AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT
                        ), AnalysisState.ROMANTIC_ANALYSIS).getModValue()
*/
                    println ("$firstPoll, $secondPoll")

                    dataRows.add(AstroDataRow(secondPoll, pollValue))

                secondPoll = secondPoll.toInstant(firstTimeZone).plus(granularity, firstTimeZone).toLocalDateTime(firstTimeZone)
            }

            firstPoll = firstPoll.toInstant(secondTimeZone).plus(granularity, secondTimeZone).toLocalDateTime(secondTimeZone)

        }

        AstroAnalysisRow(firstPollStart, initDataRows = dataRows.toList()).fullReport()
        println("row generated")

    }

    @Test
    fun testApproxAnalysisMA() {

        val granularity = DateTimePeriod(hours = 1)
        val duration = DateTimePeriod(hours = 24)

        val firstDatePivot = LocalDate(1984, 6, 6)
        val secondDatePivot = LocalDate(1977, 6, 20)

        val firstTimeZone = TimeZone.of("America/Denver")
        val secondTimeZone = TimeZone.of("America/Chicago")

        val firstPollStart = LocalDateTime(firstDatePivot.year, firstDatePivot.monthNumber, firstDatePivot.dayOfMonth, 0, 0, 0).toInstant(TimeZone.UTC).toLocalDateTime(firstTimeZone)
        val secondPollStart = LocalDateTime(secondDatePivot.year, secondDatePivot.monthNumber, secondDatePivot.dayOfMonth, 0, 0, 0).toInstant(TimeZone.UTC).toLocalDateTime(secondTimeZone)

        val firstPollEnd = firstPollStart.toInstant(firstTimeZone).plus(duration, firstTimeZone).toLocalDateTime(firstTimeZone)
        val secondPollEnd = secondPollStart.toInstant(secondTimeZone).plus(duration, secondTimeZone).toLocalDateTime(secondTimeZone)

        var firstPoll = firstPollStart
        var secondPoll : LocalDateTime

        val dataRows = mutableSetOf<AstroDataRow>()

        println ("$firstPollStart - $firstPollEnd, $secondPollStart - $secondPollEnd")

        while (firstPoll < firstPollEnd) {

            val firstPollSnapshot = CelestialSnapshot(
                EarthLocation(
                    -111.8910,
                    40.7608,
                    1288.00,
                    firstTimeZone,
                    firstPoll
                )
            )

            secondPoll = secondPollStart

            while (secondPoll < secondPollEnd) {
                val secondPollSnapshot = CelestialSnapshot(
                    EarthLocation(
                        LON_ATX,
                        LAT_ATX,
                        ALT_ATX,
                        secondTimeZone,
                        secondPoll
                    )
                )

                val pollValue = ValueChart(
                    StateChart(
                        firstPollSnapshot,
                        secondPollSnapshot,
                        ChartState.COMPOSITE_CHART,
                        AspectsState.ALL_ASPECTS,
                        TimeAspectsState.TIME_ASPECTS_ENABLED,
                        AspectOverlayState.ASPECT_NATCOMP_OVERLAY_DEFAULT
                    ), AnalysisState.NO_ANALYSIS
                ).getBaseValue()/* +
                        ValueChart(
                            StateChart(
                                firstPollSnapshot,
                                secondPollSnapshot,
                                ChartState.SYNASTRY_CHART,
                                AspectsState.ALL_ASPECTS,
                                TimeAspectsState.TIME_ASPECTS_ENABLED,
                                AspectOverlayState.ASPECT_SYNASTRY_OVERLAY_DEFAULT
                            ), AnalysisState.ROMANTIC_ANALYSIS).getModValue()
*/
                println ("$firstPoll, $secondPoll")

                dataRows.add(AstroDataRow(secondPoll, pollValue))

                secondPoll = secondPoll.toInstant(firstTimeZone).plus(granularity, firstTimeZone).toLocalDateTime(firstTimeZone)
            }

            firstPoll = firstPoll.toInstant(secondTimeZone).plus(granularity, secondTimeZone).toLocalDateTime(secondTimeZone)

        }

        AstroAnalysisRow(firstPollStart, initDataRows = dataRows.toList()).fullReport()
        println("row generated")

    }
}