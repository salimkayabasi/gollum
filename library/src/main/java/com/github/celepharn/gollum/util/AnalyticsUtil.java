package com.github.celepharn.gollum.util;

import android.content.Context;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class AnalyticsUtil {

  private static final String GIT_SHA = "GIT_SHA";
  private static final String BUILD_TIME = "BUILD_TIME";

  private Context context = null;
  private Tracker tracker = null;

  @Inject
  public AnalyticsUtil(Context context, int useProfile) {
    this.context = context;
    tracker = GoogleAnalytics.getInstance(context).newTracker(useProfile);
  }

  @Inject
  public AnalyticsUtil(Context context, int useProfile, String buildTime, String gitSha) {
    this.context = context;
    tracker = GoogleAnalytics.getInstance(context).newTracker(useProfile);
    tracker.set(BUILD_TIME, buildTime);
    tracker.set(GIT_SHA, gitSha);
  }

  private boolean canSend() {
    return context != null && tracker != null;
  }

  public void sendScreenView(String screenName) {
    if (canSend()) {
      tracker.setScreenName(screenName);
      tracker.send(new HitBuilders.AppViewBuilder().build());
      Timber.d("Screen View recorded: " + screenName);
    } else {
      Timber.d("Screen View NOT recorded (analytics disabled or not ready).");
    }
  }

  public void sendEvent(String category, String action, String label, long value) {
    if (canSend()) {
      tracker.send(new HitBuilders.EventBuilder()
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
    } else {
      Timber.d("Analytics event ignored (analytics disabled or not ready).");
    }
  }

  public void sendEvent(String category, String action, String label) {
    sendEvent(category, action, label, 0);
  }
}