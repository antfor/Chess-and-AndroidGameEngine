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

public class QueenObject  extends PiceObject {



    public QueenObject(Context context, Pice pice) {
     super(context,pice);
    }


    @Override
    public void loadAssets() {
        LoadObjectAssets load = new LoadObjectAssets(mActivityContext);
        mesh.setNormals(load.LoadFloatArrayAsset(R.raw.queennor, 8640));
        mesh.setVertices(load.LoadFloatArrayAsset(R.raw.queenver, 8640));

        // mesh.setTextureCoordinates(load.LoadFloatArrayAsset( R.raw.earthtexcord, 4428));
        // mesh.loadtexture(load.LoadImageAsset(R.drawable.earthglobe));

        float col = 1;
        if (p.getColur().equals(ColorP.Black)) {
            col = 0.3f;
        }
        float[] colurs = new float[11520];
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
