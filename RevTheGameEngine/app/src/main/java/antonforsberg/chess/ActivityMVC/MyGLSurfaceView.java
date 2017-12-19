package antonforsberg.chess.ActivityMVC;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import antonforsberg.chess.ActivityMVC.NewMyGLRenderer;
import antonforsberg.chess.Chess.GameOfChess;
import antonforsberg.chess.Interfaces.ControllerListener;
import antonforsberg.chess.Interfaces.DraweblePerspectiv;
import antonforsberg.chess.UserInput.UiPressListener;

/**
 * Created by Anton Forsberg on 2017-07-05.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private final NewMyGLRenderer mRenderer;
    private GameOfChess gameOfChess;

    private List<ControllerListener> controllerListeners= new ArrayList<>(10);
    private List<UiPressListener> pressListeners= new ArrayList<>(10);

    public MyGLSurfaceView(Context context){
        super(context);

        //setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);


        setEGLContextClientVersion(3);
        mRenderer = new NewMyGLRenderer(context);

        setRenderer(mRenderer);



        gameOfChess=new GameOfChess(context);
        mRenderer.addDPP(gameOfChess);
        mRenderer.addOrtP(gameOfChess);
        pressListeners.add(gameOfChess);

      // updateLoop.addDeltaTimeListnerer(gameOfChess);
    }



    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
        int index = e.getActionIndex();
        int action = e.getActionMasked();
        int pointerId = e.getPointerId(index);


        float x = e.getX(index);
        float y = e.getY(index);
        float dx = x - mPreviousX;
        float dy = y - mPreviousY;


        switch (action) {
            case MotionEvent.ACTION_MOVE: break;

            case MotionEvent.ACTION_DOWN: break;
            case MotionEvent.ACTION_UP:
                for (int i = 0; i <pressListeners.size() ; i++) {
                    pressListeners.get(i).press(x,y);
                }
                break;

            case MotionEvent.ACTION_POINTER_DOWN:   break;

            case MotionEvent.ACTION_POINTER_UP:
                for (int i = 0; i <pressListeners.size() ; i++) {
                    pressListeners.get(i).press(x,y);
                }

                break;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}