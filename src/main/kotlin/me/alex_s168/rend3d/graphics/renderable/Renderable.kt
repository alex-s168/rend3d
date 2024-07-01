package me.alex_s168.rend3d.graphics.renderable

import org.joml.Matrix4f
import org.joml.Matrix4fStack

interface Renderable {
    fun render(poseStack: Matrix4fStack, projection: Matrix4f, partial: Float)

    fun initRender()
}