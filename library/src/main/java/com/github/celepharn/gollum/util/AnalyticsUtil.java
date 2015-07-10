package com.github.celepharn.gollum.util;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class AnalyticsUtil {

  private Tracker mTracker;

  @Inject
  public AnalyticsUtil(Tracker tracker) {
    this.mTracker = tracker;
  }

  public void sendScreenView(String screenName) {
    mTracker.setScreenName(screenName);
    mTracker.send(new HitBuilders.AppViewBuilder().build());
    Timber.d("Screen View recorded: " + screenName);
  }

  public void sendEvent(String category, String action, String label, long value) {
    mTracker.send(new HitBuilders.EventBuilder()
        .setCategory(category)
        .setAction(action)
        .setLabel(label)
        .setValue(value)
        .build());

    Timber.d("Event recorded:");
    Timber.d("\tCategory: " + category);
    Timber.d("\tAction: " + action);
    Timber.d("\tLabel: " + label);
    Timber.d("\tValue: " + value);
  }

  public void sendEvent(String category, String action, String label) {
    sendEvent(category, action, label, 0);
  }
}