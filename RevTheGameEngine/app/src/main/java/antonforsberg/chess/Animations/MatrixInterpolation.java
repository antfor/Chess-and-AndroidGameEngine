package antonforsberg.chess.Animations;

import android.opengl.Matrix;

import java.util.Arrays;

import antonforsberg.chess.Interfaces.Draweble;
import antonforsberg.chess.comobject.Quat4d;
import antonforsberg.chess.comobject.RotQ;

/**
 * Created by Anton Forsberg on 2017-12-18.
 */

public class MatrixInterpolation {

    private double Starttime;
    private double animationLength;
    private boolean animating;
  //  private float[] end=new float[16];
  //  private float[] start=new float[16];
    private float[] middle=new float[16];

    public MatrixInterpolation(double timeLength){
        animationLength=timeLength;
    }

    public void startaAimate(){
        animating=true;
        Starttime=System.currentTimeMillis();
    }
    public void startaAimate(double length){
        animationLength=length;
        animating=true;
        Starttime=System.currentTimeMillis();
    }

   public float[] animate(float[] start ,float [] end){

       if(animating) {
           linear(start, end);
           return middle;
       }

       return end;
   }


   private void stopanimation(){
       animating=false;
   }

    // TODO: 2017-12-18 funkar kanske inte för rotation
   private void linear(float[] start ,float [] end){

       double timedif= (System.currentTimeMillis()-Starttime)/animationLength;



       if(timedif>1){
           timedif=1;
       }

       translatInterpolation(start,end,timedif);
       rotateInterpolation(start,end,timedif);
       scaleInterpolation(start,end,timedif);
       middle[15]=end[15];


       if(timedif==1){
           stopanimation();
       }

       System.out.println(Arrays.toString(start));
       System.out.println(Arrays.toString(middle));
       System.out.println(Arrays.toString(end));
       System.out.println("stop");

   }

    private void scaleInterpolation(float[] start, float[] end, double timedif) {
       float[] ss= RotQ.getScale(start);
       float[] se= RotQ.getScale(end);

        for (int i = 0; i <3 ; i++) {
            ss[i]=(float) (((se[i]-ss[i])*timedif)+ss[i]);
        }
        Matrix.scaleM(middle,0,ss[0],ss[1],ss[2]);
    }

    private void rotateInterpolation(float[] start, float[] end, double timedif) {
      RotQ rotQ = new RotQ();
        rotQ.getmatq(start);
        Quat4d quat4d =new Quat4d(rotQ.getq());
        rotQ.getmatq(end);
        quat4d.slerp(new Quat4d(rotQ.getq()),(float) timedif);
        rotQ.quatToMatrix(quat4d,middle);
    }


    private void translatInterpolation(float[] start,float[] end,double timedif){
       for (int i = 0; i <3 ; i++) {
           middle[i+12]=(float) (((end[i+12]-start[i+12])*timedif)+start[i+12]);
       }
   }
   public boolean isAnimating(){
       return animating;
   }

}
