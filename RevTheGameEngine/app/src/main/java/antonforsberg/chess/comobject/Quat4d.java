package antonforsberg.chess.comobject;

/**
 * Created by Anton Forsberg on 2017-12-18.
 */

public class Quat4d {

    public Quat4d(){
        x=y=z=w=0;
    }
    public Quat4d(float x,float y, float z,float w){
       this.x=x;
        this.y=y;
        this.z=z;
        this.w=w;
    }
    public Quat4d(Quat4d quat4d){
        this.x=quat4d.x;
        this.y=quat4d.y;
        this.z=quat4d.z;
        this.w=quat4d.w;
    }
    float x;
    float y;
    float z;
    float w;

    private void normalize(){
      double n= 1/Math.sqrt(x*x+y*y+z*z+w*w);
      x*=n;
      y*=n;
      z*=n;
      w*=n;
    }


    public Quat4d slerp (Quat4d end, float alpha) {
        final float d = this.x * end.x + this.y * end.y + this.z * end.z + this.w * end.w;
        float absDot = d < 0.f ? -d : d;

        // Set the first and second scale for the interpolation
        float scale0 = 1f - alpha;
        float scale1 = alpha;

        // Check if the angle between the 2 quaternions was big enough to
        // warrant such calculations
        if ((1 - absDot) > 0.1) {// Get the angle between the 2 quaternions,
            // and then store the sin() of that angle
            final float angle = (float)Math.acos(absDot);
            final float invSinTheta = 1f / (float)Math.sin(angle);

            // Calculate the scale for q1 and q2, according to the angle and
            // it's sine value
            scale0 = ((float)Math.sin((1f - alpha) * angle) * invSinTheta);
            scale1 = ((float)Math.sin((alpha * angle)) * invSinTheta);
        }

        if (d < 0.f) scale1 = -scale1;

        // Calculate the x, y, z and w values for the quaternion by using a
        // special form of linear interpolation for quaternions.
        x = (scale0 * x) + (scale1 * end.x);
        y = (scale0 * y) + (scale1 * end.y);
        z = (scale0 * z) + (scale1 * end.z);
        w = (scale0 * w) + (scale1 * end.w);

        // Return the interpolated quaternion
        return this;
    }


    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }


    public Quat4d multiply(Quat4d q1, Quat4d q2){
        Quat4d newq= new Quat4d();
        newq.x =  q1.x * q2.w + q1.y * q2.z - q1.z * q2.y + q1.w * q2.x;
        newq.y = -q1.x * q2.z + q1.y * q2.w + q1.z * q2.x + q1.w * q2.y;
        newq.z =  q1.x * q2.y - q1.y * q2.x + q1.z * q2.w + q1.w * q2.z;
        newq.w = -q1.x * q2.x - q1.y * q2.y - q1.z * q2.z + q1.w * q2.w;
        return newq;
    }
/*
    public Quat4d add(Quat4d q1, Quat4d q2){
        Quat4d newq= new Quat4d();
        newq.x=q1.x+q2.x;
        newq.y=q1.y+q2.y;
        newq.z=q1.z+q2.z;
        newq.w=q1.w+q2.w;
        return newq;
    }
    */
    public Quat4d minus(Quat4d q1, Quat4d q2){
        Quat4d newq= new Quat4d();
        newq.x=q1.x-q2.x;
        newq.y=q1.y-q2.y;
        newq.z=q1.z-q2.z;
        newq.w=q1.w-q2.w;
        return newq;
    }
}
