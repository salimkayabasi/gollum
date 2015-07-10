package com.github.celepharn.gollum.serializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Pattern;


public class AspNetDateJsonSerializer implements JsonSerializer<Date>, JsonDeserializer<Date> {

  private static final long HOUR_IN_MILLISECOND = 60L * 60 * 1000;
  private static final String minusSign = "-";
  private static final String plusSign = "+";
  private static Pattern pattern = Pattern.compile("^/Date\\([0-9\\+-]*\\)/$");
  private static DecimalFormat formatter = new DecimalFormat("#00.###");

  @Override
  public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    String value = json.getAsString();
    if (!pattern.matcher(value).matches()) {
      return null;
    }
    long utcMillisecond =
        Long.parseLong(value.substring(value.indexOf("(") + 1, value.indexOf(")") - 5));
    return new Date(utcMillisecond);
  }

  @Override
  public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
    int zoneOffsetMillisecond = TimeZone.getDefault().getOffset(arg0.getTime());
    String sign = plusSign;
    if (zoneOffsetMillisecond < 0) {
      sign = minusSign;
      zoneOffsetMillisecond *= -1;
    }
    int minute = (int) (zoneOffsetMillisecond % HOUR_IN_MILLISECOND);
    int hour = (zoneOffsetMillisecond / 1000 / 60 / 60);
    return new JsonPrimitive("/Date(" + arg0.getTime() + sign + formatter.format(hour) + formatter.format(minute) + ")/");
  }
}