package antonforsberg.chess.Chess.Game.Logic;

import android.content.Context;
import android.graphics.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import antonforsberg.chess.Chess.Buttons.SelectedButton;
import antonforsberg.chess.Chess.ChessObjects.BoardObject;
import antonforsberg.chess.Chess.Enums.ColorP;
import antonforsberg.chess.Chess.Enums.DeadorAlive;
import antonforsberg.chess.Chess.Game.Controller;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.MoveObserver;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.SelectedObserver;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.ThreatenedObserver;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pice;
import antonforsberg.chess. Chess.Player.Black;
import antonforsberg.chess.Chess.Player.Player;
import antonforsberg.chess. Chess.Player.White;
import antonforsberg.chess.comobject.RotQ;

public class GameLogic implements SelectedObserver , MoveObserver,ThreatenedObserver {


    private Context mActivityContext;
    private Pice[][] board;
    private BoardObject boardObject;
    private Pice selected;
    private Player currentPlayer;
    private Player watingPlayer;
    private Controller controller;

    public GameLogic(Context mActivityContext){
        this.mActivityContext=mActivityContext;
        boardObject=new BoardObject(mActivityContext);
        controller=new Controller(mActivityContext,this);
        currentPlayer=new White(mActivityContext);
        watingPlayer=new Black(mActivityContext);
        updateBoard();
        addSelectedButtons();
    }

    public void ButtonpressEvent(float x, float y){
        controller.ButtonpressEvent(x,y);
    }

    private void addSelectedButtons() {
        for (Pice p:currentPlayer.getPices()) {
            controller.addSelectButton(p);
        }
        for (Pice p:watingPlayer.getPices()) {
            controller.addSelectButton(p);

        }

    }

    public void draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){
        RotQ rotq=new RotQ();



    if(currentPlayer.getColor().equals(ColorP.Black)) {
        float[] modelM=new float[16];
        System.arraycopy(mModelMatrix, 0,    modelM , 0,    16);

       rotq.rotate(0,1,0,180);
        rotq.rotate(1,0,0,55);
       rotq.matrix(modelM);
        boardObject.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, modelM);
        controller.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, modelM);
        currentPlayer.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, modelM);
        watingPlayer.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, modelM);
    }
    else {
        rotq.rotate(1,0,0,55);
        rotq.matrix(mModelMatrix);
        boardObject.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
        controller.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
        currentPlayer.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
        watingPlayer.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
    }
    }

    private void NextTurn(){
        selected=null;
        controller.deleteKillAndMoveButton();
        controller.nonSelected();
        Player temp=currentPlayer;
        currentPlayer=watingPlayer;
        watingPlayer=temp;

    }

    private void moveSelectedTo(Point p){
        System.out.println(selected.getPos());
        board[selected.getPos().x][selected.getPos().y]=null;
        selected.Move(p);
        System.out.println(selected.getPos());
        board[selected.getPos().x][selected.getPos().y]=selected;

        NextTurn();
    }


    private void updateBoard(){
        board=new Pice[8][8];
        for (Pice p:currentPlayer.getPices()) {
                board[p.getPos().x][p.getPos().y]=p;
        }
        for (Pice p:watingPlayer.getPices()) {
                board[p.getPos().x][p.getPos().y]=p;
        }
    }


    private void setSelected(Pice pice){

        selected=pice;
        buttonsToRender();

    }

    public Pice getSelected(){
        return selected;
    }
    private void buttonsToRender(){

        controller.deleteKillAndMoveButton();

        List<Point> moves=selected.getPossibleMoves(board);//todo skicka inte iväg board för då kan de göra ändringar på den skicka en kopia istället


        List<Point> move=new ArrayList<>(20);
        List<Point> kill=new ArrayList<>(20);

        for (Point p:moves) {
          if(board[p.x][p.y]==null){
              //controller.addMoveButton(p);
              move.add(p);
          }
          else {
             // controller.addKillButton(p);
              kill.add(p);
          }
        }
        controller.setMoveButtonstoadd(move);
        controller.setKillButtonstoadd(kill);
    }

    @Override
    public void actOnSelectedButton(Pice pice, SelectedButton selectedButton){
        if( pice.getColur().equals(currentPlayer.getColor())){
            setSelected(pice);
            controller.setSelectedButtonToDraw(selectedButton);
        }

    }

    @Override
    public void actOnMoveButton(Point point) {
        moveSelectedTo(point);
    }

    @Override
    public void actOnKillButton(Point point) {
        killPice(point);
        moveSelectedTo(point);

    }

    private void killPice(Point point)
    {
        Pice p=board[point.x][point.y];
        watingPlayer.removePice(p);
        controller.deleteOneSelectedButton(p);
    }
}
