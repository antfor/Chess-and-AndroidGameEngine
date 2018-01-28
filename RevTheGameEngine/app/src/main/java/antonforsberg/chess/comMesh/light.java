package antonforsberg.chess.comMesh;

/**
 * Created by Anton Forsberg on 2018-01-13.
 */

public class light {
    float[] color={1,1,1,1};
    float[] position={0,0,0,1};

    public light (){

    }
    public light (float x,float y,float z){
        setPosition(x,y,z);
    }

    public light(float x,float y,float z,float r,float g,float b,float a){
        this(x,y,z);
       setColor(r,g,b,a);
    }
    public light(light l){
        color=l.getColor();
        position=l.getPosition();
    }

    public void setPosition(float x,float y,float z){
        position[0]=x;
        position[1]=y;
        position[2]=z;
    }


    public float[] getPosition() {
        return new float[]{position[0],position[1],position[2],position[3]};
    }

    public float[] getColor() {
        return new float[]{color[0],color[1],color[2],color[3]};
    }

    public void setColor(float[] color) {
        this.color = color;
    }
    public void setColor(float r ,float g, float b, float a) {
        color[0]=r;
        color[1]=g;
        color[2]=b;
        color[3]=a;
    }

    public float x(){
        return position[0];
    }
    public float y(){
        return position[1];
    }
    public float z(){
        return position[2];
    }

    public float r(){
        return color[0];
    }
    public float g(){
        return color[1];
    }
    public float b(){
        return color[2];
    }
    public float a(){
        return color[3];
    }
}

