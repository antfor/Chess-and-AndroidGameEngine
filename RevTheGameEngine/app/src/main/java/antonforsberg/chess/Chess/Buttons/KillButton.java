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

public class KillButton extends Button3D {
    private Context mActivityContext;
    private Point point;
    private GameLogic logic;
    private float[] modelMatrix=new float[16];

    public KillButton(Context mActivityContext, Point point, GameLogic logic){
        super(mActivityContext);
        this.mActivityContext=mActivityContext;
        this.point=point;
        this.logic=logic;
        loadAssets();
        loadVertex();
    }

    @Override
    public void function(){
        logic.actOnKillButton(point);
    }

    private void loadAssets(){
        LoadObjectAssets o=new LoadObjectAssets(mActivityContext);
        setimage(o.LoadImageAsset(R.drawable.killbutton));
        setvercords(o.LoadFloatArrayAsset(R.raw.chessbutton,18));
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
        Matrix.translateM(modelMatrix, 0, 0.25f*point.y, 0, -0.25f*point.x);
        super.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,  modelMatrix);
    }

    private void loadVertex(){}
}
