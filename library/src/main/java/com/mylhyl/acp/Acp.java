package com.mylhyl.acp;

import android.content.Context;

/**
 * Created by hupei on 2016/4/26.
 */
public final class Acp {
    private volatile boolean mInitialized;
    private AcpManager mAcpManager;

    public static Acp getInstance(Context context) {
        Acp instance = HolderClass.instance;
        instance.init(context);
        return instance;
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

    AcpManager getAcpManager() {
        return mAcpManager;
    }

    private void init(Context context) {
        if (mInitialized) {
            return;
        }
        mAcpManager = new AcpManager(context.getApplicationContext());
        mInitialized = true;
    }

    private static class HolderClass {
        private static final Acp instance = new Acp();
    }
}
