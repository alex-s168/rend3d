package me.alex_s168.math.mat.stack

import me.alex_s168.math.Angle
import me.alex_s168.math.mat.impl.Mat4f
import me.alex_s168.math.vec.impl.Vec3f

class Mat4fStack(
    val stack: MutableList<Mat4f> = mutableListOf(Mat4f())
) {
    fun push() {
        stack += stack.last().copy()
    }

    fun pop() {
        stack.removeAt(stack.size - 1)
    }

    fun top() =
        stack.last()

    fun translate(a: Vec3f) {
        stack.last().translate(a)
    }

    fun rotate(a: Vec3f, angle: Angle) {
        stack.last().rotate(a, angle)
    }

    fun scale(by: Vec3f) {
        stack.last().scale(by)
    }

    fun scale(by: Float) {
        stack.last().scale(Vec3f(by, by, by))
    }
}