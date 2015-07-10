package com.github.celepharn.gollum.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.celepharn.gollum.util.BusUtil;
import com.google.android.gms.analytics.GoogleAnalytics;

import timber.log.Timber;

public abstract class LActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GoogleAnalytics.getInstance(this).reportActivityStart(this);
    BusUtil.BUS.register(this);
  }

  @Override
  protected void onDestroy() {
    try {
      BusUtil.BUS.unregister(this);
    } catch (Exception e) {
      Timber.e(e, "");
    }
    GoogleAnalytics.getInstance(this).reportActivityStop(this);
    super.onDestroy();
  }

}