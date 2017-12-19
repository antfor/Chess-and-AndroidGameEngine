package antonforsberg.chess.GUI.Buttons;

import android.content.Context;
import android.util.DisplayMetrics;

import antonforsberg.chess.Global.GLview;
import antonforsberg.chess.Global.GLviewListener;

/**
 * Created by Anton Forsberg on 2017-12-19.
 */

public abstract class Button2D extends Button3D implements GLviewListener {

    private Context mActivityContext;

    public Button2D(Context mActivityContext) {
        super(mActivityContext);
        this.mActivityContext=mActivityContext;
        GLview.addListener(this);
    }



    public void button2dDp(float x ,int LorR, float y,int UorD, float with , float height , float layer ){
        DisplayMetrics displayMetrics=mActivityContext.getResources().getDisplayMetrics();

        //fixa så att om man tar 1 på lor uord så blir det x respektive y gånger två
        x= Math.abs(LorR-((displayMetrics.xdpi*x/160.0f)/ GLview.width));
        y= Math.abs(UorD-((displayMetrics.ydpi*y/160.0f)/GLview.height));

        with=(displayMetrics.xdpi*with/160.0f)/GLview.width;;
        height= (displayMetrics.ydpi*height/160.0f)/GLview.height;

        button2d(x,y,with,height,layer);

    }

    public void button2dPrecentage(float x ,int LorR, float y,int UorD, float with , float height, float layer ){

        x=Math.abs(LorR-x);
        y=Math.abs(UorD-y);

       // height*=GLview.ratio;
       // y=(y-0.5f)*GLview.ratio+0.5f;

        button2d(x,y,with,height,layer);
    }

    public void button2dDpPerMiddle(float x ,int LorR, float y,int UorD, float with , float height, float layer ){

        DisplayMetrics displayMetrics=mActivityContext.getResources().getDisplayMetrics();

        with=(displayMetrics.xdpi*with/160.0f)/GLview.width;
        height= (displayMetrics.ydpi*height/160.0f)/GLview.height;

        x=Math.abs(LorR-x-with/2f);
        y=Math.abs(UorD-y-height/2f);


        button2d(x,y,with,height,layer);
    }
    private void button2d(float x , float y, float with , float height, float layer ){


        x=(x-0.5f)*2*GLview.ratio;
        y=(y-0.5f)*2;


        height=height*2;
        with=with*2*GLview.ratio;

        layer*=-0.1f;

        float squareCoords[] = {
                0.0f+x,+   0.0f -y,  layer,  //top left
                0.0f+x,-height-y, layer,    //bottom left
                with+x,-height-y,layer,     //bottom right
                with+x,-height-y, layer,     //bottom right
                with+x,   0.0f-y, layer,     //top right
                0.0f+x,   0.0f-y, layer, }; // top left


        setvercords(squareCoords);


        float texcords[] ={
                0.0f,0.0f,
                0.0f,1.0f,
                1.0f,1.0f,
                1.0f,1.0f,
                1.0f,0.0f,
                0.0f,0.0f

        };
        mesh.setTextureCoordinates(texcords);
    }

    @Override
    public abstract void viewUpdate();
}
