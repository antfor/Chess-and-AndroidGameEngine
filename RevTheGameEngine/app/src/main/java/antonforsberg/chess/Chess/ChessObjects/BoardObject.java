package antonforsberg.chess.Chess.ChessObjects;

import android.content.Context;

import antonforsberg.chess.R;
import antonforsberg.chess.comMesh.FinalMesh;
import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public class BoardObject {
    private FinalMesh mesh =new FinalMesh();
    private Context mActivityContext;

    public BoardObject(Context context){
        mActivityContext=context;

        loadAssets();
    }

    private void loadAssets (){
        LoadObjectAssets load =new LoadObjectAssets(mActivityContext);
       // mesh.setNormals(load.LoadFloatArrayAsset(R.raw.boardnor,2880));
        mesh.setVertices(load.LoadFloatArrayAsset( R.raw.planeboardver, 18));
        mesh.setTextureCoordinates(load.LoadFloatArrayAsset( R.raw.planeboardtex, 12));
        mesh.loadtexture(load.LoadImageAsset(R.drawable.brownboard));
        mesh.setShader("button","button");
        float[] col=new float[6*4];
      /*  for (int i = 0; i <col.length ; i++) {
            col[i]=1;
        }
        */

        mesh.setColor(col);

    }
    public void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){
        mesh.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,mModelMatrix);
    }
}
