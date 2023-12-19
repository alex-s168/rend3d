package me.alex_s168.graphics

abstract class ProgramAttribute {
    internal val id = RenderSystem.allocateShaderAttributeID()
    internal abstract fun canBeUsed(): Boolean
}