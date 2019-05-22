package com.mylhyl.acp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.mylhyl.acp.os.OsHelper;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by hupei on 2016/4/26.
 */
class AcpManager {
    private static final String TAG = "AcpManager";
    private static final int REQUEST_CODE_PERMISSION = 0x38;
    private static final int REQUEST_CODE_SETTING = 0x39;
    private final List<String> mDeniedPermissions = new LinkedList<>();
    private final Set<String> mManifestPermissions = new HashSet<>(1);
    private Context appContext;
    private Activity internalActivity;
    private AcpOptions mOptions;
    private AcpListener mCallback;

    AcpManager(Context context) {
        appContext = context;
        getManifestPermissions();
    }

    /**
     * 开始请求
     *
     * @param options
     * @param acpListener
     */
    synchronized void request(AcpOptions options, AcpListener acpListener) {
        mCallback = acpListener;
        mOptions = options;
        checkSelfPermission();
    }

    /**
     * 检查权限是否存在拒绝不再提示
     *
     * @param activity
     */
    synchronized void checkRequestPermissionRationale(Activity activity) {
        internalActivity = activity;
        boolean rationale = false;
        //如果有拒绝则提示申请理由提示框，否则直接向系统请求权限
        for (String permission : mDeniedPermissions) {
            rationale = rationale || AcpService.shouldShowRequestPermissionRationale(internalActivity, permission);
        }
        Log.i(TAG, "rationale = " + rationale);
        String[] permissions = mDeniedPermissions.toArray(new String[mDeniedPermissions.size()]);
        if (rationale) showRationalDialog(permissions);
        else requestPermissions(permissions);
    }

    /**
     * 响应向系统请求权限结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    synchronized void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION:
                LinkedList<String> grantedPermissions = new LinkedList<>();
                LinkedList<String> deniedPermissions = new LinkedList<>();
                for (int i = 0; i < permissions.length; i++) {
                    String permission = permissions[i];
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                        grantedPermissions.add(permission);
                    else deniedPermissions.add(permission);
                }
                //全部允许才回调 onGranted 否则只要有一个拒绝都回调 onDenied
                if (!grantedPermissions.isEmpty() && deniedPermissions.isEmpty()) {
                    if (mCallback != null)
                        mCallback.onGranted();
                    onDestroy();
                } else if (!deniedPermissions.isEmpty()) showDeniedDialog(deniedPermissions);
                break;
        }
    }

    /**
     * 响应设置权限返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    synchronized void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallback == null || mOptions == null
                || requestCode != REQUEST_CODE_SETTING) {
            onDestroy();
            return;
        }
        checkSelfPermission();
    }

    private synchronized void getManifestPermissions() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = appContext.getPackageManager().getPackageInfo(
                    appContext.getPackageName(), PackageManager.GET_PERMISSIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            String[] permissions = packageInfo.requestedPermissions;
            if (permissions != null) {
                for (String perm : permissions) {
                    mManifestPermissions.add(perm);
                }
            }
        }
    }

    /**
     * 检查权限
     */
    private synchronized void checkSelfPermission() {
        mDeniedPermissions.clear();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.i(TAG, "Build.VERSION.SDK_INT < Build.VERSION_CODES.M");
            if (mCallback != null)
                mCallback.onGranted();
            onDestroy();
            return;
        }
        String[] permissions = mOptions.getPermissions();
        for (String permission : permissions) {
            //检查申请的权限是否在 AndroidManifest.xml 中
            if (mManifestPermissions.contains(permission)) {
                int checkSelfPermission = AcpService.checkSelfPermission(appContext, permission);
                Log.i(TAG, "checkSelfPermission = " + checkSelfPermission);
                //如果是拒绝状态则装入拒绝集合中
                if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                    mDeniedPermissions.add(permission);
                }
            }
        }
        //检查如果没有一个拒绝响应 onGranted 回调
        if (mDeniedPermissions.isEmpty()) {
            Log.i(TAG, "mDeniedPermissions.isEmpty()");
            if (mCallback != null)
                mCallback.onGranted();
            onDestroy();
            return;
        }
        startAcpActivity();
    }

    /**
     * 启动处理权限过程的 Activity
     */
    private synchronized void startAcpActivity() {
        Intent intent = new Intent(appContext, AcpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
    }

    /**
     * 申请理由对话框
     *
     * @param permissions
     */
    private synchronized void showRationalDialog(final String[] permissions) {
        AlertDialog alertDialog = new AlertDialog.Builder(internalActivity)
                .setMessage(mOptions.getRationalMessage())
                .setPositiveButton(mOptions.getRationalBtnText(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(permissions);
                    }
                }).create();
        alertDialog.setCancelable(mOptions.isDialogCancelable());
        alertDialog.setCanceledOnTouchOutside(mOptions.isDialogCanceledOnTouchOutside());
        alertDialog.show();
    }

    /**
     * 向系统请求权限
     *
     * @param permissions
     */
    private synchronized void requestPermissions(String[] permissions) {
        AcpService.requestPermissions(internalActivity, permissions, REQUEST_CODE_PERMISSION);
    }

    /**
     * 拒绝权限提示框
     *
     * @param permissions
     */
    private synchronized void showDeniedDialog(final List<String> permissions) {
        AlertDialog alertDialog = new AlertDialog.Builder(internalActivity)
                .setMessage(mOptions.getDeniedMessage())
                .setNegativeButton(mOptions.getDeniedCloseBtn(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (mCallback != null)
                            mCallback.onDenied(permissions);
                        onDestroy();
                    }
                })
                .setPositiveButton(mOptions.getDeniedSettingBtn(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startSetting();
                    }
                }).create();
        alertDialog.setCancelable(mOptions.isDialogCancelable());
        alertDialog.setCanceledOnTouchOutside(mOptions.isDialogCanceledOnTouchOutside());
        alertDialog.show();
    }

    /**
     * 摧毁本库的 AcpActivity
     */
    private void onDestroy() {
        if (internalActivity != null) {
            internalActivity.finish();
            internalActivity = null;
        }
        mCallback = null;
    }

    /**
     * 跳转到设置界面
     */
    private void startSetting() {
        OsHelper.startSetting(internalActivity,REQUEST_CODE_SETTING);
    }
}
