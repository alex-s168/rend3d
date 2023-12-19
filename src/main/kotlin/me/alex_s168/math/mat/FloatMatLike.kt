package me.alex_s168.math.mat

import me.alex_s168.math.vec.NumVecLike
import java.nio.FloatBuffer

interface FloatMatLike<S: FloatMatLike<S>>: NumMatLike<Float, S> {
    /** clones */
    fun toArray(): FloatArray
    /** does not clone */
    fun asArray(): FloatArray
    fun writeTo(buffer: FloatBuffer)
    fun writeTo(arr: FloatArray, offset: Int = 0)
    fun from(other: MatLike<Float, S>)
    fun from(other: FloatArray)
    fun from(other: FloatBuffer)
    fun new(): S

    override fun plus(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] + other[i].toFloat()
        }
        return result
    }

    override fun minus(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] - other[i].toFloat()
        }
        return result
    }

    override fun times(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] * other[i].toFloat()
        }
        return result
    }

    override fun div(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] / other[i].toFloat()
        }
        return result
    }

    override fun rem(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] % other[i].toFloat()
        }
        return result
    }

    override fun unaryMinus(): S {
        val result = new()
        for (i in 0 until size) {
            result[i] = -this[i]
        }
        return result
    }

    override fun plusAssign(other: NumMatLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] += other[i].toFloat()
        }
    }

    override fun minusAssign(other: NumMatLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] -= other[i].toFloat()
        }
    }

    override fun timesAssign(other: NumMatLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] *= other[i].toFloat()
        }
    }

    override fun divAssign(other: NumMatLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] /= other[i].toFloat()
        }
    }

    override fun remAssign(other: NumMatLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] %= other[i].toFloat()
        }
    }

    override fun translate(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until size) {
            result[i] = this[i] + other[i].toFloat()
        }
        return result
    }

    override fun translateSelf(other: NumMatLike<*, *>): S {
        for (i in 0 until size) {
            this[i] += other[i].toFloat()
        }
        return this as S
    }

    override fun translate(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until size) {
            result[i] = this[i] + other[i].toFloat()
        }
        return result
    }

    override fun translateSelf(other: NumVecLike<*, *>): S {
        for (i in 0 until size) {
            this[i] += other[i].toFloat()
        }
        return this as S
    }

    override fun rotate(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until size) {
            result[i] = this[i] * other[i].toFloat()
        }
        return result
    }

    override fun rotateSelf(other: NumMatLike<*, *>): S {
        for (i in 0 until size) {
            this[i] *= other[i].toFloat()
        }
        return this as S
    }

    override fun rotate(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until size) {
            result[i] = this[i] * other[i].toFloat()
        }
        return result
    }

    override fun rotateSelf(other: NumVecLike<*, *>): S {
        for (i in 0 until size) {
            this[i] *= other[i].toFloat()
        }
        return this as S
    }

    override fun scale(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until size) {
            result[i] = this[i] * other[i].toFloat()
        }
        return result
    }

    override fun scaleSelf(other: NumMatLike<*, *>): S {
        for (i in 0 until size) {
            this[i] *= other[i].toFloat()
        }
        return this as S
    }

    override fun scale(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until size) {
            result[i] = this[i] * other[i].toFloat()
        }
        return result
    }

    override fun scaleSelf(other: NumVecLike<*, *>): S {
        for (i in 0 until size) {
            this[i] *= other[i].toFloat()
        }
        return this as S
    }

    override fun zeroSelf(): S {
        for (i in 0 until size) {
            this[i] = 0f
        }
        return this as S
    }
}