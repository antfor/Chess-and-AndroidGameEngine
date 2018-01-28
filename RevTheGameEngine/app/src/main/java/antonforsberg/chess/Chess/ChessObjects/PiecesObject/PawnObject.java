package antonforsberg.chess.Chess.ChessObjects.PiecesObject;

import android.content.Context;

import antonforsberg.chess.Chess.Enums.ColorP;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pice;
import antonforsberg.chess.R;
import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public class PawnObject  extends PiceObject {


    public PawnObject(Context context,Pice pice){
        super(context,pice);
}


    @Override
    public void loadAssets() {
        LoadObjectAssets load =new LoadObjectAssets(mActivityContext);
     //  mesh.setNormals(load.LoadFloatArrayAsset(R.raw.pawnnor,4428));
        mesh.setVertices(load.LoadFloatArrayAsset( R.raw.pawnver, 4428));

        mesh.setNormals(load.LoadFloatArrayAsset(R.raw.pawnnorsmooth,4428));
mesh.setTextureCoordinates(load.LoadFloatArrayAsset(R.raw.pawnuv,2952));
mesh.loadnormaltexture(load.LoadImageAsset(R.drawable.pawntex));

if(p.getColur().equals(ColorP.Black)){mesh.loadtexture(load.LoadImageAsset(R.drawable.blackmarble));}
else {mesh.loadtexture(load.LoadImageAsset(R.drawable.whitemarble));}

       // mesh.setTextureCoordinates(load.LoadFloatArrayAsset( R.raw.earthtexcord, 4428));
       // mesh.loadtexture(load.LoadImageAsset(R.drawable.earthglobe));

            float col=1;
        if(p.getColur().equals(ColorP.Black)){
            col=0.3f;
        }
        float[] colurs = new float[5904];
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
        mesh.setShader("NorCol","NorColMap");
    }



}
