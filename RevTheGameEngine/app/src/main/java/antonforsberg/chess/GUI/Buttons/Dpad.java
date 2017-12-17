package antonforsberg.chess.GUI.Buttons;


import antonforsberg.chess.GUI.ButtonGUI;
import antonforsberg.chess.R;

/**
 * Created by Anton Forsberg on 2017-11-03.
 */

public class Dpad extends ButtonGUI {

    private Dpad(){
        setImage(R.drawable.dpad);
        setDist(-0.95f);
    }

    public Dpad(float x ,int LorR, float y,int UorD, float with , float height){
        this();
        ButtonInt(x,LorR ,y, UorD,with, height);

    }

    public Dpad(float x ,int LorR, float y,int UorD, float with , float height , boolean per){
        this();
        ButtonIntPer(x,LorR ,y, UorD,with, height);
    }
}
