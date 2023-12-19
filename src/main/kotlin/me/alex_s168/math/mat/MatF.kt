package me.alex_s168.math.mat

import java.nio.FloatBuffer

abstract class MatF<S: MatF<S>>(
    final override val size: Int,
    val data: FloatArray = FloatArray(size),
    val offset: Int = 0
): FloatMatLike<S> {

    override fun toArray(): FloatArray {
        return data.copyOf()
    }

    override fun asArray(): FloatArray {
        return data
    }

    override fun get(row: Int, col: Int): Float {
        return data[row * width + col]
    }

    override fun set(row: Int, col: Int, value: Float) {
        data[row * width + col] = value
    }

    override fun writeTo(buffer: FloatBuffer) {
        buffer.put(data, offset, size)
    }

    override fun copy(): S {
        return new().also {
            it.from(this)
        }
    }

    override fun writeTo(arr: FloatArray, offset: Int) {
        for (i in offset until size + offset) {
            arr[i] = data[i]
        }
    }

    override fun from(other: MatLike<Float, S>) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other[i]
        }
    }

    override fun from(other: FloatBuffer) {
        if (other.remaining() != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other.get()
        }
    }

    override fun from(other: FloatArray) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other[i]
        }
    }

    override fun get(index: Int): Float {
        return data[index + offset]
    }

    override fun set(index: Int, value: Float) {
        data[index + offset] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MatF<*>) return false
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