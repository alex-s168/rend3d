package me.alex_s168.math.mat.impl

import me.alex_s168.math.mat.MatI

class Mat4i(
    data: IntArray = IntArray(16),
    offset: Int = 0
): MatI<Mat4i>(16, data, offset) {
    init {
        require(offset + 16 <= data.size) {
            "offset + 16 > data.size"
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

    var m03: Int
        get() = get(0, 3)
        set(value) = set(0, 3, value)

    var m10: Int
        get() = get(1, 0)
        set(value) = set(1, 0, value)

    var m11: Int
        get() = get(1, 1)
        set(value) = set(1, 1, value)

    var m12: Int
        get() = get(1, 2)
        set(value) = set(1, 2, value)

    var m13: Int
        get() = get(1, 3)
        set(value) = set(1, 3, value)

    var m20: Int
        get() = get(2, 0)
        set(value) = set(2, 0, value)

    var m21: Int
        get() = get(2, 1)
        set(value) = set(2, 1, value)

    var m22: Int
        get() = get(2, 2)
        set(value) = set(2, 2, value)

    var m23: Int
        get() = get(2, 3)
        set(value) = set(2, 3, value)

    var m30: Int
        get() = get(3, 0)
        set(value) = set(3, 0, value)

    var m31: Int
        get() = get(3, 1)
        set(value) = set(3, 1, value)

    var m32: Int
        get() = get(3, 2)
        set(value) = set(3, 2, value)

    var m33: Int
        get() = get(3, 3)
        set(value) = set(3, 3, value)

    constructor(
        m00: Int, m01: Int, m02: Int, m03: Int,
        m10: Int, m11: Int, m12: Int, m13: Int,
        m20: Int, m21: Int, m22: Int, m23: Int,
        m30: Int, m31: Int, m32: Int, m33: Int
    ) : this() {
        this.m00 = m00
        this.m01 = m01
        this.m02 = m02
        this.m03 = m03
        this.m10 = m10
        this.m11 = m11
        this.m12 = m12
        this.m13 = m13
        this.m20 = m20
        this.m21 = m21
        this.m22 = m22
        this.m23 = m23
        this.m30 = m30
        this.m31 = m31
        this.m32 = m32
        this.m33 = m33
    }

    override fun toString(): String {
        return "Mat4f($m00, $m01, $m02, $m03, $m10, $m11, $m12, $m13, $m20, $m21, $m22, $m23, $m30, $m31, $m32, $m33)"
    }

    override fun new() =
        Mat4i()

    companion object {
        fun wrap(data: IntArray, offset: Int = 0) =
            Mat4i(data, offset)

        fun wrap(mat: MatI<*>, offset: Int = 0) =
            Mat4i(mat.data, mat.offset + offset)
    }

}