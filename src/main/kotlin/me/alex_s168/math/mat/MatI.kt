package me.alex_s168.math.mat

import java.nio.IntBuffer

abstract class MatI<S: MatI<S>>(
    final override val size: Int,
    val data: IntArray = IntArray(size),
    val offset: Int = 0
): IntMatLike<S> {

    override fun toArray(): IntArray {
        return data.copyOf()
    }

    override fun asArray(): IntArray {
        return data
    }

    override fun get(row: Int, col: Int): Int {
        return data[row * size + col]
    }

    override fun set(row: Int, col: Int, value: Int) {
        data[row * size + col] = value
    }

    override fun writeTo(buffer: IntBuffer) {
        buffer.put(data, offset, size)
    }

    override fun copy(): S {
        return new().also {
            it.from(this)
        }
    }

    override fun writeTo(arr: IntArray, offset: Int) {
        for (i in offset until size + offset) {
            arr[i] = data[i]
        }
    }

    override fun from(other: MatLike<Int, S>) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other[i]
        }
    }

    override fun from(other: IntBuffer) {
        if (other.remaining() != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other.get()
        }
    }

    override fun from(other: IntArray) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other[i]
        }
    }

    override fun get(index: Int): Int {
        return data[index + offset]
    }

    override fun set(index: Int, value: Int) {
        data[index + offset] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MatI<*>) return false
        if (size != other.size) return false
        for (i in offset until size + offset) {
            if (data[i] != other.data[i]) return false
        }
        return true
    }

    override fun hashCode(): Int {
        return data.sliceArray(offset until size + offset).contentHashCode()
    }

    override fun toString(): String {
        return "MatF(size=$size, data=${data.contentToString()})"
    }

}