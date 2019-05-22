package com.mylhyl.acp.os;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by hupei on 2019/5/21 20:04.
 */
public class Vivo implements SettingPage {

    private Context context;

    public Vivo(Context context) {
        this.context = context;
    }

    @Override
    public Intent createIntent() throws PackageManager.NameNotFoundException {

        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra(EXTRA_PKG_NAME, context.getPackageName());
        intent.putExtra(EXTRA_PKG, context.getPackageName());

        intent.setClassName("com.vivo.permissionmanager"
                , "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity");
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }

        intent.setClassName("com.iqoo.secure"
                , "com.iqoo.secure.safeguard.SoftPermissionDetailActivity");
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }

        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.MainActivity");
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }

        return null;
    }

}
