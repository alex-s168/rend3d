package me.alex_s168.graphics

interface UploadTarget {
    fun execute(block: UploadTargetContext.() -> Unit)
}