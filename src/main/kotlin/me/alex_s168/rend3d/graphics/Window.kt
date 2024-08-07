package me.alex_s168.rend3d.graphics

import me.alex_s168.rend3d.input.Key
import me.alex_s168.math.color.Color
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack.stackPush
import org.lwjgl.system.MemoryUtil.NULL

class Window(
    val title: String,
    var width: Int,
    var height: Int,
    val resizable: Boolean = true,
    visibleIn: Boolean = true,
    vararg parameters: Parameters.Parameter
): AutoCloseable {
    object Parameters {
        data class Parameter internal constructor(
            val hint: Int,
            val value: Int
        )

        fun DECORATED(value: Boolean) =
            Parameter(GLFW_DECORATED, value.toGLFW())

        fun FOCUSED(value: Boolean) =
            Parameter(GLFW_FOCUSED, value.toGLFW())

        fun SAMPLES(value: Int) =
            Parameter(GLFW_SAMPLES, value)
    }

    private var id: Long

    val aspect: Float
        get() = width.toFloat() / height.toFloat()

    var visible = visibleIn
        set(value) {
            field = value
            if (value) {
                glfwShowWindow(id)
            } else {
                glfwHideWindow(id)
            }
        }

    var keyCallback: (key: Int, scancode: Int, action: Key.Action, mods: Int) -> Unit = { _, _, _, _ -> }
        set(value) {
            field = value
            glfwSetKeyCallback(
                id
            ) { _: Long, key: Int, scancode: Int, actionIn: Int, mods: Int ->
                val action = when (actionIn) {
                    GLFW_PRESS -> Key.Action.PRESS
                    GLFW_RELEASE -> Key.Action.RELEASE
                    GLFW_REPEAT -> Key.Action.REPEAT
                    else -> throw IllegalStateException("Unknown key action: $actionIn")
                }
                value(key, scancode, action, mods)
            }
        }

    var mouseButtonCallback: (button: Key.MouseButton, action: Key.Action, mods: Int) -> Unit = { _, _, _ -> }
        set(value) {
            field = value
            glfwSetMouseButtonCallback(
                id
            ) { _: Long, button: Int, actionIn: Int, mods: Int ->
                val action = when (actionIn) {
                    GLFW_PRESS -> Key.Action.PRESS
                    GLFW_RELEASE -> Key.Action.RELEASE
                    GLFW_REPEAT -> Key.Action.REPEAT
                    else -> throw IllegalStateException("Unknown key action: $actionIn")
                }
                value(Key.MouseButton.fromId(button), action, mods)
            }
        }

    var cursorPosCallback: (x: Double, y: Double) -> Unit = { _, _ -> }
        set(value) {
            field = value
            glfwSetCursorPosCallback(
                id
            ) { _: Long, x: Double, y: Double ->
                value(x, y)
            }
        }

    var scrollCallback: (x: Double, y: Double) -> Unit = { _, _ -> }
        set(value) {
            field = value
            glfwSetScrollCallback(
                id
            ) { _: Long, x: Double, y: Double ->
                value(x, y)
            }
        }

    var resizeCallback: (window: Window) -> Unit = { _ -> }
        set(value) {
            field = value
            glfwSetWindowSizeCallback(
                id
            ) { _: Long, width: Int, height: Int ->
                glViewport(0, 0, width, height)
                this.width = width
                this.height = height
                value(this)
            }
        }

    // TODO: gamepad support

    var vSync: Boolean = false
        set(value) {
            field = value
            glfwSwapInterval(if (value) 1 else 0)
        }

    var current: Boolean = false
        get() = (RenderSystem.currentWindow == id)
        set(value) {
            if (value == field) {
                return
            }
            field = value
            if (value) {
                RenderSystem.currentWindow = id
            } else {
                RenderSystem.currentWindow = NULL
            }
        }

    init {
        RenderSystem.assertInitialized()

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, visibleIn.toGLFW())
        glfwWindowHint(GLFW_RESIZABLE, resizable.toGLFW())
        parameters.forEach {
            glfwWindowHint(it.hint, it.value)
        }
        id = glfwCreateWindow(width, height, title, NULL, NULL)
        if (id == NULL) {
            throw RuntimeException("Failed to create the GLFW window")
        }

        stackPush().use {
            // Get the resolution of the primary monitor
            val vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
                ?: throw RuntimeException("Failed to get the video mode")

            // Center the window
            glfwSetWindowPos(
                id,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
            )
        }

        current = true
    }

    fun execute(block: Window.() -> Unit) {
        val old = RenderSystem.currentWindow
        current = true
        glViewport(0, 0, width, height)
        resizeCallback = resizeCallback
        block(this)
        RenderSystem.currentWindow = old
    }

    var closed = false
        private set

    override fun close() {
        if (closed) {
            return
        }
        glfwFreeCallbacks(id)
        glfwDestroyWindow(id)
        closed = true
    }

    class WindowLoopContext(
        val window: Window
    ) {
        var backgroundColor: Color = Color.BLACK
            set(it) {
                window.current = true
                RenderSystem.GL.clearColor(it.r, it.g, it.b, it.a)
                field = it
            }
    }

    fun loop(block: WindowLoopContext.() -> Unit) {
        execute {
            val context = WindowLoopContext(this)
            while (!(glfwWindowShouldClose(id) || closed)) {
                glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
                context.block()
                glFlush()
                glfwSwapBuffers(id)
                glfwPollEvents()
            }
        }
    }
}