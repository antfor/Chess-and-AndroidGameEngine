package antonforsberg.chess.Chess.Game.Logic.PiecesLogic;

import android.content.Context;
import android.graphics.Point;

import antonforsberg.chess.Chess.ChessObjects.PiecesObject.PawnObject;
import antonforsberg.chess.Chess.ChessObjects.PiecesObject.QueenObject;
import antonforsberg.chess.Chess.Enums.ColorP;
import antonforsberg.chess.Chess.Game.Logic.GameLogic;


import java.util.ArrayList;
import java.util.List;

public class Queen extends Pice{

    public Queen(Context context,int x, int y, ColorP c){
        super(context,new Point(x,y),c);
    }

    @Override
    public List<Point> getPossibleMoves(Pice[][] board) {
        List<Point> moves=new ArrayList<>(30);
        int i=1;
        int len=board.length;
        int x=pos.x;
        int y=pos.y;
        while (x+i<len){

            if(board[x+i][y]==null){
                moves.add(new Point(x+i,y));
            }
            else if(!board[x+i][y].getColur().equals(col)){
                moves.add(new Point(x+i,y));
                break;
            }
            else {break;}

            i++;
        }
        i=1;
        while (x-i>-1){
            if(board[x-i][y]==null){
                moves.add(new Point(x-i,y));
            }
            else if(!board[x-i][y].getColur().equals(col)){
                moves.add(new Point(x-i,y));
                break;
            }
            else {break;}

            i++;
        }

        i=1;
        while (y+i<len){
            if(board[x][y+i]==null){
                moves.add(new Point(x,y+i));
            }
            else if(!board[x][y+i].getColur().equals(col)){
                moves.add(new Point(x,y+i));
                break;
            }
            else {break;}

            i++;
        }
        i=1;
        while (y-i>-1){
            if(board[x][y-i]==null){
                moves.add(new Point(x,y-i));
            }
            else if(!board[x][y-i].getColur().equals(col)){
                moves.add(new Point(x,y-i));
                break;
            }
            else {break;}

            i++;
        }




        i=1;
        while (x + i < len && y + i < len) {
            if (board[x+i][y+i]==null) {
                moves.add(new Point(x + i, y + i));
            } else if (!board[x+i][y+i].getColur().equals(col)) {
                moves.add(new Point(x + i, y + i));
                break;
            } else {
                break;
            }
            i++;
        }
        i=1;
        while (x - i > -1 && y - i > -1) {
            if (board[x-i][y-i]==null) {
                moves.add(new Point(x - i, y - i));
            } else if (!board[x-i][y-i].getColur().equals(col)) {
                moves.add(new Point(x - i, y - i));
                break;
            } else {
                break;
            }
            i++;
        }

        i=1;
        while (x + i < len && y - i > -1) {
            if (board[x+i][y-i]==null) {
                moves.add(new Point(x + i, y - i));
            } else if (!board[x+i][y-i].getColur().equals(col)) {
                moves.add(new Point(x + i, y - i));
                break;
            } else {
                break;
            }
            i++;
        }

        i=1;
        while (x - i > -1 && y + i < len) {
            if (board[x-i][y+i]==null) {
                moves.add(new Point(x - i, y + i));
            } else if (!board[x-i][y+i].getColur().equals(col)) {
                moves.add(new Point(x - i, y + i));
                break;
            } else {
                break;
            }
            i++;
        }
        return moves;
    }

    @Override
    protected void setModel() {
        model=new QueenObject(context,this);
    }
}
