package antonforsberg.chess.Chess.Game.Logic.ListenerInterface;

import android.graphics.Point;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public interface MoveObserver {
    void actOnMoveButton(Point point);
}
