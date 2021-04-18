package com.scpfoundation.psybotic.app.util;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    public static void showToast(Context ctx, String msg) {
        Toast toast = Toast.makeText(ctx, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

}
