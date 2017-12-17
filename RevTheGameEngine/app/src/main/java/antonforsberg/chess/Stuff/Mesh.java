package antonforsberg.chess.Stuff;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Anton Forsberg on 2017-07-08.
 */


public class Mesh {

    /** Used for debug logs. */
    private static final String TAG = "Mesh";

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
    private float[] mLightPosInModelSpace = new float[] {2.0f, 2.0f, 0.5f};
    private  FloatBuffer NormalBuffer=null;
    private FloatBuffer verticesBuffer = null;
    private  FloatBuffer ColorBuffer=null;
public Mesh(){
    final String vertexShader = getVertexShader();
    final String fragmentShader = getFullFragmentShader();

    final int vertexShaderHandle = compileShader(GLES20.GL_VERTEX_SHADER, vertexShader);
    final int fragmentShaderHandle = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentShader);

    mProgram = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
            new String[] {"a_Position",  "a_Color", "a_Normal"});

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
                "uniform mat4 u_MVPMatrix;      \n"		// A constant representing the combined model/view/projection matrix.
                        + "uniform mat4 u_MVMatrix;       \n"		// A constant representing the combined model/view matrix.

                        + "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
                        + "attribute vec4 a_Color;        \n"		// Per-vertex color information we will pass in.
                        + "attribute vec3 a_Normal;       \n"		// Per-vertex normal information we will pass in.

                        + "varying vec3 v_Position;       \n"		// This will be passed into the fragment shader.
                        + "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.
                        + "varying vec3 v_Normal;         \n"		// This will be passed into the fragment shader.

                        // The entry point for our vertex shader.
                        + "void main()                                                \n"
                        + "{                                                          \n"
                        // Transform the vertex into eye space.
                        + "   v_Position = vec3(u_MVMatrix * a_Position);             \n"
                        // Pass through the color.
                        + "   v_Color = a_Color;                                      \n"
                        // Transform the normal's orientation into eye space.
                        + "   v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));      \n"
                        // gl_Position is a special variable used to store the final position.
                        // Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
                        + "   gl_Position = u_MVPMatrix * a_Position;                 \n"
                        + "}                                                          \n";

        return perPixelVertexShader;
    }

    protected String getFragmentShader()
    {
        final String perPixelFragmentShader =
                "precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a
                        // precision in the fragment shader.
                        + "uniform vec3 u_LightPos;       \n"	    // The position of the light in eye space.

                        + "varying vec3 v_Position;		\n"		// Interpolated position for this fragment.
                        + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the
                        // triangle per fragment.
                        + "varying vec3 v_Normal;         \n"		// Interpolated normal for this fragment.

                        // The entry point for our fragment shader.
                        + "void main()                    \n"
                        + "{                              \n"
                        // Will be used for attenuation.
                        + "   float distance = length(u_LightPos - v_Position);                  \n"
                        // Get a lighting direction vector from the light to the vertex.
                        + "   vec3 lightVector = normalize(u_LightPos - v_Position);             \n"
                        // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are
                        // pointing in the same direction then it will get max illumination.
                        + "   float diffuse = max(dot(v_Normal, lightVector), 0.1);              \n"
                        // Add attenuation.
                        + "   diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));  \n"
                        // Multiply the color by the diffuse illumination level to get final output color.
                        + "   gl_FragColor = v_Color* diffuse * 2.0f ;                                  \n"
                        + "}                                                                     \n";

        return perPixelFragmentShader;
    }

// */








    protected String getNewVertexShader()
    {
        // Define our per-pixel lighting shader.
        final String perPixelVertexShader =
                "uniform mat4 u_MVPMatrix;      \n"		// A constant representing the combined model/view/projection matrix.
                        + "uniform mat4 u_MVMatrix;       \n"		// A constant representing the combined model/view matrix.

                        + "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
                        + "attribute vec4 a_Color;        \n"		// Per-vertex color information we will pass in.
                        + "attribute vec3 a_Normal;       \n"		// Per-vertex normal information we will pass in.

                        + "varying vec3 v_Position;       \n"		// This will be passed into the fragment shader.
                        + "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.
                        + "varying vec3 v_Normal;         \n"		// This will be passed into the fragment shader.

                        // The entry point for our vertex shader.
                        + "void main()                                                \n"
                        + "{                                                          \n"
                        // Transform the vertex into eye space.
                        + "   v_Position = vec3(u_MVMatrix * a_Position);             \n"
                        // Pass through the color.
                        + "   v_Color = a_Color;                                      \n"
                        // Transform the normal's orientation into eye space.
                        + "   v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));      \n"
                        // gl_Position is a special variable used to store the final position.
                        // Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
                        + "   gl_Position = u_MVPMatrix * a_Position;                 \n"
                        + "}                                                          \n";

        return perPixelVertexShader;
    }

    protected String getNewFragmentShader()
    {
        final String perPixelFragmentShader =
                "precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a
                        // precision in the fragment shader.
                        + "uniform vec3 u_LightPos;       \n"	    // The position of the light in eye space.

                        + "varying vec3 v_Position;		\n"		// Interpolated position for this fragment.
                        + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the
                        // triangle per fragment.
                        + "varying vec3 v_Normal;         \n"		// Interpolated normal for this fragment.

                        // The entry point for our fragment shader.
                        + "void main()                    \n"
                        + "{                              \n"
                        // Will be used for attenuation.
                        + "   float distance = length(u_LightPos - v_Position);                  \n"
                        // Get a lighting direction vector from the light to the vertex.
                        + "   vec3 lightVector = normalize(u_LightPos - v_Position);             \n"
                        // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are
                        // pointing in the same direction then it will get max illumination.
                       // + "   float diffuse = dot(v_Normal, lightVector);              \n"
                        // Add attenuation.
                     //   + "   diffuse = diffuse * ();  \n"
                        + "   float rug =0.3f;  \n"
                        + "   float expL = pow((1.0f-dot(v_Normal, lightVector)),5.0f);  \n"
                       ///+ "   float Fd= 0.5f+2.0f * rug *cos(acos(dot(v_Normal, lightVector))*acos(dot(v_Normal, lightVector))); \n"

                        + "   float expfd = pow((dot(v_Normal, lightVector)),2.0f);  \n"
                        + "   float Fd= 0.5f+2.0f * rug *expfd; \n"

                        // Multiply the color by the diffuse illumination level to get final output color.
                        + "   gl_FragColor = v_Color *1.0f /3.14159265359f * (1.0f+(Fd-1.0f)*(1.0f-expL))*(1.0f+(Fd-1.0f)*(1.0f-expL));                                  \n"
                        + "}                                                                     \n";

        return perPixelFragmentShader;
    }




    protected String getFullFragmentShader()
    {
        final String perPixelFragmentShader =
                "precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a
                        // precision in the fragment shader.
                        + "uniform vec3 u_LightPos;       \n"	    // The position of the light in eye space.

                        + "varying vec3 v_Position;		\n"		// Interpolated position for this fragment.
                        + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the
                        // triangle per fragment.
                        + "varying vec3 v_Normal;         \n"		// Interpolated normal for this fragment.

                        // The entry point for our fragment shader.
                        + "void main()                    \n"
                        + "{                              \n"
                        // Will be used for attenuation.
                        + "   float distance = length(u_LightPos - v_Position);                  \n"
                        // Get a lighting direction vector from the light to the vertex.
                        + "   vec3 lightVector = normalize(u_LightPos - v_Position);             \n"
                        // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are
                        // pointing in the same direction then it will get max illumination.
                        // + "   float diffuse = dot(v_Normal, lightVector);              \n"
                        // Add attenuation.
                        //   + "   diffuse = diffuse * ();  \n"
                        + "   float rug =0.3f;  \n"



//                        +"float rand(){ " +
                      +  "float kone=(v_Position.x+v_Position.y)*(v_Position.x+v_Position.y+1.0f)/2.0f+v_Position.y;  \n"
                       + "float ktwo=(v_Position.y+v_Position.z)*(v_Position.y+v_Position.z+1.0f)/2.0f+v_Position.z; \n"
 //                       "return (fract(sin(dot(vec2(kone,ktwo) ,vec2(12.9898,78.233))) * 43758.5453));}\n"
  //                      +"float erandom= rand();\n"

                        +"float erandom=fract(sin(dot(vec2(kone,ktwo) ,vec2(12.9898,78.233))) * 43758.5453);  \n"
                        +"float Hcos =sqrt((1.0f-erandom)/(1.0f+(rug*rug-1.0f)*erandom));  \n"

                    //    +"\n"
                    //    +" if(acos(Hcos)>acos(dot(v_Normal, lightVector))){}  \n"
                     //   +"\n"
                        +"float D= rug*rug*rug*rug/(3.14159265359f*(1.0f+(rug*rug*rug*rug-1.0f))*(1.0f+(rug*rug*rug*rug-1.0f)));\n"
                        +"float ior=0.04f;\n"
                        +"float F= ior+(1.0f-ior)*pow(1.0f-(dot(v_Normal, lightVector)),5.0f);\n"




                        //cook
                            +"float spec=0.05f; \n"
                        +"float defuse = 1.0f-spec; \n"
                        +"vec4 Seccol=vec4(0.5,0.5,0.5,1) ;\n"

                        +"vec3 campos=vec3(0.0f,0.0f,-0.5f);\n"
                        //ändra
                        +"vec3 V=normalize(campos - v_Position);\n"
                        +"\n"
                        +"float ndotv =dot(v_Normal,V);\n"
                        +"vec3 H=normalize(V+lightVector);\n"
                        +"float ndoth=dot(v_Normal,H);\n"
                        +"float ndotl=dot(v_Normal,lightVector);\n"
                        +"float vdoth=dot(V,H);\n"
                        +"float alpha =acos(ndoth);\n"
                        +"float ldoth=dot(lightVector,H);\n"

                        //byt rug till rug*rug
                        +"float Dtwo = rug*rug/(3.14159265359f* (1.0f+(rug*rug-1.0f)*ndoth*ndoth)*(1.0f+(rug*rug-1.0f)*ndoth*ndoth));  \n"
                        +"float Dmask = rug*rug/(3.14159265359f* (1.0f+(rug*rug-1.0f)*(ldoth*ldoth))*(1.0f+(rug*rug-1.0f)*(ldoth*ldoth)));  \n"
                     //   +"  float mask =(1.0f+(rug*rug-1.0f)*ndoth*ndoth)*(1.0f+(rug*rug-1.0f)*ndoth*ndoth); \n"

                        + "   float expL = pow((1.0f-dot(v_Normal, lightVector)),5.0f);  \n"
                        + "   float expV = pow((1.0f-dot(v_Normal, V)),5.0f);  \n"
                        ///+ "   float Fd= 0.5f+2.0f * rug *cos(acos(dot(v_Normal, lightVector))*acos(dot(v_Normal, lightVector))); \n"

                        + "   float expfd = pow((dot(v_Normal, lightVector)),2.0f);  \n"
                        + "   float Fd= 0.5f+2.0f * rug *expfd; \n"


                        +"float Fcook= (1.0f+(Fd-1.0f)*(1.0f-expL))*(1.0f+(Fd-1.0f)*(1.0f-expV)); \n"
                        +"float Dcook =100.0f*exp(-1.0f*(alpha*alpha/(rug*rug))); \n"
                        +"float G =min((2.0f*ndoth*ndotv/vdoth),(2.0f*ndoth*ndotl/vdoth)); \n"
                        +"G= min(1.0f,G);\n"
                        +"float cook=Dtwo*Fcook*G/(3.14159265359f*ndotv); \n"
                        +"vec4 Scolor = vec4(cook/3.14159265359f*spec,cook/3.14159265359f*spec,cook/3.14159265359f*spec,1);\n"
                        +";\n"
                        +"\n"
                        +"\n"
                      //  +"gl_FragColor =Seccol*Scolor;\n"



                        + "   float Fz= 0.5f+2.0f * 1.0f *expfd; \n"
                   //     +Dtwo/(200.0f);
        // Multiply the color by the diffuse illumination level to get final output color.
                      + "   gl_FragColor = v_Color *2.0f*(0.2/Dmask-0.3) /3.14159265359f * (1.0f+(Fd-1.0f)*(1.0f-expL))*(1.0f+(Fd-1.0f)*(1.0f-expV))*defuse+Scolor;        \n"
                //     + "   gl_FragColor =  v_Color *min(Dcook*G,1.0f);"
                     + "   gl_FragColor =  v_Color*(1.0-Dmask)*defuse+Scolor ;"
                //        + "   gl_FragColor =  v_Color*Dcook ;"
                     //  + "   gl_FragColor = v_Color*dot(v_Normal, lightVector)*(1.0f+(Fz-1.0f)*(1.0f-expL))*(1.0f+(Fz-1.0f)*(1.0f-expL)) ; \n"
                      // +"gl_FragColor = v_Color/pow((1.0f+100.0f*dot(v_Normal, lightVector)),1.5f);\n"
                     //  +"gl_FragColor = v_Color*pow(dot(v_Normal, lightVector),1.0f-rug);\n"
                        + "}                                                                     \n";

        return perPixelFragmentShader;
    }



    protected String getVVertexShader()
    {
        // TODO: Explain why we normalize the vectors, explain some of the vector math behind it all. Explain what is eye space.
        final String vertexShader =
                "uniform mat4 u_MVPMatrix;      \n"		// A constant representing the combined model/view/projection matrix.
                        + "uniform mat4 u_MVMatrix;       \n"		// A constant representing the combined model/view matrix.
                        + "uniform vec3 u_LightPos;       \n"	    // The position of the light in eye space.

                        + "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
                        + "attribute vec4 a_Color;        \n"		// Per-vertex color information we will pass in.
                        + "attribute vec3 a_Normal;       \n"		// Per-vertex normal information we will pass in.

                        + "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.

                        + "void main()                    \n" 	// The entry point for our vertex shader.
                        + "{                              \n"
                        // Transform the vertex into eye space.
                        + "   vec3 modelViewVertex = vec3(u_MVMatrix * a_Position);              \n"
                        // Transform the normal's orientation into eye space.
                        + "   vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));     \n"
                        // Will be used for attenuation.
                        + "   float distance = length(u_LightPos - modelViewVertex);             \n"
                        // Get a lighting direction vector from the light to the vertex.
                        + "   vec3 lightVector = normalize(u_LightPos - modelViewVertex);        \n"
                        // Calculate the dot product of the light vector and vertex normal. If the normal and light vector are
                        // pointing in the same direction then it will get max illumination.
                        + "   float diffuse = max(dot(modelViewNormal, lightVector), 0.1);       \n"
                        // Attenuate the light based on distance.
                        + "   diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));  \n"
                        // Multiply the color by the illumination level. It will be interpolated across the triangle.
                        + "   v_Color = a_Color * diffuse;                                       \n"
                        // gl_Position is a special variable used to store the final position.
                        // Multiply the vertex by the matrix to get the final point in normalized screen coordinates.
                        + "   gl_Position = u_MVPMatrix * a_Position;                            \n"
                        + "}                                                                     \n";

        return vertexShader;
    }

    protected String getVFragmentShader()
    {
        final String fragmentShader =
                "precision mediump float;       \n"		// Set the default precision to medium. We don't need as high of a
                        // precision in the fragment shader.
                        + "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the
                        // triangle per fragment.
                        + "void main()                    \n"		// The entry point for our fragment shader.
                        + "{                              \n"
                        + "   gl_FragColor = v_Color;     \n"		// Pass the color directly through the pipeline.
                        + "}                              \n";

        return fragmentShader;
    }

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

