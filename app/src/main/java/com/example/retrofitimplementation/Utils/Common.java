package com.example.retrofitimplementation.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Common {
    private static ProgressDialog dialog;


    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    // Show Dialog
    public static void progressDialog(Context context, String msg) {
        dismisDialog();
        dialog = new ProgressDialog(context);
        dialog.setMessage(msg);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    // Dismiss Dialog
    public static void dismisDialog() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }
}
