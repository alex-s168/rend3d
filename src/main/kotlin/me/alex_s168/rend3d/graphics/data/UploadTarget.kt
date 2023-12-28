package me.alex_s168.rend3d.graphics.data

interface UploadTarget {
    fun bufferData(
        usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW,
        sizeBytes: Int,
        block: UploadTargetContext.() -> Unit
    )
}