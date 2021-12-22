package event

interface EventsStatistic {
    fun incEvent(name: String)
    fun getEventStatisticByName(name: String): Int
    fun getAllEventStatistic(): Map<String, Int>
    fun printStatistics()
}