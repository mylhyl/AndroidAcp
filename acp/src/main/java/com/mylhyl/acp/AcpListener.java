package com.mylhyl.acp;

import java.util.List;

/**
 * Created by hupei on 2016/4/26.
 */
public interface AcpListener {
    void onGranted();

    void onDenied(List<String> permissions);
}
