package astro

import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test
import river.exertion.sac.astro.EarthLocation
import kotlin.test.assertEquals

@ExperimentalUnsignedTypes
class TestEarthLocation {

    private val refLocation = EarthLocation.getDefaultEarthLocation(LocalDate(2021, 11, 8))

    @Test
    fun testRender() {

        println(EarthLocation.getRenderLocalTimeLabel(refLocation.localDateTime))
        println(EarthLocation.getRenderUTCTimeLabel(refLocation.utcDateTime))
        println(EarthLocation.getRenderLocalDateLabel(refLocation.localDateTime))
        println(EarthLocation.getRenderUTCDateLabel(refLocation.utcDateTime))
        println(EarthLocation.getRenderLocalTimezoneLabel(refLocation.getTimezoneOffsetString()))
        println(EarthLocation.getRenderUTCTimezoneLabel())
        println(EarthLocation.getRenderLocalLatitudeLabel(refLocation.latitude))
        println(EarthLocation.getRenderLocalLongitudeLabel(refLocation.longitude))

        println(EarthLocation.getRenderEarthLocationDataFirstLine(refLocation))
        println(EarthLocation.getRenderEarthLocationDataSecondLine(refLocation))

        println(EarthLocation.getTimeString(refLocation.localDateTime))
        println(EarthLocation.getTimeString(refLocation.utcDateTime))
    }

    @Test
    fun testTimezoneIteration()  {
        //outside of -18 to 18 throws kotlinx.datetime.IllegalTimeZoneException
        for (timeZoneOffset in -18..18) {
            val timeZoneStringInt =
                EarthLocation.getOffsetStringInt(EarthLocation.getTimeZoneFromOffsetInt(timeZoneOffset))
            val timeZoneStringDouble =
                EarthLocation.getOffsetStringDouble(EarthLocation.getTimeZoneFromOffsetInt(timeZoneOffset))

            if (timeZoneOffset % 2 == 0)
                println ("timeZone: $timeZoneOffset: $timeZoneStringInt")
            else
                println ("timeZone: $timeZoneOffset: $timeZoneStringDouble")

            if (timeZoneOffset == -12) assertEquals("-12.0", timeZoneStringDouble)

            if (timeZoneOffset == 0) assertEquals("Z", EarthLocation.getTimeZoneString(timeZoneOffset))

            if (timeZoneOffset == 5) assertEquals("+5", timeZoneStringInt)
        }
    }
}