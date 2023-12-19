package me.alex_s168.math.vec.impl

import me.alex_s168.math.vec.VecI

class Vec3i(
    data: IntArray = IntArray(3),
    offset: Int = 0
): VecI<Vec3i>(3, data, offset) {
    init {
        require(offset + 3 <= data.size) {
            "offset + 3 > data.size"
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

    constructor(x: Int, y: Int, z: Int) : this() {
        this.x = x
        this.y = y
        this.z = z
    }

    override fun copy(): Vec3i {
        return Vec3i().also {
            it.from(this)
        }
    }

    override fun toString(): String {
        return "Vec3i($x, $y, $z)"
    }

    override fun new() =
        Vec3i()

    companion object {
        fun wrap(data: IntArray, offset: Int = 0) =
            Vec3i(data, offset)

        fun wrap(vec: VecI<*>, offset: Int = 0) =
            Vec3i(vec.data, vec.offset + offset)
    }
}