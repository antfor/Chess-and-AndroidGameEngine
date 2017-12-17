package antonforsberg.chess.Chess.General;

/**
 * Created by Anton Forsberg on 2017-12-05.
 */

public class Tupel<L,R> {

    private L left;
    private R right;

    public Tupel(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() { return left; }
    public R getRight() { return right; }

}
