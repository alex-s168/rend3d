package me.alex_s168.math.mat

import me.alex_s168.math.vec.NumVecLike

interface NumMatLike<T: Number, S>: MatLike<T, S> {
    operator fun plus(other: NumMatLike<*, *>): S
    operator fun minus(other: NumMatLike<*, *>): S
    operator fun times(other: NumMatLike<*, *>): S
    operator fun div(other: NumMatLike<*, *>): S
    operator fun rem(other: NumMatLike<*, *>): S
    operator fun unaryMinus(): S
    operator fun plusAssign(other: NumMatLike<*, *>)
    operator fun minusAssign(other: NumMatLike<*, *>)
    operator fun timesAssign(other: NumMatLike<*, *>)
    operator fun divAssign(other: NumMatLike<*, *>)
    operator fun remAssign(other: NumMatLike<*, *>)

    fun zeroSelf(): S

    fun identitySelf(): S
}