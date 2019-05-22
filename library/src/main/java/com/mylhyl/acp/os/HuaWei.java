package com.mylhyl.acp.os;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by hupei on 2019/5/21 20:30.
 */
public class HuaWei implements SettingPage {

    private Context context;

    public HuaWei(Context context) {
        this.context = context;
    }

    @Override
    public Intent createIntent() throws PackageManager.NameNotFoundException {

        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra(EXTRA_PKG_NAME, context.getPackageName());
        intent.putExtra(EXTRA_PKG, context.getPackageName());

        intent.setClassName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }

        return null;
    }
}
