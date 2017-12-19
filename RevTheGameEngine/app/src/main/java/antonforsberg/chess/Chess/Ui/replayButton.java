package antonforsberg.chess.Chess.Ui;

import android.content.Context;

import antonforsberg.chess.Chess.GameOfChess;
import antonforsberg.chess.GUI.Buttons.Button2D;
import antonforsberg.chess.Interfaces.Desplay;
import antonforsberg.chess.R;
import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-12-19.
 */

public class replayButton extends Button2D implements Desplay {

private Context context;
private boolean displaying;
private GameOfChess gameOfChess;


    public replayButton(GameOfChess gameOfChess,Context mActivityContext) {
        super(mActivityContext);
        this.gameOfChess=gameOfChess;
        context=mActivityContext;
        loadAssets();
        displaying=true;
        viewUpdate();

    }

    @Override
    public void function() {
        gameOfChess.newGame();
    }

    @Override
    public void viewUpdate() {

        button2dDpPerMiddle(0.5f,1,0.5f,1,48*3,48*3,2);
        //button2dDp(24*3,1,24,0,48,48,3);
    }

    private void loadAssets(){
        LoadObjectAssets o=new LoadObjectAssets(context);
        setimage(o.LoadImageAsset(R.drawable.replaybutton));
    }


    @Override
    public void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){

        super.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
    }
}
