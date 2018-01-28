package antonforsberg.chess.comMesh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Forsberg on 2018-01-13.
 */

public class Lightning {
    private List<light>lights= new ArrayList<>(10);
    public void addLight(light l){
        lights.add(l);
    }

    public void generateShadowmap(light l){

    }


}
