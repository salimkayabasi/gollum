package com.github.celepharn.gollum.ui.util;

import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class LInputUtil {
  private LInputUtil() {
  }

  public static void addFilter(AppCompatEditText editText, InputFilter... filters) {
    if (editText == null || filters == null || filters.length == 0) {
      return;
    }
    InputFilter[] filterArray = editText.getFilters();
    List<InputFilter> list = new ArrayList<>();
    if (filterArray != null && filterArray.length > 0) {
      list.addAll(Arrays.asList(filterArray));
    }
    list.addAll(Arrays.asList(filters));
    editText.setFilters(list.toArray(new InputFilter[list.size()]));
  }
}