package me.yarond.mytime

import me.yarond.mytime.model.Day
import java.time.DayOfWeek
import java.time.LocalDate

class Utils {

    companion object {

        fun getCurrentDay(): Day {
            return when (LocalDate.now().dayOfWeek) {
                DayOfWeek.SUNDAY -> Day.Sunday
                DayOfWeek.MONDAY -> Day.Monday
                DayOfWeek.TUESDAY -> Day.Tuesday
                DayOfWeek.WEDNESDAY -> Day.Wednesday
                DayOfWeek.THURSDAY -> Day.Thursday
                DayOfWeek.FRIDAY -> Day.Friday
                DayOfWeek.SATURDAY -> Day.Saturday
            }
        }

        fun getTomorrowDay(): Day {
            return when (getCurrentDay()) {
                Day.Sunday -> Day.Monday
                Day.Monday -> Day.Tuesday
                Day.Tuesday -> Day.Wednesday
                Day.Wednesday -> Day.Thursday
                Day.Thursday -> Day.Friday
                Day.Friday -> Day.Saturday
                Day.Saturday -> Day.Sunday
            }
        }

        fun formatTime(time: String): Int {
            if (time[0] == '0') {
                return time[1].digitToInt()
            }
            return time.toInt()
        }
    }
}