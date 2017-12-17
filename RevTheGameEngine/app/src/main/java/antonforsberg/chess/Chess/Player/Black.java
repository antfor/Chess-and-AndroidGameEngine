package antonforsberg.chess. Chess.Player;

import android.content.Context;

import antonforsberg.chess.Chess.Game.Logic.GameLogic;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Bishop;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.King;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Knight;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pawns;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Queen;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Rook;
import antonforsberg.chess.Chess.Enums.ColorP;


public class Black extends Player{


    public Black(Context context){
        super(ColorP.Black);
        ColorP color=ColorP.Black;
      pices.add(new Pawns(context,6,0, color));
        pices.add(new Pawns(context,6,1, color));
       pices.add(new Pawns(context,6,2, color));
        pices.add(new Pawns(context,6,3, color));
        pices.add(new Pawns(context,6,4, color));
        pices.add(new Pawns(context,6,5, color));
        pices.add(new Pawns(context,6,6, color));
        pices.add(new Pawns(context,6,7, color));
 /*
        pices.add(new Pawns(context,7,0, color));
        pices.add(new Pawns(context,7,1, color));
        pices.add(new Pawns(context,7,2, color));
        pices.add(new Pawns(context,7,4, color));
        pices.add(new Pawns(context,7,3, color));
        pices.add(new Pawns(context,7,5, color));
        pices.add(new Pawns(context,7,6, color));
        pices.add(new Pawns(context,7,7, color));
*/

       pices.add(new Rook(context,7,0, color));
        pices.add(new Knight(context,7,1, color));
       pices.add(new Bishop(context,7,2, color));
        pices.add(new King(context,7,4, color));
        pices.add(new Queen(context,7,3, color));
        pices.add(new Bishop(context,7,5, color));
        pices.add(new Knight(context,7,6, color));
        pices.add(new Rook(context,7,7, color));
 // */
    }
}
