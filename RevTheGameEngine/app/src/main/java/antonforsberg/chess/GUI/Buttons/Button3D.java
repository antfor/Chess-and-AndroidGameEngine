package antonforsberg.chess.GUI.Buttons;

import android.content.Context;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Vibrator;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import antonforsberg.chess.GUI.ScreenData;
import antonforsberg.chess.Global.GLview;
import antonforsberg.chess.R;
import antonforsberg.chess.comMesh.FinalMesh;
import antonforsberg.chess.comobject.LoadObjectAssets;

/**
 * Created by Anton Forsberg on 2017-12-01.
 */

public class Button3D {
    protected FinalMesh mesh=new FinalMesh();
    private Context mActivityContext;
   private float[] viweMatrix= new float[16];
    private float[] perspectiMatrix= new float[16];
   private float[] modelMatrix= new float[16];
    private boolean selected;
    private float[] colurs=new float[24];
    private float[] colornotslected=new float[24];
    private int pointerid;
    private List<float[]> corners=new ArrayList<>(4);




public  Button3D(Context mActivityContext){
    this.mActivityContext= mActivityContext;




    mesh.setShader("button","button");
    LoadObjectAssets o=new LoadObjectAssets(mActivityContext);
    mesh.loadtexture(o.LoadImageAsset(R.drawable.dpad));

    setTexturecoords();

    setButtonColor(0.25f,0.25f,0.25f,0.0f,colurs);
    setButtonColor(0f,0f,0f,0.0f, colornotslected);
    mesh.setColor(colornotslected);

    float squareCoords[] = {
            -1.0f,  1.0f, -4.0f,  //top left
            -1.0f, -1.0f, -4.0f,    //bottom left
            1.0f, -1.0f, -4.0f,     //bottom right
            1.0f, -1.0f, -4.0f,     //bottom right
            1.0f,  1.0f, -4.0f,     //top right
            -1.0f,  1.0f, -4.0f, }; // top left

    setvercords(squareCoords);

}



protected void settexture(int id){
    mesh.loadtexture(id);
}

private void setTexturecoords(){
    float texcords[] ={
            0.0f,0.0f,
            0.0f,1.0f,
            1.0f,1.0f,
            1.0f,1.0f,
            1.0f,0.0f,
            0.0f,0.0f

    };
    mesh.setTextureCoordinates(texcords);
}
protected void texcoords(float[] t){
    mesh.setTextureCoordinates(t);
}

public void setvercords(float[] v){
    setCorners(v);
    mesh.setVertices(v);
}
private void setCorners(float[] v){
    int k=0;
    corners.clear();
    for (int i = 0; i < v.length; ) {
        corners.add(new float[4]);
        for (int n = 0; n < 3&&i<v.length; n++, i++) {

            corners.get(k)[n]=v[i];

        }

        k++;
    }

     removedupe();
     vec_to_point();
}
private void  removedupe(){
    for (int i = 0; i < corners.size(); i++)  {
        for (int n = i+1; n <corners.size() ; n++) {

           if( Arrays.toString(corners.get(i)).equals(Arrays.toString(corners.get(n)))){
               corners.remove(n);

           }
        }
    }
}

    private void press(){

        vibrate();
        function();

    }
    public boolean isPressed(float x ,float y){
        List<Point>cord=get2Dcords();

        int xmin=cord.get(0).x;
        int xmax=cord.get(0).x;
        int ymin=cord.get(0).y;
        int ymax=cord.get(0).y;
        for (Point p: cord) {
            if(p.x<xmin){xmin=p.x;}
            else if(p.x>xmax){xmax=p.x;}
            if(p.y<ymin){ymin=p.y;}
            else if(p.y>ymax){ymax=p.y;}
        }

        if ( x  >= xmin && x  <= xmax && y  >= ymin && y  <=ymax) {

            if(point_inside_button(new Point((int)(x+0.5),(int)(y+0.5)) ,cord )){ //kan bara använda den här.
                press();
                return true;
            }
        }

        return false;
    }

   public void  draw(float [] mMVPMatrix,float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){
       mesh.draw(mMVPMatrix,mProjectionMatrix,mViewMatrix, mModelMatrix);

       uptadeModelMatrix(mProjectionMatrix,mViewMatrix,mModelMatrix);
   }

   protected void uptadeModelMatrix(float [] mProjectionMatrix,float [] mViewMatrix,float [] mModelMatrix){
       System.arraycopy(mViewMatrix, 0, viweMatrix , 0,16);
       System.arraycopy(mProjectionMatrix, 0,   perspectiMatrix , 0,   16);
       System.arraycopy(mModelMatrix, 0,    modelMatrix , 0,    16);

   }
    private Point get2dPoint(float [] point, float [] viewMatrix,
                             float [] projectionMatrix , float[] mModelMatrix) {



        float [] viewProjectionMatrix=new float[16];
        Matrix.multiplyMM(viewProjectionMatrix, 0, viewMatrix, 0,mModelMatrix , 0);


       Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewProjectionMatrix, 0);

        float[] point3D = new float[point.length];
        Matrix.multiplyMV(point3D,0,viewProjectionMatrix,0,point,0);

        int winX = (int) Math.round((( point3D[0]/point3D[3] + 1 ) / 2.0) *GLview.width);

        int winY = (int) Math.round((( 1 - point3D[1]/point3D[3] ) / 2.0) *GLview.height);
      //  System.out.println(winX+"  ff "+winY);
      //  System.out.println(GLview.width+"  dd "+GLview.height);
        return new Point(winX,winY);
    }
   public FinalMesh getMesh(){
       return mesh;
   }

   public void setimage(int id){
       mesh.loadtexture(id);
   }


   protected void  vibrate(){
       Vibrator v = (Vibrator) mActivityContext.getSystemService(mActivityContext.VIBRATOR_SERVICE);

       v.vibrate(1);
   }
    protected void setButtonColor(float r,float g, float b,float a,float []colurs){
        int n = 0;
        for (int i = 0; i < colurs.length; i++) {
            if (n == 0) {
                colurs[i] = r;
                n++;
            } else if (n == 1) {
                colurs[i] = g;
                n++;
            } else if (n == 2) {
                colurs[i] = b;
                n++;
            } else if (n == 3) {
                colurs[i] = a;
                n = 0;
            }
        }

    }

    public List<Point > get2Dcords() {
         List<Point > win=new ArrayList<>(4);
        for (float[] c:corners) {
           win.add(get2dPoint(c,viweMatrix,perspectiMatrix,modelMatrix));
        }
        return win;
    }

    private boolean point_inside_button(Point point,List<Point> corners){
        for (int i = 0; i <corners.size() ; i++) {

          if( point_inside_trigon(point,corners.get(((i+1)%corners.size())),corners.get(((i+2)%corners.size())),corners.get(((i+3)%corners.size()))) ){
              return true;
          }

        }


        return false;
    }
    private boolean  point_inside_trigon(Point s, Point a, Point b, Point c)
    {
        int as_x = s.x-a.x;
        int as_y = s.y-a.y;

        boolean s_ab = (b.x-a.x)*as_y-(b.y-a.y)*as_x > 0;

        if((c.x-a.x)*as_y-(c.y-a.y)*as_x > 0 == s_ab) return false;

        if((c.x-b.x)*(s.y-b.y)-(c.y-b.y)*(s.x-b.x) > 0 != s_ab) return false;

        return true;
    }

    private void vec_to_point(){
        for (float[] arr: corners) {
            for (int i = 3; i < arr.length; i=i+4) {
                arr[i]=1;
            }
        }
    }

     public void function(){}
}
