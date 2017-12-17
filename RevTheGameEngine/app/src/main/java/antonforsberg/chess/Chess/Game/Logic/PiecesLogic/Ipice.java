package antonforsberg.chess.Chess.Game.Logic.PiecesLogic;


import android.graphics.Point;

public interface Ipice {

   Point getPos();
   void Move(Point p);
   void MoveAnimation(Point p);

}
