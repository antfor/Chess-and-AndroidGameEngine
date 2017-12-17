package antonforsberg.chess.Chess.Game.Logic.PiecesLogic;

import android.content.Context;
import android.graphics.Point;

import antonforsberg.chess.Chess.ChessObjects.PiecesObject.PawnObject;
import antonforsberg.chess.Chess.ChessObjects.PiecesObject.RookObject;
import antonforsberg.chess.Chess.Enums.ColorP;
import antonforsberg.chess.Chess.Game.Logic.GameLogic;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Pice{

    public Rook(Context context, int x, int y, ColorP c){
        super(context,new Point(x,y) , c );
    }

    @Override
    public List<Point> getPossibleMoves(Pice[][] board) {
        List<Point>moves=new ArrayList<>(15);
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
        return moves;
    }

    @Override
    protected void setModel() {
        model=new RookObject(context,this);
    }


}
