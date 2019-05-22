package com.mylhyl.acp.os;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by hupei on 2019/5/21 20:54.
 */
public class MeiZu implements SettingPage {

    private static final String PKG = "com.meizu.safe";
    private static final String L_MAIN_CLS = "com.meizu.safe.SecurityMainActivity";
    private static final String N_MAIN_CLS = "com.meizu.safe.permission.PermissionMainActivity";

    private Context context;

    public MeiZu(Context context) {
        this.context = context;
    }

    private static String getCls() {
        if (OsHelper.isAndroidL()) {
            return L_MAIN_CLS;
        } else {
            return N_MAIN_CLS;
        }
    }

    @Override
    public Intent createIntent() throws ActivityNotFoundException {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PKG, getCls()));
        intent.putExtra(EXTRA_PKG, context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
