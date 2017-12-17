package antonforsberg.chess.comobject;

/**
 * Created by Anton Forsberg on 2017-07-10.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;

public class TextureHelper
{

    public static int loadTexture(final Context context, final int resourceId)
    {
        final int[] textureHandle = new int[1];

        GLES30.glGenTextures(1, textureHandle, 0);


        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;	// No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
           // GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
           // GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_2D, GLES30.GL_LINEAR);
            GLES30. glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30. GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30. GL_TEXTURE_WRAP_T,GLES30. GL_CLAMP_TO_EDGE);


            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }
        GLES30. glGenerateMipmap(GLES30.GL_TEXTURE_2D);
        GLES30. glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30. GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);

        if (textureHandle[0] == 0)
        {
           throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }


    //min är nedan
    public static int loadCubMap(final Context context, final int[] resourceId)
    {
        final int[] textureHandle = new int[1];
        ByteBuffer fcbuffer = null;

        GLES30.glGenTextures(1, textureHandle, 0);


       // byte[] cubePixels0 = { 127, 0, 0 };
       // ByteBuffer cubePixels = ByteBuffer.allocateDirect(3);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;	// No pre-scaling

            // Bind to the texture in OpenGL
            GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP, textureHandle[0]);
            Bitmap img=null;

         //   GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);
          //  GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);
          //  GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
         //   GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
            GLES30. glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP,GLES30. GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP,GLES30. GL_TEXTURE_WRAP_T,GLES30. GL_CLAMP_TO_EDGE);

            for (int i = 0; i < 6; i++) {
                // Read in the resource
                  img = BitmapFactory.decodeResource(context.getResources(), resourceId[i], options);
                fcbuffer = ByteBuffer.allocateDirect(img.getHeight() * img.getWidth() * 4);

                img.copyPixelsToBuffer(fcbuffer);
                fcbuffer.position(0);
                GLES30.glTexImage2D(GLES30.GL_TEXTURE_CUBE_MAP_POSITIVE_X+i, 0, GLES30.GL_RGBA,
                        img.getWidth(),img.getHeight() , 0,GLES30.GL_RGBA ,GLES30.GL_UNSIGNED_BYTE, fcbuffer);
                fcbuffer = null;


               img.recycle();

            }
         //   GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_CUBE_MAP);
            GLES30. glGenerateMipmap(GLES30.GL_TEXTURE_CUBE_MAP);
            GLES30. glTexParameteri(GLES30.GL_TEXTURE_CUBE_MAP,GLES30. GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR_MIPMAP_LINEAR);

            // fcbuffer = null;

            // Set filtering


            // Load the bitmap into the bound texture.
            //GLUtils.texImage2D(GLES20.GL_TEXTURE_CUBE_MAP, 0, img, 0);
           // GLES30.glBindTexture(GLES30.GL_TEXTURE_CUBE_MAP,0);
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];

    }

//public int getNumberOftextures(){return numberOftextures;}

}