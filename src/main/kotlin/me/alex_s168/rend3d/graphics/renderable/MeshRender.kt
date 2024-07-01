package me.alex_s168.rend3d.graphics.renderable

import me.alex_s168.meshlib.Mesh
import me.alex_s168.meshlib.texture.TextureCoordinate
import me.alex_s168.rend3d.graphics.RenderSystem
import me.alex_s168.rend3d.graphics.data.GPUBufferObject
import me.alex_s168.rend3d.graphics.data.VertexArrayObject
import me.alex_s168.rend3d.graphics.shader.GPUBufferProgramAttribute
import me.alex_s168.rend3d.graphics.shader.Program
import me.alex_s168.rend3d.graphics.shader.Shader
import me.alex_s168.rend3d.graphics.texture.Texture
import me.alex_s168.rend3d.obj.Object3
import org.joml.Matrix4f
import org.joml.Matrix4fStack
import org.lwjgl.opengl.GL11.*

class MeshRender(
    val mesh: Mesh,
    val texture: Texture,
    val invertTexture: Boolean = false
): Object3(), Renderable {
    private lateinit var program: Program
    private lateinit var vao: VertexArrayObject
    private lateinit var attribVert: GPUBufferProgramAttribute
    private lateinit var attribTex: GPUBufferProgramAttribute
    private var paramTextureSampler: Program.UniformParameter? = null
    private var paramWorldMatrix: Program.UniformParameter? = null
    private var paramProjectionMatrix: Program.UniformParameter? = null

    override fun render(poseStack: Matrix4fStack, projection: Matrix4f, partial: Float) {
        poseStack.pushMatrix()

        poseStack.translate(position.x, position.y, position.z)
        poseStack.scale(scale.x, scale.y, scale.z)
        poseStack.rotateXYZ(rotation.roll.radians, rotation.pitch.radians, rotation.yaw.radians)

        RenderSystem.enableDepthTest(RenderSystem.DepthFunc.LESS)

        RenderSystem.enableBlend()
        RenderSystem.blendFunc(RenderSystem.BlendFuncParam.SRC_ALPHA, RenderSystem.BlendFuncParam.ONE_MINUS_SRC_ALPHA)

        RenderSystem.enableCullFace(front = false, back = true)
        glFrontFace(GL_CCW)

        program.execute {
            RenderSystem.allocateTextureSampler { textureSampler ->
                paramTextureSampler?.set(textureSampler)
                paramWorldMatrix?.set(poseStack)
                paramProjectionMatrix?.set(projection)
                vao.execute {
                    texture.execute {
                        textureSampler.bind()
                        drawArrays(RenderSystem.RenderMode.TRIANGLES, 0, mesh.size * 3)
                    }
                }
            }
        }

        RenderSystem.disableCullFace()

        RenderSystem.disableBlend()

        RenderSystem.disableDepthTest()

        poseStack.popMatrix()
    }

    override fun initRender() {
        vao = VertexArrayObject()
        vao.execute {
            val bufferVert = GPUBufferObject(GPUBufferObject.Type.ARRAY)
            bufferVert.bufferData(sizeBytes = Float.SIZE_BYTES * 3 * 3 * mesh.size) {
                mesh.triangles.forEach { tri -> // TODO: make faster
                    upload(tri.a.asArray())
                    upload(tri.b.asArray())
                    upload(tri.c.asArray())
                }
            }
            attribVert = GPUBufferProgramAttribute(bufferVert)
            attribVert.configure(3, GPUBufferProgramAttribute.Type.FLOAT)

            val bufferTex = GPUBufferObject(GPUBufferObject.Type.ARRAY)
            bufferTex.bufferData(sizeBytes = Float.SIZE_BYTES * 2 * 3 * mesh.size) {
                mesh.forEach { face ->
                    fun s(v: TextureCoordinate?) {
                        val fu = v?.u ?: 0.0f
                        val fv = v?.v ?: 0.0f
                        if (invertTexture) {
                            upload(floatArrayOf(fu, 1.0f - fv))
                        } else {
                            upload(floatArrayOf(fu, fv))
                        }
                    }
                    s(face.tex?.a)
                    s(face.tex?.b)
                    s(face.tex?.c)
                }
            }
            attribTex = GPUBufferProgramAttribute(bufferTex)
            attribTex.configure(2, GPUBufferProgramAttribute.Type.FLOAT)

            program = Program(vertexShader, fragmentShader)
            program.attributes["in_Position"] = attribVert
            program.attributes["in_TexCoord"] = attribTex
            program.link()

            paramTextureSampler = program.parameters["textureSampler"]
            paramWorldMatrix = program.parameters["worldMatrix"]
            paramProjectionMatrix = program.parameters["projectionMatrix"]
        }
    }

    companion object {
        private val vertexShaderSource = """
            #version 330 core
    
            in  vec3 in_Position;
            in  vec2 in_TexCoord;
    
            out vec2 out_TexCoord;
            
            uniform mat4 worldMatrix;
            uniform mat4 projectionMatrix;
    
            void main() {
                gl_Position = projectionMatrix * worldMatrix * vec4(in_Position, 1.0);
                out_TexCoord = in_TexCoord;
            }
        """.trimIndent()

        private val fragmentShaderSource = """
            #version 330 core
    
            precision highp float;
    
            in  vec2 out_TexCoord;
            out vec4 fragColor;
            
            uniform sampler2D textureSampler;
    
            void main() {
                vec4 tex = texture(textureSampler, out_TexCoord);
                fragColor = tex;
            }
        """.trimIndent()

        private val vertexShader by lazy {
            Shader(Shader.Type.VERT, vertexShaderSource).also {
                it.compile()
            }
        }

        private val fragmentShader by lazy {
            Shader(Shader.Type.FRAG, fragmentShaderSource).also {
                it.compile()
            }
        }
    }
}