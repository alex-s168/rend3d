package me.alex_s168.math.mat

import me.alex_s168.math.vec.NumVecLike
import java.nio.IntBuffer

interface IntMatLike<S: IntMatLike<S>>: NumMatLike<Int, S> {
    /** clones */
    fun toArray(): IntArray
    /** does not clone */
    fun asArray(): IntArray
    fun writeTo(buffer: IntBuffer)
    fun writeTo(arr: IntArray, offset: Int = 0)
    fun from(other: MatLike<Int, S>)
    fun from(other: IntArray)
    fun from(other: IntBuffer)
    fun new(): S

    override fun plus(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] + other[i].toInt()
        }
        return result
    }

    override fun minus(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] - other[i].toInt()
        }
        return result
    }

    override fun times(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] * other[i].toInt()
        }
        return result
    }

    override fun div(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] / other[i].toInt()
        }
        return result
    }

    override fun rem(other: NumMatLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] % other[i].toInt()
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
            this[i] += other[i].toInt()
        }
    }

    override fun minusAssign(other: NumMatLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] -= other[i].toInt()
        }
    }

    override fun timesAssign(other: NumMatLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] *= other[i].toInt()
        }
    }

    override fun divAssign(other: NumMatLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] /= other[i].toInt()
        }
    }

    override fun remAssign(other: NumMatLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] %= other[i].toInt()
        }
    }

    override fun zeroSelf(): S {
        for (i in 0 until size) {
            this[i] = 0
        }
        return this as S
    }

    override fun identitySelf(): S {
        for (i in 0 until size) {
            this[i] = if (i % (width + 1) == 0) 1 else 0
        }
        return this as S
    }
}