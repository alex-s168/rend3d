package me.alex_s168

import me.alex_s168.math.Angle
import me.alex_s168.rend3d.graphics.data.GPUBufferObject
import me.alex_s168.rend3d.graphics.data.VertexArrayObject
import me.alex_s168.rend3d.graphics.shader.GPUBufferProgramAttribute
import me.alex_s168.rend3d.graphics.shader.Program
import me.alex_s168.rend3d.graphics.shader.Shader
import me.alex_s168.rend3d.graphics.texture.Texture
import me.alex_s168.math.color.Color
import me.alex_s168.math.mat.impl.Mat4f
import me.alex_s168.math.mat.stack.Mat4fStack
import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.rend3d.graphics.RenderSystem
import me.alex_s168.rend3d.graphics.Window
import me.alex_s168.rend3d.graphics.random
import me.alex_s168.rend3d.input.Key
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import java.io.File

fun main() {
    RenderSystem.logging = true
    RenderSystem.initialize()

    val window = Window("Hello World!", 300, 300, true, false)
    window.vSync = true
    window.visible = true

    val texture = Texture.fromPNG(File("src/main/resources/brick_wall.png").inputStream())

    val vertexShaderSource = """
        #version 330 core

        in  vec3 in_Position;
        in  vec2 in_TexCoord;

        out vec2 out_TexCoord;
        
        uniform mat4 worldMatrix;
        uniform mat4 projectionMatrix;

        void main() {
            gl_Position = projectionMatrix * worldMatrix * vec4(in_Position, 1.0);
            out_TexCoord = in_TexCoord;
        }
    """.trimIndent()

    val fragmentShaderSource = """
        #version 330 core

        precision highp float;

        in  vec2 out_TexCoord;
        out vec4 fragColor;
        
        uniform sampler2D textureSampler;

        void main() {
            fragColor = texture(textureSampler, out_TexCoord); 
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

    val textureSamplerParam = program.parameters["textureSampler"]
    val worldMatrixParam = program.parameters["worldMatrix"]
    val projectionMatrixParam = program.parameters["projectionMatrix"]

    val projection = Mat4f.perspective(Angle.fromDegrees(45f), window.width.toFloat() / window.height, 0.1f, 100f)
    val poseStack = Mat4fStack(mutableListOf(Mat4f.identity()))

    window.keyCallback = { key, _, action, _ ->
        if (key == GLFW_KEY_ESCAPE && action == Key.Action.PRESS) {
            window.close()
        }
        if (key == GLFW_KEY_W && action == Key.Action.REPEAT) {
            poseStack.translate(Vec3f(0f, 0f, -0.1f))
        }
        if (key == GLFW_KEY_S && action == Key.Action.REPEAT) {
            poseStack.translate(Vec3f(0f, 0f, 0.1f))
        }
        if (key == GLFW_KEY_A && action == Key.Action.REPEAT) {
            poseStack.translate(Vec3f(-0.1f, 0f, 0f))
        }
        if (key == GLFW_KEY_D && action == Key.Action.REPEAT) {
            poseStack.translate(Vec3f(0.1f, 0f, 0f))
        }
    }

    window.loop {
        glViewport(0, 0, window.width, window.height)
        glDisable(GL_CULL_FACE)
        backgroundColor = Color.BLACK
        program.execute {
            poseStack.rotate(Vec3f(0f, 1f, 0f), Angle.fromDegrees(0.3f))
            textureSamplerParam.set(0)
            projectionMatrixParam.set(projection)
            worldMatrixParam.set(poseStack.top())
            vao.execute {
                RenderSystem.GL.activateTexture(0)
                texture.execute {
                    drawArrays(RenderSystem.RenderMode.TRIANGLES, 0, 3)
                }
            }
        }
    }

    window.close()
    RenderSystem.terminate()
}