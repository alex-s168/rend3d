package me.alex_s168.math.vec

import java.nio.FloatBuffer

abstract class VecF<S: VecF<S>>(
    final override val size: Int,
    val data: FloatArray = FloatArray(size),
    val offset: Int = 0
): FloatVecLike<S> {

    override fun toArray(): FloatArray {
        return data.copyOf()
    }

    override fun asArray(): FloatArray {
        return data
    }

    override fun writeTo(buffer: FloatBuffer) {
        for (i in offset until size + offset) {
            buffer.put(data[i])
        }
    }

    override fun writeTo(arr: FloatArray, offset: Int) {
        for (i in this.offset until size + this.offset) {
            arr[i + offset] = data[i]
        }
    }

    override fun from(other: VecLike<Float, S>) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other[i]
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

    override fun from(other: FloatBuffer) {
        if (other.remaining() != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other.get()
        }
    }

    override fun get(index: Int): Float {
        return data[index + offset]
    }

    override fun set(index: Int, value: Float) {
        data[index + offset] = value
    }
}