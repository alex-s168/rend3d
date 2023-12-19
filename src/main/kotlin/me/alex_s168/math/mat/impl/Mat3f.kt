package me.alex_s168.math.mat.impl

import me.alex_s168.math.mat.MatF

class Mat3f(
    data: FloatArray = FloatArray(9),
    offset: Int = 0
): MatF<Mat3f>(9, data, offset) {
    init {
        require(offset + 9 <= data.size) {
            "offset + 9 > data.size"
        }
    }

    var m00: Float
        get() = get(0, 0)
        set(value) = set(0, 0, value)

    var m01: Float
        get() = get(0, 1)
        set(value) = set(0, 1, value)

    var m02: Float
        get() = get(0, 2)
        set(value) = set(0, 2, value)

    var m10: Float
        get() = get(1, 0)
        set(value) = set(1, 0, value)

    var m11: Float
        get() = get(1, 1)
        set(value) = set(1, 1, value)

    var m12: Float
        get() = get(1, 2)
        set(value) = set(1, 2, value)

    var m20: Float
        get() = get(2, 0)
        set(value) = set(2, 0, value)

    var m21: Float
        get() = get(2, 1)
        set(value) = set(2, 1, value)

    var m22: Float
        get() = get(2, 2)
        set(value) = set(2, 2, value)

    constructor(
        m00: Float, m01: Float, m02: Float,
        m10: Float, m11: Float, m12: Float,
        m20: Float, m21: Float, m22: Float
    ): this() {
        this.m00 = m00
        this.m01 = m01
        this.m02 = m02
        this.m10 = m10
        this.m11 = m11
        this.m12 = m12
        this.m20 = m20
        this.m21 = m21
        this.m22 = m22
    }

    override fun toString(): String {
        return "Mat3f($m00, $m01, $m02, $m10, $m11, $m12, $m20, $m21, $m22)"
    }

    override fun new() =
        Mat3f()

    companion object {
        fun wrap(data: FloatArray, offset: Int = 0) =
            Mat3f(data, offset)

        fun wrap(mat: MatF<*>, offset: Int = 0) =
            Mat3f(mat.data, mat.offset + offset)
    }
}