package antonforsberg.chess.ActivityMVC;


import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

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

       View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.

       //  updateLoop =new UpdateLoop();
        System.out.println("its nothing you can doooooo!!!");
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
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        assets=assets;
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
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}

    }


}
