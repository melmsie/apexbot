package me.stylite.predator.utils

object Utils {
    fun toTimeString(l: Long): String {
        val seconds = l / 1000 % 60
        val minutes = l / (1000 * 60) % 60
        val hours = l / (1000 * 60 * 60) % 24
        val days = l / (1000 * 60 * 60 * 24)

        return when {
            days > 0 -> String.format("%02d days, %02d hours, %02d minutes and %02d seconds", days, hours, minutes, seconds)
            hours > 0 -> String.format("%02d hours, %02d minutes and %02d seconds", hours, minutes, seconds)
            minutes > 0 -> String.format("%02d minutes and %02d seconds", minutes, seconds)
            else -> String.format("%d seconds", seconds)
        }
    }
}
