package com.mylhyl.acp;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

/**
 * Created by hupei on 2016/4/26.
 */
final class AcpService {
    private AcpService() {
        //no instance
    }
    private static final String TAG = "AcpService";

    /**
     * 检查权限授权状态
     *
     * @param context
     * @param permission
     * @return
     */
    static int checkSelfPermission(Context context, String permission) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    Log.i(TAG, "targetSdkVersion >= Build.VERSION_CODES.M");
                    return ContextCompat.checkSelfPermission(context, permission);
                } else {
                    return PermissionChecker.checkSelfPermission(context, permission);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return ContextCompat.checkSelfPermission(context, permission);
    }

    /**
     * 向系统请求权限
     *
     * @param activity
     * @param permissions
     * @param requestCode
     */
    static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    /**
     * 检查权限是否存在拒绝不再提示
     *
     * @param activity
     * @param permission
     * @return
     */
    static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        boolean shouldShowRational = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        Log.i(TAG, "shouldShowRational = " + shouldShowRational);
        return shouldShowRational;
    }
}
