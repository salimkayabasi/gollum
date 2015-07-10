package com.github.celepharn.gollum.ui.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.github.celepharn.gollum.R;

public final class LDialogUtil {

  private LDialogUtil() {
  }

  public static ProgressDialog showProgress(Context context) {
    return showProgress(context, context.getString(R.string.msg_progress_wait));
  }

  public static ProgressDialog showProgress(Context context, String message) {
    return showProgress(context, message, false);
  }

  public static ProgressDialog showProgress(Context context, String message, boolean cancelable) {
    ProgressDialog progress = new ProgressDialog(context);
    progress.setCancelable(cancelable);
    progress.setMessage(message);
    progress.show();
    return progress;
  }

  public static void error(Context context, Throwable throwable) {
    showMessage(context, context.getString(R.string.title_error), throwable.getMessage());
  }

  public static void error(Context context, String message) {
    showMessage(context, context.getString(R.string.title_error), message);
  }

  public static void showMessage(Context context, String message) {
    showMessage(context, null, message, true, context.getString(R.string.button_ok), null);
  }

  public static void showMessage(Context context, String title, String message) {
    showMessage(context, title, message, true, context.getString(R.string.button_ok), null);
  }

  public static void showMessage(Context context, String message, boolean cancelable,
                                 DialogInterface.OnClickListener okListener) {
    showMessage(context, null, message, cancelable,
        context.getString(R.string.button_ok), okListener);
  }

  public static void showMessage(Context context, String message, boolean cancelable,
                                 String okText, DialogInterface.OnClickListener okListener) {
    showMessage(context, null, message, cancelable, okText, okListener);
  }

  public static void showMessage(Context context, String title,
                                 String message, boolean cancelable,
                                 DialogInterface.OnClickListener okListener) {
    showMessage(context, title, message, cancelable, context.getString(R.string.button_ok),
        okListener);
  }

  public static void showMessage(Context context, String title, String message,
                                 boolean cancelable, String okText,
                                 DialogInterface.OnClickListener okListener) {
    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
    alertDialog.setTitle(title);
    alertDialog.setMessage(message);
    alertDialog.setCancelable(cancelable);
    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, okText, okListener);
    alertDialog.setCancelable(false);
    alertDialog.show();
  }

  public static void confirmDialog(Context context, String message,
                                   DialogInterface.OnClickListener okListener) {
    confirmDialog(context, null, message, context.getString(R.string.button_ok),
        okListener, context.getString(R.string.button_cancel), null);
  }

  public static void confirmDialog(Context context, String message,
                                   String okText, DialogInterface.OnClickListener okListener) {
    confirmDialog(context, null, message, okText, okListener,
        context.getString(R.string.button_cancel), null);
  }

  public static void confirmDialog(Context context, String title, String message,
                                   String okText, DialogInterface.OnClickListener okListener) {
    confirmDialog(context, title, message, okText, okListener,
        context.getString(R.string.button_cancel), null);
  }

  public static void confirmDialog(Context context, String title, String message,
                                   String okText, DialogInterface.OnClickListener okListener,
                                   String cancelText,
                                   DialogInterface.OnClickListener cancelListener) {
    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
    alertDialog.setTitle(title);
    alertDialog.setMessage(message);
    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, okText, okListener);
    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, cancelText, cancelListener);
    alertDialog.setCancelable(false);
    alertDialog.show();
  }

  public static void todoDialog(Context context) {
    toast(context, R.string.msg_todo);
  }

  public static void toast(Context context, int resourceId) {
    toast(context, context.getString(resourceId));
  }

  public static void toast(Context context, String message) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
  }

  public static void updatePopUp(final Context context) {
    showMessage(context, context.getString(R.string.title_warning),
        context.getString(R.string.msg_login_update_need),
        false, context.getString(R.string.button_yes),
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int which) {
            final String appPackageName = context.getPackageName();
            try {
              context.startActivity(new Intent(Intent.ACTION_VIEW,
                  Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
              context.startActivity(new Intent(Intent.ACTION_VIEW,
                  Uri.parse("http://play.google.com/store/apps/details?id="
                      + appPackageName)));
            }
          }
        }
    );
  }
}