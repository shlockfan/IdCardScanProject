package com.app.fan.idcardscanproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import id.zelory.compressor.Compressor;

/**
 * Created by Administrator on 2016/10/19.
 */

public class BitMapUtils {


    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            return "";
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                return "";
            }
        }
        return result;
    }


    /**
     * 从SD卡上获取图片。如果不存在则返回null</br>
     * @return 代表图片的Bitmap对象
     */
    public static Bitmap getBitmapFromSDCard(String url) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(url));
            if (inputStream != null && inputStream.available() > 0) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String zoomBitMapFromPath(Context context, String path) {
        String newPath;
        File f = new Compressor.Builder(context)
                .setMaxWidth(1920)
                .setMaxHeight(1080)
                .setQuality(100)
                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                .setDestinationDirectoryPath(context.getCacheDir().getAbsolutePath())
                .build()
                .compressToFile(new File(path));
        newPath = f.getPath();
        return newPath;
    }

}
