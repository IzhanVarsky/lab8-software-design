package event

import clock.SettableClock

fun main() {
    with(EventsStatisticImpl(SettableClock())) {
        incEvent("kek")
        incEvent("kek")
        incEvent("kek")
        incEvent("lol")
        incEvent("lol")
        printStatistics()
    }
}