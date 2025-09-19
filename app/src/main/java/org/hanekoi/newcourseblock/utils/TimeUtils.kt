package org.hanekoi.newcourseblock.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val getTodayDate:() -> LocalDate = { LocalDate.now() }

val getWeekDates:() -> List<LocalDate> = {
    run {
    val monday = getTodayDate().with(DayOfWeek.MONDAY)
    (0..6).map { monday.plusDays(it.toLong()) }
    }
}

fun LocalTime.getFormattedTime(): String =
    format(DateTimeFormatter.ofPattern("HH:mm"))

fun LocalDate.getFormattedDate(): String =
    format(DateTimeFormatter.ofPattern("M/dd"))

val periods: List<Pair<LocalTime, LocalTime>> = listOf(
    LocalTime.of(8, 0) to LocalTime.of(8, 45),
    LocalTime.of(8, 55) to LocalTime.of(9, 40),
    LocalTime.of(10, 0) to LocalTime.of(10, 45),
    LocalTime.of(10, 55) to LocalTime.of(11, 40),
    LocalTime.of(12, 0) to LocalTime.of(12, 45),
    LocalTime.of(12, 55) to LocalTime.of(13, 40),
    LocalTime.of(14, 0) to LocalTime.of(14, 45),
    LocalTime.of(14, 55) to LocalTime.of(15, 40),
    LocalTime.of(16, 0) to LocalTime.of(16, 45),
    LocalTime.of(16, 55) to LocalTime.of(17, 40),
    LocalTime.of(18, 0) to LocalTime.of(18, 45),
    LocalTime.of(18, 55) to LocalTime.of(19, 40),
    LocalTime.of(20, 0) to LocalTime.of(20, 45),
    LocalTime.of(20, 55) to LocalTime.of(21, 40)
)
