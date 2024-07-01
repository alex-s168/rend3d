package me.alex_s168.rend3d.graphics.data

import me.alex_s168.rend3d.graphics.RenderSystem
import org.lwjgl.opengl.GL43.*

class GPUBufferObject(
    val type: Type = Type.ARRAY
): AutoCloseable, UploadTarget {
    internal val id: Int = RenderSystem.GL.genBuffers()

    fun bind() {
        RenderSystem.GL.bindBuffer(type.glType, id)
    }

    override fun bufferData(usage: Usage, sizeBytes: Int, block: UploadTargetContext.() -> Unit) {
        bind()
        UploadTargetContext(type.glType, usage, sizeBytes)
            .also(block)
            .finalize()
    }

    override fun close() {
        RenderSystem.GL.deleteBuffers(id)
    }

    enum class Type(val glType: Int) {
        ARRAY(GL_ARRAY_BUFFER),
        ELEMENT_ARRAY(GL_ELEMENT_ARRAY_BUFFER),
        COPY_SRC(GL_COPY_READ_BUFFER),
        COPY_DST(GL_COPY_WRITE_BUFFER),
        PIXEL_PACK(GL_PIXEL_PACK_BUFFER),
        SHADER_STORAGE(GL_SHADER_STORAGE_BUFFER),
        TEXTURE(GL_TEXTURE_BUFFER),
        UNIFORM(GL_UNIFORM_BUFFER)
    }

    enum class Usage(val glUsage: Int) {
        STATIC_DRAW(GL_STATIC_DRAW),
        STATIC_READ(GL_STATIC_READ),
        STATIC_COPY(GL_STATIC_COPY),
        DYNAMIC_DRAW(GL_DYNAMIC_DRAW),
        DYNAMIC_READ(GL_DYNAMIC_READ),
        DYNAMIC_COPY(GL_DYNAMIC_COPY),
        STREAM_DRAW(GL_STREAM_DRAW),
        STREAM_READ(GL_STREAM_READ),
        STREAM_COPY(GL_STREAM_COPY)
    }
}