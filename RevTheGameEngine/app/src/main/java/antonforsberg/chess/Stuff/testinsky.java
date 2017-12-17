package antonforsberg.chess.Stuff;

import android.content.Context;

import antonforsberg.chess.R;
import antonforsberg.chess.comobject.RawObjectData;
import antonforsberg.chess.comMesh.FinalMesh;
import antonforsberg.chess.comobject.TextureHelper;

/**
 * Created by Anton Forsberg on 2017-08-25.
 */

public class testinsky extends FinalMesh {
    private  Context mActivityContext;

    public testinsky(final Context activityContext){

        mActivityContext = activityContext;

       // float[]vertices={-1.000000f,1.000000f,1.000000f,-1.000000f,-1.000000f,-1.000000f,-1.000000f,-1.000000f,1.000000f,-1.000000f,1.000000f,-1.000000f,1.000000f,-1.000000f,-1.000000f,-1.000000f,-1.000000f,-1.000000f,1.000000f,1.000000f,-1.000000f,1.000000f,-1.000000f,1.000000f,1.000000f,-1.000000f,-1.000000f,1.000000f,1.000000f,1.000000f,-1.000000f,-1.000000f,1.000000f,1.000000f,-1.000000f,1.000000f,1.000000f,-1.000000f,-1.000000f,-1.000000f,-1.000000f,1.000000f,-1.000000f,-1.000000f,-1.000000f,-1.000000f,1.000000f,-1.000000f,1.000000f,1.000000f,1.000000f,1.000000f,1.000000f,-1.000000f,-1.000000f,1.000000f,1.000000f,-1.000000f,1.000000f,-1.000000f,-1.000000f,-1.000000f,-1.000000f,-1.000000f,1.000000f,-1.000000f,1.000000f,1.000000f,-1.000000f,1.000000f,-1.000000f,-1.000000f,1.000000f,1.000000f,-1.000000f,1.000000f,1.000000f,1.000000f,1.000000f,-1.000000f,1.000000f,1.000000f,1.000000f,1.000000f,-1.000000f,1.000000f,1.000000f,-1.000000f,-1.000000f,1.000000f,1.000000f,-1.000000f,-1.000000f,1.000000f,-1.000000f,1.000000f,-1.000000f,-1.000000f,1.000000f,-1.000000f,1.000000f,-1.000000f,-1.000000f,1.000000f,1.000000f,1.000000f,1.000000f,1.000000f};
        setVertices(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 9216, "v"));
        setNormals(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 9216, "n"));

        //float[]Normals={-1f,0f,0f,-1f,0f,0f,-1f,0f,0f,0f,0f,-1f,0f,0f,-1f,0f,0f,-1f,1f,0f,0f,1f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,1f,0f,0f,1f,0f,-1f,0f,0f,-1f,0f,0f,-1f,0f,0f,1f,0f,0f,1f,0f,0f,1f,0f,-1f,0f,0f,-1f,0f,0f,-1f,0f,0f,0f,0f,-1f,0f,0f,-1f,0f,0f,-1f,1f,0f,0f,1f,0f,0f,1f,0f,0f,0f,0f,1f,0f,0f,1f,0f,0f,1f,0f,-1f,0f,0f,-1f,0f,0f,-1f,0f,0f,1f,0f,0f,1f,0f,0f,1f,0f};

        int[]sky={R.drawable.output_skybox_posx,R.drawable.output_skybox_negx,R.drawable.output_skybox_posy,R.drawable.output_skybox_negy,R.drawable.output_skybox_posz,R.drawable.output_skybox_negz};
       // setVertices(vertices);
      //  setNormals(Normals);

        loadskybox(TextureHelper.loadCubMap(mActivityContext, sky));

    }
}
