package me.alex_s168.math.mat.impl

import me.alex_s168.math.mat.MatI

class Mat3i(
    data: IntArray = IntArray(9),
    offset: Int = 0
): MatI<Mat3i>(9, data, offset) {
    init {
        require(offset + 9 <= data.size) {
            "offset + 9 > data.size"
        }
    }

    var m00: Int
        get() = get(0, 0)
        set(value) = set(0, 0, value)

    var m01: Int
        get() = get(0, 1)
        set(value) = set(0, 1, value)

    var m02: Int
        get() = get(0, 2)
        set(value) = set(0, 2, value)

    var m10: Int
        get() = get(1, 0)
        set(value) = set(1, 0, value)

    var m11: Int
        get() = get(1, 1)
        set(value) = set(1, 1, value)

    var m12: Int
        get() = get(1, 2)
        set(value) = set(1, 2, value)

    var m20: Int
        get() = get(2, 0)
        set(value) = set(2, 0, value)

    var m21: Int
        get() = get(2, 1)
        set(value) = set(2, 1, value)

    var m22: Int
        get() = get(2, 2)
        set(value) = set(2, 2, value)

    constructor(
        m00: Int, m01: Int, m02: Int,
        m10: Int, m11: Int, m12: Int,
        m20: Int, m21: Int, m22: Int
    ) : this() {
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
        return "Mat3i($m00, $m01, $m02, $m10, $m11, $m12, $m20, $m21, $m22)"
    }

    override fun new(): Mat3i {
        return Mat3i()
    }

    companion object {
        fun wrap(data: IntArray, offset: Int = 0) =
            Mat3i(data, offset)

        fun wrap(mat: MatI<*>, offset: Int = 0) =
            Mat3i(mat.data, mat.offset + offset)
    }

}