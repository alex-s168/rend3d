package me.alex_s168.rend3d.obj

import me.alex_s168.math.mat.impl.Mat4f
import me.alex_s168.math.mat.stack.Mat4fStack
import me.alex_s168.math.vec.impl.Quaternionf
import me.alex_s168.math.vec.impl.Vec3f
import me.alex_s168.rend3d.graphics.renderable.Renderable

class CombinedObject3(
    val objects: List<Object3>
): Object3(), Renderable {
    override var position = Vec3f()
        set(value) {
            field = value
            val off = value - lastPosition
            objects.forEach { it.position = it.position + off }
        }

    override var rotation = Quaternionf()
        set(value) {
            field = value
            val off = value - lastRotation
            objects.forEach { it.rotation = it.rotation * off }
        }
    override var scale = Vec3f(1f, 1f, 1f)
        set(value) {
            field = value
            val off = value - lastScale
            objects.forEach { it.scale = it.scale * off }
        }

    private var lastPosition = position
    private var lastRotation = rotation
    private var lastScale = scale

    override fun tick() {
        objects.forEach { it.tick() }
    }

    override fun render(poseStack: Mat4fStack, projection: Mat4f, partial: Float) {
        objects.forEach {
            if (it is Renderable) {
                it.render(poseStack, projection, partial)
            }
        }
    }

    override fun initRender() {
        objects.forEach {
            if (it is Renderable) {
                it.initRender()
            }
        }
    }
}