package me.alex_s168.graphics

import me.alex_s168.math.color.Color
import me.alex_s168.math.vec.impl.*
import org.lwjgl.opengl.GL20.*

class Program(
    vararg shaders: Shader
): AutoCloseable {
    internal val id: Int = RenderSystem.GL.createProgram()

    init {
        shaders.forEach { attachShader(it) }
    }

    fun attachShader(shader: Shader) {
        shader.assertUsable()
        RenderSystem.GL.attachShader(id, shader.id)
    }

    operator fun plusAssign(shader: Shader) {
        attachShader(shader)
    }

    fun detachShader(shader: Shader) {
        shader.assertUsable()
        RenderSystem.GL.detachShader(id, shader.id)
    }

    operator fun minusAssign(shader: Shader) {
        detachShader(shader)
    }

    private var linked = false

    fun link() {
        if (linked) {
            throw IllegalStateException("Program already linked!")
        }
        if (closed) {
            throw IllegalStateException("Program already closed!")
        }
        RenderSystem.GL.linkProgram(id)
        linked = true
    }

    private var closed = false

    override fun close() {
        if (closed) {
            throw IllegalStateException("Program already closed!")
        }
        RenderSystem.GL.deleteProgram(id)
        closed = true
    }

    class Parameters(
        private val program: Program
    ) {
        operator fun get(name: String): UniformParameter {
            if (!program.linked) {
                throw IllegalStateException("Program not linked!")
            }
            if (program.closed) {
                throw IllegalStateException("Program already closed!")
            }
            if (program.isUsed) {
                throw IllegalStateException("Program already in use!")
            }
            val location = RenderSystem.GL.getUniformLocation(program.id, name)
            if (location == -1) {
                throw IllegalStateException("Parameter $name not found!")
            }
            return UniformParameter(location, program)
        }
    }

    val parameters = Parameters(this)

    class Attributes(
        private val program: Program
    ) {
        operator fun set(name: String, value: ProgramAttribute) {
            if (!value.canBeUsed()) {
                throw IllegalStateException("Attribute not configured!")
            }
            if (program.closed) {
                throw IllegalStateException("Program already closed!")
            }
            if (program.linked) {
                throw IllegalStateException("Program already linked!")
            }
            if (program.isUsed) {
                throw IllegalStateException("Program already in use!")
            }
            RenderSystem.GL.bindAttribLocation(program.id, value.id, name)
        }
    }

    val attributes = Attributes(this)

    internal var isUsed = false

    fun execute(block: ProgramUseContext.() -> Unit) {
        if (!linked) {
            throw IllegalStateException("Program not linked!")
        }
        if (closed) {
            throw IllegalStateException("Program already closed!")
        }
        RenderSystem.GL.useProgram(id)
        isUsed = true
        block(ProgramUseContext())
        RenderSystem.GL.useProgram(0)
        isUsed = false
    }

    class ProgramUseContext internal constructor() {
        fun drawArrays(
            mode: RenderSystem.RenderMode,
            first: Int,
            count: Int
        ) {
            RenderSystem.GL.drawArrays(mode.glMode, first, count)
        }
    }

    class UniformParameter internal constructor(
        private val location: Int,
        private val program: Program
    ) {
        private fun assertUsable() {
            if (!program.isUsed) {
                throw IllegalStateException("Program not active!")
            }
        }

        fun set(value: Float) {
            assertUsable()
            glUniform1f(location, value)
        }

        fun set(value: Int) {
            assertUsable()
            glUniform1i(location, value)
        }

        fun set(value: Boolean) {
            assertUsable()
            glUniform1i(location, value.toGLFW())
        }

        fun set(value: Vec2f) {
            assertUsable()
            glUniform2fv(location, value.asArray())
        }

        fun set(value: Vec3f) {
            assertUsable()
            glUniform3fv(location, value.asArray())
        }

        fun set(value: Vec4f) {
            assertUsable()
            glUniform4fv(location, value.asArray())
        }

        fun set(value: Vec2i) {
            assertUsable()
            glUniform2i(location, value.x, value.y)
        }

        fun set(value: Vec3i) {
            assertUsable()
            glUniform3i(location, value.x, value.y, value.z)
        }

        fun set(value: Vec4i) {
            assertUsable()
            glUniform4i(location, value.x, value.y, value.z, value.w)
        }

        fun set(value: Color) {
            assertUsable()
            glUniform4fv(location, value.asArray())
        }

        // TODO: matrices
    }

}