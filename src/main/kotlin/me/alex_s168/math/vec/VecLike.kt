package me.alex_s168.math.vec

import me.alex_s168.math.Angle
import java.nio.FloatBuffer
import java.nio.IntBuffer
import kotlin.math.min

interface VecLike<T, S>: Collection<T> {
    operator fun get(index: Int): T
    operator fun set(index: Int, value: T)

    override fun iterator(): Iterator<T> =
        VecIterator(this)

    class VecIterator<T, S>(val vec: VecLike<T, S>): Iterator<T> {
        var index = 0
        override fun hasNext(): Boolean = index < vec.size
        override fun next(): T = vec[index++]
    }

    override fun contains(element: T): Boolean {
        for (e in this) {
            if (e == element) {
                return true
            }
        }
        return false
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        for (element in elements) {
            if (!contains(element)) {
                return false
            }
        }
        return true
    }

    override fun isEmpty(): Boolean =
        false

    fun copy(): S
}

interface NumVecLike<T: Number, S>: VecLike<T, S> {
    operator fun plus(other: NumVecLike<*, *>): S
    operator fun minus(other: NumVecLike<*, *>): S
    operator fun times(other: NumVecLike<*, *>): S
    operator fun div(other: NumVecLike<*, *>): S
    operator fun rem(other: NumVecLike<*, *>): S
    operator fun unaryMinus(): S
    operator fun plusAssign(other: NumVecLike<*, *>)
    operator fun minusAssign(other: NumVecLike<*, *>)
    operator fun timesAssign(other: NumVecLike<*, *>)
    operator fun divAssign(other: NumVecLike<*, *>)
    operator fun remAssign(other: NumVecLike<*, *>)
    infix fun dot(other: NumVecLike<*, *>): T
    // TODO: Cross product
    fun length(): Double
    fun lengthSqr(): Double
    fun normalize(): S
    fun normalizeSelf()
    fun lerp(other: NumVecLike<*, *>, t: Double): S
    infix fun lerp(otherAndT: Pair<NumVecLike<*, *>, Double>): S =
        lerp(otherAndT.first, otherAndT.second)
    fun lerpSelf(other: NumVecLike<*, *>, t: Double)
    infix fun lerpSelf(otherAndT: Pair<NumVecLike<*, *>, Double>) =
        lerpSelf(otherAndT.first, otherAndT.second)
    infix fun distance(other: NumVecLike<*, *>): Double
    infix fun distanceSqr(other: NumVecLike<*, *>): Double
    infix fun angle(other: NumVecLike<*, *>): Angle
}