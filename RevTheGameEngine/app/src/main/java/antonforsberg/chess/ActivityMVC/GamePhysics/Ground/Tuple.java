package antonforsberg.chess.ActivityMVC.GamePhysics.Ground;

import android.support.annotation.NonNull;

import java.util.Objects;

public class Tuple<T,A>{

    public void setPx(T   px) {
        this.px = px;
        x=px;
    }

    public void setPy(A py) {
        this.py = py;
        y=py;
    }

    private T px;
    private A py;

    public T x;
    public A y;

    public Tuple(T x,A y){

    }

}
