package antonforsberg.chess.Global;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Forsberg on 2017-12-17.
 */

public class GLview {

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

}
