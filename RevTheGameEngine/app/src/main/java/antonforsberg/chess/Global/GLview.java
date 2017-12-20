package antonforsberg.chess.Global;

import android.content.Context;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Forsberg on 2017-12-17.
 */

public class GLview {
    public static Context mActivityContext;
    private GLview() {}

    private static List<GLviewListener> listeners=new ArrayList<>(15);
    public static float width;
    public static float height;
    public static float ratio;

    public static void updated(){
        for (int i = 0; i < listeners.size(); i++) {
            listeners.get(i).viewUpdate();
        }
    }

    public static void addListener(GLviewListener L){
        listeners.add(L);
    }

    public static float XDpToPixels(float x){
        DisplayMetrics displayMetrics=mActivityContext.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi*x/160.0f)/GLview.width;

    }
    public static float YDpToPixels(float y){
        DisplayMetrics displayMetrics=mActivityContext.getResources().getDisplayMetrics();
       return (displayMetrics.ydpi*y/160.0f)/GLview.height;
    }
    public static float XPixelToDp(float x){
        DisplayMetrics displayMetrics=mActivityContext.getResources().getDisplayMetrics();
        return x*width*160/displayMetrics.xdpi;
    }
    public static float YPixelToDp(float y){
        DisplayMetrics displayMetrics=mActivityContext.getResources().getDisplayMetrics();
        return y*height*160/displayMetrics.ydpi;
    }
}
