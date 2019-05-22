package com.mylhyl.acp.os;

import android.content.Intent;
import android.content.pm.PackageManager;

/**
 * Created by hupei on 2019/5/21 20:03.
 */
public interface SettingPage {

    String EXTRA_PKG = "package";
    String EXTRA_PKG_NAME = "packagename";

    Intent createIntent() throws PackageManager.NameNotFoundException;

}
