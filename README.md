### EventsStatistic

Приложение подсчитывает число произошедших событий за последний час.

Доступные методы интерфейса `EventsStatistic`:

* `incEvent(String name)` - инкрементит число событий name
* `getEventStatisticByName(name: String): Int` - выдает количество событий name за последний час
* `getAllEventStatistic(): Map<String, Int>` - выдает статистику всех произошедших событий за последний час
* `printStatistic()` - печатает на консоль статистику всех событий за всё время

При реализации тестов использовался паттерн `Clock`.