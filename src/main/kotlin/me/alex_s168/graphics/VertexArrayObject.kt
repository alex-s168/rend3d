package me.alex_s168.graphics

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
    }
}