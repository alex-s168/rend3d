package me.alex_s168.math.color

import me.alex_s168.math.vec.VecF
import me.alex_s168.math.vec.impl.Vec4i

class Color(
    data: FloatArray = FloatArray(4),
    offset: Int = 0
): VecF<Color>(4, data, offset) {

    init {
        require(offset + 4 <= data.size) {
            "offset + 4 > data.size"
        }
    }

    var r: Float
        get() = get(0)
        set(value) = set(0, value)

    var g: Float
        get() = get(1)
        set(value) = set(1, value)

    var b: Float
        get() = get(2)
        set(value) = set(2, value)

    var a: Float
        get() = get(3)
        set(value) = set(3, value)

    constructor(r: Float, g: Float, b: Float, a: Float) : this(floatArrayOf(r, g, b, a))

    constructor(r: Int, g: Int, b: Int, a: Int) : this(
        r.toFloat() / 255f,
        g.toFloat() / 255f,
        b.toFloat() / 255f,
        a.toFloat() / 255f
    )

    constructor(r: Int, g: Int, b: Int) : this(
        r.toFloat() / 255f,
        g.toFloat() / 255f,
        b.toFloat() / 255f,
        1f
    )

    constructor(r: Float, g: Float, b: Float) : this(
        r,
        g,
        b,
        1f
    )

    fun toInt(): Int =
        ((r * 255f).toInt() shl 24) or
        ((g * 255f).toInt() shl 16) or
        ((b * 255f).toInt() shl 8) or
        (a * 255f).toInt()

    override fun toString(): String =
        "Color($r, $g, $b, $a)"

    fun toIntVec() = Vec4i(
            (r * 255f).toInt(),
            (g * 255f).toInt(),
            (b * 255f).toInt(),
            (a * 255f).toInt()
        )

    override fun new(): Color =
        Color()

    override fun copy(): Color =
        Color().also {
            it.from(this)
        }

    companion object {
        val BLACK = Color(0f, 0f, 0f, 1f)
        val WHITE = Color(1f, 1f, 1f, 1f)
        val RED = Color(1f, 0f, 0f, 1f)
        val GREEN = Color(0f, 1f, 0f, 1f)
        val BLUE = Color(0f, 0f, 1f, 1f)
        val YELLOW = Color(1f, 1f, 0f, 1f)
        val CYAN = Color(0f, 1f, 1f, 1f)
        val MAGENTA = Color(1f, 0f, 1f, 1f)

        fun wrap(data: FloatArray, offset: Int = 0): Color =
            Color(data, offset)

        fun wrap(vec: VecF<*>, offset: Int = 0): Color =
            Color(vec.data, vec.offset + offset)
    }
}