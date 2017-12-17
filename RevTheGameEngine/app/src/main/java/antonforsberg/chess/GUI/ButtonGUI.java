package antonforsberg.chess.GUI;

/**
 * Created by Anton Forsberg on 2017-10-07.
 */


import android.content.Context;
import android.os.Vibrator;
import android.util.DisplayMetrics;

import antonforsberg.chess.R;
import antonforsberg.chess.comMesh.FinalMesh;
import antonforsberg.chess.comobject.LoadObjectAssets;


//Buttons can be on the layer 0 to 1
public class ButtonGUI extends FinalMesh  {

    private float x,y,with,height;
    private float[] colurs=new float[24];
    private float[] colornotslected=new float[24];
  //  private float[] colnotactive=new float[24];
    private  boolean presed;
    private  boolean selected;
    private int pointerid;

    private static Context mActivityContext;
    private static float ratio;
    private static float scwidth,scheight;
    private static float[] cam;
    private static float dist;

    public ButtonGUI(){

        LoadObjectAssets load=new LoadObjectAssets(mActivityContext);
        loadtexture (load.LoadImageAsset(R.drawable.actionbuttongoogle));
        setButtonColor(0.25f,0.25f,0.25f,0.0f,colurs);
        setButtonColor(0f,0f,0f,0.0f, colornotslected);
        pointerid=-2;
        dist=0f;

    }
    //context,ratio,scwidth,scheigt
   public static void ButtonOncreate(Context context, float[] cams, float ratios, float scwidths, float scheights){
        mActivityContext= context;
        cam=cams;
        ratio=ratios;
        scwidth=scwidths;
        scheight=scheights;

    }


    void setCamRatioScWidthScHeight(float[] cam,float ratio,float scwidth,float scheight){

        if(cam!=null){this.cam=cam;}
        if(ratio!=-1){this.ratio=ratio;}
        if(scwidth!=-1){this.scwidth=scwidth;}
        if(scheight!=-1){this.scheight=scheight;}

    }


    public void ButtonInt(float x ,int LorR, float y,int UorD, float with , float height ){
        DisplayMetrics displayMetrics=mActivityContext.getResources().getDisplayMetrics();

        //fixa så att om man tar 1 på lor uord så blir det x respektive y gånger två
        x= Math.abs(LorR-((displayMetrics.xdpi*x/160.0f)/scwidth));
        y= Math.abs(UorD-((displayMetrics.ydpi*y/160.0f)/scheight));

        with=(displayMetrics.xdpi*with/160.0f)/scwidth;;
        height= (displayMetrics.ydpi*height/160.0f)/scheight;

        this.with=with;
        this.height=height;
        this.x=x;
        this.y=y;


       float texcords[] ={
               0.0f,0.0f,
               0.0f,1.0f,
               1.0f,1.0f,
               1.0f,1.0f,
               1.0f,0.0f,
               0.0f,0.0f

       };
       setTextureCoordinates(texcords);

       x=(x-0.5f)*2*ratio;
       y=(y-0.5f)*2;



       height=this.height*2;
       with=this.with*2*ratio;


        float squareCoords[] = {
                cam[0]+  0.0f+x,cam[1]+   0.0f -y, cam[2]+ dist,  //top left
                cam[0]+  0.0f+x,cam[1]-  height-y,cam[2]+ dist,    //bottom left
                cam[0]+  with+x,cam[1]-  height-y,cam[2]+ dist,     //bottom right
                cam[0]+   with+x,cam[1]-  height-y,cam[2]+ dist,     //bottom right
                cam[0]+   with+x,cam[1]+   0.0f-y,cam[2]+ dist,     //top right
                cam[0]+   0.0f+x,cam[1]+   0.0f-y,cam[2]+ dist, }; // top left

        setVertices(squareCoords);
        setShader("button","button");

               /* float squareCoords[] = {
                -1.0f,  1.0f, -1.0f,  //top left
                -1.0f, -1.0f, -1.0f,    //bottom left
                1.0f, -1.0f, -1.0f,     //bottom right
                1.0f, -1.0f, -1.0f,     //bottom right
                1.0f,  1.0f, -1.0f,     //top right
                -1.0f,  1.0f, -1.0f, }; // top left
*/
    }
    //göt typ samma sak
    public void ButtonIntPer(float x ,int LorR, float y,int UorD, float with , float height ){


        x=Math.abs(LorR-x);
        y=Math.abs(UorD-y);
        this.with=with;
        this.height=height;
        this.x=x;
        this.y=y;


        float texcords[] ={
                0.0f,0.0f,
                0.0f,1.0f,
                1.0f,1.0f,
                1.0f,1.0f,
                1.0f,0.0f,
                0.0f,0.0f

        };
        setTextureCoordinates(texcords);

        x=(x-0.5f)*2*ratio;
        y=(y-0.5f)*2;



        height=height*2;
        with=with*2*ratio;


        float squareCoords[] = {
                cam[0]+  0.0f+x,cam[1]+   0.0f -y, cam[2]+ dist,  //top left
                cam[0]+  0.0f+x,cam[1]-  height-y,cam[2]+ dist,    //bottom left
                cam[0]+  with+x,cam[1]-  height-y,cam[2]+ dist,     //bottom right
                cam[0]+   with+x,cam[1]-  height-y,cam[2]+ dist,     //bottom right
                cam[0]+   with+x,cam[1]+   0.0f-y,cam[2]+ dist,     //top right
                cam[0]+   0.0f+x,cam[1]+   0.0f-y,cam[2]+ dist, }; // top left

        setVertices(squareCoords);
        setShader("button","button");
    }

    void ButtonInt(int x , int y, int with ){

    }

    protected void setImage(int resourceId){
        LoadObjectAssets load=new LoadObjectAssets(mActivityContext);
        loadtexture (load.LoadImageAsset(resourceId));
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

  //  void addPressedColor(boolean b){}

   public boolean actionDown(double x,double y,int id){

       if( x/scwidth>=this.x&& x/scwidth<=this.x+with&&   y/scheight>=this.y&& y/scheight<=this.y+height){

            pointerid=id;
            selected=true;
            return true;
        }

        return false;
    }

    public boolean actionUp(double x,double y,int id){
    if(id == pointerid) {
        pointerid = -2;
        selected = false;
        if ( x / scwidth >= this.x && x / scwidth <= this.x + with && y / scheight >= this.y && y / scheight <= this.y + height) {
            setPressed(true);
            return true;
        }

    }
        return false;
    }
    public void setPressed(boolean b){
        presed=b;
        if(b){
            Vibrator v = (Vibrator) mActivityContext.getSystemService(mActivityContext.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(1);
            selected=false;
        }
    }

    public void action(){
       if(presed){
           setPressed(false);
           selected=false;

       }

    }

    @Override
    public void draw(float[] mvpMatrix, float[] mProjectionMatrix, float[] mViewMatrix, float[] mModelMatrix) {
        if(selected){
            setColor(colurs);

            }
        else{
            setColor(colornotslected);

        }
        super.draw(mvpMatrix, mProjectionMatrix, mViewMatrix, mModelMatrix);
    }

    protected void setDist(float d){
        dist=d;
    }
}
