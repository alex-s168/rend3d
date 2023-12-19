package me.alex_s168.rend3d.graphics

import java.time.Instant
import java.util.Date

class SimpleLogger(
    val format: String = " %t %s",
    val infoPrefix: String = "[INFO]",
    val warnPrefix: String = "[WARN]",
    val errPrefix: String = "[ERR]",
    val debugPrefix: String = "[DEBUG]"
): Logger {
    fun format(prefix: String, message: String): String =
        prefix + format.replace("%t", Date.from(Instant.now()).toString()).replace("%s", message)

    override fun info(message: String) =
        println(format(infoPrefix, message))

    override fun warn(message: String) =
        System.err.println(format(warnPrefix, message))

    override fun err(message: String) =
        System.err.println(format(errPrefix, message))

    override fun debug(message: String) =
        println(format(debugPrefix, message))
}