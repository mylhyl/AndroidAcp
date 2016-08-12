package com.mylhyl.acp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by hupei on 2016/4/26.
 */
public class Acp {

    private static Acp mInstance;
    private AcpManager mAcpManager;

    public static Acp getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Acp(context);
        }
        return mInstance;
    }

    private Acp(Context context) {
        mAcpManager = new AcpManager(context.getApplicationContext());
    }

    /**
     * 开始请求
     *
     * @param options
     * @param acpListener
     */
    public void request(AcpOptions options, AcpListener acpListener) {
        if (options == null) new NullPointerException("AcpOptions is null...");
        if (acpListener == null) new NullPointerException("AcpListener is null...");
        mAcpManager.request(options, acpListener);
    }

    /**
     * 响应向系统请求权限结果
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mAcpManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 响应设置权限返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data) {
        mAcpManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 检查权限是否存在拒绝不再提示
     *
     * @param activity
     */
    void checkRequestPermissionRationale(Activity activity) {
        mAcpManager.checkRequestPermissionRationale(activity);
    }
}
