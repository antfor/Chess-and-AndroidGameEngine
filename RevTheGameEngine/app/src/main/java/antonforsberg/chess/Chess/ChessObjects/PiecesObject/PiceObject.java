package antonforsberg.chess.Chess.ChessObjects.PiecesObject;

import android.content.Context;
import android.graphics.Point;
import android.opengl.Matrix;

import antonforsberg.chess.Animations.MatrixInterpolation;
import antonforsberg.chess.Animations.IMatrixInterpolation;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pice;
import antonforsberg.chess.comMesh.FinalMesh;
import antonforsberg.chess.comobject.objectAssets.ObjectInterface.BasicObject;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public abstract class PiceObject implements BasicObject,IMatrixInterpolation {
    protected FinalMesh mesh=new FinalMesh();
    protected Context mActivityContext;
    protected Pice p;
    private Point lastPoint;
    private float[] oldModelMatrix= new float[16];
    private float[] modelMatrix= new float[16];
    private MatrixInterpolation matrixInterpolation =new MatrixInterpolation(500);

    public PiceObject(Context context,Pice pice){
        mActivityContext=context;
        p=pice;
        loadAssets();
        lastPoint=new Point(p.getPos());
    }



    @Override
    public void draw(float[] mMVPMatrix, float[] mProjectionMatrix, float[] mViewMatrix, float[] mModelMatrix) {

        System.arraycopy(mModelMatrix, 0,    modelMatrix , 0,    16);

        Matrix.translateM(modelMatrix, 0, (-0.875f)+0.25f*p.getPos().y, 0, (0.875f)-0.25f*p.getPos().x);


        System.arraycopy(mModelMatrix, 0,    oldModelMatrix , 0,    16);
        Matrix.translateM(oldModelMatrix, 0, (-0.875f)+0.25f*p.getOldPos().y, 0, (0.875f)-0.25f*p.getOldPos().x);

        if(!lastPoint.equals(p.getPos())){
            matrixInterpolation.startaAimate();
        }

        mesh. draw(mMVPMatrix,mProjectionMatrix,mViewMatrix, matrixInterpolation.animate(oldModelMatrix,modelMatrix));




        lastPoint=new Point(p.getPos());
    }

    @Override
    public abstract void loadAssets();


    @Override
    public boolean interpoltate() {
        return matrixInterpolation.isAnimating();
    }

}

