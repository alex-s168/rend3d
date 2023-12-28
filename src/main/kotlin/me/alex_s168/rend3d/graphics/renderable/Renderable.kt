package me.alex_s168.rend3d.graphics.renderable

import me.alex_s168.math.mat.impl.Mat4f
import me.alex_s168.math.mat.stack.Mat4fStack

interface Renderable {
    fun render(poseStack: Mat4fStack, projection: Mat4f, partial: Float)

    fun initRender()
}