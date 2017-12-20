package antonforsberg.chess.Chess.Ui;

import android.content.Context;

import antonforsberg.chess.GUI.Buttons.Button2D;
import antonforsberg.chess.Global.GLview;
import antonforsberg.chess.R;
import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-12-19.
 */

public class Text extends Button2D {
    private Context context;
    public Text(Context mActivityContext) {
        super(mActivityContext);
        context=mActivityContext;
        viewUpdate();
        LoadObjectAssets o=new LoadObjectAssets(context);
        setimage(o.LoadImageAsset(R.drawable.paused));
    }

    @Override
    public void viewUpdate() {
        System.out.println((GLview.YDpToPixels(48*3+56*1)/GLview.height));
        button2dDpPerMiddle(0.5f,1,0.5f+(GLview.YDpToPixels(48*3+56*1)),1,246*1,56*1,3);
    }

    @Override
    public void function() {

    }

    public void showVictory(){
        LoadObjectAssets o=new LoadObjectAssets(context);
        setimage(o.LoadImageAsset(R.drawable.victory));
    }
    public void showPaused(){
        LoadObjectAssets o=new LoadObjectAssets(context);
        setimage(o.LoadImageAsset(R.drawable.paused));

    }

}
