package me.alex_s168.rend3d.graphics

import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.*
import org.lwjgl.opengl.GL30.*
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

    /**
     * If true, textures will be automatically resized to the nearest power of 2.
     */
    var autoResizeTextures = false

    enum class DepthFunc(val glFunc: Int) {
        NEVER(GL_NEVER),
        LESS(GL_LESS),
        EQUAL(GL_EQUAL),
        LEQUAL(GL_LEQUAL),
        GREATER(GL_GREATER),
        NOTEQUAL(GL_NOTEQUAL),
        GEQUAL(GL_GEQUAL),
        ALWAYS(GL_ALWAYS)
    }

    enum class BlendFuncParam(val glFunc: Int) {
        ZERO(GL_ZERO),
        ONE(GL_ONE),
        SRC_COLOR(GL_SRC_COLOR),
        ONE_MINUS_SRC_COLOR(GL_ONE_MINUS_SRC_COLOR),
        DST_COLOR(GL_DST_COLOR),
        ONE_MINUS_DST_COLOR(GL_ONE_MINUS_DST_COLOR),
        SRC_ALPHA(GL_SRC_ALPHA),
        ONE_MINUS_SRC_ALPHA(GL_ONE_MINUS_SRC_ALPHA),
        DST_ALPHA(GL_DST_ALPHA),
        ONE_MINUS_DST_ALPHA(GL_ONE_MINUS_DST_ALPHA),
        CONSTANT_COLOR(GL_CONSTANT_COLOR),
        ONE_MINUS_CONSTANT_COLOR(GL_ONE_MINUS_CONSTANT_COLOR),
        CONSTANT_ALPHA(GL_CONSTANT_ALPHA),
        ONE_MINUS_CONSTANT_ALPHA(GL_ONE_MINUS_CONSTANT_ALPHA),
        SRC_ALPHA_SATURATE(GL_SRC_ALPHA_SATURATE)
    }

    fun enableDepthTest(func: DepthFunc) {
        logRenderCall("Enable", GL_DEPTH_TEST)
        glEnable(GL_DEPTH_TEST)
        logRenderCall("DepthFunc", func.glFunc)
        glDepthFunc(func.glFunc)
    }

    fun disableDepthTest() {
        logRenderCall("Disable", GL_DEPTH_TEST)
        glDisable(GL_DEPTH_TEST)
    }

    fun enableBlend() {
        logRenderCall("Enable", GL_BLEND)
        glEnable(GL_BLEND)
    }

    fun disableBlend() {
        logRenderCall("Disable", GL_BLEND)
        glDisable(GL_BLEND)
    }

    fun blendFunc(funcSource: BlendFuncParam, funcDest: BlendFuncParam) {
        logRenderCall("BlendFunc", funcSource.glFunc, funcDest.glFunc)
        glBlendFunc(funcSource.glFunc, funcDest.glFunc)
    }

    fun enableCullFace(front: Boolean, back: Boolean) {
        logRenderCall("Enable", GL_CULL_FACE)
        glEnable(GL_CULL_FACE)
        val x = (if (front) GL_FRONT else 0) or (if (back) GL_BACK else 0)
        logRenderCall("CullFace", x)
        glCullFace(x)
    }

    fun disableCullFace() {
        logRenderCall("Disable", GL_CULL_FACE)
        glDisable(GL_CULL_FACE)
    }

    fun enableMultisample() {
        logRenderCall("Enable", GL_MULTISAMPLE)
        glEnable(GL_MULTISAMPLE)
    }

    fun disableMultisample() {
        logRenderCall("Disable", GL_MULTISAMPLE)
        glDisable(GL_MULTISAMPLE)
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

    private val textureSamplers by lazy {
        BooleanArray(GL.getInteger(GL_MAX_TEXTURE_UNITS)) { false }
    }

    data class TextureSampler internal constructor(val id: Int) {
        fun bind() {
            GL.logRenderCall("ActiveTexture", id)
            glActiveTexture(GL_TEXTURE0 + id)
        }
    }

    fun allocateTextureSampler(block: (TextureSampler) -> Unit) {
        val id = textureSamplers.indexOfFirst { !it }
        if (id == -1) {
            throw IllegalStateException("No free texture samplers!")
        }
        textureSamplers[id] = true
        block(TextureSampler(id))
        textureSamplers[id] = false
    }

    object GL {
        fun getInteger(name: Int): Int {
            logRenderCall("GetInteger", name)
            return glGetInteger(name)
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

        fun generateMipmap2d() {
            logRenderCall("GenerateMipmap", GL_TEXTURE_2D)
            glGenerateMipmap(GL_TEXTURE_2D)
        }

        fun logRenderCall(name: String, vararg args: Any?) =
            RenderSystem.logRenderCall(name, *args)
    }
}