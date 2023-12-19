package me.alex_s168.math.mat

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
        val result = copy()
        result *= other
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
        for (i in 0 until height) {
            for (j in 0 until width) {
                var sum = 0f
                for (k in 0 until width) {
                    sum += this[i, k] * other[k, j].toFloat()
                }
                this[i, j] = sum
            }
        }
    }

    override fun times(scalar: Float): S {
        val result = new()
        for (i in 0 until size) {
            result[i] = this[i] * scalar
        }
        return result
    }

    override fun timesAssign(scalar: Float) {
        for (i in 0 until size) {
            this[i] *= scalar
        }
    }

    override fun div(scalar: Float): S {
        val result = new()
        for (i in 0 until size) {
            result[i] = this[i] / scalar
        }
        return result
    }

    override fun divAssign(scalar: Float) {
        for (i in 0 until size) {
            this[i] /= scalar
        }
    }

    override fun zeroSelf(): S {
        for (i in 0 until size) {
            this[i] = 0f
        }
        return this as S
    }

    override fun identitySelf(): S {
        for (i in 0 until size) {
            this[i] = if (i % (width + 1) == 0) 1f else 0f
        }
        return this as S
    }
}