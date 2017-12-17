package antonforsberg.chess.comobject;

import android.content.Context;

import antonforsberg.chess.R;
import antonforsberg.chess.comMesh.FinalMesh;

/**
 * Created by Anton Forsberg on 2017-09-02.
 */

public class Skycubemap extends FinalMesh {
    private final Context mActivityContext;
    public Skycubemap(final Context activityContext){

        mActivityContext = activityContext;

        //byt ver till dem i learn  opengl
        setShader("sky","sky");
        float[]vertices={
                // positions
                -1.0f,  1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,

                -1.0f, -1.0f,  1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f, -1.0f,
                -1.0f,  1.0f,  1.0f,
                -1.0f, -1.0f,  1.0f,

                1.0f, -1.0f, -1.0f,
                1.0f, -1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f,  1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,

                -1.0f, -1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f, -1.0f,  1.0f,
                -1.0f, -1.0f,  1.0f,

                -1.0f,  1.0f, -1.0f,
                1.0f,  1.0f, -1.0f,
                1.0f,  1.0f,  1.0f,
                1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f,  1.0f,
                -1.0f,  1.0f, -1.0f,

                -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f,  1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f,  1.0f,
                1.0f, -1.0f,  1.0f
        }; //  float[]Normals={-1f,0f,0f,-1f,0f,0f,-1f,0f,0f,0f,0f,-1f,0f,0f,-1f,0f,0f,-1f,1f,0f,0f,1f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,1f,0f,0f,1f,0f,-1f,0f,0f,-1f,0f,0f,-1f,0f,0f,1f,0f,0f,1f,0f,0f,1f,0f,-1f,0f,0f,-1f,0f,0f,-1f,0f,0f,0f,0f,-1f,0f,0f,-1f,0f,0f,-1f,1f,0f,0f,1f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,1f,0f,0f,1f,0f,-1f,0f,0f,-1f,0f,0f,-1f,0f,0f,1f,0f,0f,1f,0f,0f,1f,0f};

        int[]sky={R.drawable.output_skybox_posx,R.drawable.output_skybox_negx,R.drawable.output_skybox_posy,R.drawable.output_skybox_negy,R.drawable.output_skybox_posz,R.drawable.output_skybox_negz};
        loadskybox(TextureHelper.loadCubMap(mActivityContext, sky));
        setVertices(vertices);
      //  setNormals(Normals);

    }
}
