package com.mylhyl.acp.os;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

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
    public Intent createIntent() throws PackageManager.NameNotFoundException {

        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra(EXTRA_PKG_NAME, context.getPackageName());
        intent.putExtra(EXTRA_PKG, context.getPackageName());

        intent.setClassName(PKG, getCls());
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }

        return null;
    }
}
