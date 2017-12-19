package antonforsberg.chess.comobject;

import android.opengl.Matrix;
import android.util.FloatMath;

import static java.lang.Math.cos;
import static java.lang.Math.floor;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Created by Anton Forsberg on 2017-07-24.
 */

public class RotQ {
float[] rotmat=new float[3];
    float[] h=new float[4];



    float[] hprim=new float[4];
    float[] rot=new float[4];



    float[] p=new float[4];
    public RotQ(){
        rotmat[0]=1.0f;
        rotmat[1]=1.0f;
        rotmat[2]=1.0f;

        p[0]=0.0f;
        p[1]=1.0f;
        p[2]=0.0f;

        h[0]=0.0f;
        h[1]=1.0f;
        h[2]=0.0f;

        hprim[0]=0.0f;
        hprim[1]=1.0f;
        hprim[2]=0.0f;
    }

float x,y,z=0;
    float qx,qy,qz,qw;

    float mx,my,mz,mw;
    float w=1;

    public void startrot(){x=y=z=0;w=1;}
public void rotate(double vx,double vy,double vz, double angle){
    angle*=-1;
    angle=Math.toRadians(angle);

    mx=x;
    my=y;
    mz=z;
    mw=w;
    x=(float)(vx*sin(angle*0.5));
    y=(float)(vy*sin(angle*0.5));
    z=(float)(vz*sin(angle*0.5));
    w=(float)(cos(angle*0.5));

    //getmatq(Matrix);
     rqmulti();
   // matrix(Matrix);
}

public void qmulti(){
    float xx = +this.x *mw + this.y *mz - this.z *my + this.w *mx;
    float yy = -this.x *mz + this.y *mw + this.z *mx + this.w *my;
    float zz = +this.x *my - this.y *mx + this.z *mw + this.w *mz;
    float ww = -this.x *mx - this.y *my - this.z *mz + this.w *mw;

    this.x = xx;
    this.y = yy;
    this.z = zz;
    this.w = ww;
}
    public void rqmulti(){
        float xx = +mx *w + my *z - mz *y + mw *x;
        float yy = -mx *z + my *w + mz *x + mw *y;
        float zz = +mx *y - my *x + mz *w + mw *z;
        float ww = -mx *x - my *y - mz *z + mw *w;

        this.x = xx;
        this.y = yy;
        this.z = zz;
        this.w = ww;
    }

    public Quat4d getq(){
        return new Quat4d(qx,qy,qz,qw);
    }
public void getmatq(float[] Matrix){

    float m00,m01,m02,m10,m11,m12,m20,m21,m22;

    m00=Matrix[0];
    m01=Matrix[1];
    m02=Matrix[2];
    m10=Matrix[4];
    m11=Matrix[5];
    m12=Matrix[6];
    m20=Matrix[8];
    m21=Matrix[9];
    m22=Matrix[10];

    float tr = Matrix[0] + Matrix[5] + Matrix[10];

    if (tr > 0) {
        float S = (float)sqrt(tr+1.0) * 2; // S=4*qw
        qw = (float)0.25 * S;
        qx = (float)(m21 - m12) / S;
        qy = (float)(m02 - m20) / S;
        qz = (float)(m10 - m01) / S;
    } else if ((m00 > m11)&(m00 > m22)) {
        float S =(float) sqrt(1.0 + m00 - m11 - m22) * 2; // S=4*qx
        qw = (float)(m21 - m12) / S;
        qx = (float)0.25 * S;
        qy =(float) (m01 + m10) / S;
        qz =(float) (m02 + m20) / S;
    } else if (m11 > m22) {
        float S = (float)sqrt(1.0 + m11 - m00 - m22) * 2; // S=4*qy
        qw = (float)(m02 - m20) / S;
        qx = (float)(m01 + m10) / S;
        qy =(float) 0.25 * S;
        qz =(float) (m12 + m21) / S;
    } else {
        float S =(float) sqrt(1.0 + m22 - m00 - m11) * 2; // S=4*qz
        qw =(float) (m10 - m01) / S;
        qx =(float) (m02 + m20) / S;
        qy =(float) (m12 + m21) / S;
        qz =(float) 0.25 * S;
    }

}


    public final void quatToMatrix(Quat4d q,float[] Matrix){
        float sqw = q.w*q.w;
        float sqx = q.x*q.x;
        float sqy = q.y*q.y;
        float sqz = q.z*q.z;
/*
        float m00=Matrix[0];
        float m01=Matrix[1];
        float m02=Matrix[2];
        float m10=Matrix[4];
        float m11=Matrix[5];
        float m12=Matrix[6];
        float m20=Matrix[8];
        float m21=Matrix[9];
        float m22=Matrix[10];
*/
        // invs (inverse square length) is only required if quaternion is not already normalised
        float invs = 1 / (sqx + sqy + sqz + sqw);
        Matrix[0] = ( sqx - sqy - sqz + sqw)*invs ; // since sqw + sqx + sqy + sqz =1/invs*invs
        Matrix[5] = (-sqx + sqy - sqz + sqw)*invs ;
        Matrix[10] = (-sqx - sqy + sqz + sqw)*invs ;

        float tmp1 = q.x*q.y;
        float tmp2 = q.z*q.w;
        Matrix[4] = 2.0f * (tmp1 + tmp2)*invs ;
        Matrix[1] = 2.0f * (tmp1 - tmp2)*invs ;

        tmp1 = q.x*q.z;
        tmp2 = q.y*q.w;
        Matrix[8] = 2.0f * (tmp1 - tmp2)*invs ;
        Matrix[2] = 2.0f * (tmp1 + tmp2)*invs ;
        tmp1 = q.y*q.z;
        tmp2 = q.x*q.w;
        Matrix[9] = 2.0f * (tmp1 + tmp2)*invs ;
        Matrix[6] = 2.0f * (tmp1 - tmp2)*invs ;
    }


public void matrix(float[] matrix){

    float x2, y2, z2, xx, xy, xz, yy, yz, zz, wx, wy, wz;

    float sx =(Matrix.length(matrix[0],matrix[4],matrix[8]));
    float sy =(Matrix.length(matrix[0+1],matrix[4+1],matrix[8+1]));
    float sz =(Matrix.length(matrix[0+2],matrix[4+2],matrix[8+2]));


    // calculate coefficients
    x2 = x + x;
    y2 = y + y;
    z2 = z + z;

    xx = x * x2;   xy = x * y2;   xz = x * z2;
    yy = y * y2;   yz = y * z2;   zz = z * z2;
    wx = w * x2;   wy = w * y2;   wz = w * z2;


    matrix[0] = 1.0f - (yy + zz);
    matrix[1] = xy - wz;
    matrix[2] = xz + wy;


    matrix[4] = xy + wz;
    matrix[5] = 1.0f - (xx + zz);
    matrix[6] = yz - wx;

    matrix[8] = xz - wy;
    matrix[9] = yz + wx;
    matrix[10] = 1.0f - (xx + yy);

    Matrix.scaleM(matrix,0,sx,sy,sz);
}

    //gammla matrix metod
    public void matrixNoScale(float[] Matrix){

        float x2, y2, z2, xx, xy, xz, yy, yz, zz, wx, wy, wz;



        // calculate coefficients
        x2 = x + x;
        y2 = y + y;
        z2 = z + z;

        xx = x * x2;   xy = x * y2;   xz = x * z2;
        yy = y * y2;   yz = y * z2;   zz = z * z2;
        wx = w * x2;   wy = w * y2;   wz = w * z2;


        Matrix[0] = 1.0f - (yy + zz);
        Matrix[1] = xy - wz;
        Matrix[2] = xz + wy;


        Matrix[4] = xy + wz;
        Matrix[5] = 1.0f - (xx + zz);
        Matrix[6] = yz - wx;

        Matrix[8] = xz - wy;
        Matrix[9] = yz + wx;
        Matrix[10] = 1.0f - (xx + yy);

    }
    public void qrotx (double deg,float[] vec,float[] Matrix){
        deg=Math.toRadians(deg);
        p[0]=0;
        p[1]=Matrix[12];
        p[2]=Matrix[13];
        p[3]=Matrix[14];

        h[0]=(float)cos(deg/2);
        h[1]=(float)(vec[0]*sin(deg/2));
        h[2]=(float)(vec[1]*sin(deg/2));
        h[3]=(float)(vec[2]*sin(deg/2));

        hprim[0]=(float)cos(deg/2);
        hprim[1]=-1.0f*(float)(vec[0]*sin(deg/2));
        hprim[2]=-1.0f*(float)(vec[1]*sin(deg/2));
        hprim[3]=-1.0f*(float)(vec[2]*sin(deg/2));
        multi();

        System.out.println("1q: "+rot[0]);
        System.out.println("2q: "+rot[1]);
        System.out.println("3q: "+rot[2]);
        System.out.println("4q: "+rot[3]);

    }

public void multi(){
    rot[0]=h[0]*p[0]-h[1]*p[1]-h[2]*p[2]-h[3]*p[3];
    rot[1]=h[0]*p[2]+h[1]*p[0]+h[2]*p[3]-h[3]*p[2];
    rot[2]=h[0]*p[2]-h[1]*p[3]+h[2]*p[0]-h[3]*p[1];
    rot[3]=h[0]*p[3]+h[1]*p[2]-h[2]*p[1]+h[3]*p[0];


    rot[0]=rot[0]*hprim[0]-rot[1]*hprim[1]-rot[2]*hprim[2]-rot[3]*hprim[3];
    rot[1]=rot[0]*hprim[2]+rot[1]*hprim[0]+rot[2]*hprim[3]-rot[3]*hprim[2];
    rot[2]=rot[0]*hprim[2]-rot[1]*hprim[3]+rot[2]*hprim[0]-rot[3]*hprim[1];
    rot[3]=rot[0]*hprim[3]+rot[1]*hprim[2]-rot[2]*hprim[1]+rot[3]*hprim[0];

}


    public void rotx (float deg,float[] mModelMatrix){

        deg=(float)Math.toRadians(deg);
        rotmat[1]= (float)(cos(deg)*mModelMatrix[5]-sin(deg)*mModelMatrix[6]);
        rotmat[2]=(float)(sin(deg)*mModelMatrix[9]+cos(deg)*mModelMatrix[10]);
        rot(mModelMatrix);
    }
    public void roty (float deg,float[] mModelMatrix){
        deg=(float)Math.toRadians(deg);
        rotmat[0]= (float)(cos(deg)*rotmat[0]+sin(deg)*rotmat[2]);
        rotmat[2]=(float)(-sin(deg)*rotmat[0]+cos(deg)*rotmat[2]);
        rot(mModelMatrix);
    }
    public void rotz (float deg,float[] mModelMatrix){
        deg=(float)Math.toRadians(deg);
        rotmat[0]= (float)(cos(deg)*rotmat[0]-sin(deg)*rotmat[1]);
        rotmat[1]=(float)(sin(deg)*rotmat[0]+cos(deg)*rotmat[1]);
        rot(mModelMatrix);
    }

    private void rot(float[] mModelMatrix){

        Matrix.rotateM(mModelMatrix,0,(float)Math.atan(rotmat[1]/rotmat[2]),1,0,0);
          Matrix.rotateM(mModelMatrix,0,(float)Math.atan(rotmat[2]/rotmat[0]),0,1,0);
         Matrix.rotateM(mModelMatrix,0,(float)Math.atan(rotmat[1]/rotmat[0]),0,0,1);

        rotmat[0]=0.0f;
        rotmat[1]=1.0f;
        rotmat[2]=0.0f;
    }

    public static void translateM(float[] matrix,float offset,float x ,float y, float z){
        matrix[12]+=x;
        matrix[13]+=y;
        matrix[14]+=z;
    }
    public static float[] getScale(float[] matrix){
        float sx =(Matrix.length(matrix[0],matrix[4],matrix[8]));
        float sy =(Matrix.length(matrix[0+1],matrix[4+1],matrix[8+1]));
        float sz =(Matrix.length(matrix[0+2],matrix[4+2],matrix[8+2]));

        return new  float[]{sx,sy,sz};
    }
}
