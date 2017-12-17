package antonforsberg.chess.comobject;

import java.util.ArrayList;

/**
 * Created by Anton Forsberg on 2017-07-13.
 */

public class Objecthelper {

 private static int len;

    public static float[] verS(String ALL){

        return sub(ALL.substring(ALL.indexOf("v")+1, ALL.lastIndexOf("v")));
    }

    public static float[] norS(String ALL){

        return sub(ALL.substring(ALL.indexOf("n")+1, ALL.lastIndexOf("n")));
    }

    public static float[] texS(String ALL){

        return sub( ALL.substring(ALL.indexOf("t")+1, ALL.lastIndexOf("t")));
    }

    public static float[] sub(String data) {
        ArrayList<Float> ver = new ArrayList<Float>();
        data.trim();
        while (true) {

            if (data.equals(",")) {
                break;
            }
         //   System.out.println(data.substring(data.indexOf(",") + 1, (data.substring(data.indexOf(",") + 1).indexOf(",") + 1)));
            ver.add(Float.parseFloat(
                    data.substring(data.indexOf(",") + 1, (data.substring(data.indexOf(",") + 1).indexOf(",") + 1))));
            data = data.substring(data.substring(data.indexOf(",") + 1).indexOf(",") + 1);

        }

        int  n=0;
        float[] bob=new float[ver.size()];
        for (float i:ver) {
            bob[n]=i;
            n++;
        }

        return bob;
    }


}
