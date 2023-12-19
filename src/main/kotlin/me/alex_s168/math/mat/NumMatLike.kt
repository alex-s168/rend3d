package me.alex_s168.math.mat

interface NumMatLike<T: Number, S>: MatLike<T, S> {
    operator fun plus(other: NumMatLike<*, *>): S
    operator fun minus(other: NumMatLike<*, *>): S
    operator fun times(other: NumMatLike<*, *>): S
    operator fun unaryMinus(): S
    operator fun plusAssign(other: NumMatLike<*, *>)
    operator fun minusAssign(other: NumMatLike<*, *>)
    operator fun timesAssign(other: NumMatLike<*, *>)
    operator fun times(scalar: T): S
    operator fun timesAssign(scalar: T)
    operator fun div(scalar: T): S
    operator fun divAssign(scalar: T)

    fun zeroSelf(): S

    fun identitySelf(): S
}