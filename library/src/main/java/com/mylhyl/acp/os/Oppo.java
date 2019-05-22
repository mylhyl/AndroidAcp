package com.mylhyl.acp.os;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by hupei on 2019/5/21 20:36.
 */
public class Oppo implements SettingPage {

    private Context context;

    public Oppo(Context context) {
        this.context = context;
    }

    @Override
    public Intent createIntent() throws PackageManager.NameNotFoundException {

        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.putExtra(EXTRA_PKG_NAME, context.getPackageName());
        intent.putExtra(EXTRA_PKG, context.getPackageName());

        intent.setClassName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }

        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity");
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }

        intent.setClassName("com.coloros.safecenter"
                , "com.coloros.safecenter.permission.singlepage.PermissionSinglePageActivity");
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }


        intent.setClassName("com.coloros.securitypermission"
                , "com.coloros.securitypermission.permission.PermissionGroupsActivity");
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }

        intent.setClassName("com.coloros.securitypermission"
                , "com.coloros.securitypermission.permission.PermissionAppAllPermissionActivity");
        if (OsHelper.isIntentAvailable(context, intent) && OsHelper.isActivityExported(context, intent)) {
            return intent;
        }
        return null;
    }
}
