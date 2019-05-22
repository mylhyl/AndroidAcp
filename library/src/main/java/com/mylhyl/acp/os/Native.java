package com.mylhyl.acp.os;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by hupei on 2019/5/21 20:14.
 */
public class Native implements SettingPage {

    private Context context;

    public Native(Context context) {
        this.context = context;
    }

    @Override
    public Intent createIntent() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts(EXTRA_PKG, context.getPackageName(), null);
        intent.setData(uri);
        return intent;
    }
}
