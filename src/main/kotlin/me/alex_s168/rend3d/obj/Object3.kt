package me.alex_s168.rend3d.obj

import me.alex_s168.math.vec.impl.Vec3af
import me.alex_s168.math.vec.impl.Vec3f

abstract class Object3 {
    open var position = Vec3f()
    open var rotation = Vec3af()
    open var scale = Vec3f(1f, 1f, 1f)

    open fun tick() {

    }
}