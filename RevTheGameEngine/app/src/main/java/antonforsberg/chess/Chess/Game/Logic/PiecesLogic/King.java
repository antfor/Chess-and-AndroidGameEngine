package antonforsberg.chess.Chess.Game.Logic.PiecesLogic;

import android.content.Context;
import android.graphics.Point;

import antonforsberg.chess.Chess.ChessObjects.PiecesObject.KingObject;
import antonforsberg.chess.Chess.ChessObjects.PiecesObject.PawnObject;
import antonforsberg.chess.Chess.Enums.ColorP;

import java.util.ArrayList;
import java.util.List;

public class King extends Pice{

    public King(Context context, int x, int y, ColorP c){
        super(context,new Point(x,y),c);
    }

    @Override
    public List<Point> getPossibleMoves(Pice[][] board) {
       List<Point> moves =new ArrayList<>(10);
       int x=pos.x;
       int y=pos.y;
       int len=board.length;
       if(x+1<len&&(board[x+1][y]==null||!board[x+1][y].getColur().equals(col))){
           moves.add(new Point(x+1,y));
       }
        if(x-1>-1&&(board[x-1][y]==null||!board[x-1][y].getColur().equals(col))){
            moves.add(new Point(x-1,y));
        }

        if(y+1<len&&(board[x][y+1]==null||!board[x][y+1].getColur().equals(col))){
            moves.add(new Point(x,y+1));
        }
        if(y-1>-1&&(board[x][y-1]==null||!board[x][y-1].getColur().equals(col))){
            moves.add(new Point(x,y-1));
        }

        if(x-1>-1&&y-1>-1&&(board[x-1][y-1]==null||!board[x-1][y-1].getColur().equals(col))){
            moves.add(new Point(x-1,y-1));
        }
        if(x+1<len&&y+1<len&&(board[x+1][y+1]==null||!board[x+1][y+1].getColur().equals(col))){
            moves.add(new Point(x+1,y+1));
        }

        if(x+1<len && y-1>-1 &&(board[x+1][y-1]==null||!board[x+1][y-1].getColur().equals(col))){
            moves.add(new Point(x+1,y-1));
        }
        if(y+1<len && x-1>-1 &&(board[x-1][y+1]==null||!board[x-1][y+1].getColur().equals(col))){
            moves.add(new Point(x-1,y+1));
        }
        return moves;
    }

    @Override
    protected void setModel() {
        model=new KingObject(context,this);
    }
}
