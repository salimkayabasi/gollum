package com.github.celepharn.gollum.util;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import timber.log.Timber;

public class FormatUtil {

  public static String formatPhone(String phone) {
    if (TextUtils.isEmpty(phone)) {
      return "";
    }
    String formattedPhone = phone.trim();
    int length = formattedPhone.length();
    if (length > 10) {
      formattedPhone = formattedPhone.substring(length - 10);
      length = formattedPhone.length();
    }
    if (length == 10) {
      formattedPhone = String.format("0 (%s) %s %s %s",
          formattedPhone.substring(0, 3), formattedPhone.substring(3, 6),
          formattedPhone.substring(6, 8), formattedPhone.substring(8, 10));
    }
    return formattedPhone;
  }

  public static String formatImageUrl(String url) {
    String separator = "image=";
    int ind = url.indexOf(separator);
    if (ind >= 0) {
      try {
        url = url.substring(0, ind) + separator
            + URLEncoder.encode(url.substring(ind + separator.length()), "UTF-8");
      } catch (UnsupportedEncodingException e) {
        Timber.e(e, "URLEncoder.encode");
      }
    }
    return url;
  }

}