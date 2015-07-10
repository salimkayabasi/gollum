package com.github.celepharn.gollum.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.github.celepharn.gollum.util.BusUtil;

import timber.log.Timber;

public abstract class LFragment extends Fragment {

  protected static final String PARAM_BUNDLE = "PARAM_BUNDLE";
  protected FragmentActivity context;
  private Bundle savedState;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    BusUtil.BUS.register(this);
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    context = getActivity();
    if (!restoreStateFromArguments()) {
      init();
    } else {
      onRestore();
    }
  }

  protected abstract void onRestore();

  protected abstract void init();

  protected abstract void onSaveState(Bundle outState);

  protected abstract void onRestoreState(Bundle savedInstanceState);

  @Override
  public void onSaveInstanceState(Bundle outState) {
    saveStateToArguments();
    super.onSaveInstanceState(outState);
  }

  @Override
  public void onDestroyView() {
    saveStateToArguments();
    super.onDestroyView();
  }

  @Override
  public void onDestroy() {
    try {
      BusUtil.BUS.unregister(this);
    } catch (Exception e) {
      Timber.e(e, "onDestroy->BUS->unregister");
    }
    super.onDestroy();
  }

  private void saveStateToArguments() {
    if (getView() != null)
      savedState = saveState();
    if (savedState != null) {
      Bundle bundle = getArguments();
      if (bundle != null) {
        bundle.putBundle(PARAM_BUNDLE, savedState);
      }
    }
  }

  private boolean restoreStateFromArguments() {
    Bundle bundle = getArguments();
    if (bundle == null) {
      return false;
    }
    savedState = bundle.getBundle(PARAM_BUNDLE);
    if (savedState == null) {
      return false;
    }
    restoreState();
    return true;
  }

  private void restoreState() {
    if (savedState != null) {
      onRestoreState(savedState);
    }
  }

  private Bundle saveState() {
    Bundle state = new Bundle();
    onSaveState(state);
    return state;
  }

  @SuppressWarnings("unused")
  public boolean onBackPressed() {
    return false;
  }
}


