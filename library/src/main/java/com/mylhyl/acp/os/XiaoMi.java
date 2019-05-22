package com.mylhyl.acp.os;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hupei on 2019/5/21 20:41.
 */
public class XiaoMi implements SettingPage {

    private static final String PKG = "com.miui.securitycenter";
    private static final String EXTRA_MIUI_PKG = "extra_pkgname";
    private static final String MIUI_ACTION = "miui.intent.action.APP_PERM_EDITOR";
    private static final String MIUI8_MAIN_CLS = "com.miui.securityscan.MainActivity";
    private static final String MIUI_MAIN_CLS = "com.miui.permcenter.permissions.AppPermissionsEditorActivity";
    private static final String MIUI_COMMAND_VERSION = "getprop ro.miui.ui.version.name";

    private Context context;

    public XiaoMi(Context context) {
        this.context = context;
    }

    private static String getSystemProperty() {
        String line = "";
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec(MIUI_COMMAND_VERSION);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    @Override
    public Intent createIntent() throws ActivityNotFoundException {
        Intent intent = new Intent();
        String systemProperty = getSystemProperty();
        if (systemProperty.contains("7") || systemProperty.contains("6") || systemProperty.contains("5")) {
            intent = new Intent(MIUI_ACTION);
            intent.setComponent(new ComponentName(PKG, MIUI_MAIN_CLS));
            intent.putExtra(EXTRA_MIUI_PKG, context.getPackageName());
        } else {
            // miui 8
            intent.putExtra(EXTRA_PKG, context.getPackageName());
            intent.setComponent(new ComponentName(PKG, MIUI8_MAIN_CLS));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
}
