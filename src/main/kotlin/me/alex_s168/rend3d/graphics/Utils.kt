package me.alex_s168.rend3d.graphics

import org.lwjgl.BufferUtils
import org.lwjgl.glfw.GLFW.GLFW_FALSE
import org.lwjgl.glfw.GLFW.GLFW_TRUE
import java.nio.FloatBuffer
import java.nio.IntBuffer

fun Boolean.toGLFW() =
    if (this) GLFW_TRUE else GLFW_FALSE

fun FloatArray.toFloatBuffer(): FloatBuffer =
    BufferUtils.createFloatBuffer(this.size).also {
        it.put(this)
        it.flip()
    }

fun IntArray.toIntBuffer(): IntBuffer =
    BufferUtils.createIntBuffer(this.size).also {
        it.put(this)
        it.flip()
    }

fun ClosedFloatingPointRange<Float>.random() =
    (Math.random() * (endInclusive - start) + start).toFloat()