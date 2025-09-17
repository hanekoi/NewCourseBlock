package org.hanekoi.newcourseblock.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun LocalTime.getFormattedTime():String =
    format(DateTimeFormatter.ofPattern("HH:mm"))

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
