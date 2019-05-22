package com.mylhyl.acp.os;

import android.content.ActivityNotFoundException;
import android.content.Intent;

/**
 * Created by hupei on 2019/5/21 20:03.
 */
public interface SettingPage {

    String EXTRA_PKG = "package";
    String EXTRA_PKG_NAME = "packagename";

    Intent createIntent() throws ActivityNotFoundException;

}
