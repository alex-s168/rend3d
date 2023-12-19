package me.alex_s168.math.vec

import java.nio.IntBuffer

abstract class VecI<S: VecI<S>>(
    final override val size: Int,
    val data: IntArray = IntArray(size),
    val offset: Int = 0
): IntVecLike<S> {

    override fun toArray(): IntArray {
        return data.copyOf()
    }

    override fun asArray(): IntArray {
        return data
    }

    override fun writeTo(buffer: IntBuffer) {
        for (i in offset until size + offset) {
            buffer.put(data[i])
        }
    }

    override fun writeTo(arr: IntArray, offset: Int) {
        for (i in this.offset until size + this.offset) {
            arr[i + offset] = data[i]
        }
    }

    override fun from(other: VecLike<Int, S>) {
        if (other.size != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other[i]
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

    override fun from(other: IntBuffer) {
        if (other.remaining() != size) {
            throw IllegalArgumentException("Size mismatch!")
        }
        for (i in offset until size + offset) {
            data[i] = other.get()
        }
    }

    override fun get(index: Int): Int {
        return data[index + offset]
    }

    override fun set(index: Int, value: Int) {
        data[index + offset] = value
    }
}