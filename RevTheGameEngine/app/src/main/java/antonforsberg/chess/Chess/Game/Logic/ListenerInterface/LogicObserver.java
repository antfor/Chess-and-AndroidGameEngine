package antonforsberg.chess.Chess.Game.Logic.ListenerInterface;

import antonforsberg.chess.Chess.Enums.ColorP;

/**
 * Created by Anton Forsberg on 2017-12-20.
 */

public interface LogicObserver {
    void gameVictory(ColorP colorP);
    void pawnUpgrade(ColorP colorP);

}
