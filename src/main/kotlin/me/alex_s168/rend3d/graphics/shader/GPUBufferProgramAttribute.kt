package me.alex_s168.rend3d.graphics.shader

import me.alex_s168.rend3d.graphics.data.GPUBufferObject
import me.alex_s168.rend3d.graphics.RenderSystem
import me.alex_s168.rend3d.graphics.data.UploadTarget
import me.alex_s168.rend3d.graphics.data.UploadTargetContext
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL41.GL_FIXED

class GPUBufferProgramAttribute(
    val buffer: GPUBufferObject
): ProgramAttribute(), UploadTarget {

    override fun execute(block: UploadTargetContext.() -> Unit) =
        buffer.execute(block)

    var configured = false
        private set

    fun bind() =
        buffer.bind()

    fun configure(
        sizePerVertex: Int,
        type: Type,
        normalized: Boolean = false,
        stride: Int = 0,
        pointer: Long = 0
    ) {
        if (configured) {
            throw IllegalStateException("Attribute already configured!")
        }
        bind()
        RenderSystem.GL.vertexAttributePointer(id, sizePerVertex, type.glId, normalized, stride, pointer)
        RenderSystem.GL.enableVertexAttribArray(id)
        configured = true
    }

    enum class Type(internal val glId: Int) {
        BYTE(GL_BYTE),
        UNSIGNED_BYTE(GL_UNSIGNED_BYTE),
        SHORT(GL_SHORT),
        UNSIGNED_SHORT(GL_UNSIGNED_SHORT),
        INT(GL_INT),
        UNSIGNED_INT(GL_UNSIGNED_INT),
        FLOAT(GL_FLOAT),
        DOUBLE(GL_DOUBLE),
        FIXED(GL_FIXED),
    }

    override fun canBeUsed(): Boolean =
        configured

}