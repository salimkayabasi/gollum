package com.github.celepharn.gollum.example;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.celepharn.gollum.ui.fragment.LFragment;

public class MainActivityFragment extends LFragment {

  public MainActivityFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_main, container, false);
  }

  @Override
  protected void onRestore() {

  }

  @Override
  protected void init() {

  }

  @Override
  protected void onSaveState(Bundle outState) {

  }

  @Override
  protected void onRestoreState(Bundle savedInstanceState) {

  }
}