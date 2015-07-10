package com.github.celepharn.gollum.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import timber.log.Timber;

public class BitmapUtil {

  public static String getBase64(byte[] baos) {
    return Base64.encodeToString(baos, Base64.NO_WRAP);
  }

  public static Bitmap decodeFile(String path) {
    try {
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inJustDecodeBounds = false;
      options.inPreferredConfig = Bitmap.Config.RGB_565;
      options.inDither = true;
      return BitmapFactory.decodeFile(path);
    } catch (Exception e) {
      Timber.e(e, "Error while decoding file");
    }
    return null;
  }

  public static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {
    if (bitmap.getWidth() > width) {
      bitmap = resizeBitmapByWidth(bitmap, width);
    }
    if (bitmap.getHeight() > height) {
      bitmap = resizeBitmapByHeight(bitmap, height);
    }
    return bitmap;
  }

  public static Bitmap resizeBitmapByWidth(Bitmap bitmap, int newWidth) {
    int newHeight = (bitmap.getHeight() * newWidth) / bitmap.getWidth();
    return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
  }

  public static Bitmap resizeBitmapByHeight(Bitmap bitmap, int newHeight) {
    int newWidth = (bitmap.getWidth() * newHeight) / bitmap.getHeight();
    return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
  }
}