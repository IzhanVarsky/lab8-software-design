import clock.SettableClock
import event.ClockEventsStatistic
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Instant

class ClockEventsStatisticTest {
    private fun ClockEventsStatistic.myTripleAssert(
        expectedWithDefaultEventName: Int,
        expectedSize: Int
    ) {
        assertEquals(0, getEventStatisticByName(notDefaultEventName))
        assertEquals(expectedWithDefaultEventName, getEventStatisticByName(defaultEventName))
        assertEquals(expectedSize, getAllEventStatistic().size)
    }

    @Test
    fun `one incEvent`() {
        with(ClockEventsStatistic(SettableClock(zeroInstant))) {
            myTripleAssert(0, 0)
            incEvent(defaultEventName)
            myTripleAssert(1, 1)
        }
    }

    @Test
    fun `no events`() {
        val clock = SettableClock(zeroInstant)
        with(ClockEventsStatistic(clock)) {
            myTripleAssert(0, 0)
            clock.instant = instantLessThanHour
            myTripleAssert(0, 0)
            clock.instant = instantGreaterThanHour
            myTripleAssert(0, 0)
        }
    }

    @Test
    fun `same event two times`() {
        val clock = SettableClock(zeroInstant)
        with(ClockEventsStatistic(clock)) {
            incEvent(defaultEventName)
            myTripleAssert(1, 1)
            clock.instant = instantLessThanHour
            myTripleAssert(1, 1)
            clock.instant = instantGreaterThanHour
            myTripleAssert(0, 1)

            clock.instant = instantLessThanHour
            incEvent(defaultEventName)
            myTripleAssert(2, 1)
            clock.instant = instantGreaterThanHour
            myTripleAssert(1, 1)
        }
    }

    @Test
    fun `a lot of same events`() {
        val clock = SettableClock(zeroInstant)
        with(ClockEventsStatistic(clock)) {
            (1..100).forEach { i ->
                clock.instant = zeroInstant
                incEvent(defaultEventName)
                myTripleAssert(i, 1)

                clock.instant = instantGreaterThanHour
                myTripleAssert(0, 1)
            }
        }
    }

    @Test
    fun `new event on every second huge test`() {
        val clock = SettableClock(zeroInstant)
        val maxSecondForEvent = 4000
        val secondsForEvents = 0..maxSecondForEvent
        val testSecondsForEvents = 0..(maxSecondForEvent + 3601)
        with(ClockEventsStatistic(clock)) {
            for (eventSec in secondsForEvents) {
                clock.instant = Instant.ofEpochSecond(eventSec.toLong())
                incEvent(eventSec.toString())

                assertEquals(eventSec + 1, getAllEventStatistic().size)
            }
            for (curTime in testSecondsForEvents) {
                clock.instant = Instant.ofEpochSecond(curTime.toLong())
                for (eventSec in secondsForEvents) {
                    val expected = if (curTime < eventSec || eventSec + 3600 < curTime) 0 else 1
                    assertEquals(expected, getEventStatisticByName(eventSec.toString()))
                }
            }
        }
    }

    companion object {
        private const val defaultEventName: String = "event"
        private const val notDefaultEventName: String = "not default event"
        private val zeroInstant = Instant.ofEpochSecond(0)
        private val instantLessThanHour = Instant.ofEpochSecond(3000)
        private val instantGreaterThanHour = Instant.ofEpochSecond(4000)
    }
}