package me.alex_s168.math.vec

import me.alex_s168.math.Angle
import java.nio.FloatBuffer

interface FloatVecLike<S: FloatVecLike<S>>: NumVecLike<Float, S> {
    /** clones */
    fun toArray(): FloatArray
    /** does not clone */
    fun asArray(): FloatArray
    fun writeTo(buffer: FloatBuffer)
    fun writeTo(arr: FloatArray, offset: Int = 0)
    fun from(other: VecLike<Float, S>)
    fun from(other: FloatArray)
    fun from(other: FloatBuffer)
    fun new(): S

    override fun plus(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] + other[i].toFloat()
        }
        return result
    }

    override fun minus(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] - other[i].toFloat()
        }
        return result
    }

    override fun times(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] * other[i].toFloat()
        }
        return result
    }

    override fun div(other: NumVecLike<*, *>): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = this[i] / other[i].toFloat()
        }
        return result
    }

    override fun rem(other: NumVecLike<*, *>): S {
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

    override fun plusAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] += other[i].toFloat()
        }
    }

    override fun minusAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] -= other[i].toFloat()
        }
    }

    override fun timesAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] *= other[i].toFloat()
        }
    }

    override fun divAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] /= other[i].toFloat()
        }
    }

    override fun remAssign(other: NumVecLike<*, *>) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] %= other[i].toFloat()
        }
    }

    override fun dot(other: NumVecLike<*, *>): Float {
        var result = 0f
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result += this[i] * other[i].toFloat()
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
            result[i] = (this[i] / len).toFloat()
        }
        return result
    }

    override fun normalizeSelf(): S {
        val len = length()
        for (i in 0 until size) {
            this[i] /= len.toFloat()
        }
        return this as S
    }

    override fun lerp(other: NumVecLike<*, *>, t: Double): S {
        val result = new()
        for (i in 0 until kotlin.math.min(size, other.size)) {
            result[i] = (this[i] + (other[i].toFloat() - this[i]) * t).toFloat()
        }
        return result
    }

    override fun lerpSelf(other: NumVecLike<*, *>, t: Double) {
        for (i in 0 until kotlin.math.min(size, other.size)) {
            this[i] = (this[i] + (other[i].toFloat() - this[i]) * t).toFloat()
        }
    }

    override fun distance(other: NumVecLike<*, *>): Double {
        return kotlin.math.sqrt(distanceSqr(other))
    }

    override fun distanceSqr(other: NumVecLike<*, *>): Double {
        var result = 0.0
        for (i in 0 until kotlin.math.min(size, other.size)) {
            val d = this[i] - other[i].toFloat()
            result += d * d
        }
        return result
    }

    override fun angle(other: NumVecLike<*, *>): Angle =
        Angle.fromRadians(kotlin.math.acos((this dot other) / (length() * other.length())).toFloat())
}