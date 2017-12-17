package antonforsberg.chess.Chess.Game.Logic.ListenerInterface;

import antonforsberg.chess.Chess.Buttons.SelectedButton;
import antonforsberg.chess.Chess.Game.Logic.PiecesLogic.Pice;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public interface SelectedObserver {
    void actOnSelectedButton(Pice p, SelectedButton selectedButton);
}
