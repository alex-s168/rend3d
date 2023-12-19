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

    fun translate(other: NumMatLike<*, *>): S
    fun translateSelf(other: NumMatLike<*, *>): S

    fun translate(other: NumVecLike<*, *>): S
    fun translateSelf(other: NumVecLike<*, *>): S

    fun rotate(other: NumMatLike<*, *>): S
    fun rotateSelf(other: NumMatLike<*, *>): S

    fun rotate(other: NumVecLike<*, *>): S
    fun rotateSelf(other: NumVecLike<*, *>): S

    fun scale(other: NumMatLike<*, *>): S
    fun scaleSelf(other: NumMatLike<*, *>): S

    fun scale(other: NumVecLike<*, *>): S
    fun scaleSelf(other: NumVecLike<*, *>): S

    fun zeroSelf(): S
}