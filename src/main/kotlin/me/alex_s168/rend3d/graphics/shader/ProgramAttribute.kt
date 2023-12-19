package me.alex_s168.rend3d.graphics.shader

import me.alex_s168.rend3d.graphics.RenderSystem

abstract class ProgramAttribute {
    internal val id = RenderSystem.allocateShaderAttributeID()
    internal abstract fun canBeUsed(): Boolean
}