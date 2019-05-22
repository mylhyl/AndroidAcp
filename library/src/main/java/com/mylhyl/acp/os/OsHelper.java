package com.mylhyl.acp.os;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.List;

/**
 * Created by hupei on 2019/5/21 20:26.
 */
public final class OsHelper {
    private static final String OS_HUAWEI = "HUAWEI";
    private static final String OS_XIAOMI = "XiaoMi";
    private static final String OS_OPPO = "Oppo";
    private static final String OS_VIVO = "vivo";
    private static final String OS_MEIZU = "meizu";
    private static final String OS_NATIVE = Build.MANUFACTURER;

    private OsHelper() {
        //no instance
    }

    public static Intent getIntentNative(Context context) {
        return new Native(context).createIntent();
    }

    public static void startSetting(Activity activity, int requestCode) {
        Intent intent = null;
        try {
            SettingPage settingPage = null;
            if (OS_HUAWEI.equalsIgnoreCase(OS_NATIVE)) {
                settingPage = new HuaWei(activity);
            } else if (OS_OPPO.equalsIgnoreCase(OS_NATIVE)) {
                settingPage = new Oppo(activity);
            } else if (OS_VIVO.equalsIgnoreCase(OS_NATIVE)) {
                settingPage = new Vivo(activity);
            } else if (OS_XIAOMI.equalsIgnoreCase(OS_NATIVE)) {
                settingPage = new XiaoMi(activity);
            } else if (OS_MEIZU.equalsIgnoreCase(OS_NATIVE)) {
                settingPage = new MeiZu(activity);
            }
            intent = settingPage.createIntent();
        } catch (Exception e) {
            Log.e("acp", "OsHelper startSetting " + OS_NATIVE + " " + e.getMessage());
        }
        if (intent == null) {
            intent = getIntentNative(activity);
        }
        try {
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Log.e("acp", "OsHelper startActivityForResult " + OS_NATIVE + " " + e.getMessage());
            intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            activity.startActivityForResult(intent, requestCode);
        }
    }

    static boolean isAndroidL() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }

    /**
     * 判断 activity 是否存在
     *
     * @param context
     * @param intent
     * @return
     */
    static boolean isIntentAvailable(Context context, Intent intent) {
        if (intent == null) return false;
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent
                , PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.size() > 0;
    }

    static boolean isActivityExported(Context context, Intent intent) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        ActivityInfo activityInfo = packageManager.getActivityInfo(intent.getComponent()
                , PackageManager.MATCH_DEFAULT_ONLY);
        return activityInfo != null && !activityInfo.exported;
    }
}
