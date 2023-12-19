package me.alex_s168.math.vec.impl

import me.alex_s168.math.vec.VecF

class Vec4f(
    data: FloatArray = FloatArray(4),
    offset: Int = 0
): VecF<Vec4f>(4, data, offset) {
    init {
        require(offset + 4 <= data.size) {
            "offset + 4 > data.size"
        }
    }

    var x: Float
        get() = get(0)
        set(value) = set(0, value)

    var y: Float
        get() = get(1)
        set(value) = set(1, value)

    var z: Float
        get() = get(2)
        set(value) = set(2, value)

    var w: Float
        get() = get(3)
        set(value) = set(3, value)

    constructor(x: Float, y: Float, z: Float, w: Float): this() {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    override fun copy(): Vec4f {
        return Vec4f().also {
            it.from(this)
        }
    }

    override fun toString(): String {
        return "Vec4f($x, $y, $z, $w)"
    }

    override fun new() =
        Vec4f()

    companion object {
        fun wrap(data: FloatArray, offset: Int = 0) =
            Vec4f(data, offset)

        fun wrap(vec: VecF<*>, offset: Int = 0) =
            Vec4f(vec.data, vec.offset + offset)
    }
}