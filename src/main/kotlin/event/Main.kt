package event

import clock.SettableClock

fun main() {
    with(ClockEventsStatistic(SettableClock())) {
        incEvent("kek")
        incEvent("kek")
        incEvent("kek")
        incEvent("lol")
        incEvent("lol")
        printStatistics()
    }
}