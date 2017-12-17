package antonforsberg.chess.Chess.ChessObjects.PiecesObject;

import android.content.Context;
import android.opengl.Matrix;

import antonforsberg.chess.Chess.Enums.ColorP;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pice;
import antonforsberg.chess.R;
import antonforsberg.chess.comMesh.FinalMesh;
import antonforsberg.chess.comobject.LoadObjectAssets;
import antonforsberg.chess.comobject.objectAssets.ObjectInterface.BasicObject;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public class RookObject  implements BasicObject {
    private FinalMesh mesh = new FinalMesh();
    private Context mActivityContext;
    private Pice p;
    private float[] modelMatrix = new float[16];


    public RookObject(Context context, Pice pice) {
        mActivityContext = context;
        p = pice;
        loadAssets();
    }


    @Override
    public void draw(float[] mMVPMatrix, float[] mProjectionMatrix, float[] mViewMatrix, float[] mModelMatrix) {

        System.arraycopy(mModelMatrix, 0, modelMatrix, 0, 16);
        Matrix.translateM(modelMatrix, 0, (-0.875f) + 0.25f * p.getPos().y, 0, (0.875f) - 0.25f * p.getPos().x);

        mesh.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, modelMatrix);

    }

    @Override
    public void loadAssets() {
        LoadObjectAssets load = new LoadObjectAssets(mActivityContext);
        mesh.setNormals(load.LoadFloatArrayAsset(R.raw.towernor, 6192));
        mesh.setVertices(load.LoadFloatArrayAsset(R.raw.towerver, 6192));

        // mesh.setTextureCoordinates(load.LoadFloatArrayAsset( R.raw.earthtexcord, 4428));
        // mesh.loadtexture(load.LoadImageAsset(R.drawable.earthglobe));

        float col = 1;
        if (p.getColur().equals(ColorP.Black)) {
            col = 0.3f;
        }
        float[] colurs = new float[8256];
        int n = 0;
        for (int i = 0; i < colurs.length; i++) {
            if (n == 0) {
                colurs[i] = col;
                n++;
            } else if (n == 1) {
                colurs[i] = col;
                n++;
            } else if (n == 2) {
                colurs[i] = col;
                n++;
            } else if (n == 3) {
                colurs[i] = 1.0f;
                n = 0;
            }

        }

        mesh.setColor(colurs);
        mesh.setShader("NorCol", "NorCol");
    }

}
