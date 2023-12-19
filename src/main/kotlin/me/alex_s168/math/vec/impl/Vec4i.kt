package me.alex_s168.math.vec.impl

import me.alex_s168.math.vec.VecI

class Vec4i(
    data: IntArray = IntArray(4),
    offset: Int = 0
): VecI<Vec4i>(4, data, offset) {
    init {
        require(offset + 4 <= data.size) {
            "offset + 4 > data.size"
        }
    }

    var x: Int
        get() = get(0)
        set(value) = set(0, value)

    var y: Int
        get() = get(1)
        set(value) = set(1, value)

    var z: Int
        get() = get(2)
        set(value) = set(2, value)

    var w: Int
        get() = get(3)
        set(value) = set(3, value)

    constructor(x: Int, y: Int, z: Int, w: Int) : this() {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    override fun copy(): Vec4i {
        return Vec4i().also {
            it.from(this)
        }
    }

    override fun toString(): String {
        return "Vec4i($x, $y, $z, $w)"
    }

    override fun new() =
        Vec4i()

    companion object {
        fun wrap(data: IntArray, offset: Int = 0) =
            Vec4i(data, offset)

        fun wrap(vec: VecI<*>, offset: Int = 0) =
            Vec4i(vec.data, vec.offset + offset)
    }
}