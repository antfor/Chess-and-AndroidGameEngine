package antonforsberg.chess.comobject.objectAssets.ObjectInterface;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public interface BasicObject {

    void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix);
    void loadAssets();
}
