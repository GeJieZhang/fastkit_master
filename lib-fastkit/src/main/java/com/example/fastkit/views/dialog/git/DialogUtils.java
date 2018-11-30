package com.example.fastkit.views.dialog.git;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;


public class DialogUtils {

    static MaterialDialog dialog;

    public static void showNormalDialog(Context context, String str) {

        if (dialog == null) {
            dialog = new MaterialDialog.Builder(context)
                    .content(str)
                    .progress(true, 0)
                    .progressIndeterminateStyle(false).show();

        }

    }

    public static void dismiss() {

        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

    }


}
