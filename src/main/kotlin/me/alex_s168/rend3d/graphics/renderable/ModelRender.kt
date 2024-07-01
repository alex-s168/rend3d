package me.alex_s168.rend3d.graphics.renderable

import me.alex_s168.meshlib.LoadedModel
import me.alex_s168.rend3d.graphics.texture.Texture
import me.alex_s168.rend3d.obj.Object3
import org.joml.Matrix4f
import org.joml.Matrix4fStack

class ModelRender(
    val model: LoadedModel,
    val texture: Texture,
    val invertTexture: Boolean = false
): Object3(), Renderable {
    private val meshRenders = model.groups.values.map { MeshRender(it.mesh, texture, invertTexture) }

    override fun render(poseStack: Matrix4fStack, projection: Matrix4f, partial: Float) {
        poseStack.pushMatrix()

        poseStack.translate(position.x, position.y, position.z)
        poseStack.scale(scale.x, scale.y, scale.z)
        poseStack.rotateXYZ(rotation.roll.radians, rotation.pitch.radians, rotation.yaw.radians)

        meshRenders.forEach {
            // TODO: frustum culling
            it.render(poseStack, projection, partial)
        }

        poseStack.popMatrix()
    }

    override fun initRender() {
        meshRenders.forEach {
            it.initRender()
        }
    }
}