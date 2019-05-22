package com.mylhyl.acp.os;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by hupei on 2019/5/21 20:30.
 */
public class HuaWei implements SettingPage {

    private static final String PKG = "com.huawei.systemmanager";
    private static final String MAIN_CLS = "com.huawei.permissionmanager.ui.MainActivity";
    private Context context;

    public HuaWei(Context context) {
        this.context = context;
    }

    @Override
    public Intent createIntent() throws PackageManager.NameNotFoundException {
//        Intent intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
//        Uri uri = Uri.fromParts(EXTRA_PKG, context.getPackageName(), null);
//        intent.setData(uri);
        //intent.setClassName("com.android.packageinstaller", "com.android.packageinstaller.permission.ui
        // .ManagePermissionsActivity");
        Intent intent = new Intent();
        intent.setClassName(PKG, MAIN_CLS);
        intent.putExtra(EXTRA_PKG_NAME, context.getPackageName());
        if (OsHelper.isActivityExported(context, intent)) {
            intent = null;
        }
        return intent;
    }
}
