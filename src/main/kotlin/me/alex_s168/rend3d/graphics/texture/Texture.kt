package me.alex_s168.rend3d.graphics.texture

import de.matthiasmann.twl.utils.PNGDecoder
import me.alex_s168.rend3d.graphics.RenderSystem
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE
import org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER
import org.lwjgl.opengl.GL14.GL_MIRRORED_REPEAT
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

    enum class Wrap(internal val id: Int) {
        REPEAT(GL_REPEAT),
        MIRRORED_REPEAT(GL_MIRRORED_REPEAT),
        CLAMP_TO_EDGE(GL_CLAMP_TO_EDGE),
        CLAMP_TO_BORDER(GL_CLAMP_TO_BORDER),
        CLAMP(GL_CLAMP)
    }

    companion object {
        fun fromPNG(
            stream: InputStream,
            minFilter: Filter = Filter.NEAREST,
            magFilter: Filter = Filter.NEAREST,
            wrapS: Wrap = Wrap.REPEAT,
            wrapT: Wrap = Wrap.REPEAT
        ): Texture {
            val decoder = PNGDecoder(stream)
            var width = decoder.width
            var height = decoder.height
            if ((width % 2 != 0 || height % 2 != 0) && !RenderSystem.autoResizeTextures) {
                RenderSystem.logger.warn("Texture dimensions are not a power of 2. This may cause issues with some graphics cards. Consider setting \"RenderSystem.autoResizeTextures\" to true.")
            }
            val buf: ByteBuffer = ByteBuffer.allocateDirect(
                4 * width * height
            )
            if (RenderSystem.autoResizeTextures) {
                width -= width % 2
                height -= height % 2
            }
            decoder.decode(buf, width * 4, PNGDecoder.Format.RGBA)
            buf.flip()
            val texture = Texture(width, height, RenderSystem.GL.allocateTextureID())
            texture.bind2d()
            RenderSystem.GL.pixelStorei(GL_UNPACK_ALIGNMENT, 1)
            RenderSystem.GL.texParameteri2d(GL_TEXTURE_WRAP_S, wrapS.id)
            RenderSystem.GL.texParameteri2d(GL_TEXTURE_WRAP_T, wrapT.id)
            RenderSystem.GL.texParameteri2d(GL_TEXTURE_MIN_FILTER, minFilter.id)
            RenderSystem.GL.texParameteri2d(GL_TEXTURE_MAG_FILTER, magFilter.id)
            RenderSystem.GL.generateMipmap2d()
            RenderSystem.GL.texImage2d(0, GL_RGBA, width, height,
                0, GL_RGBA, GL_UNSIGNED_BYTE, buf)
            texture.unbind2d()
            return texture
        }
    }
}