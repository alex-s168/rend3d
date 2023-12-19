package me.alex_s168.math.mat.impl

import me.alex_s168.math.mat.MatI

class Mat2i(
    data: IntArray = IntArray(4),
    offset: Int = 0
): MatI<Mat2i>(4, data, offset) {
    init {
        require(offset + 4 <= data.size) {
            "offset + 4 > data.size"
        }
    }

    var m00: Int
        get() = get(0, 0)
        set(value) = set(0, 0, value)

    var m01: Int
        get() = get(0, 1)
        set(value) = set(0, 1, value)

    var m10: Int
        get() = get(1, 0)
        set(value) = set(1, 0, value)

    var m11: Int
        get() = get(1, 1)
        set(value) = set(1, 1, value)

    constructor(
        m00: Int, m01: Int,
        m10: Int, m11: Int
    ): this() {
        this.m00 = m00
        this.m01 = m01
        this.m10 = m10
        this.m11 = m11
    }

    override fun toString(): String {
        return "Mat2i($m00, $m01, $m10, $m11)"
    }

    override fun new() =
        Mat2i()

    companion object {
        fun wrap(data: IntArray, offset: Int = 0) =
            Mat2i(data, offset)

        fun wrap(mat: MatI<*>, offset: Int = 0) =
            Mat2i(mat.data, mat.offset + offset)
    }
}