package antonforsberg.chess.Chess;

import android.content.Context;
import android.graphics.Point;
import android.opengl.Matrix;

import java.util.ArrayList;
import java.util.List;

import antonforsberg.chess.Chess.Buttons.KillButton;
import antonforsberg.chess.Chess.Buttons.MoveButton;
import antonforsberg.chess.Chess.Buttons.SelectedButton;
import antonforsberg.chess.Chess.Game.Controller;
import antonforsberg.chess.Chess.Game.Logic.GameLogic;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.MoveObserver;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.SelectedObserver;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.ThreatenedObserver;
import antonforsberg.chess. Chess.Player.Black;
import antonforsberg.chess. Chess.Player.White;
import antonforsberg.chess.Interfaces.DeltaTimeListener;
import antonforsberg.chess.Interfaces.DraweblePerspectiv;
import antonforsberg.chess.UserInput.UiPressListener;


public class GameOfChess implements DraweblePerspectiv,UiPressListener,DeltaTimeListener {

    private GameLogic gameLogic;
    private Context mActivityContext;


    public GameOfChess(Context context){

     mActivityContext=context;

    }

    public void newGame_TwoPlayers(){
        newGameLogic();
    }

    public void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){
        Matrix.translateM(mModelMatrix, 0, 0, 0f, -2.42f);
          if(gameLogic==null) {newGameLogic();}

            gameLogic.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);

    }

    private void newGameLogic(){
        gameLogic =new GameLogic(mActivityContext);
    }


    @Override
    public void press(float x, float y) {
        gameLogic.ButtonpressEvent(x,y);
    }


    @Override
    public void deltaTimeUpdate(double dt) {

    }
}
