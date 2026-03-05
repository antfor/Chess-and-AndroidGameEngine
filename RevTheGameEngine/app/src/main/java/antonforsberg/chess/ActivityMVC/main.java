package antonforsberg.chess.ActivityMVC;


import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-07-08.
 */

public class main extends Activity {
    /** Hold a reference to our GLSurfaceView */
    private GLSurfaceView mGLView;
   // private  UpdateLoop updateLoop;
    LoadObjectAssets assets;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        enterFullscreen();

        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
     //   updateLoop.start();
    }


    protected  void onStop(){

        super.onStop();


    }



    @Override
    protected void onPause() {
      //  mGLView.setVisibility(View.GONE);
        super.onPause();
       // updateLoop.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        enterFullscreen();
    //    updateLoop.start();
     //   mGLView.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && mGLView.getVisibility() == View.GONE) {
            mGLView.setVisibility(View.VISIBLE);

        }


        if (hasFocus) {
            enterFullscreen();
        }

    }

    private void enterFullscreen() {
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        WindowInsetsControllerCompat controller =
                new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());

        controller.hide(WindowInsetsCompat.Type.systemBars());
        controller.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
    }

}
