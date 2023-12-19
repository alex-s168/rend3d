package me.alex_s168.math.mat

import kotlin.math.sqrt

interface MatLike<T, S>: Collection<T> {
    operator fun get(row: Int, col: Int): T
    operator fun set(row: Int, col: Int, value: T)

    operator fun get(index: Int): T
    operator fun set(index: Int, value: T)

    override fun iterator(): Iterator<T> =
        MatIterator(this)

    class MatIterator<T, S>(val mat: MatLike<T, S>): Iterator<T> {
        var index = 0
        override fun hasNext(): Boolean =
            index < mat.size
        override fun next(): T =
            mat[index++]
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

    val width: Int get() =
        sqrt(size.toDouble()).toInt()

    val height: Int get() =
        sqrt(size.toDouble()).toInt()
}