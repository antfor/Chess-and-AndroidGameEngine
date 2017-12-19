package antonforsberg.chess.Interfaces;

/**
 * Created by Anton Forsberg on 2017-12-19.
 */

public interface Desplay {
    //void shuldDisplay(Boolean desplay);
    void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix);
     boolean isPressed(float x ,float y);
}
