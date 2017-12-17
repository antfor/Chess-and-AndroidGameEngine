package antonforsberg.chess.Chess.Buttons;

import java.util.ArrayList;
import java.util.List;

import antonforsberg.chess.GUI.Buttons.Button3D;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public class Buttons {

    List<Button3D> buttons=new ArrayList<>(64);

    public void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){
        for (Button3D b:buttons) {
            b.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,mModelMatrix);
        }
    }


}
