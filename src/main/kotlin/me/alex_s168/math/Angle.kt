package me.alex_s168.math

data class Angle(
    internal val valRadians: Float
) {
    val radians = valRadians

    val degrees get() =
        valRadians * 180f / Math.PI.toFloat()

    val gradians get() =
        valRadians * 200f / Math.PI.toFloat()

    companion object {
        fun fromRadians(radians: Float) =
            Angle(radians)

        fun fromDegrees(degrees: Float) =
            Angle(degrees * Math.PI.toFloat() / 180f)

        fun fromGradians(gradians: Float) =
            Angle(gradians * Math.PI.toFloat() / 200f)
    }
}
