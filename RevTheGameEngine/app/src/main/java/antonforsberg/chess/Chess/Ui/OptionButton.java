package antonforsberg.chess.Chess.Ui;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import antonforsberg.chess.GUI.Buttons.Button2D;
import antonforsberg.chess.Interfaces.Desplay;
import antonforsberg.chess.R;
import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-12-19.
 */

public class OptionButton extends Button2D {

private Context context;
private optionback back;
private pausedText pausedText;
private boolean preesed;
private List<Desplay> buttons=new ArrayList<>(5);
    public OptionButton(Context mActivityContext) {
        super(mActivityContext);
        context=mActivityContext;
        loadAssets();
        viewUpdate();
        back=new optionback(context);
        pausedText=new pausedText(context);


    }

    public void addButton(Desplay b){
        buttons.add(b);
    }

    @Override
    public void viewUpdate() {
        button2dDp(24,0,24,0,48,48,3);
    }

    private void loadAssets(){
        LoadObjectAssets o=new LoadObjectAssets(context);
        setimage(o.LoadImageAsset(R.drawable.settingsbutton));

    }
    @Override
    public void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){
        if(preesed){
            back.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
            pausedText.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
            for (int i = 0; i < buttons.size(); i++) {
                buttons.get(i).draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);

            }
        }

        super.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
    }

    @Override
    public void function() {
    preesed=!preesed;
    }

    public boolean isPressed(float x ,float y){
        super.isPressed(x,y);
    if(preesed) {
    for (int i = 0; i < buttons.size(); i++) {
        if(buttons.get(i).isPressed(x, y)){
            preesed=false;
        }
    }
    }
        return false;
    }
}
