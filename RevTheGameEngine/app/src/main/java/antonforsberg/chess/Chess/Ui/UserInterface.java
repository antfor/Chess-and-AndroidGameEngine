package antonforsberg.chess.Chess.Ui;


import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import antonforsberg.chess.Chess.Game.Logic.GameLogic;
import antonforsberg.chess.Chess.GameOfChess;
import antonforsberg.chess.GUI.Buttons.Button3D;

/**
 * Created by Anton Forsberg on 2017-12-19.
 */

public class UserInterface {

    private Context mActivityContext;
    private List<Button3D> buttons= new ArrayList<>(5);


    public UserInterface(GameLogic logic, GameOfChess gameOfChess, Context  context){
        mActivityContext=context;

        OptionButton optionButton=new OptionButton(context);
        optionButton.addButton(new replayButton(gameOfChess,context));
        addButton(optionButton);

    }

    private void addButton(Button3D b){
        buttons.add(b);
    }

    public void draw(float[] mMVPMatrix, float[] mProjectionMatrix, float[] mViewMatrix, float[] mModelMatrix) {
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
        }
    }

    public boolean ButtonpressEvent(float x,float y){
        for (int i = 0; i <buttons.size() ; i++) {
            if(buttons.get(i).isPressed(x,y)){
                return true;
            }
        }
        return false;
    }
}
