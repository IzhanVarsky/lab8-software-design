package event

import clock.Clock
import java.time.Duration
import kotlin.math.max

class ClockEventsStatistic(private val clock: Clock) : EventsStatistic {
    private val events: MutableMap<String, MutableList<Long>> = hashMapOf()

    private val nowEpochSecond: Long
        get() = clock.instant().epochSecond

    private fun getFilterRange(): LongRange {
        val now = nowEpochSecond
        val hourAgo = max(now - Duration.ofHours(1).seconds, 0)
        return hourAgo..now
    }

    override fun incEvent(name: String) {
        events.getOrPut(name) { mutableListOf() }.add(nowEpochSecond)
    }

    override fun getEventStatisticByName(name: String): Int {
        val eventsByName = events[name] ?: return 0
        return eventsByName.count { it in getFilterRange() }
    }

    override fun getAllEventStatistic(): Map<String, Int> =
        events.keys.associateWith { getEventStatisticByName(it) }

    override fun printStatistics() {
        events.forEach { (key, value) ->
            println("Event: `$key`, count: ${value.count { it >= 0 }}")
        }
    }
}