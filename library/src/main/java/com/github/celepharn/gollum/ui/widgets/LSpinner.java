package com.github.celepharn.gollum.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.github.celepharn.gollum.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LSpinner<T extends Parcelable> extends AppCompatSpinner {

  private static final Handler HANDLER = new Handler(Looper.getMainLooper());
  private static final String PARAM_STATE = "PARAM_STATE";
  private static final String PARAM_SELECTED_ITEM = "PARAM_SELECTED_ITEM";
  private static final String PARAM_SELECTED_ITEMS = "PARAM_SELECTED_ITEMS";
  private static final String PARAM_TYPE = "PARAM_TYPE";
  private static final String PARAM_ITEMS = "PARAM_ITEMS";

  private List<T> items = new ArrayList<>();
  private T selectedItem;
  private List<T> selectedItems = new ArrayList<>();
  private int titleColor;

  private String hint = "";
  private String titleString = "";
  private String negativeText = "";
  private String positiveText = "";
  private SpinnerType type = SpinnerType.SINGLE;

  public LSpinner(Context context) {
    super(context);
    initialize(context, null, 0);
  }

  public LSpinner(Context context, AttributeSet attrs) {
    super(context, attrs);
    initialize(context, attrs, 0);
  }

  public LSpinner(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initialize(context, attrs, defStyle);
  }

  private void initialize(final Context context, AttributeSet attrs, int defStyle) {
    if (attrs != null) {
      TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.LSpinner, defStyle, 0);
      titleColor = types.getColor(R.styleable.LSpinner_lspinner_title_color, defStyle);
      if (types.hasValue(R.styleable.LSpinner_lspinner_title)) {
        titleString = types.getString(R.styleable.LSpinner_lspinner_title);
      }
      if (types.hasValue(R.styleable.LSpinner_lspinner_negative_text)) {
        negativeText = types.getString(R.styleable.LSpinner_lspinner_negative_text);
      }
      if (types.hasValue(R.styleable.LSpinner_lspinner_hint)) {
        hint = types.getString(R.styleable.LSpinner_lspinner_hint);
      } else if (!TextUtils.isEmpty(titleString)) {
        hint = titleString;
      } else {
        hint = getContext().getString(R.string.please_select);
      }
      types.recycle();
    }

    setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
          event.setAction(MotionEvent.ACTION_CANCEL);
          showDialog(context);
        }
        return true;
      }
    });
  }

  public void setType(SpinnerType type) {
    this.type = type;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }

  public void setTitle(String title) {
    this.titleString = title;
  }

  public void setNegativeText(String negativeText) {
    this.negativeText = negativeText;
  }

  public void setPositiveText(String positiveText) {
    this.positiveText = positiveText;
  }

  public List<T> getItems() {
    return items;
  }

  public void setItems(List<T> items) {
    this.items = items;
    updateAdapter(hint);
  }

  public Parcelable saveInstanceState() {
    Bundle bundle = new Bundle();
    bundle.putParcelable(PARAM_STATE, super.onSaveInstanceState());
    bundle.putParcelable(PARAM_SELECTED_ITEM, this.selectedItem);
    bundle.putParcelableArrayList(PARAM_SELECTED_ITEMS,
        (ArrayList<T>) this.selectedItems);
    bundle.putParcelableArrayList(PARAM_ITEMS,
        (ArrayList<T>) this.items);
    bundle.putInt(PARAM_TYPE, type.ordinal());
    return bundle;
  }

  public void restoreInstanceState(Parcelable state) {
    if (state == null) {
      return;
    }
    if (state instanceof Bundle) {
      Bundle bundle = (Bundle) state;
      this.items = bundle.getParcelableArrayList(PARAM_ITEMS);
      this.selectedItem = bundle.getParcelable(PARAM_SELECTED_ITEM);
      this.selectedItems = bundle.getParcelableArrayList(PARAM_SELECTED_ITEMS);
      this.type = SpinnerType.values()[bundle.getInt(PARAM_TYPE)];
      state = bundle.getParcelable(PARAM_STATE);
      setItems(this.items);
      if (this.type == SpinnerType.SINGLE) {
        setSelectedItem(this.selectedItem);
      } else if (this.type == SpinnerType.MULTI) {
        setSelectedItems(this.selectedItems);
      }
    }
    super.onRestoreInstanceState(state);
  }

  @Override
  public T getSelectedItem() {
    return selectedItem;
  }

  public void setSelectedItem(T selected) {
    if (type == SpinnerType.SINGLE && selected != null && items.contains(selected)) {
      selectedItem = selected;
      updateAdapter(selectedItem.toString());
    } else {
      clear();
    }
  }

  public List<T> getSelectedItems() {
    return selectedItems;
  }

  public void setSelectedItems(List<T> selectedItems) {
    if (type == SpinnerType.MULTI && selectedItems.size() > 0) {
      this.selectedItems = selectedItems;
      List<String> list = new ArrayList<>();
      for (T item : selectedItems) {
        list.add(item.toString());
      }
      updateAdapter(TextUtils.join(",", list));
    } else {
      clear();
    }
  }

  private void setError(View v, int errorSourceId) {
    setError(v, getContext().getString(errorSourceId));
  }

  public void setError() {
    setError(R.string.required_field);
  }

  public void setError(int errorSourceId) {
    View v = getSelectedView();
    if (v != null) {
      setError(v, errorSourceId);
    }
  }

  public void setError(View v, String text) {
    ((TextView) v).setError(text);
  }

  private void showDialog(Context context) {
    MaterialDialog.Builder builder = new MaterialDialog.Builder(context);
    if (items != null && items.size() > 0) {
      builder.items(getItemDescriptions(items));
    }
    if (type == SpinnerType.SINGLE) {
      builder.itemsCallback(
          new MaterialDialog.ListCallback() {
            @Override
            public void onSelection(MaterialDialog d, View view, int i, CharSequence t) {
              setSelectedItem(items.get(i));
            }
          });
    } else if (type == SpinnerType.MULTI) {
      builder.itemsCallbackMultiChoice(
          getSelectedItemsIndex(),
          new MaterialDialog.ListCallbackMultiChoice() {
            @Override
            public boolean onSelection(MaterialDialog dialog, Integer[] which,
                                       CharSequence[] text) {
              selectedItems.clear();
              for (Integer choice : which) {
                selectedItems.add(items.get(choice));
              }
              setSelectedItems(selectedItems);
              return true;
            }
          })
          .positiveText(positiveText);
    }
    if (!TextUtils.isEmpty(titleString)) {
      builder.title(titleString)
          .titleColorRes(titleColor);
    }
    if (!TextUtils.isEmpty(negativeText)) {
      builder
          .negativeText(negativeText)
          .callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onNegative(MaterialDialog dialog) {
              clear();
            }
          });
    }
    builder.show();
  }

  private Integer[] getSelectedItemsIndex() {
    Integer[] result = new Integer[selectedItems.size()];
    for (int i = 0; i < selectedItems.size(); i++) {
      result[i] = items.indexOf(selectedItems.get(i));
    }
    Arrays.sort(result);
    return result;
  }

  private String[] getItemDescriptions(List list) {
    if (list == null) {
      return new String[0];
    }
    final String[] choices = new String[list.size()];
    for (int i = 0; i < list.size(); i++) {
      choices[i] = list.get(i).toString();
    }
    return choices;
  }

  public void clear() {
    this.selectedItem = null;
    this.selectedItems.clear();
    updateAdapter(hint);
  }

  public void hide() {
    setVisibility(View.GONE);
    clear();
    items.clear();
  }

  private void updateAdapter(final List<String> list) {
    HANDLER.post(new Runnable() {
      public void run() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
            android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        superSetAdapter(adapter);
      }
    });
  }

  private void updateAdapter(String description) {
    List<String> list = new ArrayList<>();
    list.add(description);
    updateAdapter(list);
  }

  private void superSetAdapter(ArrayAdapter adapter) {
    super.setAdapter(adapter);
  }

  public enum SpinnerType {
    SINGLE, MULTI
  }

}