package antonforsberg.chess.comobject;

import android.content.Context;
import android.opengl.GLES30;
import android.util.SparseArray;
import android.util.SparseIntArray;


import java.util.HashMap;
import java.util.Map;


/**
 * Created by Anton Forsberg on 2017-11-04.
 */

public class LoadObjectAssets  {
   private Context mActivityContext;

   private static SparseArray<float[]> arrMap=new SparseArray<>();
   private  static SparseIntArray imgMap=new SparseIntArray();
    private  static  Map<int[],Integer> cubmap=new HashMap<>();

   // Map <Integer,float[]> m=new HashMap<>();
   // Map <Integer,Integer> n=new HashMap<>();

   public LoadObjectAssets(Context mActivityContext){
        this.mActivityContext =mActivityContext;
    }


        public float[] LoadFloatArrayAsset(int id,int size, String type){
            if(arrMap.indexOfKey(id)>-1){
                return arrMap.get(id);
            }
            arrMap.put(id,RawObjectData.readObjectData(mActivityContext, id, size, type));

            return arrMap.get(id);
        }

    public float[] LoadFloatArrayAsset(int id,int size){
        if(arrMap.indexOfKey(id)>-1){

            return arrMap.get(id);
        }

        arrMap.put(id,RawObjectData.readObjectData(mActivityContext, id, size));
        return arrMap.get(id);
    }

    public int LoadImageAsset(int id) {
        clearList();
        if(imgMap.indexOfKey(id)>-1){


            return imgMap.get(id);
        }

        imgMap.put(id, TextureHelper.loadTexture(mActivityContext, id));
        return imgMap.get(id) ;
    }

    public int LoadcubmapAsset(int[] id){
        clearList();
            for (Map.Entry<int[], Integer> entry : cubmap.entrySet()) {
                if (entry.getKey()[0] == id[0]) {
                    return entry.getValue();
                }
            }


            int temp=TextureHelper.loadCubMap(mActivityContext, id);
            cubmap.put(id,temp);
            return temp;
    }
    private void clearList (){
        final int[] textureHandle = new int[1];

        GLES30.glGenTextures(1, textureHandle, 0);
        if(textureHandle[0]<3){
            cubmap.clear();
            imgMap.clear();
        }
        GLES30.glDeleteTextures(1, textureHandle, 0);
    }

    public Map<int[],Integer> getCubeMap(){return cubmap;}
    public SparseArray<float[]> getFloatMap(){
            return arrMap;
    }
    public SparseIntArray getImgageMap(){
        return imgMap;
    }

}
