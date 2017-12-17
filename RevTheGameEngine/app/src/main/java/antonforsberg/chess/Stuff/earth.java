package antonforsberg.chess.Stuff;

import android.content.Context;

import antonforsberg.chess.R;
import antonforsberg.chess.comobject.RawObjectData;
import antonforsberg.chess.comobject.TextureHelper;
import antonforsberg.chess.comMesh.FinalMesh;

/**
 * Created by Anton Forsberg on 2017-07-23.
 */

public class earth extends FinalMesh {



    public earth(final Context mActivityContext) {

System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
        setNormals(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 9216, "n"));

        setVertices(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 9216, "v"));

        loadtexture (TextureHelper.loadTexture(mActivityContext, R.drawable.earthglobe));

        loadGlosstexture (TextureHelper.loadTexture(mActivityContext, R.drawable.earthglobegloss));

        loadoclusiontexture (TextureHelper.loadTexture(mActivityContext, R.drawable.earthoclusion));

        loadnormaltexture (TextureHelper.loadTexture(mActivityContext, R.drawable.earthglobecbnormal));

    //    loadskyhdr (TextureHelper.loadTexture(mActivityContext, R.drawable.feald));


        setTextureCoordinates(RawObjectData.readObjectData(mActivityContext, R.raw.earth, 6144, "t"));

        float[] colurs = new float[12288];
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
