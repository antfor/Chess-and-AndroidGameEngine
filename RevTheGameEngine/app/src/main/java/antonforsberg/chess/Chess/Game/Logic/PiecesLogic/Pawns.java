package antonforsberg.chess.Chess.Game.Logic.PiecesLogic;

import android.content.Context;
import android.graphics.Point;

import antonforsberg.chess.Chess.ChessObjects.PiecesObject.PawnObject;
import antonforsberg.chess.Chess.Enums.ColorP;
import antonforsberg.chess.Chess.Game.Logic.GameLogic;

import java.util.ArrayList;

public class Pawns extends Pice {

    private boolean firstmove =true;


    public Pawns(Context context,Point p, ColorP c){
    super(context,p,c);

    }

    @Override
    public void Move(Point p) {
        super.Move(p);
        firstmove=false;
    }


    public Pawns(Context context,int x,int y, ColorP c){
        super( context,new Point(x,y),c);
    }

    public ArrayList <Point> getPossibleMoves(Pice[][] board) {
        ArrayList<Point> p=new ArrayList<>(5);
        int row=1;
        if(col.equals(ColorP.Black)){row=-1;}

        //när den flyttar sig för första gången se till så att firstmove ändras till false

            if((pos.x+row<board.length && pos.x+row>-1) && board[pos.x+row][pos.y]==null){
                p.add(new Point(pos.x+row,pos.y));

                if(firstmove &&board[pos.x+row*2][pos.y]==null){
                    p.add(new Point(pos.x+row*2,pos.y));
                }
            }

            if( pos.y+1<board.length && pos.x+row<board.length && pos.x+row>-1 && (board[pos.x+row][pos.y+1]!=null)&& !board[pos.x+row][pos.y+1].col.equals(col) ){
                p.add(new Point(pos.x+row,pos.y+1));
            }

            if(( pos.y-1>-1)&&(pos.x+row<board.length && pos.x+row>-1) && board[pos.x+row][pos.y-1]!=null && !board[pos.x+row][pos.y-1].col.equals(col)){
                p.add(new Point(pos.x+row,pos.y-1));
            }





        return p;
    }

    @Override
    protected void setModel() {
        model=new PawnObject(context,this);
    }
}
