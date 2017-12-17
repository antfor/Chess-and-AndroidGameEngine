package antonforsberg.chess.Chess.Buttons;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import antonforsberg.chess.Chess.Game.Logic.GameLogic;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pice;
import antonforsberg.chess.GUI.Buttons.Button3D;
import antonforsberg.chess.R;
import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public class SelectedButton extends Button3D {
    private Context mActivityContext;
    private Pice pice;
    private GameLogic logic;
    private float[] modelMatrix=new float[16];

    public SelectedButton(Context mActivityContext, Pice pice, GameLogic logic){
        super(mActivityContext);
        this.mActivityContext=mActivityContext;
       this.pice=pice;
       this.logic=logic;
        loadAssets();
        loadVertex();
    }

    @Override
    public void function(){
        logic.actOnSelectedButton(pice,this);
    }

    public Pice getPice(){
        return pice;
    }

    private void loadAssets(){
        LoadObjectAssets o=new LoadObjectAssets(mActivityContext);
        setvercords(o.LoadFloatArrayAsset(R.raw.chessbutton,18));
        setimage(o.LoadImageAsset(R.drawable.selectedbutton));
        texcoords(new float[]{0.9999f,
                0.9999f,
                0.0001f,
                0.0001f,
                0.0001f,
                0.9999f,
                0.9999f,
                0.9999f,
                0.9999f,
                0.0001f,
                0.0001f,
                0.0001f});
    }


    @Override
    public void draw(float[] mMVPMatrix, float[] mProjectionMatrix, float[] mViewMatrix, float[] mModelMatrix) {
        System.arraycopy(mModelMatrix, 0,    modelMatrix , 0,    16);
        Matrix.translateM(modelMatrix, 0, 0.25f*pice.getPos().y, 0, -0.25f*pice.getPos().x);
       // super.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, modelMatrix);

        uptadeModelMatrix( mProjectionMatrix, mViewMatrix, modelMatrix);
        if(logic.getSelected()==pice){
            mesh.draw( mMVPMatrix,mProjectionMatrix, mViewMatrix, modelMatrix);
        }
    }

    private void loadVertex(){}


}
