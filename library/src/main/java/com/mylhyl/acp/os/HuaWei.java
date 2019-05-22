package com.mylhyl.acp.os;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

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
    public Intent createIntent() throws ActivityNotFoundException {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(PKG, MAIN_CLS));
        intent.putExtra(EXTRA_PKG, context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
