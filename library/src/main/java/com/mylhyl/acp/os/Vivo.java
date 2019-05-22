package com.mylhyl.acp.os;

import android.content.Context;
import android.content.Intent;

/**
 * Created by hupei on 2019/5/21 20:04.
 */
public class Vivo implements SettingPage {

    private static final String PKG = "com.iqoo.secure";

    private Context context;

    public Vivo(Context context) {
        this.context = context;
    }

    @Override
    public Intent createIntent() {
        Intent intent = new Intent();
        intent.setClassName("com.vivo.permissionmanager"
                , "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        if (!OsHelper.isIntentAvailable(context, intent)) {
            intent.setClassName(PKG, "com.iqoo.secure.safeguard.SoftPermissionDetailActivity");
        } else if (!OsHelper.isIntentAvailable(context, intent)) {
            intent.setClassName(PKG, "com.iqoo.secure.MainActivity");
        }
        intent.putExtra(EXTRA_PKG_NAME, context.getPackageName());
        return intent;
    }

}
