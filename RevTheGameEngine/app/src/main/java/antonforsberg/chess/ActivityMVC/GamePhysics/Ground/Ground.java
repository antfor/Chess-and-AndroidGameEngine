package antonforsberg.chess.ActivityMVC.GamePhysics.Ground;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;

public class Ground {

    private Bitmap heitMap;
    private float min,max,Ywidth,Xlenght;

    public Ground(Bitmap heitMap, float min ,float max,float width,float lenght){
        this.heitMap=heitMap;
        this.min=min;
        this.max=max;
        this.Ywidth=width;
        this.Xlenght=lenght;
    }


    public float getGroundLevel(float x , float y) throws NullPointerException{
        Point p =cordToint(x,y);
       int h=Color.red(heitMap.getPixel(p.x,p.y));
        h=RGBtoMetric(h);
        return h;
        Tuple<Float,Float>t=new Tuple();
        Integer i = new Integer(1);
        Object o=new Object();
        t=i;
        boolean b=t== new Ground(null,0,0,0,0);

    }

    public float getGroundLevel(Point p){
        return getGroundLevel(p.x,p.y);
    }

    private int RGBtoMetric(int h){
        return (int)((max-min)*h/255-min+0.5);
    }
    private Point cordToint(float x, float y){
       // x=
        return new Point();
    }
}
