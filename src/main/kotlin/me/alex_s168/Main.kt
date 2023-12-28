package me.alex_s168

import me.alex_s168.math.Anglef
/*
import me.alex_s168.rend3d.graphics.data.GPUBufferObject
import me.alex_s168.rend3d.graphics.data.VertexArrayObject
import me.alex_s168.rend3d.graphics.shader.GPUBufferProgramAttribute
import me.alex_s168.rend3d.graphics.shader.Program
import me.alex_s168.rend3d.graphics.shader.Shader
 import me.alex_s168.math.mat.impl.Mat3f
 import me.alex_s168.math.vec.impl.Quaternionf
 */
import me.alex_s168.rend3d.graphics.texture.Texture
import me.alex_s168.math.color.Color

import me.alex_s168.math.mat.impl.Mat4f
import me.alex_s168.math.mat.stack.Mat4fStack
import me.alex_s168.math.vec.impl.Quaternionf

import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.meshlib.Mesh
import me.alex_s168.meshlib.Triangle
import me.alex_s168.meshlib.format.OBJModelFormat
import me.alex_s168.meshlib.texture.TextureCoordinate
import me.alex_s168.meshlib.texture.TextureFace
import me.alex_s168.rend3d.graphics.RenderSystem
import me.alex_s168.rend3d.graphics.Window
import me.alex_s168.rend3d.graphics.renderable.MeshRender
import me.alex_s168.rend3d.input.Key
import me.alex_s168.rend3d.obj.CombinedObject3
import org.lwjgl.glfw.GLFW.*
import java.io.File

fun main() {
    // RenderSystem.logging = true
    // RenderSystem.autoResizeTextures = true
    RenderSystem.initialize()

    val window = Window("Hello World!", 300, 300, true, false, Window.Parameters.SAMPLES(4))
    window.vSync = true
    window.visible = true

    val texture = Texture.fromPNG(File("src/main/resources/x_90_talon_dark_gray.png").inputStream())

    val model = OBJModelFormat().loadFrom(File("src/main/resources/x_90_talon.obj").readText())
    val renderers = model.groups.map { group ->
        MeshRender(group.mesh, texture)
    }
    val modelObject = CombinedObject3(renderers)
    /*val mesh = Mesh(
        mutableListOf(Triangle(
            Vec3f(),
            Vec3f(-0.5f, -0.5f, 0.0f),
            Vec3f(0.5f, -0.5f, 0.0f),
            Vec3f(0.0f,  0.5f, 0.0f)
        )),
        mutableListOf(
            TextureFace(
                TextureCoordinate(0.0f, 0.0f, 0.0f),
                TextureCoordinate(1.0f, 0.0f, 0.0f),
                TextureCoordinate(0.5f, 1.0f, 0.0f)
            )
        )
    )
    val modelObject = MeshRender(mesh, texture)*/

    val projection = Mat4f.identity() * Mat4f.perspective(Anglef.fromDegrees(70f), window.aspect, 1f, 1000f)
    val poseStack = Mat4fStack(mutableListOf(Mat4f.identity()))

    RenderSystem.enableMultisample()

    window.keyCallback = { key, _, action, _ ->
        if (key == GLFW_KEY_ESCAPE && action == Key.Action.PRESS) {
            window.close()
        }
        if (key == GLFW_KEY_W && action == Key.Action.REPEAT) {
            modelObject.position = modelObject.position + Vec3f(0f, 0f, -0.2f)
        }
        if (key == GLFW_KEY_S && action == Key.Action.REPEAT) {
            modelObject.position = modelObject.position + Vec3f(0f, 0f, 0.2f)
        }
        if (key == GLFW_KEY_A && action == Key.Action.REPEAT) {
            modelObject.position = modelObject.position + Vec3f(-0.2f, 0f, 0f)
        }
        if (key == GLFW_KEY_D && action == Key.Action.REPEAT) {
            modelObject.position = modelObject.position + Vec3f(0.2f, 0f, 0f)
        }
    }

    modelObject.initRender()

    modelObject.scale = Vec3f(0.001f, 0.001f, 0.001f)

    window.loop {
        backgroundColor = Color.BLACK
        println(modelObject.position)
        modelObject.tick()
        poseStack.push()
        modelObject.render(poseStack, projection, 0f)
        poseStack.pop()
    }

/*
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

    val projection = Mat4f.identity() * Mat4f.perspective(Anglef.fromDegrees(45f), window.aspect, 1f, 1000f)
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

    RenderSystem.enableDepthTest(RenderSystem.DepthFunc.LESS)

    RenderSystem.enableBlend()
    RenderSystem.blendFunc(RenderSystem.BlendFuncParam.SRC_ALPHA, RenderSystem.BlendFuncParam.ONE_MINUS_SRC_ALPHA)

    RenderSystem.enableMultisample()

    var z = -2.7f
    var yaw = 0f
    window.loop {
        poseStack.push()
        poseStack.translate(Vec3f(0f, 0f, z))
        poseStack.rotate(Vec3f(0f, 1f, 0f), Anglef.fromDegrees(yaw).normalized)
        //z -= 0.01f
        yaw += 0.5f
        glViewport(0, 0, window.width, window.height)
        backgroundColor = Color.BLACK
        program.execute {
            //poseStack.rotate(Vec3f(0f, 1f, 0f), Anglef.fromDegrees(0.5f))
            RenderSystem.allocateTextureSampler { textureSampler ->
                textureSamplerParam.set(textureSampler)
                worldMatrixParam.set(poseStack.top())
                projectionMatrixParam.set(projection)
                vao.execute {
                    textureSampler.bind()
                    texture.execute {
                        drawArrays(RenderSystem.RenderMode.TRIANGLES, 0, 3)
                    }
                }
            }
        }
        poseStack.pop()
    }
    */

    window.close()
    RenderSystem.terminate()
}