package me.alex_s168.math.mat.impl

import me.alex_s168.math.mat.MatF

class Mat2f(
    data: FloatArray = FloatArray(4),
    offset: Int = 0
): MatF<Mat2f>(4, data, offset) {
    init {
        require(offset + 4 <= data.size) {
            "offset + 4 > data.size"
        }
    }

    var m00: Float
        get() = get(0, 0)
        set(value) = set(0, 0, value)

    var m01: Float
        get() = get(0, 1)
        set(value) = set(0, 1, value)

    var m10: Float
        get() = get(1, 0)
        set(value) = set(1, 0, value)

    var m11: Float
        get() = get(1, 1)
        set(value) = set(1, 1, value)

    constructor(
        m00: Float, m01: Float,
        m10: Float, m11: Float
    ): this() {
        this.m00 = m00
        this.m01 = m01
        this.m10 = m10
        this.m11 = m11
    }

    override fun toString(): String {
        return "Mat2f($m00, $m01, $m10, $m11)"
    }

    override fun new() =
        Mat2f()

    companion object {
        fun wrap(data: FloatArray, offset: Int = 0) =
            Mat2f(data, offset)

        fun wrap(mat: MatF<*>, offset: Int = 0) =
            Mat2f(mat.data, mat.offset + offset)
    }
}