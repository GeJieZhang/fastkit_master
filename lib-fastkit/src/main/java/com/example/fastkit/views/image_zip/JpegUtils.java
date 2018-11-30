package com.example.fastkit.views.image_zip;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by nick on 2017/2/23
 */

public class JpegUtils {
    //设置图片质量
    private static final int QUALITY = 75;


    static {
        System.loadLibrary("native-lib");
    }

    /**
     * 压缩图片默认图片质量75
     *
     * @param path
     * @return
     */
    public static String compressImage(String path) {
        Bitmap bitmap = decodeFile(path);
        String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                new File(path).getName();

        boolean isOk = compressBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), newPath, QUALITY);

        if (!isOk) {
            return "";
        }
        return newPath;
    }

    /**
     * 压缩图片自定义图片的质量
     *
     * @param path
     * @param quality
     * @return
     */
    public static String compressImage(String path, int quality) {
        Bitmap bitmap = decodeFile(path);
        String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                new File(path).getName();

        boolean isOk = compressBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), newPath, quality);

        if (!isOk) {
            return "";
        }
        return newPath;
    }

    /**
     * 获取图片所占的内存
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static String getImageSize(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        return getNetFileSizeDescription(fis.available());
    }


    public static native boolean compressBitmap(Bitmap bitmap, int width, int height, String fileName, int quality);


    public static Bitmap decodeFile(String path) {
        int finalWidth = 800;
        // 先获取宽度
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 不加载图片到内存只拿宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int bitmapWidth = options.outWidth;

        int inSampleSize = 1;

        if (bitmapWidth > finalWidth) {
            inSampleSize = bitmapWidth / finalWidth;
        }

        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }


    public static String getNetFileSizeDescription(long size) {
        StringBuffer bytes = new StringBuffer();
        DecimalFormat format = new DecimalFormat("###.0");
        if (size >= 1024 * 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0 * 1024.0));
            bytes.append(format.format(i)).append("GB");
        } else if (size >= 1024 * 1024) {
            double i = (size / (1024.0 * 1024.0));
            bytes.append(format.format(i)).append("MB");
        } else if (size >= 1024) {
            double i = (size / (1024.0));
            bytes.append(format.format(i)).append("KB");
        } else if (size < 1024) {
            if (size <= 0) {
                bytes.append("0B");
            } else {
                bytes.append((int) size).append("B");
            }
        }
        return bytes.toString();
    }
}
