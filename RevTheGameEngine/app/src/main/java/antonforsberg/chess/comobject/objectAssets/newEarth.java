package antonforsberg.chess.comobject.objectAssets;

import android.content.Context;

import antonforsberg.chess.R;
import antonforsberg.chess.comMesh.FinalMesh;
import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-11-05.
 */

public class newEarth extends FinalMesh {
int n;
    public newEarth(final Context mActivityContext) {

        LoadObjectAssets load =new LoadObjectAssets(mActivityContext);
        setNormals(load.LoadFloatArrayAsset(R.raw.earthnormal,8640));
        setVertices(load.LoadFloatArrayAsset( R.raw.earthvertex, 8640));
        setTextureCoordinates(load.LoadFloatArrayAsset( R.raw.earthtexcord, 5760));


        loadtexture(load.LoadImageAsset(R.drawable.earthglobe));
        loadGlosstexture(load.LoadImageAsset(R.drawable.earthglobegloss));
        loadoclusiontexture(load.LoadImageAsset(R.drawable.earthoclusion));
        loadnormaltexture(load.LoadImageAsset(R.drawable.earthglobecbnormal));

        float[] colurs = new float[11520];
        int n = 0;
        for (int i = 0; i < colurs.length; i++) {
            if (n == 0) {
                colurs[i] = 1.0f;
                n++;
            } else if (n == 1) {
                colurs[i] = 1.0f;
                n++;
            } else if (n == 2) {
                colurs[i] = 1.0f;
                n++;
            } else if (n == 3) {
                colurs[i] = 1.0f;
                n = 0;
            }

        }

        setColor(colurs);
        setShader("earth","earth");
    }
}
