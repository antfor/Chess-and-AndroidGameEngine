package antonforsberg.chess.comMesh;

/**
 * Created by Anton Forsberg on 2017-07-23.
 */



        import android.content.Context;
        import android.opengl.GLES30;
        import android.opengl.Matrix;
        import android.util.Log;


        import java.nio.ByteBuffer;
        import java.nio.ByteOrder;
        import java.nio.FloatBuffer;



public class FinalMesh {

    /** Used for debug logs. */
    private static final String TAG = "FinalMesh";

    private int vertexCount=-1;

    private int mProgram;

    /** This will be used to pass in the transformation matrix. */
    private int mMVPMatrixHandle;

    /** This will be used to pass in the modelview matrix. */
    private int mMVMatrixHandle;

    private int mMMatrixHandle;

    /** This will be used to pass in the light position. */
    private int mLightPosHandle;

    private int mCamPosHandle;

    /** This will be used to pass in model position information. */
    private int mPositionHandle;

    /** This will be used to pass in model color information. */
    private int mColorHandle;

    private int mTextureDataHandle;

    private int mGlossDataHandle;

    private int mNortexDataHandle;

    private int moclusionDataHandle;

    private int mSkytexDataHandle;

    private int mTextureCoordinateHandle;

    private int mTextureUniformHandle;

    private int mMettexDataHandle;

    private int mskyboxHandle;

    private  int mprefilterHandle;

    private  int mIblLutHandle;

    private  int mIrradiancHandle;


    /** This will be used to pass in model normal information. */
    private int mNormalHandle;
    private float[] mLightPosInModelSpace = new float[] {2.0f, 2.0f, 0.5f};
    private float[] camrapos = new float[] {0.0f, 0.0f, -0.5f};
    private  FloatBuffer NormalBuffer=null;
    private FloatBuffer verticesBuffer = null;
    private  FloatBuffer ColorBuffer=null;
    private FloatBuffer mTextureBuffer=null;

    public FinalMesh(){
    /*    final String vertexShader = Normal_and_ColorVS();
        final String fragmentShader = Normal_and_ColorFS();
     // final String vertexShader = get3VertexShader();
      // final String fragmentShader = get3TexfullFragmentShader();
       // final String vertexShader=getcubVS();
      //  final String fragmentShader=getcubFS();

        final int vertexShaderHandle = compileShader(GLES30.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = compileShader(GLES30.GL_FRAGMENT_SHADER, fragmentShader);

        mProgram = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
               new String[] {"a_Position",  "a_Color", "a_Normal", "a_TexCoordinate"});
*/


    }
    public void setShader(String vertexShader, String fragmentShader){
        switch (vertexShader){
            case "sky":  vertexShader=getskyVS(); break;
            case "cub": vertexShader=getcubVS();  break;
            case "3.0":vertexShader=  get3VertexShader(); break;
            case "ibl":vertexShader=  get3IBLVertexShader(); break;
            case "earth":  vertexShader=getTexVertexShader(); break;
            case "metal":   break;
            case "button": vertexShader=getButtonVS(); break;
            case "NorCol":vertexShader=Normal_and_ColorVS();break;
            default: System.out.println("no vertexshader whit that name dose not exist"); break;
        }
        switch (fragmentShader){
            case "sky": fragmentShader=getskyFS();  break;
            case "cub":  fragmentShader=getcubFS(); break;
            case "3.0": fragmentShader=get3TexfullFragmentShader();  break;
            case "ibl":fragmentShader=  get3IBLFragmentShader(); break;
            case "earth":   fragmentShader = getTexfullFragmentShader(); break;
            case "metal":   break;
            case "button": fragmentShader=getButtonFS(); break;
            case "NorCol":fragmentShader=Normal_and_ColorFS();break;
            case "opace":  fragmentShader=opaceFS();break;
            default: System.out.println("no fragmentshader whit that name dose not exist"); break;
        }

        final int vertexShaderHandle = compileShader(GLES30.GL_VERTEX_SHADER, vertexShader);
        final int fragmentShaderHandle = compileShader(GLES30.GL_FRAGMENT_SHADER, fragmentShader);

        mProgram = createAndLinkProgram(vertexShaderHandle, fragmentShaderHandle,
                new String[] {"a_Position",  "a_Color", "a_Normal", "a_TexCoordinate"});
    }


    private  boolean useskybox=false;
    //private int skypx,skynx;
    public void loadskybox (int sky){// Load the texture

        mskyboxHandle=sky;
        useskybox=true;

    }
    private boolean useirradianc=false;
    public void loadirradianceMap (int irradianc){// Load the texture

        mIrradiancHandle=irradianc;
        useirradianc=true;

    }
    private boolean useprefilte=false;
    public void loadprefilterMap(int prefilte,int LUT){// Load the texture

        mprefilterHandle=prefilte;
        useprefilte=true;
        mIblLutHandle=LUT;
    }

private boolean usetex=false;

    public void loadtexture (int tex){// Load the texture
        mTextureDataHandle = tex;

        usetex=true;
    }

   private boolean useglosstex=false;

    public void loadGlosstexture (int tex){// Load the texture
        mGlossDataHandle= tex;

        useglosstex=true;
    }
    boolean usenormaltex=false;

    public void loadnormaltexture (int tex){// Load the texture
        mNortexDataHandle= tex;

        usenormaltex=true;
    }
    boolean useoclutex=false;

    public void loadoclusiontexture (int tex){// Load the texture
        moclusionDataHandle= tex;

        useoclutex=true;
    }

    private boolean useskyhdr=false;
    public void loadskyhdr (int tex){// Load the texture
        mSkytexDataHandle= tex;

        useskyhdr=true;
    }

    private boolean usemetal=false;
    public void loadmetal (int tex){// Load the texture
        mMettexDataHandle= tex;

        usemetal=true;
    }

    boolean usetexcord=false;
    public void setTextureCoordinates(float[] textureCoords) {
        // float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(
                textureCoords.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mTextureBuffer = byteBuf.asFloatBuffer();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);
        usetexcord=true;
    }


    public void setVertices(float[] vertices) {
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        verticesBuffer = vbb.asFloatBuffer();
        verticesBuffer.put(vertices);
        verticesBuffer.position(0);
        vertexCount=vertices.length/3;
    }
    private boolean usenormalbuffer=false;
    public void setNormals(float[] Normals) {
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(Normals.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        NormalBuffer = vbb.asFloatBuffer();
        NormalBuffer.put(Normals);
        NormalBuffer.position(0);
        usenormalbuffer=true;
    }
    private boolean usecolur=false;
    public void setColor(float[] Color) {
        // a float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer vbb = ByteBuffer.allocateDirect(Color.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        ColorBuffer = vbb.asFloatBuffer();
        ColorBuffer.put(Color);
        ColorBuffer.position(0);
        usecolur=true;
    }



    public void draw(float[] mvpMatrix,float[] mProjectionMatrix,float[] mViewMatrix,float[] mModelMatrix)
    {

        GLES30.glUseProgram(mProgram);
        // Set program handles for cube drawing.
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "u_MVPMatrix");
        mMVMatrixHandle = GLES30.glGetUniformLocation(mProgram, "u_MVMatrix");
        mMMatrixHandle=GLES30.glGetUniformLocation(mProgram, "u_MMatrix");
        mLightPosHandle = GLES30.glGetUniformLocation(mProgram, "u_LightPos");
       mCamPosHandle = GLES30.glGetUniformLocation(mProgram, "u_camPos");
        mPositionHandle = GLES30.glGetAttribLocation(mProgram, "a_Position");
        mColorHandle = GLES30.glGetAttribLocation(mProgram, "a_Color");
        mNormalHandle = GLES30.glGetAttribLocation(mProgram, "a_Normal");

        mTextureUniformHandle = GLES30.glGetUniformLocation(mProgram, "u_Texture");
        mTextureCoordinateHandle = GLES30.glGetAttribLocation(mProgram, "a_TexCoordinate");
        mTextureUniformHandle  = GLES30.glGetAttribLocation(mProgram, "skybox");
        if(usetex){
            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
            GLES30.glUniform1i(mTextureUniformHandle, 0);
            // Set the active texture unit to texture unit 0.
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0);

            //float aniso = 0.0f;

            // Bind the texture to this unit.
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureDataHandle);

        }

        if(usetexcord){
            GLES30.glVertexAttribPointer(mTextureCoordinateHandle, 2, GLES30.GL_FLOAT, false,
                    0, mTextureBuffer);

            GLES30.glEnableVertexAttribArray(mTextureCoordinateHandle);
        }
        if(useglosstex){
            mTextureUniformHandle = GLES30.glGetUniformLocation(mProgram, "u_glosstex");
            // Set the active texture unit to texture unit 0.
            GLES30.glUniform1i(mTextureUniformHandle, 1);
            GLES30.glActiveTexture(GLES30.GL_TEXTURE1);



            // Bind the texture to this unit.
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mGlossDataHandle);

            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.


        }


        if(useoclutex){
            // Set the active texture unit to texture unit 0.
            mTextureUniformHandle = GLES30.glGetUniformLocation(mProgram, "u_oclusiontex");
            GLES30.glUniform1i(mTextureUniformHandle, 2);

            GLES30.glActiveTexture(GLES30.GL_TEXTURE2);

            // Bind the texture to this unit.
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, moclusionDataHandle);

            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.


        }
        if(usenormaltex){
            mTextureUniformHandle = GLES30.glGetUniformLocation(mProgram, "u_normaltex");
            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
            GLES30.glUniform1i(mTextureUniformHandle, 3);
            // Set the active texture unit to texture unit 0.
            GLES30.glActiveTexture(GLES30.GL_TEXTURE3);

            // Bind the texture to this unit.
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mNortexDataHandle);

        }


        if(usemetal){
            mTextureUniformHandle = GLES30.glGetUniformLocation(mProgram, "u_metallicMap");
            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
            GLES30.glUniform1i(mTextureUniformHandle, 5);
            // Set the active texture unit to texture unit 0.
            GLES30.glActiveTexture(GLES30.GL_TEXTURE5);

            // Bind the texture to this unit.
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mMettexDataHandle);

        }

        if(useskybox){

            mTextureUniformHandle= GLES30.glGetUniformLocation(mProgram, "skybox");
            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.

            // Set the active texture unit to texture unit 0.
            GLES30.glUniform1i(mTextureUniformHandle, 6);
            GLES30.glActiveTexture(GLES30.GL_TEXTURE6);

            GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP,mskyboxHandle);



        }
        if(useirradianc){

            mTextureUniformHandle= GLES30.glGetUniformLocation(mProgram, "irradianceMap");
            GLES30.glUniform1i(mTextureUniformHandle, 7);
            GLES30.glActiveTexture(GLES30.GL_TEXTURE7);

            GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP,mIrradiancHandle);



        }
        if(useprefilte){

            mTextureUniformHandle= GLES30.glGetUniformLocation(mProgram, "prefilterMap");

            GLES30.glUniform1i(mTextureUniformHandle, 8);

            GLES30.glActiveTexture(GLES30.GL_TEXTURE8);

            GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP,mprefilterHandle);

            mTextureUniformHandle= GLES30.glGetUniformLocation(mProgram, "brdfLUT");
            GLES30.glUniform1i(mTextureUniformHandle, 4);

            GLES30.glActiveTexture(GLES30.GL_TEXTURE4);

            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mIblLutHandle);

        }


        // Pass in the position information
        //   mCubePositions.position(0);
        GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES30.GL_FLOAT, false,
                0,verticesBuffer);

        GLES30.glEnableVertexAttribArray(mPositionHandle);


        if(usecolur) {
            // Pass in the color information
            // mCubeColors.position(0);
            GLES30.glVertexAttribPointer(mColorHandle, 4, GLES30.GL_FLOAT, false,
                    0, ColorBuffer);

            GLES30.glEnableVertexAttribArray(mColorHandle);
        }
        if(usenormalbuffer) {
            // Pass in the normal information
            GLES30.glVertexAttribPointer(mNormalHandle, 3, GLES30.GL_FLOAT, false,
                    0, NormalBuffer);

            GLES30.glEnableVertexAttribArray(mNormalHandle);
        }

        // This multiplies the view matrix by the model matrix, and stores the result in the MVP matrix
        // (which currently contains model * view).
        GLES30.glUniformMatrix4fv(mMMatrixHandle, 1, false, mModelMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);

        // Pass in the modelview matrix.
        GLES30.glUniformMatrix4fv(mMVMatrixHandle, 1, false, mvpMatrix, 0);

        // This multiplies the modelview matrix by the projection matrix, and stores the result in the MVP matrix
        // (which now contains model * view * projection).
        Matrix.multiplyMM(mvpMatrix, 0, mProjectionMatrix, 0, mvpMatrix, 0);


        // Pass in the combined matrix.
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // Pass in the light position in eye space.
        GLES30.glUniform3f(mLightPosHandle, mLightPosInModelSpace[0], mLightPosInModelSpace[1], mLightPosInModelSpace[2]);

        GLES30.glUniform3f(mCamPosHandle,  camrapos[0],  camrapos[1],  camrapos[2]);

        // Draw the cube.
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);

       if(usenormalbuffer){ GLES30.glDisableVertexAttribArray(mNormalHandle);}
        if(usecolur){  GLES30.glDisableVertexAttribArray(mColorHandle);}
        GLES30.glDisableVertexAttribArray(mPositionHandle);
        if(usetex){ GLES30.glDisableVertexAttribArray(mTextureCoordinateHandle);}
      //  Log.d("after","fk");
    }


    private String get3IBLVertexShader()
    {
        return      "#version 300 es"+
                "\n uniform mat4 u_MVPMatrix;\t\t// A constant representing the combined model/view/projection matrix.      \t\t       \n" +
                "uniform mat4 u_MVMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +
                "uniform mat4 u_MMatrix;"+
                "\t\t  \t\t\t\n" +
                "in vec4 a_Position;\t\t// Per-vertex position information we will pass in.   \t\t\t\t\n" +
                "in vec4 a_Color;\t\t\t// Per-vertex color information we will pass in. \t\t\t\t\n" +
                "in vec3 a_Normal;\t\t// Per-vertex normal information we will pass in.      \n" +
                "in vec2 a_TexCoordinate; // Per-vertex texture coordinate information we will pass in. \t\t\n" +
                "\t\t  \n" +
                "out vec3 v_Position;\t\t// This will be passed into the fragment shader.       \t\t\n" +
                "out vec4 v_Color;\t\t\t// This will be passed into the fragment shader.          \t\t\n" +
                "out vec3 v_Normal;\t\t\t// This will be passed into the fragment shader.  \n" +
                "out vec2 v_TexCoordinate;   // This will be passed into the fragment shader.    \t\t\n" +
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
            //    "    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));\n" +
                "   v_Normal =vec3(mat3(transpose(inverse(u_MMatrix))) *a_Normal);  "+

                "\t// gl_Position is a special variable used to store the final position.\n" +
                "\t// Multiply the vertex by the matrix to get the final point in normalized screen coordinates.\n" +
                "\tgl_Position = u_MVPMatrix * a_Position;                       \t\t  \n" +
                "}                                                          ";
    }

    private String get3IBLFragmentShader()
    {
     //   final String perPixelFragmentShader =
        return        "#version 300 es"+
                        " \n precision highp float;     " +
                        "uniform vec3 u_camPos;       " +
                        "uniform vec3 u_LightPos;       " +
                        "uniform sampler2D u_Texture;    " +
                        "uniform sampler2D u_glosstex;    " +
                        "uniform sampler2D u_oclusiontex;    " +
                        "uniform sampler2D u_normaltex;    " +
                        "uniform sampler2D u_skyhdr;    " +
                        " uniform sampler2D u_metallicMap; \n" +
                        // IBL
                        "uniform samplerCube irradianceMap;\n" +
                        "uniform samplerCube prefilterMap;\n" +
                        "uniform sampler2D brdfLUT;"+


                        " out vec4 FragColor; \n" +
                        "in vec3 v_Position;" +
                        "in vec4 v_Color;         " +

                        "in vec3 v_Normal;         \n" +
                        "in vec2 v_TexCoordinate;   \n" +
                        " const float PI = 3.14159265359; \n" +
                        "  \n" +
                        //   "float DistributionGGX(vec3 N, vec3 H, float roughness);\n" +
                        //  "float GeometrySchlickGGX(float NdotV, float roughness);\n" +
                        //  "float GeometrySmith(vec3 N, vec3 V, vec3 L, float roughness);\n" +
                        //  "vec3 fresnelSchlickRoughness(float cosTheta, vec3 F0, float roughness); \n" +

                        "void main()                    \n" +
                        "{                              \n" +

                    /*    //  " vec3 albedo     = pow(texture(u_Texture, v_TexCoordinate).rgb, 2.2f);\n" +
                        "vec3 albedo;"+
                        "albedo.x=pow(texture(u_Texture, v_TexCoordinate).r, 2.2f);"+
                        "albedo.y=pow(texture(u_Texture, v_TexCoordinate).g, 2.2f);"+
                        "albedo.z=pow(texture(u_Texture, v_TexCoordinate).b, 2.2f);"+

                        // "    vec3 normal     = getNormalFromNormalMap();\n" +
                        "    float metallic  = texture(u_metallicMap, v_TexCoordinate).r; \n" +
                        "    float roughness = texture(u_glosstex, v_TexCoordinate).r; \n" +
                        //"    float ao        = texture(aoMap, TexCoords).r;"+
                        "    float ao        = 1.0;"+
                        // "albedo=vec3(1.0,1.0,1.0);"+
                        // "roughness=0.2f;"+
                        // "metallic=0.0f;"+
                        ""+
                        ""+
                        ""+

*/
                        // "vec3 N = normalize(Normal); \n" +
                        " vec3 tangentNormal = texture(u_normaltex, v_TexCoordinate).xyz * 2.0 - 1.0;\n" +
                        "\n" +
                        "    vec3 Q1  = dFdx(v_Position);\n" +
                        "    vec3 Q2  = dFdy(v_Position);\n" +
                        "    vec2 st1 = dFdx(v_TexCoordinate);\n" +
                        "    vec2 st2 = dFdy(v_TexCoordinate);\n" +
                        "\n" +
                        "    vec3 N   = normalize(v_Normal);\n" +
                        "    vec3 T  = normalize(Q1*st2.t - Q2*st1.t);\n" +

                   //     "T=vec3(mat3(transpose(inverse(u_MMatrix))) *T)"+
                          "T=normalize(T-N*dot(N,T));"+

                        "    vec3 B  = -normalize(cross(N, T));\n" +
                    //    "T=normalize(T-N*dot(N,T));"+
                      //  "if(dot(cross(N,T),B)<0.0f){T=T*-1.0;}"+

                        "    mat3 TBN = mat3(T, B, N);"+
                        " N = normalize(TBN*tangentNormal); \n" +
                        //  "N=normalize(v_Normal+endnorvec);"+
                        "    vec3 V = normalize(u_camPos - v_Position); \n" +
/*
                     //   "vec3 R = reflect(-V, N);\n" +

                        "    vec3 F0 = vec3(0.04); \n" +
                        "    F0 = mix(F0, albedo, metallic); \n" +
                        "\t           \n" +
                        "   " +
                        "    vec3 Lo = vec3(0.0);"+



                        " " +
                        "        vec3 L = normalize(u_LightPos - v_Position); \n" +
                        "        vec3 H = normalize(V + L); \n" +
                        "        float distance    = length(u_LightPos - v_Position); \n" +

                        "        float attenuation = 1.0 / (distance * distance); \n" +
                        "vec3 lightColors =vec3(23.47, 21.31, 20.79); "+
                        "        vec3 radiance     = lightColors * attenuation;        \n" +
                        "        \n" +
                        "       " +
                        //    "        float NDF = DistributionGGX(N, H, roughness);        \n" +
                        //   "        float G   = GeometrySmith(N, V, L, roughness);      \n" +
                        //   "        vec3 F    = fresnelSchlick(max(dot(H, V), 0.0), F0);       \n" +




                        "  float a      = roughness*roughness; \n" +
                        "    float a2     = a*a; \n" +
                        "    float NdotH  = max(dot(N, H), 0.0); \n" +
                        "    float NdotH2 = NdotH*NdotH; \n" +
                        "\t\n" +
                        "    float nom   = a2; \n" +
                        "    float denom = (NdotH2 * (a2 - 1.0) + 1.0); \n" +
                        "    denom = PI * denom * denom;"+
                        "   float NDF=nom/denom;     \n" +

                        " float r = (roughness + 1.0); \n" +
                        "    float k = (r*r) / 8.0; \n" +
                        "\n" +
                        "     nom   = max(dot(N, V), 0.0); \n" +
                        "     denom = max(dot(N, V), 0.0)* (1.0 - k) + k;"+
                        "    float G= nom/denom;    \n" +

                        "   \n" +
                        "     nom   = max(dot(N, L), 0.0); \n" +
                        "     denom = max(dot(N, L), 0.0) * (1.0 - k) + k;"+
                        "G*=nom/denom;\n" +
                        "\n" +

                        "    vec3 F=F0 + (1.0 - F0) * pow(1.0 - max(dot(H, V), 0.0), 5.0);    \n" +

                        "        vec3 kS = F; \n" +
                        "        vec3 kD = vec3(1.0) - kS; \n" +
                        "        kD *= 1.0 - metallic;\t  \n" +
                        "        \n" +
                        "        vec3 nominator    = NDF * G * F; \n" +
                        "        float denominator = 4.0 * max(dot(N, V), 0.0) * max(dot(N, L), 0.0) + 0.001; \n" +
                        "        vec3 specular     = nominator / denominator; \n" +
                        "            \n" +
                        "        // add to outgoing radiance Lo \n" +
                        "        float NdotL = max(dot(N, L), 0.0);                \n" +
                        "        Lo += (kD * albedo / PI + specular) * radiance * NdotL; "+



                        "F=F0 + (max(vec3(1.0 - roughness), F0) - F0) * pow(1.0 - max(dot(N, V), 0.0), 5.0);\n" +
                        " kS = F;\n" +
                        " kD = 1.0 - kS;\n" +
                        " kD *= 1.0 - metallic;\t  \n" +
                        "    \n" +
                        "   vec3 irradiance = texture(irradianceMap, N).rgb;\n" +
                        " vec3 diffuse      = irradiance * albedo;\n" +
                        "    \n" +
                        "    // sample both the pre-filter map and the BRDF lut and combine them together as per the Split-Sum approximation to get the IBL specular part.\n" +
            */           //"    const float MAX_REFLECTION_LOD = 4.0;\n" +
                "   vec3 Ighg = normalize(v_Position - u_camPos);\n" +
                        "vec3 R = reflect( Ighg, N);\n" +
                        "    vec3 prefilteredColor = texture(prefilterMap, N).rgb;    \n" +
             /*         //  "    vec3 prefilteredColor = texture(prefilterMap, R).rgb;    \n" +
                        "    vec2 brdf  = texture(brdfLUT, vec2(max(dot(N, V), 0.0), roughness)).rg;\n" +
                        "    specular = prefilteredColor * (F * brdf.x + brdf.y);\n" +
                        "\n" +
                        "    vec3 ambient = (kD * diffuse + specular) * ao;\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +


                        "    vec3 color = ambient + Lo; \n" +
                        "\n" +
                      //  "    color = color / (color + vec3(1.0)); \n" +
                        "    color = pow(color, vec3(1.0/2.2));  \n" +
              */          "   \n" +
                        "    FragColor = vec4(prefilteredColor, 1.0);"+
                       //    "    FragColor = vec4(prefilteredColor,1.0);"+
                        "}                                                                     \t\n" +
                        "\n";

    //    return perPixelFragmentShader;
    }

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------


    private String getskyVS(){
        final String perPixelVertexShader ="#version 300 es"+
                " \n layout (location = 0) in vec4 a_Position;   \n" +
                "\n uniform mat4 u_MVPMatrix;\t\t// A constant representing the combined model/view/projection matrix.      \t\t       \n" +
                "uniform mat4 u_MVMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +
                "uniform mat4 u_MMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +

                "in vec3 a_Normal;     \n" +
                "out vec3 v_Normal;       \n" +
                "out vec3  v_Position;" +

                "void main() {  \n"+
                //   "   gl_Position    = u_MVPMatrix * a_Position;  \n "+
                "\n"+
                "   v_Normal =vec3(mat3(transpose(inverse(u_MMatrix))) *a_Normal);  "+

                "v_Position = vec3( u_MVMatrix *a_Position); \t     "+
                "   gl_Position    = u_MVMatrix" +
                " * a_Position;  } ";
        //    "   v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));  "+
        //   "\tv_Position = vec3(a_Position);        }    \n" ;

        //  "    LightIntensity = max(dot(normalize(LightPos - EyeDir), Normal), 0.0); }";
        return perPixelVertexShader;
    }

    private String getskyFS(){
        final String perPixelVertexShader ="#version 300 es"+
                "\n precision highp float;                            \n" +
                "in vec3 v_Normal;                              \n" +
                "in vec3 v_Position;                              \n" +
                "uniform vec3 u_camPos;       " +
                "uniform samplerCube skybox; " +

                " out vec4 FragColor; \n" +
                "void main() {     \n" +
           //     "   float ratio = 1.00 / 1.52;                                   \n" +
            //    "   vec3 I = normalize(v_Position - u_camPos);\n" +
                //    "    vec3 R =  refract(I, normalize(v_Normal),ratio);\n" +  //USE FOR GLASS (GENOMSKINLIGA OBJECT)
              //  "    vec3 R = reflect(I, normalize(v_Normal));\n" +
                "    FragColor = texture(skybox,v_Position); \n" +
                //  "FragColor=texture(skybox, v_Position);"+
                "}"+
                "\n";
        return perPixelVertexShader;
    }





    private String getcubVS(){
        final String perPixelVertexShader ="#version 300 es"+
                "\n precision highp float;                            \n" +
                "\n uniform mat4 u_MVPMatrix;\t\t// A constant representing the combined model/view/projection matrix.      \t\t       \n" +
                "uniform mat4 u_MVMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +
                "uniform mat4 u_MMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +
                "in vec4 a_Position;   \n" +
                "in vec3 a_Normal;     \n" +
                "in vec2 a_TexCoordinate;   \n" +
                "out vec2 v_TexCoordinate;   \n" +
                "out vec3 v_Normal;       \n" +
                "out vec3  v_Position;" +
                ""+

                "void main() {  \n"+
                //   "   gl_Position    = u_MVPMatrix * a_Position;  \n "+
                "\n"+
                "   v_Normal =vec3(mat3(transpose(inverse(u_MMatrix))) *a_Normal);  "+

                "v_Position = vec3(u_MVMatrix * a_Position); \t     "+
                "\n"+
                "v_TexCoordinate= a_TexCoordinate;\n"+
                "\n"+
                "\n"+
                "   gl_Position    = u_MVPMatrix * a_Position;  } ";
        //    "   v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));  "+
        //   "\tv_Position = vec3(a_Position);        }    \n" ;

        //  "    LightIntensity = max(dot(normalize(LightPos - EyeDir), Normal), 0.0); }";
        return perPixelVertexShader;
    }



    private String getcubFS(){
        final String perPixelVertexShader ="#version 300 es"+
                "\n precision highp float;                            \n" +
                "in vec3 v_Normal;                              \n" +
                "in vec3 v_Position;                              \n" +
                "uniform vec3 u_camPos;       " +
                "uniform samplerCube skybox; " +
                "uniform samplerCube prefilterMap;\n" +
                "uniform sampler2D u_normaltex;    " +
                "in vec2 v_TexCoordinate;   \n" +
                "uniform mat4 u_MMatrix;"+

                " out vec4 FragColor; \n" +
                "void main() {     \n" +
                "   float ratio = 1.00 / 1.52;                                   \n" +
                "   vec3 I = normalize(v_Position - u_camPos);\n" +



                " vec3 tangentNormal = texture(u_normaltex, v_TexCoordinate).xyz * 2.0 - 1.0;\n" +
                "\n" +
                "    vec3 Q1  = dFdx(v_Position);\n" +
                "    vec3 Q2  = dFdy(v_Position);\n" +
                "    vec2 st1 = dFdx(v_TexCoordinate);\n" +
                "    vec2 st2 = dFdy(v_TexCoordinate);\n" +
                "\n" +
                "    vec3 N   = normalize(v_Normal);\n" +
                "    vec3 T  = normalize(Q1*st2.t - Q2*st1.t);\n" +
              //  "T= normalize(vec3(mat3(transpose(inverse(u_MMatrix))) *T));"+

                "T=normalize(T-N*dot(N,T));"+

                "    vec3 B  = -normalize(cross(N, T));\n" +
              //  "T=normalize(T-N*dot(N,T));"+
               //"if(dot(cross(N,T),B)<0.0f){T=T*-1.0;}"+

                "    mat3 TBN = mat3(T, B, N);"+
                " N = normalize(TBN*tangentNormal); \n" +

               /*
                "vec4 normapcol=texture(u_normaltex, v_TexCoordinate);   \n"+

                "vec3 endnorvec=vec3(1.0,1.0,1.0); \n"+
                " endnorvec.x= (normapcol.x*2.0f-1.0f);  \n" +
                " endnorvec.y= 1.0*(normapcol.y*2.0f-1.0f);  \n" +
                " endnorvec.z= (normapcol.z*-2.0f+1.0f);  \n" +
                " vec3 Normal=normalize(v_Normal*endnorvec);\n"+
*/              "N   = normalize(v_Normal);\n" +
            //   "    vec3 R =  refract(I, normalize(v_Normal),ratio);\n" +  //USE FOR GLASS (GENOMSKINLIGA OBJECT)
               "    vec3 R = reflect(I, N);\n" +
              "    FragColor = vec4(texture(prefilterMap, R).rgb, 1); \n" +
              //  "FragColor=vec4(0f,0f,0f,0.7f);"+
                "}"+
                "\n";
        return perPixelVertexShader;
    }



    private String get3VertexShader()
    {
        // Define our per-pixel lighting shader.
        final String perPixelVertexShader =
                "#version 300 es"+
                "\n uniform mat4 u_MVPMatrix;\t\t// A constant representing the combined model/view/projection matrix.      \t\t       \n" +
                        "uniform mat4 u_MVMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +
                        "\t\t  \t\t\t\n" +
                        "in vec4 a_Position;\t\t// Per-vertex position information we will pass in.   \t\t\t\t\n" +
                        "in vec4 a_Color;\t\t\t// Per-vertex color information we will pass in. \t\t\t\t\n" +
                        "in vec3 a_Normal;\t\t// Per-vertex normal information we will pass in.      \n" +
                        "in vec2 a_TexCoordinate; // Per-vertex texture coordinate information we will pass in. \t\t\n" +
                        "\t\t  \n" +
                        "out vec3 v_Position;\t\t// This will be passed into the fragment shader.       \t\t\n" +
                        "out vec4 v_Color;\t\t\t// This will be passed into the fragment shader.          \t\t\n" +
                        "out vec3 v_Normal;\t\t\t// This will be passed into the fragment shader.  \n" +
                        "out vec2 v_TexCoordinate;   // This will be passed into the fragment shader.    \t\t\n" +
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

    private String get3TexfullFragmentShader()
    {
        final String perPixelFragmentShader =
                "#version 300 es"+
                "\n precision highp float;     " +
                        "uniform vec3 u_camPos;       " +
                        "uniform vec3 u_LightPos;       " +
                        "uniform sampler2D u_Texture;    " +
                        "uniform sampler2D u_glosstex;    " +
                        "uniform sampler2D u_oclusiontex;    " +
                        "uniform sampler2D u_normaltex;    " +
                        "uniform sampler2D u_skyhdr;    " +
                        " uniform sampler2D u_metallicMap; \n" +
                        " out vec4 FragColor; \n" +
                        "in vec3 v_Position;" +
                        "in vec4 v_Color;         " +

                        "in vec3 v_Normal;         \n" +
                        "in vec2 v_TexCoordinate;   \n" +
                        " const float PI = 3.14159265359; \n" +
                        "  \n" +
                     //   "float DistributionGGX(vec3 N, vec3 H, float roughness);\n" +
                      //  "float GeometrySchlickGGX(float NdotV, float roughness);\n" +
                      //  "float GeometrySmith(vec3 N, vec3 V, vec3 L, float roughness);\n" +
                      //  "vec3 fresnelSchlickRoughness(float cosTheta, vec3 F0, float roughness); \n" +

                        "void main()                    \n" +
                        "{                              \n" +

                          //  " vec3 albedo     = pow(texture(u_Texture, v_TexCoordinate).rgb, 2.2f);\n" +
                        "vec3 albedo;"+
                        "albedo.x=pow(texture(u_Texture, v_TexCoordinate).r, 2.2f);"+
                        "albedo.y=pow(texture(u_Texture, v_TexCoordinate).g, 2.2f);"+
                        "albedo.z=pow(texture(u_Texture, v_TexCoordinate).b, 2.2f);"+

                       // "    vec3 normal     = getNormalFromNormalMap();\n" +
                        "    float metallic  = texture(u_metallicMap, v_TexCoordinate).r; \n" +
                        "    float roughness = texture(u_glosstex, v_TexCoordinate).r; \n" +
                        //"    float ao        = texture(aoMap, TexCoords).r;"+
                        "    float ao        = 1.0;"+
                       // "albedo=vec3(1.0,1.0,1.0);"+
                       // "roughness=0.2f;"+
                       // "metallic=0.0f;"+
                        ""+
                        ""+
                        ""+


                        "vec4 normapcol=texture(u_normaltex, v_TexCoordinate);   \n"+
                      //  "vec4 normask=texture(u_glosstex, v_TexCoordinate);   \n"+ //in earth
                        "vec3 endnorvec; \n"+
                        " endnorvec.x= (normapcol.x*2.0f-1.0f);  \n" +
                        " endnorvec.y= -1.0*(normapcol.y*2.0f-1.0f);  \n" +
                        " endnorvec.z= (normapcol.z*-2.0f+1.0f);  \n" +
                        " vec3 Normal=normalize(v_Normal+endnorvec); \n"+



                       // "vec3 N = normalize(Normal); \n" +
                        " vec3 tangentNormal = texture(u_normaltex, v_TexCoordinate).xyz * 2.0 - 1.0;\n" +
                        "\n" +
                        "    vec3 Q1  = dFdx(v_Position);\n" +
                        "    vec3 Q2  = dFdy(v_Position);\n" +
                        "    vec2 st1 = dFdx(v_TexCoordinate);\n" +
                        "    vec2 st2 = dFdy(v_TexCoordinate);\n" +
                        "\n" +
                        "    vec3 N   = normalize(v_Normal);\n" +
                        "    vec3 T  = normalize(Q1*st2.t - Q2*st1.t);\n" +
                        "    vec3 B  = -normalize(cross(N, T));\n" +
                        "    mat3 TBN = mat3(T, B, N);"+
                        " N = normalize(TBN*tangentNormal); \n" +
                      //  "N=normalize(v_Normal+endnorvec);"+
                        "    vec3 V = normalize(u_camPos - v_Position); \n" +
                        "\n" +
                        "    vec3 F0 = vec3(0.04); \n" +
                        "    F0 = mix(F0, albedo, metallic); \n" +
                        "\t           \n" +
                        "   " +
                        "    vec3 Lo = vec3(0.0);"+



                        " " +
                        "        vec3 L = normalize(u_LightPos - v_Position); \n" +
                        "        vec3 H = normalize(V + L); \n" +
                        "        float distance    = length(u_LightPos - v_Position); \n" +

                        "        float attenuation = 1.0 / (distance * distance); \n" +
                        "vec3 lightColors =vec3(23.47, 21.31, 20.79); "+
                        "        vec3 radiance     = lightColors * attenuation;        \n" +
                        "        \n" +
                        "       " +
                    //    "        float NDF = DistributionGGX(N, H, roughness);        \n" +
                     //   "        float G   = GeometrySmith(N, V, L, roughness);      \n" +
                     //   "        vec3 F    = fresnelSchlick(max(dot(H, V), 0.0), F0);       \n" +




                        "  float a      = roughness*roughness; \n" +
                        "    float a2     = a*a; \n" +
                        "    float NdotH  = max(dot(N, H), 0.0); \n" +
                        "    float NdotH2 = NdotH*NdotH; \n" +
                        "\t\n" +
                        "    float nom   = a2; \n" +
                        "    float denom = (NdotH2 * (a2 - 1.0) + 1.0); \n" +
                        "    denom = PI * denom * denom;"+
                        "   float NDF=nom/denom;     \n" +

                            " float r = (roughness + 1.0); \n" +
                        "    float k = (r*r) / 8.0; \n" +
                        "\n" +
                        "     nom   = max(dot(N, V), 0.0); \n" +
                        "     denom = max(dot(N, V), 0.0)* (1.0 - k) + k;"+
                        "    float G= nom/denom;    \n" +

                        "   \n" +
                        "     nom   = max(dot(N, L), 0.0); \n" +
                        "     denom = max(dot(N, L), 0.0) * (1.0 - k) + k;"+
                        "G*=nom/denom;\n" +
                        "\n" +

                        "    vec3 F=F0 + (1.0 - F0) * pow(1.0 - max(dot(H, V), 0.0), 5.0);    \n" +

                        "        vec3 kS = F; \n" +
                        "        vec3 kD = vec3(1.0) - kS; \n" +
                        "        kD *= 1.0 - metallic;\t  \n" +
                        "        \n" +
                        "        vec3 nominator    = NDF * G * F; \n" +
                        "        float denominator = 4.0 * max(dot(N, V), 0.0) * max(dot(N, L), 0.0) + 0.001; \n" +
                        "        vec3 specular     = nominator / denominator; \n" +
                        "            \n" +
                        "        // add to outgoing radiance Lo \n" +
                        "        float NdotL = max(dot(N, L), 0.0);                \n" +
                        "        Lo += (kD * albedo / PI + specular) * radiance * NdotL; "+







                        " vec3 ambient = vec3(0.03) * albedo * ao; \n" +
                        "    vec3 color = ambient + Lo; \n" +
                        "\t\n" +
                        "    color = color / (color + vec3(1.0)); \n" +
                        "    color = pow(color, vec3(1.0/2.2));  \n" +
                        "   \n" +
                        "    FragColor = vec4(color, 1.0);"+
                     //   "    FragColor = vec4(NDF,NDF,NDF, 1.0);"+
                        "}                                                                     \t\n" +
                        "\n";

        return perPixelFragmentShader;
    }



    //---------------------------------------------------------------------------------------------------------------------------------------------------------------


    private String getButtonVS(){return "#version 300 es"+
            "\n precision highp float;                            \n" +
            "\n uniform mat4 u_MVPMatrix;\t\t// A constant representing the combined model/view/projection matrix.      \t\t       \n" +
            "uniform mat4 u_MVMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +
            "uniform mat4 u_MMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +
            "in vec4 a_Position;   \n" +
            "in vec3 a_Normal;     \n" +
            "in vec2 a_TexCoordinate;   \n" +
            "out vec2 v_TexCoordinate;   \n" +
            "out vec3 v_Normal;       \n" +
            "out vec3  v_Position;" +
            "in vec4 a_Color;"+
            "out vec4 v_Color;"+

            "void main() {  \n"+
            "\n"+
            "   v_Normal =vec3(mat3(transpose(inverse(u_MMatrix))) *a_Normal);  "+

        //    "v_Position = vec3( a_Position); \t     "+
            "\n"+
            "v_TexCoordinate= a_TexCoordinate;\n"+
            "v_Color=a_Color;\n"+
            "\n"+
            "v_Position = vec3(u_MVMatrix * a_Position);   \t     "+
            "\tgl_Position = u_MVPMatrix * a_Position;                       \t\t  \n"  +
            "   } ";
            }
    private String getButtonFS(){return  "#version 300 es"+
            "\n precision highp float;                            \n" +
            "in vec3 v_Position;" +
            "in vec4 v_Color;                              \n" +
            "uniform vec3 u_camPos;       " +
            "uniform sampler2D u_Texture; " +
            "in vec2 v_TexCoordinate;   \n" +

            " out vec4 FragColor; \n" +
            "void main() {     \n" +

            "FragColor=(texture(u_Texture, v_TexCoordinate)*(1.0f-v_Color));"+
           // "FragColor=vec4(v_Color);"+
            "}";}


    private String opaceFS(){
        return  "#version 300 es"+
                "\n precision highp float;                            \n" +
                "in vec3 v_Position;" +
                "in vec4 v_Color;                              \n" +
                "uniform vec3 u_camPos;       " +
                "uniform sampler2D u_Texture; " +
                "in vec2 v_TexCoordinate;   \n" +

                " out vec4 FragColor; \n" +
                "void main() {     \n" +

                //"FragColor=(texture(u_Texture, v_TexCoordinate)*(v_Color));"+
                 "FragColor=vec4(v_Color);"+
                "}";
    }


    private String getTexVertexShader()
    {
        // Define our per-pixel lighting shader.
        return
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


    }

    private String getTexFragmentShader()
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
                        "    gl_FragColor = (v_Color * diffuse );                                  \t\t\n" +
                        "}                                                                     \t\n" +
                        "\n";

        return perPixelFragmentShader;
    }


    private String getTexfullFragmentShader()
    {
        final String perPixelFragmentShader =
                "precision highp float;       \t// Set the default precision to medium. We don't need as high of a \n" +
                        "\t\t\t\t\t\t\t\t// precision in the fragment shader.\n" +
                        "uniform vec3 u_LightPos;       \t// The position of the light in eye space.\n" +
                        "uniform sampler2D u_Texture;    // The input texture.\n" +
                        "uniform sampler2D u_glosstex;    // The input texture.\n" +
                        "uniform sampler2D u_oclusiontex;    // The input texture.\n" +
                        "uniform sampler2D u_normaltex;    // The input texture.\n" +
                        "uniform sampler2D u_skyhdr;    // The input texture.\n" +
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


                        "vec4 normapcol=texture2D(u_normaltex, v_TexCoordinate);   \n"+
                        "vec4 normask=texture2D(u_glosstex, v_TexCoordinate);   \n"+
                        "vec3 endnorvec; \n"+
                        " endnorvec.x= (normapcol.x*2.0f-1.0f)*(normask.x*-1.0f+1.0);  \n" +
                        " endnorvec.y= -1.0*(normapcol.y*2.0f-1.0f)*(normask.y*-1.0f+1.0);  \n" +
                        " endnorvec.z= (normapcol.z*-2.0f+1.0f)*(normask.z*-1.0f+1.0);  \n" +
                        " vec3 Normal=normalize(v_Normal+endnorvec);\n"+

                        "   float rug =0.3f;  \n"+
                        // "rug=1.0;"+
                        //"   rug =texture2D(u_glosstex, v_TexCoordinate).x;  \n"+

                        //   "rug=0.3;"+

                        //cook
                        "float spec=0.1f; \n"
                        //  +"spec=1.0;"

                        +"float defuse = 1.0f-spec; \n"
                        +"vec4 Seccol=vec4(0.5,0.5,0.5,1) ;\n"

                        +"vec3 campos=vec3(0.0f,0.0f,-0.5f);\n"
                        //ändra
                        +"vec3 V=normalize(campos - v_Position);\n"
                        +"\n"
                        +"float ndotv =dot(v_Normal,V);\n"
                        +"float ndotvnormap =dot(Normal,V);\n"

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
                        + "   float Fd= 0.5f+2.0f * rug *expfd; \n"+

//1.2 refindm
                        "float refIndw=1.0f;"+ "float refIndm=1.2f;"+"float Rzero=(refIndw-refIndm)/(refIndw+refIndm);"+
                        "float Ferschlic=1.0-(Rzero+(1.0f-Rzero)*(pow((1.0f-ndotv),5.0f)));"

                        +"float Fcook= (1.0f+(Fd-1.0f)*(1.0f-expL))*(1.0f+(Fd-1.0f)*(1.0f-expV)); \n"
                        +"float Dcook =20.0f*exp(-1.0f*(alpha*alpha/(rug*rug))); \n"

                        +"float Dbeckham =3.0/(rug*rug*ndoth*ndoth*ndoth*ndoth)*exp(-1.0*(tan(alpha)*tan(alpha)/rug/rug)); \n"

                        +"float G =min((2.0f*ndoth*ndotv/vdoth),(2.0f*ndoth*ndotl/vdoth)); \n"
                        +"G= min(1.0f,G);\n"
                        +"float cook=Dbeckham*(Ferschlic)*G/(3.14159265359f*ndotv); \n"
                        +" cook=(Dbeckham+(Ferschlic)+G)/(3.14159265359f*ndotv); "
                        +"vec4 Scolor = vec4(cook/3.14159265359f*spec,cook/3.14159265359f*spec,cook/3.14159265359f*spec,1);\n"+



                        "\t// Calculate the dot product of the light vector and vertex normal. If the normal and light vector are\n" +
                        "\t// pointing in the same direction then it will get max illumination.\n" +
                        "    float diffuse = max(dot(Normal, lightVector), 0.0);               \t  \t\t  \t\t\t\t\t\t\t\t\t\t\t\t\t  \n" +
                        "\n" +


                        //läggtill
                        "  float alt=(1.0 / (1.0 + (0.10 * distance)));  \n"+
                        "float ambient=0.2f; \n"+
                        " ambient=0.1f; \n"+
                        "  diffuse +=ambient;\n"+
                        "diffuse=min(diffuse,1.0); \n" +
                        "\t// Multiply the color by the diffuse illumination level and texture value to get final output color.\n" +
                        //    "    gl_FragColor = (v_Color * diffuse * texture2D(u_Texture, v_TexCoordinate));                                  \t\t\n" +
                        "   vec4 coloflight=vec4 (1.0,1.0,1.0,1.0);  \n"+
                        "float rcs=texture2D(u_Texture, v_TexCoordinate).x+(coloflight.x-texture2D(u_Texture, v_TexCoordinate).x)*max(0.0,1.0-Ferschlic-(Rzero))/(0.0-(Rzero));"+
                        "float gcs=texture2D(u_Texture, v_TexCoordinate).y+(coloflight.y-texture2D(u_Texture, v_TexCoordinate).y)*max(0.0,1.0-Ferschlic-(Rzero))/(0.0-(Rzero));"+
                        "float bcs=texture2D(u_Texture, v_TexCoordinate).z+(coloflight.z-texture2D(u_Texture, v_TexCoordinate).z)*max(0.0,1.0-Ferschlic-(Rzero))/(0.0-(Rzero));"+
                        "vec4 colshift=vec4 (rcs,gcs,bcs,1.0);"+
                        //(texture2D(u_Texture, v_TexCoordinate)+((0.4*(1.0-Ferschlic))))
                        "   gl_FragColor =(v_Color* diffuse*defuse *(texture2D(u_Texture, v_TexCoordinate)+((0.4*(1.0-Ferschlic))))* texture2D(u_oclusiontex, v_TexCoordinate)+Scolor* texture2D(u_glosstex, v_TexCoordinate));  \n" +

                        "   gl_FragColor =(v_Color*pow(diffuse,(1.0f/2.0f))*defuse *(texture2D(u_Texture, v_TexCoordinate)+((0.4*(1.0-Ferschlic))))* texture2D(u_oclusiontex, v_TexCoordinate)+Scolor* texture2D(u_glosstex, v_TexCoordinate));  \n" +

                        // "vec4 bob; bob.x=Normal.x;bob.y=Normal.y;bob.z=Normal.z;bob.w=0.0f;"+
                        // "   gl_FragColor = (v_Color*Ferschlic);"+
                        //   "   gl_FragColor = (v_Color *(1.0-1.0f/3.14159265359f * (1.0f+(Fd-1.0f)*(1.0f-expL))*(1.0f+(Fd-1.0f)*(1.0f-expV))));  \n"+
                        //    "float fer=(1.0f+(Fd-1.0f)*(1.0f-expL))*(1.0f+(Fd-1.0f)*(1.0f-expV));  \n"+
                        //  "   gl_FragColor = (v_Color * Dbeckham);"+
                        //   "   gl_FragColor = (v_Color *pow(Scolor,vec4(1.0f/2.2f))); \n" +
                        "}                                                                     \t\n" +
                        "\n";

        return perPixelFragmentShader;
    }




    // /*
    private String getVertexShader()
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

    private String getFragmentShader()
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
                "precision highp float;       \n"		// Set the default precision to medium. We don't need as high of a
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
                        +"float cook=Dcook*Fcook*G/(3.14159265359f*ndotv); \n"
                        +"vec4 Scolor = vec4(cook/3.14159265359f*spec,cook/3.14159265359f*spec,cook/3.14159265359f*spec,1);\n"
                        +";\n"
                        +"\n"
                        +"\n"
                        //  +"gl_FragColor =Seccol*Scolor;\n"



                        + "   float Fz= 0.5f+2.0f * 1.0f *expfd; \n"

                        // pointing in the same direction then it will get max illumination.
                        + "   float diffuse = max(dot(v_Normal, lightVector), 0.0);             \n"
                        // Add attenuation.
                        + "  float  alt= (1.0 / (1.0 + (0.1 * distance )));  \n"

                     //   + " if(diffuse>0.1f){diffuse=1.0f;}  \n"
                      //  + " if(diffuse<1.0f){diffuse+=0.8f;}  \n"
                        //     +Dtwo/(200.0f);
                        // Multiply the color by the diffuse illumination level to get final output color.
                       // + "   gl_FragColor = v_Color *2.0f*(0.2/Dmask-0.3) /3.14159265359f * (1.0f+(Fd-1.0f)*(1.0f-expL))*(1.0f+(Fd-1.0f)*(1.0f-expV))*defuse+Scolor;        \n"
                        //     + "   gl_FragColor =  v_Color *min(Dcook*G,1.0f);"
                    //    + "   gl_FragColor =  v_Color*(1.0-Dmask)*defuse+Scolor ;"
                        //        + "   gl_FragColor =  v_Color*Dcook ;"
                        //  + "   gl_FragColor = v_Color*dot(v_Normal, lightVector)*(1.0f+(Fz-1.0f)*(1.0f-expL))*(1.0f+(Fz-1.0f)*(1.0f-expL)) ; \n"
                        // +"gl_FragColor = v_Color/pow((1.0f+100.0f*dot(v_Normal, lightVector)),1.5f);\n"
                        //  +"gl_FragColor = v_Color*pow(dot(v_Normal, lightVector),1.0f-rug);\n"

                        + "   gl_FragColor = (v_Color *2.0f/3.14159265359f * (1.0f+(Fd-1.0f)*(1.0f-expL))*(1.0f+(Fd-1.0f)*(1.0f-expV))*defuse+Scolor+0.1f)*alt ;        \n"
                      + "   gl_FragColor = (v_Color *1.0f/3.14159265359f * (1.0f+(Fd-1.0f)*(1.0f-expL))*(1.0f+(Fd-1.0f)*(1.0f-expV));  \n"
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
        int shaderHandle = GLES30.glCreateShader(shaderType);

        if (shaderHandle != 0)
        {
            // Pass in the shader source.
            GLES30.glShaderSource(shaderHandle, shaderSource);

            // Compile the shader.
            GLES30.glCompileShader(shaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES30.glGetShaderiv(shaderHandle, GLES30.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling shader: " + GLES30.glGetShaderInfoLog(shaderHandle));
                GLES30.glDeleteShader(shaderHandle);
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
        int programHandle = GLES30.glCreateProgram();

        if (programHandle != 0)
        {
            // Bind the vertex shader to the program.
            GLES30.glAttachShader(programHandle, vertexShaderHandle);

            // Bind the fragment shader to the program.
            GLES30.glAttachShader(programHandle, fragmentShaderHandle);

            // Bind attributes
            if (attributes != null)
            {
                final int size = attributes.length;
                for (int i = 0; i < size; i++)
                {
                    GLES30.glBindAttribLocation(programHandle, i, attributes[i]);
                }
            }

            // Link the two shaders together into a program.
            GLES30.glLinkProgram(programHandle);

           // Get the link status.
            final int[] linkStatus = new int[1];
            GLES30.glGetProgramiv(programHandle, GLES30.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0)
            {
                Log.e(TAG, "Error compiling program: " + GLES30.glGetProgramInfoLog(programHandle));
                GLES30.glDeleteProgram(programHandle);
               programHandle = 0;

            }

        }

        if (programHandle == 0)
        {

            throw new RuntimeException("Error creating program.");
        }

        return programHandle;
    }





    private String Normal_and_ColorVS(){
        return    "#version 300 es"+
                "\n uniform mat4 u_MVPMatrix;\t\t// A constant representing the combined model/view/projection matrix.      \t\t       \n" +
                "uniform mat4 u_MVMatrix;\t\t// A constant representing the combined model/view matrix.       \t\t\n" +
                "uniform mat4 u_MMatrix;"+
                "\t\t  \t\t\t\n" +
                "in vec4 a_Position;\t\t// Per-vertex position information we will pass in.   \t\t\t\t\n" +
                "in vec4 a_Color;\t\t\t// Per-vertex color information we will pass in. \t\t\t\t\n" +
                "in vec3 a_Normal;\t\t// Per-vertex normal information we will pass in.      \n" +

                "\t\t  \n" +
                "out vec3 v_Position;\t\t// This will be passed into the fragment shader.       \t\t\n" +
                "out vec4 v_Color;\t\t\t// This will be passed into the fragment shader.          \t\t\n" +
                "out vec3 v_Normal;\t\t\t// This will be passed into the fragment shader.  \n" +

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
                "\t\n" +
                "\t// Transform the normal's orientation into eye space.\n" +
                //    "    v_Normal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));\n" +
                "   v_Normal =vec3(mat3(transpose(inverse(u_MMatrix))) *a_Normal);  "+
                "v_Normal=normalize(v_Normal);"+

                "\t// gl_Position is a special variable used to store the final position.\n" +
                "\t// Multiply the vertex by the matrix to get the final point in normalized screen coordinates.\n" +
                "\tgl_Position = u_MVPMatrix * a_Position;                       \t\t  \n" +
                "}                                                          ";
    }

    private String Normal_and_ColorFS(){
     return    "#version 300 es"+
                " \n precision highp float;     " +
                "uniform vec3 u_camPos;       " +
                "uniform vec3 u_LightPos;       " +

                " out vec4 FragColor; \n" +
                "in vec3 v_Position;" +
                "in vec4 v_Color;         " +

                "in vec3 v_Normal;         \n" +

                " const float PI = 3.14159265359; \n" +
                "  \n" +

                "void main()                    \n" +
                "{                              \n" +
             "    float distance = length(u_LightPos - v_Position);                  \n" +
             "\t\n" +

             "    vec3 lightVector = normalize(u_LightPos - v_Position);              \t\n" +
             "\n" +
             "    float diffuse = max(dot(v_Normal, lightVector), 0.0); "+

            "\n"+

                 "   vec4 col= v_Color*diffuse;\n" +
            /* "col+=0.1;"+
             "col=min(col,1.0f);"+
             "col.x=pow(col.x, 2.2f);"+
             "col.y=pow(col.y, 2.2f);"+
             "col.z=pow(col.z, 2.2f);"+
*/
                "    FragColor =vec4(col.x,col.y,col.z,1.0f);"+

                "}                                                                     \t\n";
    }
}


