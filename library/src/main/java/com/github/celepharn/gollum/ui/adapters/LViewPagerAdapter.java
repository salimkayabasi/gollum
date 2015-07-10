package com.github.celepharn.gollum.ui.adapters;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class LViewPagerAdapter<T extends View> extends PagerAdapter {

  private Context context;
  private List<T> views = new ArrayList<>();
  private List<String> titles = new ArrayList<>();

  public LViewPagerAdapter(Context context) {
    this.context = context;
  }

  @Override
  public int getCount() {
    return views.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    if (titles.size() > 0) {
      return titles.get(position);
    }
    return "";
  }

  public void addPage(T view, @StringRes int titleId) {
    views.add(view);
    titles.add(context.getString(titleId));
  }

  public void addPage(T view) {
    views.add(view);
  }

  public void removePage(int index) {
    views.remove(index);
    if (titles.size() > 0) {
      titles.remove(index);
    }
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    View itemView = getView(position);
    container.addView(itemView);
    return itemView;
  }

  public View getView(int position) {
    return views.get(position);
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object view) {
    container.removeView((View) view);
  }

}