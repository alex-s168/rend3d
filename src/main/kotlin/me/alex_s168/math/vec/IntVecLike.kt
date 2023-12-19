package me.alex_s168.math.vec

import me.alex_s168.math.Angle
import java.nio.IntBuffer

interface IntVecLike<S: IntVecLike<S>>: NumVecLike<Int, S> {
    /** clones */
    fun toArray(): IntArray
    /** does not clone */
    fun asArray(): IntArray
    fun writeTo(buffer: IntBuffer)
    fun writeTo(arr: IntArray, offset: Int = 0)
    fun from(other: VecLike<Int, S>)
    fun from(other: IntArray)
    fun from(other: IntBuffer)
    fun new(): S

    override fun plus(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] + other[i].toInt()
        }
        return result
    }

    override fun minus(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] - other[i].toInt()
        }
        return result
    }

    override fun times(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] * other[i].toInt()
        }
        return result
    }

    override fun div(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] / other[i].toInt()
        }
        return result
    }

    override fun rem(other: NumVecLike<*, *>): S {
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

    override fun plusAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] += other[i].toInt()
        }
    }

    override fun minusAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] -= other[i].toInt()
        }
    }

    override fun timesAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] *= other[i].toInt()
        }
    }

    override fun divAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] /= other[i].toInt()
        }
    }

    override fun remAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] %= other[i].toInt()
        }
    }

    override fun dot(other: NumVecLike<*, *>): Int {
        var result = 0
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result += this[i] * other[i].toInt()
        }
        return result
    }


    override fun length(): Double {
        return kotlin.math.sqrt(lengthSqr())
    }

    override fun lengthSqr(): Double {
        var result = 0.0
        for (i in 0 until size) {
            val v = this[i]
            result += v * v
        }
        return result
    }

    override fun normalize(): S {
        val result = new()
        val len = length()
        for (i in 0 until size) {
            result[i] = (this[i] / len).toInt()
        }
        return result
    }

    override fun normalizeSelf() {
        val len = length()
        for (i in 0 until size) {
            this[i] /= len.toInt()
        }
    }

    override fun lerp(other: NumVecLike<*, *>, t: Double): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = (this[i] + (other[i].toInt() - this[i]) * t).toInt()
        }
        return result
    }

    override fun lerpSelf(other: NumVecLike<*, *>, t: Double) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] = (this[i] + (other[i].toInt() - this[i]) * t).toInt()
        }
    }

    override fun distance(other: NumVecLike<*, *>): Double {
        return kotlin.math.sqrt(distanceSqr(other))
    }

    override fun distanceSqr(other: NumVecLike<*, *>): Double {
        var result = 0.0
        for (i in 0 until kotlin.math.min(size, other.size)) {
            val d = this[i] - other[i].toInt()
            result += d * d
        }
        return result
    }

    override fun angle(other: NumVecLike<*, *>): Angle =
        Angle.fromRadians(kotlin.math.acos((this dot other) / (length() * other.length())).toFloat())
}