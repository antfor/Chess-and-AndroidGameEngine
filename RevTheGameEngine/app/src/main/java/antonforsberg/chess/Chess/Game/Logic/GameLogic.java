package antonforsberg.chess.Chess.Game.Logic;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import antonforsberg.chess.Animations.IMatrixInterpolation;
import antonforsberg.chess.Animations.IisInterpolating;
import antonforsberg.chess.Animations.MatrixInterpolation;
import antonforsberg.chess.Chess.Buttons.SelectedButton;
import antonforsberg.chess.Chess.ChessObjects.BoardObject;
import antonforsberg.chess.Chess.Enums.ColorP;
import antonforsberg.chess.Chess.Game.Controller;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.LogicObserver;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.MoveObserver;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.SelectedObserver;
import antonforsberg.chess.Chess.Game.Logic.ListenerInterface.ThreatenedObserver;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.King;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pawns;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pice;
import antonforsberg.chess. Chess.Player.Black;
import antonforsberg.chess.Chess.Player.Player;
import antonforsberg.chess. Chess.Player.White;
import antonforsberg.chess.comobject.RotQ;
import antonforsberg.chess.comobject.objectAssets.ObjectInterface.BasicObject;

public class GameLogic implements SelectedObserver , MoveObserver,ThreatenedObserver {


    private Context mActivityContext;
    private Pice[][] board;
    private BoardObject boardObject;
    private Pice selected;
    private Player currentPlayer;
    private Player watingPlayer;
    private Controller controller;
    private boolean playerchange;
    private boolean PiceAnimation;
    private boolean addbuttons;

    private float[] blackModelMatrix= new float[16];
    private float[] whiteModelMatrix= new float[16];
    private float[] start= whiteModelMatrix;
    private float[] end= blackModelMatrix;
    private MatrixInterpolation matrixInterpolation =new MatrixInterpolation(500);
    private List<BasicObject> uiObjects=new ArrayList<>(10);
    private LogicObserver logicObserver;
    private List<Pice[][]> storeboard=new ArrayList<>(10);

    public GameLogic(Context mActivityContext){
        this.mActivityContext=mActivityContext;
        boardObject=new BoardObject(mActivityContext);
        controller=new Controller(mActivityContext,this);
        currentPlayer=new White(mActivityContext);
        watingPlayer=new Black(mActivityContext);

        updateBoard();
        storeboard();
        addSelectedButtons();
    }


    public void setLogicObserver(LogicObserver o){
        logicObserver=o;
    }
    public void addUiObject(BasicObject o){
        uiObjects.add(o);
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
        if(addbuttons){
            addSelectedButtons();
            addbuttons=false;
        }
        RotQ rotq=new RotQ();

        System.arraycopy(mModelMatrix, 0,    whiteModelMatrix , 0,    16);
        rotq.rotate(0,1,0,-180);
        rotq.rotate(1,0,0,55);
        rotq.matrix(whiteModelMatrix);

        rotq.startrot();

        System.arraycopy(mModelMatrix, 0,    blackModelMatrix , 0,    16);
        rotq.rotate(1,0,0,55);
        rotq.matrix(blackModelMatrix);

        Interpoltating();

        if(!PiceAnimation && playerchange){


            playerchange=false;
            if(currentPlayer.getColor().equals(ColorP.White)){

                start=whiteModelMatrix;
                end=blackModelMatrix;
            }
            else {
                start=blackModelMatrix;
                end=whiteModelMatrix;
            }
            matrixInterpolation.startaAimate();
        }

        float[] model= matrixInterpolation.animate(start,end);


        boardObject.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, model);
        controller.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, model);
        currentPlayer.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, model);
        watingPlayer.draw(mMVPMatrix, mProjectionMatrix, mViewMatrix, model);


    }


    private void NextTurn(){
        selected=null;
        controller.deleteKillAndMoveButton();
        controller.nonSelected();
        Player temp=currentPlayer;
        currentPlayer=watingPlayer;
        watingPlayer=temp;
        playerchange=true;
    }

    private void moveSelectedTo(Point p){

        board[selected.getPos().x][selected.getPos().y]=null;
        selected.Move(p);

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
        if((!matrixInterpolation.isAnimating() &&!PiceAnimation )&& pice.getColur().equals(currentPlayer.getColor())){
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
        if(p instanceof King){
           logicObserver.gameVictory(p.getColur());
        }
        watingPlayer.removePice(p);
        controller.deleteOneSelectedButton(p);
    }

    private void Interpoltating(){
       List<Pice> l1=currentPlayer.getPices();
        List<Pice> l2= watingPlayer.getPices();
        for (int i = 0; i <l1.size() ; i++) {
            if(l1.get(i).interpoltate()){
                PiceAnimation=true;
                return;
            }
        }
        for (int i = 0; i <l2.size() ; i++) {
            if(l2.get(i).interpoltate()){
                PiceAnimation=true;
                return;
            }
        }

        PiceAnimation=false;
    }


    private void storeboard(){
        storeboard.add(copyArray(board));
    }

    public static Pice[][] copyArray(Pice[][] src) {
        int length = src.length;
        Pice[][] target = new Pice[length][src[0].length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(src[i], 0, target[i], 0, src[i].length);
        }
        return target;
    }

    public void restartGame(){
        if(currentPlayer.getColor().equals(ColorP.Black)){
            Player temp= currentPlayer;
            currentPlayer=watingPlayer;
            watingPlayer=temp;
            playerchange=true;
        }

        currentPlayer.reviveAll();
        watingPlayer.reviveAll();
        setandmove(storeboard.get(0));
        controller.deleteALL();
       // addSelectedButtons();
        addbuttons=true;


    }
    public void setandmove(Pice[][] board){
        this.board=copyArray(board);
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n <8 ; n++) {
                if(board[i][n]!=null){
                    board[i][n].Move(new Point(i,n));
                    if( board[i][n] instanceof Pawns){

                        ((Pawns)board[i][n]).setFirstmoveTrue();
                    }
                }
            }
        }
    }
}
