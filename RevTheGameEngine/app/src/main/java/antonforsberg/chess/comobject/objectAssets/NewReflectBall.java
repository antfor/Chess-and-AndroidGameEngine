package antonforsberg.chess.comobject.objectAssets;

import android.content.Context;

import antonforsberg.chess.R;
import antonforsberg.chess.comMesh.FinalMesh;
import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-11-05.
 */

public class NewReflectBall extends FinalMesh {

    public NewReflectBall(final Context mActivityContext){

        LoadObjectAssets load =new LoadObjectAssets(mActivityContext);
        setNormals(load.LoadFloatArrayAsset( R.raw.earthnormal, 8640));
        setVertices(load.LoadFloatArrayAsset(R.raw.earthvertex, 8640));
        setTextureCoordinates(load.LoadFloatArrayAsset( R.raw.earthtexcord, 5760));

        loadnormaltexture(load.LoadImageAsset(R.drawable.tag));


        int [] skyr={R.drawable.output_pmrem0,R.drawable.output_pmrem1,R.drawable.output_pmrem2,R.drawable.output_pmrem3,R.drawable.output_pmrem4,R.drawable.output_pmrem5};

        int[]sky={R.drawable.output_skybox_posx,R.drawable.output_skybox_negx,R.drawable.output_skybox_posy,R.drawable.output_skybox_negy,R.drawable.output_skybox_posz,R.drawable.output_skybox_negz};
      //  loadprefilterMap(TextureHelper.loadCubMap(mActivityContext, sky),TextureHelper.loadTexture(mActivityContext, R.drawable.iblbrdflut));
        loadprefilterMap(load.LoadcubmapAsset(sky),load.LoadImageAsset(R.drawable.iblbrdflut));

        setShader("cub","cub");
    }
}
