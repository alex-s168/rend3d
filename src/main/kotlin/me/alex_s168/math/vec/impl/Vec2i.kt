package me.alex_s168.math.vec.impl

import me.alex_s168.math.vec.VecI

class Vec2i(
    data: IntArray = IntArray(2),
    offset: Int = 0
): VecI<Vec2i>(2, data, offset) {
    init {
        require(offset + 2 <= data.size) {
            "offset + 2 > data.size"
        }
    }

    var x: Int
        get() = get(0)
        set(value) = set(0, value)

    var y: Int
        get() = get(1)
        set(value) = set(1, value)

    constructor(x: Int, y: Int) : this() {
        this.x = x
        this.y = y
    }

    override fun copy(): Vec2i {
        return Vec2i().also {
            it.from(this)
        }
    }

    override fun toString(): String {
        return "Vec2i($x, $y)"
    }

    override fun new() =
        Vec2i()

    companion object {
        fun wrap(data: IntArray, offset: Int = 0) =
            Vec2i(data, offset)

        fun wrap(vec: VecI<*>, offset: Int = 0) =
            Vec2i(vec.data, vec.offset + offset)
    }
}