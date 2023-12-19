package me.alex_s168

import me.alex_s168.rend3d.graphics.data.GPUBufferObject
import me.alex_s168.rend3d.graphics.data.VertexArrayObject
import me.alex_s168.rend3d.graphics.shader.GPUBufferProgramAttribute
import me.alex_s168.rend3d.graphics.shader.Program
import me.alex_s168.rend3d.graphics.shader.Shader
import me.alex_s168.rend3d.graphics.texture.Texture
import me.alex_s168.math.color.Color
import me.alex_s168.rend3d.graphics.RenderSystem
import me.alex_s168.rend3d.graphics.Window
import me.alex_s168.rend3d.input.Key
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear
import java.io.File

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

    val texture = Texture.fromPNG(File("src/main/resources/brick_wall.png").inputStream())

    val vertexShaderSource = """
        #version 330 core

        in  vec3 in_Position;
        in  vec2 in_TexCoord;

        out vec2 out_TexCoord;

        void main() {
            gl_Position = vec4(in_Position.x, in_Position.y, in_Position.z, 1.0);
            out_TexCoord = in_TexCoord;
        }
    """.trimIndent()

    val fragmentShaderSource = """
        #version 330 core

        precision highp float;

        in  vec2 out_TexCoord;
        out vec4 fragColor;
        
        uniform sampler2D texture_sampler;

        void main() {
            fragColor = texture(texture_sampler, out_TexCoord); 
        }
    """.trimIndent()

    val vertexShader = Shader(Shader.Type.VERT, vertexShaderSource)
    vertexShader.compile()
    val fragmentShader = Shader(Shader.Type.FRAG, fragmentShaderSource)
    fragmentShader.compile()

    val vao = VertexArrayObject()
    vao.bind()

    val vert = floatArrayOf(
        -0.5f, -0.5f, 0.0f,
        0.5f, -0.5f, 0.0f,
        0.0f,  0.5f, 0.0f
    )
    val bufferVert = GPUBufferObject(GPUBufferObject.Type.ARRAY)
    bufferVert.execute {
        upload(vert)
    }
    val attributeVert = GPUBufferProgramAttribute(bufferVert)
    attributeVert.configure(3, GPUBufferProgramAttribute.Type.FLOAT)
    attributeVert.bind()

    val tex = floatArrayOf(
        0.0f, 0.0f,
        1.0f, 0.0f,
        0.5f, 1.0f
    )
    val bufferTex = GPUBufferObject(GPUBufferObject.Type.ARRAY)
    bufferTex.execute {
        upload(tex)
    }
    val attributeTex = GPUBufferProgramAttribute(bufferTex)
    attributeTex.configure(2, GPUBufferProgramAttribute.Type.FLOAT)
    attributeTex.bind()

    val program = Program(vertexShader, fragmentShader)
    program.attributes["in_Position"] = attributeVert
    program.attributes["in_TexCoord"] = attributeTex
    program.link()

    window.loop {
        backgroundColor = Color.BLACK
        glClear(GL_COLOR_BUFFER_BIT)

        program.execute {
            program.parameters["texture_sampler"].set(0)
            vao.bind()
            RenderSystem.GL.activateTexture(0)
            texture.bind2d()
            drawArrays(RenderSystem.RenderMode.TRIANGLES, 0, 3)
            texture.bind2d()
        }
    }

    window.close()
    RenderSystem.terminate()
}