package antonforsberg.chess.Stuff;

import android.content.Context;

import antonforsberg.chess.R;
import antonforsberg.chess.comobject.RawObjectData;
import antonforsberg.chess.comobject.TextureHelper;
import antonforsberg.chess.comMesh.FinalMesh;

/**
 * Created by Anton Forsberg on 2017-07-23.
 */

public class HdpSpher extends FinalMesh {

    private final Context mActivityContext;

    public HdpSpher(final Context activityContext) {

        mActivityContext = activityContext;


        setNormals(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 9216, "n"));

        setVertices(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 9216, "v"));

        loadtexture (TextureHelper.loadTexture(mActivityContext, R.drawable.rustbs));

        loadGlosstexture (TextureHelper.loadTexture(mActivityContext, R.drawable.rustgloss));

       // loadoclusiontexture (TextureHelper.loadTexture(mActivityContext, R.drawable.earthoclusion));

        loadnormaltexture (TextureHelper.loadTexture(mActivityContext, R.drawable.rustnormal));

        loadmetal(TextureHelper.loadTexture(mActivityContext, R.drawable.rustmet));

        setTextureCoordinates(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 6144, "t"));
//IBL
        int[]sky={R.drawable.irrdiance5120,R.drawable.irrdiance5121,R.drawable.irrdiance5122,R.drawable.irrdiance5123,R.drawable.irrdiance5124,R.drawable.irrdiance5125};

        // int[]sky={R.drawable.output_iem4k0,R.drawable.output_iem4k1,R.drawable.output_iem4k2,R.drawable.output_iem4k3,R.drawable.output_iem4k4,R.drawable.output_iem4k5};
        loadirradianceMap(TextureHelper.loadCubMap(mActivityContext, sky));

        int [] skyr={R.drawable.output_pmrem0,R.drawable.output_pmrem1,R.drawable.output_pmrem2,R.drawable.output_pmrem3,R.drawable.output_pmrem4,R.drawable.output_pmrem5};
       // int[]skyr={R.drawable.output_skybox_posx,R.drawable.output_skybox_negx,R.drawable.output_skybox_posy,R.drawable.output_skybox_negy,R.drawable.output_skybox_posz,R.drawable.output_skybox_negz};

        loadprefilterMap(TextureHelper.loadCubMap(mActivityContext, skyr),TextureHelper.loadTexture(mActivityContext, R.drawable.iblbrdflut));

        setShader("ibl","ibl");
    }
}
