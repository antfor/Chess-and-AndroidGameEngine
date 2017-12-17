package antonforsberg.chess.ActivityMVC;

import android.content.Context;
//import android.opengl.GLES20;
import android.graphics.Point;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import antonforsberg.chess.Chess.ChessObjects.BoardObject;
import antonforsberg.chess.Chess.ChessObjects.PiecesObject.PawnObject;
import antonforsberg.chess.Chess.Game.Logic.GameLogic;
import antonforsberg.chess.Chess.GameOfChess;
import antonforsberg.chess.GUI.ButtonGUI;
import antonforsberg.chess.GUI.Buttons.Button3D;
import antonforsberg.chess.GUI.Buttons.Dpad;
import antonforsberg.chess.GUI.Buttons.switchobject;
import antonforsberg.chess.GUI.ScreenData;
import antonforsberg.chess.Global.GLview;
import antonforsberg.chess.Interfaces.DrawebleOrtographic;
import antonforsberg.chess.Interfaces.DraweblePerspectiv;
import antonforsberg.chess.comMesh.Objects.reflect;
import antonforsberg.chess.comobject.RotQ;
import antonforsberg.chess.comobject.Skycubemap;
import antonforsberg.chess.comobject.objectAssets.NewReflectBall;
import antonforsberg.chess.comobject.objectAssets.newEarth;

/**
 * Created by Anton Forsberg on 2017-07-08.
 */

public class NewMyGLRenderer  implements GLSurfaceView.Renderer{

    private List<DraweblePerspectiv> draweblePerspectivs= new ArrayList<>(10);
    private List<DrawebleOrtographic> drawebleOrtographics= new ArrayList<>(10);

    public void addDPP(DraweblePerspectiv p){
        draweblePerspectivs.add(p);
    }
    public void addOrtP(DrawebleOrtographic o){
        drawebleOrtographics.add(o);
    }

    //ta bort kanke
    private Context mActivityContext;
    private static float[] cam =new float[9];

    public NewMyGLRenderer(final Context activityContext){
        mActivityContext=activityContext;
    }


    private float[] mModelMatrix = new float[16];
    private float[] mViewMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private  float [] mUiMatrix= new float[16];

    /** Allocate storage for the final combined matrix. This will be passed into the shader program. */
    private float[] mMVPMatrix = new float[16];

    private float[] mRotationMatrix = new float[16];

    double angx=0;
    public double getangx(){return angx;}
    public void setangx(double deg){angx=deg;}

    double angy=0;
    public double getangy(){return angy;}
    public void setangy(double deg){angy=deg;}

    double mvx,mvy,mvz,mang;
    private List<ButtonGUI> Buttons= new ArrayList<>();

    public void PressedDown(float x, float y,int id){
        for (int i =0;i<Buttons.size();i++) {
            Buttons.get(i).actionDown(x,y,id);
        }

    }
    public void PressedUp(float x, float y,int id){

       // chess.ButtonpressEvent(x,y);

        for (int i =0;i<Buttons.size();i++) {
            Buttons.get(i).actionUp(x,y,id);
        }

    }


    public void rottuch(double x,double y,double px,double py,double ang){

        x=x-px; y=y-py;

        double length =Math.sqrt(x*x+y*y);

        mang=length*ang*0.2;
        if(mang>60){mang=1;}

        if(length==0){length=1;}

        x=x*1.0/length; y=y*1.0/length;

        mvx=y;
        mvy= x;
        mvz=0;

    }


    public void resetrot(){

        mvx=mvy=mvz=mang=0;

    }


    RotQ rotq;



    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        // Set the background clear color to black.

      //  GLES20.glClearColor(0.02f, 0.02f, 0.02f, 0.0f);
        GLES30.glClearColor(0.2f, 0.2f, 0.2f, 0.0f);
        //GLES30.glClearColor(0.8f, 0.8f, 0.8f, 0.0f);

        // GLES20.glEnable(GLES20.GL_CULL_FACE);

        // Enable depth testing
      //  GLES30.glEnable(GLES30.GL_CULL_FACE);
        //GLES30.glCullFace(GLES30.GL_FRONT);
        GLES30.glCullFace(GLES30.GL_BACK);

       GLES30.glEnable(GLES30.GL_DEPTH_TEST);

       GLES30.glBlendFunc(GLES30. GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        GLES30.glEnable(GLES30.GL_BLEND );
        // Position the eye in front of the origin.
        final float eyeX = 0.0f;
        final float eyeY = 0.0f;
        final float eyeZ = 0f;

        // We are looking toward the distance
        final float lookX = 0.0f;
        final float lookY = 0.0f;
        final float lookZ = -1.0f;

        // Set our up vector. This is where our head would be pointing were we holding the camera.
        final float upX = 0.0f;
        final float upY = 1.0f;
        final float upZ = 0.0f;

        // Set the view matrix. This matrix can be said to represent the camera position.
        // NOTE: In OpenGL 1, a ModelView matrix is used, which is a combination of a model and
        // view matrix. In OpenGL 2, we can keep track of these matrices separately if we choose.
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);
        cam[0]=eyeX;
        cam[1]=eyeY;
        cam[2]=eyeZ;
        cam[3]=lookX;
        cam[4]=lookY;
        cam[5]=lookZ;
        cam[6]=upX;
        cam[7]=upY;
        cam[8]=upZ;


    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {

        // Set the OpenGL viewport to the same size as the surface.
        GLES30.glViewport(0, 0, width, height);


        // Create a new perspective projection matrix. The height will stay the same
        // while the width will vary as per aspect ratio.
        final float ratio = (float) width /(float) height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;
        ScreenData screenData=new ScreenData();
        // TODO: 2017-12-06 add listneer to screenData
        screenData.setRatio(ratio);
        screenData.setHeight(height);
        screenData.setWidth(width);

        Matrix.frustumM(mProjectionMatrix, 0, left, right, bottom, top, near, far);
        Matrix.orthoM(mUiMatrix, 0, left, right, bottom, top, 0, 10);

        GLview.width=width;
        GLview.height=height;
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        float[] scratch = new float[16];
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

        // Do a complete rotation every 10 seconds.
        long time = SystemClock.uptimeMillis() % 50000L;
        float angleInDegrees = (360.0f / 50000.0f) * ((int) time);



        Matrix.setIdentityM(mModelMatrix, 0);


        GLES30.glDepthMask(false);

       // skymap.draw(mMVPMatrix,mProjectionMatrix,mViewMatrix, mModelMatrix);


        GLES30.glDepthMask(true);

        for (int i = 0; i < draweblePerspectivs.size(); i++) {
            draweblePerspectivs.get(i).draw(mMVPMatrix,mProjectionMatrix,mViewMatrix, mModelMatrix);
        }

        for (int i = 0; i < drawebleOrtographics.size(); i++) {
            drawebleOrtographics.get(i).draw(mMVPMatrix,mUiMatrix,mViewMatrix, mModelMatrix);
        }



        Matrix.setIdentityM(mModelMatrix, 0);

    }
}
