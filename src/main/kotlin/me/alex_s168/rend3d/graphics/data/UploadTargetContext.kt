package me.alex_s168.rend3d.graphics.data

import me.alex_s168.math.mat.impl.Mat3f
import me.alex_s168.math.mat.impl.Mat3i
import me.alex_s168.math.mat.impl.Mat4f
import me.alex_s168.math.mat.impl.Mat4i
import me.alex_s168.math.vec.impl.Vec2f
import me.alex_s168.math.vec.impl.Vec2i
import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.math.vec.impl.Vec4f
import me.alex_s168.rend3d.graphics.RenderSystem
import org.lwjgl.opengl.GL43
import java.nio.*

class UploadTargetContext internal constructor(
    internal val type: Int,
    internal val usage: GPUBufferObject.Usage,
    size: Int
) {

    init {
        RenderSystem.GL.logRenderCall("BufferData", type, size, usage.glUsage)
        GL43.glBufferData(type, size.toLong(), usage.glUsage)
    }

    internal fun finalize() {

    }

    private var writer = 0L

    fun upload(d: Byte) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, ByteBuffer.allocate(1).put(d).flip())
        writer += Byte.SIZE_BYTES
    }

    fun upload(d: Short) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, ShortBuffer.allocate(1).put(d).flip())
        writer += Short.SIZE_BYTES
    }

    fun upload(d: Int) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, IntBuffer.allocate(1).put(d).flip())
        writer += Int.SIZE_BYTES
    }

    fun upload(d: Long) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, LongBuffer.allocate(1).put(d).flip())
        writer += Long.SIZE_BYTES
    }

    fun upload(d: Float) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, FloatBuffer.allocate(1).put(d).flip())
        writer += Float.SIZE_BYTES
    }

    fun upload(d: Double) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, DoubleBuffer.allocate(1).put(d).flip())
        writer += Double.SIZE_BYTES
    }

    fun upload(d: ByteArray) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, ByteBuffer.allocate(d.size).put(d).flip())
        writer += Byte.SIZE_BYTES * d.size
    }

    fun upload(d: ShortArray) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, d)
        writer += Short.SIZE_BYTES * d.size
    }

    fun upload(d: IntArray) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, d)
        writer += Int.SIZE_BYTES * d.size
    }

    fun upload(d: LongArray) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, d)
        writer += Long.SIZE_BYTES * d.size
    }

    fun upload(d: FloatArray) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, d)
        writer += Float.SIZE_BYTES * d.size
    }

    fun upload(d: DoubleArray) {
        RenderSystem.GL.logRenderCall("BufferSubData", type, writer, d)
        GL43.glBufferSubData(type, writer, d)
        writer += Double.SIZE_BYTES * d.size
    }

    fun upload(d: Vec2i) =
        kotlin.runCatching { d.asArray() }.getOrNull() ?: d.toArray()

    fun upload(d: Vec2f) =
        kotlin.runCatching { d.asArray() }.getOrNull() ?: d.toArray()

    fun upload(d: Vec3f) =
        kotlin.runCatching { d.asArray() }.getOrNull() ?: d.toArray()

    fun upload(d: Vec4f) =
        kotlin.runCatching { d.asArray() }.getOrNull() ?: d.toArray()

    fun upload(d: Mat3f) =
        kotlin.runCatching { d.asArray() }.getOrNull() ?: d.toArray()

    fun upload(d: Mat3i) =
        kotlin.runCatching { d.asArray() }.getOrNull() ?: d.toArray()

    fun upload(d: Mat4f) =
        kotlin.runCatching { d.asArray() }.getOrNull() ?: d.toArray()

    fun upload(d: Mat4i) =
        kotlin.runCatching { d.asArray() }.getOrNull() ?: d.toArray()
}