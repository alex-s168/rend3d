package me.alex_s168.rend3d.graphics.data

interface UploadTarget {
    fun execute(block: UploadTargetContext.() -> Unit)
}