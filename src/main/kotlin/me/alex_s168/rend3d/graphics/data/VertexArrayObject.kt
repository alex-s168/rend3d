package me.alex_s168.rend3d.graphics.data

import me.alex_s168.rend3d.graphics.RenderSystem

class VertexArrayObject {
    internal val id: Int = RenderSystem.GL.genVertexArrays()

    fun bind() {
        RenderSystem.GL.bindVertexArray(id)
    }

    fun unbind() {
        RenderSystem.GL.unbindVertexArray()
    }

    fun execute(block: VertexArrayObject.() -> Unit) {
        bind()
        block()
        unbind()
    }
}