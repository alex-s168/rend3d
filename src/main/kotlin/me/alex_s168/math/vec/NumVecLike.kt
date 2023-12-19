package me.alex_s168.math.vec

import me.alex_s168.math.Angle

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

    operator fun plus(other: T): S
    operator fun minus(other: T): S
    operator fun times(other: T): S
    operator fun div(other: T): S
    operator fun rem(other: T): S
    operator fun plusAssign(other: T)
    operator fun minusAssign(other: T)
    operator fun timesAssign(other: T)
    operator fun divAssign(other: T)
    operator fun remAssign(other: T)

    infix fun dot(other: NumVecLike<*, *>): T
    // TODO: Cross product
    fun length(): Double
    fun lengthSqr(): Double
    fun normalize(): S
    fun normalizeSelf(): S
    fun lerp(other: NumVecLike<*, *>, t: Double): S
    infix fun lerp(otherAndT: Pair<NumVecLike<*, *>, Double>): S =
        lerp(otherAndT.first, otherAndT.second)
    fun lerpSelf(other: NumVecLike<*, *>, t: Double)
    infix fun lerpSelf(otherAndT: Pair<NumVecLike<*, *>, Double>) =
        lerpSelf(otherAndT.first, otherAndT.second)
    infix fun distance(other: NumVecLike<*, *>): Double
    infix fun distanceSqr(other: NumVecLike<*, *>): Double
    infix fun angle(other: NumVecLike<*, *>): Angle
    fun zeroSelf(): S
}