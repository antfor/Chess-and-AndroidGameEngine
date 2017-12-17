package antonforsberg.chess.comobject;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Anton Forsberg on 2017-07-13.
 */

public class RawObjectData {


    public static float[] readObjectData(final Context context,
                                                     final int resourceId,int ind,String val)
    {
        final InputStream inputStream = context.getResources().openRawResource(resourceId);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader);

        String nextLine;
        //final StringBuilder body = new StringBuilder();
        int  n=0;
        float[] arr=new float[ind];
        boolean read=false;

        try
        {
            while ((nextLine = bufferedReader.readLine()) != null)
            {

                if(nextLine.equals(val)||read) {

                    read=true;
                    if(!nextLine.equals(val)&& !nextLine.equals("e")) {
                        arr[n] = Float.parseFloat(nextLine);
                        n++;
                    }else if(nextLine.equals("e")){break;}
                }
                // body.append(nextLine);
               // body.append('\n');
            }
        }
        catch (IOException e)
        {
            return null;
        }
            return arr;
      //  return body.toString();
    }















    //new
    public static float[] readObjectData(final Context context, final int resourceId,int ind)
    {
        final InputStream inputStream = context.getResources().openRawResource(resourceId);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader);

        String nextLine;
        //final StringBuilder body = new StringBuilder();
        int  n=0;
        float[] arr=new float[ind];

        try
        {
            while ((nextLine = bufferedReader.readLine()) != null)
            {

                        arr[n] = Float.parseFloat(nextLine);
                        n++;

            }
        }
        catch (IOException e)
        {
            return null;
        }
        return arr;
        //  return body.toString();
    }

}
