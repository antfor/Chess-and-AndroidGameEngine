package antonforsberg.chess.GUI.Buttons;

import antonforsberg.chess.GUI.ButtonGUI;

/**
 * Created by Anton Forsberg on 2017-10-26.
 */

public class switchobject extends ButtonGUI {


    //bredd och höjd funkar bra att vara i dp men inte x och y
    // men de kan inte heller bara vara procent.
    // ska välja vilken "sida" som den ska ha sitt konstanta värde från
    //minus från höger och nere och positift uppe och vänster

    //gör en gör dp och en för procent om man vill ha ett crrosshair vill man använda procent istället för dp till exempel
   public switchobject(){
       ButtonInt(56,0 ,2*56, 1,56, 56);

      // ButtonIntPer(0.45f,0 ,0.45f, 1,0.1f, 0.1f);
   }

    public switchobject(float x ,int LorR, float y,int UorD, float with , float height){
        ButtonInt(x,LorR ,y, UorD,with, height);
    }

    public switchobject(float x ,int LorR, float y,int UorD, float with , float height , boolean per){
        ButtonIntPer(x,LorR ,y, UorD,with, height);
    }
}
