package antonforsberg.chess.Chess.Game.Logic.PiecesLogic;

import android.content.Context;
import android.graphics.Point;

import antonforsberg.chess.Chess.ChessObjects.PiecesObject.KnightObject;
import antonforsberg.chess.Chess.ChessObjects.PiecesObject.PawnObject;
import antonforsberg.chess.Chess.Enums.ColorP;



import java.util.ArrayList;
import java.util.List;

public class Knight extends Pice{

    public Knight(Context context, int x, int y, ColorP c){
        super(context ,new Point(x,y),c);
    }

    @Override
    public List<Point> getPossibleMoves(Pice[][] board) {
        List<Point>moves=new ArrayList<>(10);
        int x=pos.x;
        int y=pos.y;
        int len= board.length;
        if(y+2<len && x+1<len &&(board[x+1][y+2]==null|| !board[x+1][y+2].getColur().equals(col)) ){
            moves.add(new Point(x+1,y+2));
        }
        if(y+2<len && x-1>-1 &&(board[x-1][y+2]==null|| !board[x-1][y+2].getColur().equals(col)) ){
            moves.add(new Point(x-1,y+2));
        }

        if(y-2>-1 && x+1<len &&(board[x+1][y-2]==null|| !board[x+1][y-2].getColur().equals(col)) ){
            moves.add(new Point(x+1,y-2));
        }
        if(y-2>-1 && x-1>-1 &&(board[x-1][y-2]==null|| !board[x-1][y-2].getColur().equals(col)) ){
            moves.add(new Point(x-1,y-2));
        }



        if(x+2<len && y+1<len &&(board[x+2][y+1]==null|| !board[x+2][y+1].getColur().equals(col)) ){
            moves.add(new Point(x+2,y+1));
        }
        if(x+2<len && y-1>-1 &&(board[x+2][y-1]==null|| !board[x+2][y-1].getColur().equals(col)) ){
            moves.add(new Point(x+2,y-1));
        }

        if(x-2>-1 && y+1<len &&(board[x-2][y+1]==null|| !board[x-2][y+1].getColur().equals(col)) ){
            moves.add(new Point(x-2,y+1));
        }
        if(x-2>-1 && y-1>-1 &&(board[x-2][y-1]==null|| !board[x-2][y-1].getColur().equals(col)) ){
            moves.add(new Point(x-2,y-1));
        }

        return moves;
    }

    @Override
    protected void setModel() {
        model=new KnightObject(context,this);
    }
}
