package antonforsberg.chess.Chess.Game.Logic.PiecesLogic;

import android.content.Context;
import android.graphics.Point;
import antonforsberg.chess.Chess.Enums.ColorP;
import antonforsberg.chess.Chess.Enums.DeadorAlive;
import antonforsberg.chess.Chess.Game.Logic.GameLogic;
import antonforsberg.chess.GUI.Buttons.Button3D;
import antonforsberg.chess.comobject.objectAssets.ObjectInterface.BasicObject;

import java.util.List;

public abstract class Pice
{
    protected Point pos;
    final protected ColorP col;
    protected BasicObject model;
    protected Context context;


    public Pice(Context context,Point p, ColorP c) {
        col = c;
        pos = p;
        this.context=context;
        setModel();
    }

    public ColorP getColur() {
        return col;
    }


    public Point getPos() {
        return new Point(pos);
    }

    public void Move(Point p) {
     //   MoveAnimation(p);
        pos = new Point(p);
    }


    public void MoveAnimation(Point p) {

    }


    public void draw(float[] mMVPMatrix, float[] mProjectionMatrix, float[] mViewMatrix, float[] mModelMatrix) {

        model.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);

    }

    public abstract List<Point> getPossibleMoves(Pice[][] board);

    protected abstract void setModel();




}
