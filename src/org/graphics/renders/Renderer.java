package org.graphics.renders;

import static org.lwjgl.opengl.GL46.*;

import org.graphics.utils.ShaderProgram;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class Renderer {
    private int vaoID, vboID;
    private ShaderProgram shaderProgram;

    public void init() {
        shaderProgram = new ShaderProgram("res/shaders/vertexShader.vert", "res/shaders/fragmentShader.frag");

        float[] vertices = {
                // X, Y, Z,  R, G, B
                -0.4f,0.2f,0.0f,0.0f,0.0f,1.0f,
                0.4f,0.2f,0.0f,0.0f,0.0f,1.0f,
                -0.3f,-0.1f,0.0f,0.0f,0.0f,1.0f,
                0.3f,-0.1f,0.0f,0.0f,0.0f,1.0f,
                -0.4f,-0.4f,0.0f,0.0f,0.0f,1.0f,
                0.4f,-0.4f,0.0f,0.0f,0.0f,1.0f,
        };

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Атрибут 0 - позиция
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        // Атрибут 1 - цвят
        glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
        glEnableVertexAttribArray(1);


        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void render() {
        // A = 1, B = 6, C = 1, D = 4
        shaderProgram.use();
        glBindVertexArray(vaoID);
        Matrix4f modelMatrix = new Matrix4f().identity()
                .rotate((float)Math.toRadians(46),0,0,1)
                .translate(0.1f,0.6f,0.4f);
        shaderProgram.setUniform("modelMatrix",modelMatrix);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 6);
        glBindVertexArray(0);
    }
}