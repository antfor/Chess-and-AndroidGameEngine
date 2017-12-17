package antonforsberg.chess.comMesh.Objects;

import android.content.Context;

import antonforsberg.chess.R;
import antonforsberg.chess.comobject.RawObjectData;
import antonforsberg.chess.comobject.TextureHelper;
import antonforsberg.chess.comMesh.FinalMesh;

/**
 * Created by Anton Forsberg on 2017-09-17.
 */

public class reflect extends FinalMesh {

    public reflect(final Context mActivityContext){


        setNormals(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 9216, "n"));

        setVertices(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 9216, "v"));

         // loadnormaltexture (TextureHelper.loadTexture(mActivityContext, R.drawable.emtynormal));
        loadnormaltexture (TextureHelper.loadTexture(mActivityContext, R.drawable.tag));
        setTextureCoordinates(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 6144, "t"));
        int [] skyr={R.drawable.output_pmrem0,R.drawable.output_pmrem1,R.drawable.output_pmrem2,R.drawable.output_pmrem3,R.drawable.output_pmrem4,R.drawable.output_pmrem5};

        int[]sky={R.drawable.output_skybox_posx,R.drawable.output_skybox_negx,R.drawable.output_skybox_posy,R.drawable.output_skybox_negy,R.drawable.output_skybox_posz,R.drawable.output_skybox_negz};
        loadprefilterMap(TextureHelper.loadCubMap(mActivityContext, sky),TextureHelper.loadTexture(mActivityContext, R.drawable.iblbrdflut));

        setShader("cub","cub");
    }
}
