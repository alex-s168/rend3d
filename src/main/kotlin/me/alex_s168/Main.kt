package me.alex_s168

import me.alex_s168.math.Anglef
import me.alex_s168.math.color.Color
import me.alex_s168.math.vec.impl.Vec3af
import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.meshlib.format.Format
import me.alex_s168.rend3d.graphics.RenderSystem
import me.alex_s168.rend3d.graphics.Window
import me.alex_s168.rend3d.graphics.renderable.ModelRender
import me.alex_s168.rend3d.graphics.texture.Texture
import me.alex_s168.rend3d.input.Key
import org.joml.Matrix4f
import org.joml.Matrix4fStack
import org.lwjgl.glfw.GLFW.*
import java.io.File

fun main() {
    // RenderSystem.logging = true
    // RenderSystem.autoResizeTextures = true
    RenderSystem.initialize()

    val window = Window("Hello World!", 300, 300, true, false, Window.Parameters.SAMPLES(4))
    window.vSync = true
    window.visible = true

    val texture = Texture.fromPNG(File("src/main/resources/FA_23.png").inputStream())

    val model = Format.OBJ.loadFrom(File("src/main/resources/FA_23.obj").readText())
    val loaded = model.load { File("src/main/resources/$it").readText() }
    loaded.verify()
    val modelObject = ModelRender(loaded, texture, true)

    val projection = Matrix4f().perspective(Anglef.fromDegrees(70f).radians, window.aspect, 0.0010f, 1000f)
    val poseStack = Matrix4fStack(32)

    RenderSystem.enableMultisample()

    var deg = 0f
    window.keyCallback = { key, _, action, _ ->
        if (key == GLFW_KEY_ESCAPE && action == Key.Action.PRESS) {
            window.close()
        }
        if (action == Key.Action.REPEAT || action == Key.Action.PRESS) {
            when (key) {
                GLFW_KEY_W -> {
                    modelObject.position = modelObject.position + Vec3f(0f, 0f, -0.2f)
                }
                GLFW_KEY_S -> {
                    modelObject.position = modelObject.position + Vec3f(0f, 0f, 0.2f)
                }
                GLFW_KEY_A -> {
                    modelObject.position = modelObject.position + Vec3f(-0.2f, 0f, 0f)
                }
                GLFW_KEY_D -> {
                    modelObject.position = modelObject.position + Vec3f(0.2f, 0f, 0f)
                }
                GLFW_KEY_UP -> {
                    modelObject.position = modelObject.position + Vec3f(0.0f, 0.2f, 0f)
                }
                GLFW_KEY_DOWN -> {
                    modelObject.position = modelObject.position + Vec3f(0.0f, -0.2f, 0f)
                }
                GLFW_KEY_RIGHT -> {
                    deg ++
                }
                GLFW_KEY_LEFT -> {
                    deg --
                }
            }
        }
    }

    modelObject.initRender()
    modelObject.scale = Vec3f(0.1f, 0.1f, 0.1f)
    window.loop {
        backgroundColor = Color.BLACK
        modelObject.rotation = Vec3af(Anglef.fromDegrees(0f), Anglef.fromDegrees(deg), Anglef.fromDegrees(0f))
        modelObject.tick()
        modelObject.render(poseStack, projection, 0f)
    }

    window.close()
    RenderSystem.terminate()
}