package me.alex_s168.math.vec.impl

import me.alex_s168.math.vec.VecF

class Vec3f(
    data: FloatArray = FloatArray(3),
    offset: Int = 0
): VecF<Vec3f>(3, data, offset) {
    init {
        require(offset + 3 <= data.size) {
            "offset + 3 > data.size"
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

    constructor(x: Float, y: Float, z: Float): this() {
        this.x = x
        this.y = y
        this.z = z
    }

    override fun copy(): Vec3f {
        return Vec3f().also {
            it.from(this)
        }
    }

    override fun toString(): String {
        return "Vec3f($x, $y, $z)"
    }

    override fun new() =
        Vec3f()

    companion object {
        fun wrap(data: FloatArray, offset: Int = 0) =
            Vec3f(data, offset)

        fun wrap(vec: VecF<*>, offset: Int = 0) =
            Vec3f(vec.data, vec.offset + offset)
    }
}