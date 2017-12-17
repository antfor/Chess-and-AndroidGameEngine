package antonforsberg.chess.Stuff;


import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import antonforsberg.chess.R;
import antonforsberg.chess.comobject.TextureHelper;


/**
 * Created by Anton Forsberg on 2017-07-10.
 */

public class Meshtex {


    /** Used for debug logs. */
    private static final String TAG = "Mesh";

    private Context mActivityContext;

    private int vertexCount=-1;

    private int mProgram;

    /** This will be used to pass in the transformation matrix. */
    private int mMVPMatrixHandle;

    /** This will be used to pass in the modelview matrix. */
    private int mMVMatrixHandle;

    /** This will be used to pass in the light position. */
    private int mLightPosHandle;

    /** This will be used to pass in model position information. */
    private int mPositionHandle;

    /** This will be used to pass in model color information. */
    private int mColorHandle;

    /** This will be used to pass in model normal information. */
    private int mNormalHandle;

    private int mTextureDataHandle;

    private int mTextureCoordinateHandle;

    private int mTextureUniformHandle;

    private float[] mLightPosInModelSpace = new float[] {0.0f, 0.0f, -2.5f};
    private  FloatBuffer NormalBuffer=null;
    private FloatBuffer verticesBuffer = null;
    private  FloatBuffer ColorBuffer=null;
    private FloatBuffer mTextureBuffer;

public void setmActivityContext(final Context activityContext){mActivityContext = activityContext;}
    // Our texture id.
    private int mTextureId = -1;

    // The bitmap we want to load as a texture.
    private Bitmap mBitmap;

    public void start(){ final String vertexShader = getVertexShader();
        final String fragmentShader = getFragmentShader();

        final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

        mProgram = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                new String[] {"a_Position",  "a_Color", "a_Normal", "a_TexCoordinate"});


        // Load the texture
        mTextureDataHandle = TextureHelper.loadTexture(mActivityContext, R.drawable.cubete);}

    public  Meshtex(){


    }

    protected void setTextureCoordinates(float[] textureCoords) {
        // float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(
                textureCoords.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mTextureBuffer = byteBuf.asFloatBuffer();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);
    }

    protected void setVertices(float[] vertices) {
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        verticesBuffer = vbb.asFloatBuffer();
        verticesBuffer.put(vertices);
        verticesBuffer.position(0);
        vertexCount=vertices.length/3;
    }
    protected void setNormals(float[] Normals) {
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(Normals.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        NormalBuffer = vbb.asFloatBuffer();
        NormalBuffer.put(Normals);
        NormalBuffer.position(0);
    }

    protected void setColor(float[] Color) {
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(Color.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        ColorBuffer = vbb.asFloatBuffer();
        ColorBuffer.put(Color);
        ColorBuffer.position(0);
    }



    public void draw(float[] mvpMatrix,float[] mProjectionMatrix,float[] mViewMatrix,float[] mModelMatrix)
    {
        GLES20.glUseProgram(mProgram);
        // Set program handles for cube drawing.
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVPMatrix");
        mMVMatrixHandle = GLES20.glGetUniformLocation(mProgram, "u_MVMatrix");
        mLightPosHandle = GLES20.glGetUniformLocation(mProgram, "u_LightPos");
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "a_Position");
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "a_Color");
        mNormalHandle = GLES20.glGetAttribLocation(mProgram, "a_Normal");



        mTextureUniformHandle = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        mTextureCoordinateHandle = GLES20.glGetAttribLocation(mProgram, "a_TexCoordinate");

        // Set the active texture unit to texture unit 0.
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        // Bind the texture to this unit.
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureDataHandle);

        // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES20.glUniform1i(mTextureUniformHandle, 0);
        GLES20.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
                0, mTextureBuffer);

        GLES20.glEnableVertexAttribArray(mTextureCoordinateHandle);



        // Pass in the position information
        //   mCubePositions.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false,
                0,verticesBuffer);

        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Pass in the color information
        // mCubeColors.position(0);
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false,
                0, ColorBuffer);

        GLES20.glEnableVertexAttribArray(mColorHandle);

        // Pass in the normal information
        GLES20.glVertexAttribPointer(mNormalHandle, 3, GLES20.GL_FLOAT, false,
                0, NormalBuffer);

        GLES20.glEnableVertexAttribArray(mNormalHandle);

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        Matrix.multiplyMM(mvpMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

        // Pass in the modelview matrix.
        GLES20.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mvpMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mvpMatrix, 0, mProjectionMatrix, 0, mvpMatrix, 0);


        // Pass in the combined matrix.
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Pass in the light position in eye space.
        GLES20.glUniform3f(mLightPosHandle, mLightPosInModelSpace[0], mLightPosInModelSpace[1], mLightPosInModelSpace[2]);

        // Draw the cube.
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
    }
    // /*
    protected String getVertexShader()
    {
        // Define our per-pixel lighting shader.
        final String perPixelVertexShader =
                "uniform mat4 u_MVPMatrix;\t\t// A constant representing the combined model/view/projection matrix.      \t\t       \n" +
                        "uniform mat4 u_MVMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +
                        "\t\t  \t\t\t\n" +
                        "attribute vec4 a_Position;\t\t// Per-vertex position information we will pass in.   \t\t\t\t\n" +
                        "attribute vec4 a_Color;\t\t\t// Per-vertex color information we will pass in. \t\t\t\t\n" +
                        "attribute vec3 a_Normal;\t\t// Per-vertex normal information we will pass in.      \n" +
                        "attribute vec2 a_TexCoordinate; // Per-vertex texture coordinate information we will pass in. \t\t\n" +
                        "\t\t  \n" +
                        "varying vec3 v_Position;\t\t// This will be passed into the fragment shader.       \t\t\n" +
                        "varying vec4 v_Color;\t\t\t// This will be passed into the fragment shader.          \t\t\n" +
                        "varying vec3 v_Normal;\t\t\t// This will be passed into the fragment shader.  \n" +
                        "varying vec2 v_TexCoordinate;   // This will be passed into the fragment shader.    \t\t\n" +
                        "\t\t  \n" +
                        "// The entry point for our vertex shader.  \n" +
                        "void main()                                                 \t\n" +
                        "{                                                         \n" +
                        "\t// Transform the vertex into eye space. \t\n" +
                        "\tv_Position = vec3(u_MVMatrix * a_Position);            \n" +
                        "\t\t\n" +
                        "\t// Pass through the color.\n" +
                        "\tv_Color = a_Color;\n" +
                        "\t\n" +
                        "\t// Pass through the texture coordinate.\n" +
                        "\tv_TexCoordinate = a_TexCoordinate;                                      \n" +
                        "\t\n" +
                        "\t// Transform the normal's orientation into eye space.\n" +
                        "    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));\n" +
                        "          \n" +
                        "\t// gl_Position is a special variable used to store the final position.\n" +
                        "\t// Multiply the vertex by the matrix to get the final point in normalized screen coordinates.\n" +
                        "\tgl_Position = u_MVPMatrix * a_Position;                       \t\t  \n" +
                        "}                                                          ";

        return perPixelVertexShader;
    }

    protected String getFragmentShader()
    {
        final String perPixelFragmentShader =
                "precision mediump float;       \t// Set the default precision to medium. We don't need as high of a \n" +
                        "\t\t\t\t\t\t\t\t// precision in the fragment shader.\n" +
                        "uniform vec3 u_LightPos;       \t// The position of the light in eye space.\n" +
                        "uniform sampler2D u_Texture;    // The input texture.\n" +
                        "  \n" +
                        "varying vec3 v_Position;\t\t// Interpolated position for this fragment.\n" +
                        "varying vec4 v_Color;          \t// This is the color from the vertex shader interpolated across the \n" +
                        "  \t\t\t\t\t\t\t\t// triangle per fragment.\n" +
                        "varying vec3 v_Normal;         \t// Interpolated normal for this fragment.\n" +
                        "varying vec2 v_TexCoordinate;   // Interpolated texture coordinate per fragment.\n" +
                        "  \n" +
                        "// The entry point for our fragment shader.\n" +
                        "void main()                    \t\t\n" +
                        "{                              \n" +
                        "\t// Will be used for attenuation.\n" +
                        "    float distance = length(u_LightPos - v_Position);                  \n" +
                        "\t\n" +
                        "\t// Get a lighting direction vector from the light to the vertex.\n" +
                        "    vec3 lightVector = normalize(u_LightPos - v_Position);              \t\n" +
                        "\n" +
                        "\t// Calculate the dot product of the light vector and vertex normal. If the normal and light vector are\n" +
                        "\t// pointing in the same direction then it will get max illumination.\n" +
                        "    float diffuse = max(dot(v_Normal, lightVector), 0.0);               \t  \t\t  \t\t\t\t\t\t\t\t\t\t\t\t\t  \n" +
                        "\n" +
                        "\t// Add attenuation. \n" +
                        "    diffuse = diffuse * (1.0 / (1.0 + (0.10 * distance)));\n" +
                        "    \n" +
                        "    // Add ambient lighting\n" +
                        "    diffuse = diffuse + 0.3;  \n" +
                        "\n" +
                        "\t// Multiply the color by the diffuse illumination level and texture value to get final output color.\n" +
                        "    gl_FragColor = (v_Color * diffuse * texture2D(u_Texture, v_TexCoordinate));                                  \t\t\n" +
                        "}                                                                     \t\n" +
                        "\n";

        return perPixelFragmentShader;
    }

// */

    /**
     * Helper function to compile a shader.
     *
     * @param shaderType The shader type.
     * @param shaderSource The shader source code.
     * @return An OpenGL handle to the shader.
     */
    private int compileShader(final int shaderType, final String shaderSource)
    {
        int shaderHandle = GLES20.glCreateShader(shaderType);

        if (shaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(shaderHandle, shaderSource);

            // Compile the shader.
            GLES20.glCompileShader(shaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }

        if (shaderHandle == 0)
        {
            throw new RuntimeException("Error creating shader.");
        }

        return shaderHandle;
    }

    /**
     * Helper function to compile and link a program.
     *
     * @param vertexShaderHandle An OpenGL handle to an already-compiled vertex shader.
     * @param fragmentShaderHandle An OpenGL handle to an already-compiled fragment shader.
     * @param attributes Attributes that need to be bound to the program.
     * @return An OpenGL handle to the program.
     */
    private int createAndLinkProgram(final int vertexShaderHandle, final int fragmentShaderHandle, final String[] attributes)
    {
        int programHandle = GLES20.glCreateProgram();

        if (programHandle != 0)
        {
            // Bind the vertex shader to the program.
            GLES20.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES20.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes
            if (attributes != null)
            {
                final int size = attributes.length;
                for (int i = 0; i < size; i++)
                {
                    GLES20.glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }

            // Link the two shaders together into a program.
            GLES20.glLinkProgram(programHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling program: " + GLES20.glGetProgramInfoLog(programHandle));
                GLES20.glDeleteProgram(programHandle);
                programHandle = 0;
            }
        }

        if (programHandle == 0)
        {
            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }


}
