package me.alex_s168.rend3d.graphics.renderable

import me.alex_s168.math.mat.impl.Mat4f
import me.alex_s168.math.mat.stack.Mat4fStack
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

class MeshRender(
    val mesh: Mesh,
    val texture: Texture
): Object3(), Renderable {
    private lateinit var program: Program
    private lateinit var vao: VertexArrayObject
    private lateinit var attribVert: GPUBufferProgramAttribute
    private lateinit var attribTex: GPUBufferProgramAttribute
    private lateinit var paramTextureSampler: Program.UniformParameter
    private lateinit var paramWorldMatrix: Program.UniformParameter
    private lateinit var paramProjectionMatrix: Program.UniformParameter

    override fun render(poseStack: Mat4fStack, projection: Mat4f, partial: Float) {
        poseStack.push()

        poseStack.translate(position)
        poseStack.rotate(rotation)
        poseStack.scale(scale)

        RenderSystem.enableDepthTest(RenderSystem.DepthFunc.LESS)

        RenderSystem.enableBlend()
        RenderSystem.blendFunc(RenderSystem.BlendFuncParam.SRC_ALPHA, RenderSystem.BlendFuncParam.ONE_MINUS_SRC_ALPHA)

        // RenderSystem.enableCullFace(front = false, back = true)

        program.execute {
            RenderSystem.allocateTextureSampler { textureSampler ->
                paramTextureSampler.set(textureSampler)
                paramWorldMatrix.set(poseStack.top())
                paramProjectionMatrix.set(projection)
                vao.execute {
                    texture.execute {
                        textureSampler.bind()
                        drawArrays(RenderSystem.RenderMode.TRIANGLES, 0, mesh.size * 3 * 3)
                    }
                }
            }
        }

        RenderSystem.disableDepthTest()

        RenderSystem.disableBlend()

        RenderSystem.disableCullFace()

        poseStack.pop()
    }

    override fun initRender() {
        vao = VertexArrayObject()
        vao.execute {
            val bufferVert = GPUBufferObject(GPUBufferObject.Type.ARRAY)
            bufferVert.bufferData(sizeBytes = Float.SIZE_BYTES * 3 * 3 * mesh.size) {
                mesh.triangles.forEach { tri ->
                    upload(tri.a.toArray())
                    upload(tri.b.toArray())
                    upload(tri.c.toArray())
                }
            }
            attribVert = GPUBufferProgramAttribute(bufferVert)
            attribVert.configure(3, GPUBufferProgramAttribute.Type.FLOAT)

            val bufferTex = GPUBufferObject(GPUBufferObject.Type.ARRAY)
            bufferTex.bufferData(sizeBytes = Float.SIZE_BYTES * 2 * 3 * mesh.size) {
                mesh.forEach { face ->
                    fun s(v: TextureCoordinate?) {
                        upload(floatArrayOf(1f - (v?.u ?: 0.0f), 1f - (v?.v ?: 0.0f)))
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
                fragColor = texture(textureSampler, out_TexCoord); 
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