package antonforsberg.chess.GUI;

/**
 * Created by Anton Forsberg on 2017-12-06.
 */

public class ScreenData {

    private static int width;
    private static int height;
    private static float ratio;

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        ScreenData.width = width;
    }


    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        ScreenData.height = height;
    }



    public static float getRatio() {
        return ratio;
    }

    public static void setRatio(float ratio) {
        ScreenData.ratio = ratio;
    }


}
