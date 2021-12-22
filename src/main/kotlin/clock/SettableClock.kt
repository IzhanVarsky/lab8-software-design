package clock

import java.time.Instant

class SettableClock(var instant: Instant = Instant.now()) : Clock {
    override fun instant() = instant
}