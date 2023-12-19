package me.alex_s168.graphics

import org.lwjgl.opengl.GL43.*

class Shader(
    val type: Type,
    vararg source: String
): AutoCloseable {
    internal val id: Int

    init {
        id = RenderSystem.GL.createShader(type.glType)
        RenderSystem.GL.shaderSource(id, *source)
    }

    private var compiled = false

    fun compile() {
        if (compiled) {
            throw IllegalStateException("Shader already compiled!")
        }
        RenderSystem.GL.compileShader(id)
        compiled = true
    }

    private var deleted = false

    override fun close() {
        if (deleted) {
            throw IllegalStateException("Shader already deleted!")
        }
        RenderSystem.GL.deleteShader(id)
        deleted = true
    }

    internal fun assertUsable() {
        if (deleted) {
            throw IllegalStateException("Shader already deleted!")
        }
        if (!compiled) {
            throw IllegalStateException("Shader not compiled!")
        }
    }

    enum class Type(internal val glType: Int) {
        VERT(GL_VERTEX_SHADER),
        TESS_CTRL(GL_TESS_CONTROL_SHADER),
        TESS_EVAL(GL_TESS_EVALUATION_SHADER),
        GEO(GL_GEOMETRY_SHADER),
        FRAG(GL_FRAGMENT_SHADER),
        COMPUTE(GL_COMPUTE_SHADER)
    }
}