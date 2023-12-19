package me.alex_s168

import org.lwjgl.glfw.GLFW.*

object Key {
    enum class Action {
        PRESS,
        REPEAT,
        RELEASE
    }

    enum class MouseButton(val id: Int) {
        LEFT(GLFW_MOUSE_BUTTON_LEFT),
        RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
        MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
        FOUR(GLFW_MOUSE_BUTTON_4),
        FIVE(GLFW_MOUSE_BUTTON_5),
        SIX(GLFW_MOUSE_BUTTON_6),
        SEVEN(GLFW_MOUSE_BUTTON_7),
        EIGHT(GLFW_MOUSE_BUTTON_8)
        ;

        companion object {
            fun fromId(id: Int): MouseButton {
                return entries.first { it.id == id }
            }
        }
    }
}