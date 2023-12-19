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
        stack.removeLast()
    }

    fun top() =
        stack.last()

    fun translate(a: Vec3f) {
        stack.last().translateSelf(a)
    }

    fun rotate(a: Vec3f, angle: Angle) {
        stack.last().rotateSelf(a, angle)
    }

    fun scale(by: Vec3f) {
        stack.last().scaleSelf(by)
    }

    fun scale(by: Float) {
        stack.last().scaleSelf(Vec3f(by, by, by))
    }
}