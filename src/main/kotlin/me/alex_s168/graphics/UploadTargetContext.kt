package me.alex_s168.graphics

import org.lwjgl.opengl.GL43
import java.nio.*


class UploadTargetContext internal constructor(
    internal val type: Int
) {
    fun upload(data: FloatArray, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }

    fun upload(data: IntArray, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }

    fun upload(data: ShortArray, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }

    fun upload(data: DoubleArray, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }

    fun upload(data: FloatBuffer, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }

    fun upload(data: IntBuffer, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }

    fun upload(data: ShortBuffer, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }

    fun upload(data: DoubleBuffer, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }

    fun upload(data: LongArray, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }

    fun upload(data: LongBuffer, usage: GPUBufferObject.Usage = GPUBufferObject.Usage.STATIC_DRAW) {
        RenderSystem.GL.logRenderCall("BufferData", "<data>", usage.glUsage)
        GL43.glBufferData(type, data, usage.glUsage)
    }
}