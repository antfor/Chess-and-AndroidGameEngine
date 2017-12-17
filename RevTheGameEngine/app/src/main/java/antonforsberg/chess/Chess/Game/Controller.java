package antonforsberg.chess.Chess.Game;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;

import java.util.ArrayList;
import java.util.List;

import antonforsberg.chess.Chess.Buttons.KillButton;
import antonforsberg.chess.Chess.Buttons.MoveButton;
import antonforsberg.chess.Chess.Buttons.SelectedButton;
import antonforsberg.chess.Chess.Game.Logic.GameLogic;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pice;
import antonforsberg.chess.GUI.Buttons.Button3D;

/**
 * Created by Anton Forsberg on 2017-12-06.
 */

public class Controller {

    private List<SelectedButton> selectedButtons=new ArrayList<>(32);
    private List<MoveButton>moveButtons=new ArrayList<>(32);
    private List<KillButton>killButtons=new ArrayList<>(32);

    public void setMoveButtonstoadd(List<Point> moveButtonstoadd) {
        this.moveButtonstoadd = moveButtonstoadd;
    }
    public void setKillButtonstoadd(List<Point> killButtonstoadd) {
        this.killButtonstoadd = killButtonstoadd;
    }

    private List<Point> moveButtonstoadd=new ArrayList<>(20);
    private List<Point> killButtonstoadd=new ArrayList<>(20);

    private SelectedButton selectedButtonToDraw;
    private Context mActivityContext;
    private GameLogic logic;


    public Controller(Context mActivityContext, GameLogic logic){
        this.mActivityContext=mActivityContext;
        this.logic=logic;


    }



    public void ButtonpressEvent(float x, float y){

     for (KillButton k:killButtons) {
            if(k.isPressed(x,y)){
                return;
            }
        }
        for (MoveButton m: moveButtons){
            if(m.isPressed(x,y)){
                return;}
        }

        for (SelectedButton s:selectedButtons) {
            if(s.isPressed(x,y)){
                return;}
        }


    }

    public void addSelectButton(Pice p){
           selectedButtons.add(new SelectedButton(mActivityContext,p,logic));
    }

    public void addMoveButton(Point p){
        moveButtons.add(new MoveButton(mActivityContext,p,logic));
    }

    public void addKillButton(Point p){
        killButtons.add(new KillButton(mActivityContext,p,logic));
    }
    public void deleteKillAndMoveButton(){
        killButtons.clear();
        moveButtons.clear();
    }
    public void deleteALL(){
        deleteKillAndMoveButton();
        selectedButtons.clear();
        selectedButtonToDraw=null;
    }
    public void deleteOneSelectedButton(Pice pice){
        for (SelectedButton s:selectedButtons) {
         if(s.getPice()==pice){
             selectedButtons.remove(s);
             break;
         }
        }
    }


    private void   creatMoveAndKillButtons(){
        for (Point p:moveButtonstoadd) {
            addMoveButton(p);
        }
        for (Point p:killButtonstoadd) {
            addKillButton(p);
        }
        moveButtonstoadd.clear();
        killButtonstoadd.clear();
    }

    public void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){

        creatMoveAndKillButtons();
/*
        for (MoveButton m: moveButtons) {
            m.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,mModelMatrix);
        }
        for (KillButton k:killButtons) {
            k.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,mModelMatrix);
        }
        for (SelectedButton s:selectedButtons) {

            s.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,mModelMatrix);
        }
*/
        for (int i = 0; i <selectedButtons.size() ; i++) {
            selectedButtons.get(i).draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,mModelMatrix);
        }
        for (int i = 0; i <moveButtons.size() ; i++) {
            moveButtons.get(i).draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,mModelMatrix);
        }
        for (int i = 0; i <killButtons.size() ; i++) {
            killButtons.get(i).draw(mMVPMatrix, mProjectionMatrix, mViewMatrix,mModelMatrix);
        }

    }

    public void setSelectedButtonToDraw(SelectedButton selectedButtonToDraw) {
        this.selectedButtonToDraw = selectedButtonToDraw;
    }

    public void nonSelected(){
        selectedButtonToDraw=null;
    }

}
