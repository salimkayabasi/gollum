package com.github.celepharn.gollum.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

  public static final String DATE_FORMAT = "dd.MM.yyyy";
  public static final String SERVICE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

  public static String format(Date date) {
    if (date != null) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
      return simpleDateFormat.format(date);
    }
    return "";
  }

  public static Date getServiceDate(String date) throws ParseException {
    if (!TextUtils.isEmpty(date)) {
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SERVICE_DATE_FORMAT);
      return simpleDateFormat.parse(date);
    }
    return null;
  }
}