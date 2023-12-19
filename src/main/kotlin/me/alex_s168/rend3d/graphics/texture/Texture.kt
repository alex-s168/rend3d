package me.alex_s168.rend3d.graphics.texture

import de.matthiasmann.twl.utils.PNGDecoder
import me.alex_s168.rend3d.graphics.RenderSystem
import org.lwjgl.opengl.GL11.*
import java.io.InputStream
import java.nio.ByteBuffer

class Texture(
    val width: Int,
    val height: Int,
    internal val id: Int
): AutoCloseable {

    override fun close() {
        RenderSystem.GL.deleteTexture(id)
    }

    fun bind2d() {
        RenderSystem.GL.bindTexture2d(id)
    }

    fun unbind2d() {
        RenderSystem.GL.unbindTexture2d()
    }

    fun execute(block: () -> Unit) {
        bind2d()
        block()
        unbind2d()
    }

    enum class Filter(internal val id: Int) {
        NEAREST(GL_NEAREST),
        LINEAR(GL_LINEAR)
    }

    companion object {
        fun fromPNG(
            stream: InputStream,
            minFilter: Filter = Filter.NEAREST,
            magFilter: Filter = Filter.NEAREST
        ): Texture {
            val decoder = PNGDecoder(stream)
            if (decoder.width % 2 != 0 || decoder.height % 2 != 0) {
                RenderSystem.logger.warn("Texture dimensions are not a power of 2. This may cause issues.")
            }
            val buf: ByteBuffer = ByteBuffer.allocateDirect(
                4 * decoder.width * decoder.height
            )
            decoder.decode(buf, decoder.width * 4, PNGDecoder.Format.RGBA)
            buf.flip()
            val texture = Texture(decoder.width, decoder.height, RenderSystem.GL.allocateTextureID())
            texture.bind2d()
            RenderSystem.GL.pixelStorei(GL_UNPACK_ALIGNMENT, 1)
            RenderSystem.GL.texParameteri2d(GL_TEXTURE_MIN_FILTER, minFilter.id)
            RenderSystem.GL.texParameteri2d(GL_TEXTURE_MAG_FILTER, magFilter.id)
            RenderSystem.GL.texImage2d(0, GL_RGBA, decoder.width,
                decoder.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buf)
            texture.unbind2d()
            return texture
        }
    }
}