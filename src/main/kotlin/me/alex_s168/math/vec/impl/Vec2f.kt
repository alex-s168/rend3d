package me.alex_s168.math.vec.impl

import me.alex_s168.math.vec.VecF

class Vec2f(
    data: FloatArray = FloatArray(2),
    offset: Int = 0
): VecF<Vec2f>(2, data, offset) {
    init {
        require(offset + 2 <= data.size) {
            "offset + 2 > data.size"
        }
    }

    var x: Float
        get() = get(0)
        set(value) = set(0, value)

    var y: Float
        get() = get(1)
        set(value) = set(1, value)

    constructor(x: Float, y: Float): this() {
        this.x = x
        this.y = y
    }

    override fun copy(): Vec2f {
        return Vec2f().also {
            it.from(this)
        }
    }

    override fun toString(): String {
        return "Vec2f($x, $y)"
    }

    override fun new() =
        Vec2f()

    companion object {
        fun wrap(data: FloatArray, offset: Int = 0) =
            Vec2f(data, offset)

        fun wrap(vec: VecF<*>, offset: Int = 0) =
            Vec2f(vec.data, vec.offset + offset)
    }
}