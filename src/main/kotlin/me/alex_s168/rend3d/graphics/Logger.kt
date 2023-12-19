package me.alex_s168.rend3d.graphics

interface Logger {
    fun info(message: String)
    fun warn(message: String)
    fun err(message: String)
    fun debug(message: String)
}