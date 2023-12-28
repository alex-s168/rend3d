package me.alex_s168.rend3d.graphics.renderable

import me.alex_s168.math.mat.impl.Mat4f
import me.alex_s168.math.mat.stack.Mat4fStack
import me.alex_s168.meshlib.ModelRaw
import me.alex_s168.rend3d.graphics.texture.Texture
import me.alex_s168.rend3d.obj.Object3

class ModelRender(
    models: Iterable<ModelRaw>,
    val texture: Texture,
    val invertTexture: Boolean = false
): Object3(), Renderable {
    private val meshRenders = models.map { MeshRender(it.mesh, texture, invertTexture) }

    override fun render(poseStack: Mat4fStack, projection: Mat4f, partial: Float) {
        poseStack.push()

        poseStack.translate(position)
        poseStack.rotate(rotation)
        poseStack.scale(scale)

        meshRenders.forEach {
            // TODO: frustum culling
            it.render(poseStack, projection, partial)
        }

        poseStack.pop()
    }

    override fun initRender() {
        meshRenders.forEach {
            it.initRender()
        }
    }
}