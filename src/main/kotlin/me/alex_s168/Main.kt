package me.alex_s168

import me.alex_s168.graphics.*
import me.alex_s168.math.color.Color
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear

fun main() {
    RenderSystem.logging = true
    RenderSystem.initialize()

    val window = Window("Hello World!", 300, 300, true, false)
    window.keyCallback = { key, _, action, _ ->
        if (key == GLFW_KEY_ESCAPE && action == Key.Action.PRESS) {
            window.close()
        }
    }
    window.vSync = true
    window.visible = true

    val vertexShaderSource = """
        #version 330 core

        in  vec3 in_Position;

        void main() {
            gl_Position = vec4(in_Position.x, in_Position.y, in_Position.z, 1.0);
        }
    """.trimIndent()

    val fragmentShaderSource = """
        #version 330 core

        precision highp float;

        out vec4 fragColor;

        void main() {
            fragColor = vec4(1.0,1.0,1.0,1.0);      
        }
    """.trimIndent()

    val vertexShader = Shader(Shader.Type.VERT, vertexShaderSource)
    vertexShader.compile()
    val fragmentShader = Shader(Shader.Type.FRAG, fragmentShaderSource)
    fragmentShader.compile()

    val data = floatArrayOf(
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        0.0f,  0.5f, 0.0f
    )

    val vao = VertexArrayObject()
    vao.bind()
    val buffer = GPUBufferObject(GPUBufferObject.Type.ARRAY)
    buffer.execute {
        upload(data)
    }
    val attribute = GPUBufferProgramAttribute(buffer)
    attribute.configure(3, GPUBufferProgramAttribute.Type.FLOAT)
    attribute.bind()

    val program = Program(vertexShader, fragmentShader)
    program.attributes["in_Position"] = attribute
    program.link()

    window.loop {
        backgroundColor = Color.BLACK
        glClear(GL_COLOR_BUFFER_BIT)

        program.execute {
            vao.bind()
            drawArrays(RenderSystem.RenderMode.TRIANGLES, 0, 3)
        }
    }

    window.close()
    RenderSystem.terminate()
}