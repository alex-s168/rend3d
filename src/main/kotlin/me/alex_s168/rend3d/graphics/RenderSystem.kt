package me.alex_s168.rend3d.graphics

import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays
import java.nio.ByteBuffer

object RenderSystem {
    var initialized = false
        private set

    internal var currentWindow: Long
        get() = GLFW.glfwGetCurrentContext()
        set(value) {
            logRenderCall("MakeContextCurrent", value)
            GLFW.glfwMakeContextCurrent(value)
            logRenderCall("CreateCapabilities")
            org.lwjgl.opengl.GL.createCapabilities()
        }

    fun initialize() {
        if (initialized) {
            throw IllegalStateException("RenderSystem already initialized!")
        }
        if(!GLFW.glfwInit()) {
            throw IllegalStateException("Unable to initialize GLFW!")
        }
        initialized = true
    }

    fun assertInitialized() {
        if (!initialized) {
            throw IllegalStateException("RenderSystem not initialized!")
        }
    }

    fun terminate() {
        if (!initialized) {
            throw IllegalStateException("RenderSystem not initialized!")
        }
        logRenderCall("Terminate")
        GLFW.glfwTerminate()
        GLFW.glfwSetErrorCallback(null)?.free()
        initialized = false
    }

    private var nextShaderAttrib = 0

    fun allocateShaderAttributeID(): Int =
        nextShaderAttrib++

    enum class RenderMode(val glMode: Int) {
        POINTS(GL_POINTS),
        LINES(GL_LINES),
        LINE_LOOP(GL_LINE_LOOP),
        LINE_STRIP(GL_LINE_STRIP),
        TRIANGLES(GL_TRIANGLES),
        TRIANGLE_STRIP(GL_TRIANGLE_STRIP),
        TRIANGLE_FAN(GL_TRIANGLE_FAN),
        QUADS(GL_QUADS),
        QUAD_STRIP(GL_QUAD_STRIP),
        POLYGON(GL_POLYGON)
    }

    var logger: Logger = SimpleLogger()

    var logging = false

    private fun logRenderCall(name: String, vararg args: Any?) {
        if (logging) {
            logger.debug("Render call: $name(${args.joinToString(", ")})")
        }
    }

    var wireframe: Boolean
        get() = throw NotImplementedError()
        set(value) {
            logRenderCall("PolygonMode", if (value) GL_LINE else GL_FILL)
            glPolygonMode(GL_FRONT_AND_BACK, if (value) GL_LINE else GL_FILL)
        }

    object GL {
        fun activateTexture(unit: Int) {
            logRenderCall("ActiveTexture", unit)
            glActiveTexture(GL_TEXTURE0 + unit)
        }

        fun pixelStorei(name: Int, value: Int) {
            logRenderCall("PixelStorei", name, value)
            glPixelStorei(name, value)
        }

        fun texImage2d(
            level: Int,
            internalFormat: Int,
            width: Int,
            height: Int,
            border: Int,
            format: Int,
            type: Int,
            pixels: ByteBuffer
        ) {
            logRenderCall("TexImage2D", GL_TEXTURE_2D, level, internalFormat, width, height, border, format, type, pixels)
            glTexImage2D(GL_TEXTURE_2D, level, internalFormat, width, height, border, format, type, pixels)
        }

        fun texParameteri2d(name: Int, value: Int) {
            logRenderCall("TexParameteri", GL_TEXTURE_2D, name, value)
            glTexParameteri(GL_TEXTURE_2D, name, value)
        }

        fun allocateTextureID(): Int {
            RenderSystem.logRenderCall("GenTextures")
            return glGenTextures()
        }

        fun bindTexture2d(id: Int) {
            RenderSystem.logRenderCall("BindTexture", GL_TEXTURE_2D, id)
            glBindTexture(GL_TEXTURE_2D, id)
        }

        fun unbindTexture2d() {
            RenderSystem.logRenderCall("BindTexture", GL_TEXTURE_2D, 0)
            glBindTexture(GL_TEXTURE_2D, 0)
        }

        fun deleteTexture(id: Int) {
            RenderSystem.logRenderCall("DeleteTextures", id)
            glDeleteTextures(id)
        }

        fun bindBuffer(type: Int, id: Int) {
            logRenderCall("BindBuffer", type, id)
            glBindBuffer(type, id)
        }

        fun deleteBuffers(id: Int) {
            logRenderCall("DeleteBuffers", id)
            glDeleteBuffers(id)
        }

        fun genBuffers(): Int {
            logRenderCall("GenBuffers")
            return glGenBuffers()
        }

        fun vertexAttributePointer(
            bufferId: Int,
            sizePerVertex: Int,
            type: Int,
            normalized: Boolean = false,
            stride: Int,
            pointer: Long
        ) {
            logRenderCall("VertexAttribPointer", bufferId, sizePerVertex, type, normalized, stride, pointer)
            glVertexAttribPointer(bufferId, sizePerVertex, type, normalized, stride, pointer)
        }

        fun enableVertexAttribArray(bufferId: Int) {
            logRenderCall("EnableVertexAttribArray", bufferId)
            glEnableVertexAttribArray(bufferId)
        }

        fun useProgram(id: Int) {
            logRenderCall("UseProgram", id)
            glUseProgram(id)
        }

        fun drawArrays(mode: Int, first: Int, count: Int) {
            logRenderCall("DrawArrays", mode, first, count)
            glDrawArrays(mode, first, count)
        }

        fun getUniformLocation(program: Int, name: String): Int {
            logRenderCall("GetUniformLocation", program, name)
            return glGetUniformLocation(program, name)
        }

        fun createProgram(): Int {
            logRenderCall("CreateProgram")
            return glCreateProgram()
        }

        fun attachShader(program: Int, shader: Int) {
            logRenderCall("AttachShader", program, shader)
            glAttachShader(program, shader)
        }

        fun detachShader(program: Int, shader: Int) {
            logRenderCall("DetachShader", program, shader)
            glDetachShader(program, shader)
        }

        fun linkProgram(program: Int) {
            logRenderCall("LinkProgram", program)
            glLinkProgram(program)
            if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE) {
                val log = glGetProgramInfoLog(program)
                throw IllegalStateException("Program linking failed:\n$log")
            }
        }

        fun deleteProgram(program: Int) {
            logRenderCall("DeleteProgram", program)
            glDeleteProgram(program)
        }

        fun bindAttribLocation(program: Int, index: Int, name: String) {
            logRenderCall("BindAttribLocation", program, index, name)
            glBindAttribLocation(program, index, name)
        }

        fun compileShader(shader: Int) {
            logRenderCall("CompileShader", shader)
            glCompileShader(shader)
            if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
                val log = glGetShaderInfoLog(shader)
                throw IllegalStateException("Shader compilation failed:\n$log")
            }
        }

        fun createShader(type: Int): Int {
            logRenderCall("CreateShader", type)
            return glCreateShader(type)
        }

        fun shaderSource(shader: Int, vararg source: String) {
            logRenderCall("ShaderSource", shader, "<source>")
            glShaderSource(shader, *source)
        }

        fun deleteShader(shader: Int) {
            logRenderCall("DeleteShader", shader)
            glDeleteShader(shader)
        }

        fun clearColor(red: Float, green: Float, blue: Float, alpha: Float) {
            logRenderCall("ClearColor", red, green, blue, alpha)
            glClearColor(red, green, blue, alpha)
        }

        fun genVertexArrays(): Int {
            logRenderCall("GenVertexArrays")
            return glGenVertexArrays()
        }

        fun bindVertexArray(id: Int) {
            logRenderCall("BindVertexArray", id)
            glBindVertexArray(id)
        }

        fun unbindVertexArray() {
            logRenderCall("BindVertexArray", 0)
            glBindVertexArray(0)
        }

        fun logRenderCall(name: String, vararg args: Any?) =
            RenderSystem.logRenderCall(name, *args)
    }
}