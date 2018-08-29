package antonforsberg.chess.ActivityMVC.GamePhysics;

import java.util.ArrayList;
import java.util.List;


import antonforsberg.chess.Interfaces.DeltaTimeListener;

/**
 * Created by Anton Forsberg on 2017-12-16.
 */

public class UpdateLoop extends Thread {
    private double currentTime;
    private double deltaTime;
    private double LastTime;
    private boolean running;
    private List<DeltaTimeListener> deltaTimeListeners=new ArrayList<>(10);

    public void start(){
        super.start();
        if(!running) {
            running = true;
            loop();
        }
    }

    public void pause(){

        running=false;
    }

    public void addDeltaTimeListnerer(DeltaTimeListener L){
        deltaTimeListeners.add(L);
    }

    private void loop(){
        LastTime=System.currentTimeMillis();
        double elapsedtime;
        while (running){
            elapsedtime=System.currentTimeMillis()-LastTime;
            dtUpdate(elapsedtime);
            LastTime=elapsedtime;
        }
    }

    private void dtUpdate(double time){


        for (int i = 0; i <deltaTimeListeners.size() ; i++) {
            deltaTimeListeners.get(i).deltaTimeUpdate(time);
        }
        deltaTime=time;

    }
}
