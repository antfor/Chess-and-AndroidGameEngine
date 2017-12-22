package antonforsberg.chess. Chess.Player;

import antonforsberg.chess.Chess.Enums.ColorP;
import antonforsberg.chess.Chess.Enums.DeadorAlive;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pice;

import java.util.ArrayList;
import java.util.List;

public class Player {


    protected List<Pice> pices=new ArrayList<>(16);
    private List<Pice> dead=new ArrayList<>(16);
    private ColorP color;

    public Player(ColorP colorp){
        color=colorp;
    }
    public List<Pice> getPices(){
        return pices;
    }

    public ColorP getColor(){
        return color;
    }

    public void removePice(Pice pice){
        dead.add(pice);
        pices.remove(pice);
    }


    public void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){
        for (int i = 0; i < pices.size(); i++) {
            pices.get(i).draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,mModelMatrix);
        }
    }

    public void reviveAll(){
        pices.addAll(dead);
        dead.clear();
    }

}
