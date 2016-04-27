package com.mylhyl.acp.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.mylhyl.takephoto.TakePhotoUtil;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    static final int READ_PHONE_STATE_REQUEST_CODE = 0x38;
//    static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0x39;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickFragment(View v) {
        BlankActivity.gotoAct(this);
    }

    public void onClickAll(View v) {
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
//                .setDeniedMessage()
//                .setDeniedCloseBtn()
//                .setDeniedSettingBtn()
//                .setRationalMessage()
//                .setRationalBtn()
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        writeSD();
                        getIMEI();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        makeText(permissions.toString() + "权限拒绝");
                    }
                });
    }

    public void onClickSd(View v) {
        //写sd
//                if (checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                    writeSD();
//                } else {
//                    ActivityCompat.requestPermissions(this,
//                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//                }
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        writeSD();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        makeText(permissions.toString() + "权限拒绝");
                    }
                });
    }

    public void onClickImei(View v) {
        //读imei
//                //如果权限允许则执行运输和，否则请求权限
//                if (checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)) {
//                    getIMEI();
//                } else {
//                    //处理“不再提醒”，判断是否需要 向用户解释，为什么要申请该权限
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
//                        new AlertDialog.Builder(this).setMessage("需要申请imei")
//                                .setPositiveButton("申请", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        ActivityCompat.requestPermissions(MainActivity.this,
//                                                new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST_CODE);
//                                    }
//                                }).create().show();
//                    } else {
//                        //请求权限
//                        ActivityCompat.requestPermissions(this,
//                                new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE_REQUEST_CODE);
//                    }
//                }
        Acp.getInstance(this).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.READ_PHONE_STATE)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        getIMEI();
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        makeText(permissions.toString() + "权限拒绝");
                    }
                });

    }


//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        //用户选择允许或拒绝后，都会回调此方法
//        switch (requestCode) {
//            case READ_PHONE_STATE_REQUEST_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {// 权限允许 Granted
//                    getIMEI();
//                } else {// 权限拒绝 Denied
//                    makeText("读imei权限拒绝");
//                }
//                break;
//            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    writeSD();
//                } else {
//                    makeText("写SD权限拒绝");
//                }
//                break;
//        }
//    }

//    private boolean checkSelfPermission(Context context, String permission) {
//        //检查权限
//        int permissionCheck = ActivityCompat.checkSelfPermission(context, permission);
//        return PackageManager.PERMISSION_GRANTED == permissionCheck;
//    }

    private void writeSD() {
        File acpDir = TakePhotoUtil.getCacheDir("acp", this);
        if (acpDir != null)
            makeText("写SD成功：" + acpDir.getAbsolutePath());
    }

    private void getIMEI() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (tm != null)
            makeText("读imei成功：" + tm.getDeviceId());
    }

    private void makeText(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }

}